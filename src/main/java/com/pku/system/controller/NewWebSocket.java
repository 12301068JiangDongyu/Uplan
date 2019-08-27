package com.pku.system.controller;

//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。

import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.util.*;
import com.pku.system.util.TimerTask;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 类似Servlet的注解mapping。无需在web.xml中配置。
 * ownId为发起人
 */
@ServerEndpoint(value = "/websocket/{ownId}")
@Component
public class NewWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private String ownId;
    DealMessage dealMessage = new DealMessage();

    Time time  = new Time();

    public static List<WSocketMessage> wSocketMessageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> wSocketMessageListCenter = new ArrayList<WSocketMessage>();
    Map<String, String> dic = new HashMap<String, String>(){{ put("camera", "摄像头"); put("computer", "电脑");put("projector", "投影仪"); }};

    DeviceInfoService deviceInfoService;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    // 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, HashMap<String, NewWebSocket>> webSocketHashMap = new ConcurrentHashMap<String, HashMap<String, NewWebSocket>>();

    //与客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    public NewWebSocket() {

    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("ownId") String ownId, Session session){
        this.session = session;
        this.ownId = ownId;

        HashMap<String, NewWebSocket> hashMap = null;

        if (!webSocketHashMap.containsKey(ownId)) {
            hashMap = new HashMap<String, NewWebSocket>();
            hashMap.put(session.getId(), this);
            webSocketHashMap.put(ownId, hashMap);
        } else {
            hashMap = webSocketHashMap.get(ownId);
            hashMap.clear();
            hashMap.put(session.getId(), this);
        }
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        delUserWebSocket(ownId, this.session.getId());
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        System.out.println(webSocketHashMap);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param ownId 教学楼_教室
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(@PathParam("ownId") String ownId, String message, Session session) {
        System.out.println("来自客户端的消息:" + ownId+" "+message);

        deviceInfoService = applicationContext.getBean(DeviceInfoService.class);

        //解析message，存入列表中
        ParseData data = new ParseData();
        data.parsrJson(message);

        String[] info = ownId.split("_");
        DeviceInfo deviceInfo = deviceInfoService.selectByBuildingClassroom(info[0],info[1]);

        String msg = data.smessage;
        String[] msgp = msg.split("_");//字符串截取

        List<WSocketMessage> list = DeviceMonitorController.messageList;
        List<WSocketMessage> videoList = VideoController.messageList;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOwnId().equals(ownId))
            {
                list.remove(i);//一旦客户端可正确返回消息，则清除之前的所有的信息
                i--;
            }
        }
        for(int i=0;i<videoList.size();i++){
            if(videoList.get(i).getOwnId().equals(ownId))
            {
                videoList.remove(i);//一旦客户端可正确返回消息，则清除之前的所有的信息
                i--;
            }
        }

        deviceInfo.setRaspberryStatus(1);//并将树莓派状态置为在线
        deviceInfo.setRaspberryStreamStatus(1);//在线，空闲

        //解析设备管理
        if(msg.contains("open")||msg.contains("close")){
            dealMessage.deviceOperation(msgp,deviceInfo,msg,ownId,wSocketMessageList,dic,wSocketMessageListCenter);
        }
        //解析推拉流
        if(msg.contains("push")||msg.contains("pull")){
            dealMessage.streamOperation(msgp,deviceInfo,msg,ownId,wSocketMessageList,wSocketMessageListCenter);
        }

        deviceInfoService.updateDeviceInfoStatus(deviceInfo);

        sendDeviceMessageToOne(time.getCurrentTime(),ownId,"receive");
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 广播消息
     * @param message 客户端发送过来的消息
     */
    public void sendMessageToAll(String id,String message){
        Collection<HashMap<String, NewWebSocket>> hashMapCollection = webSocketHashMap.values();
        Iterator ite = hashMapCollection.iterator();
        while(ite.hasNext()) {
            HashMap<String, NewWebSocket> hashMap = (HashMap<String, NewWebSocket>) ite.next();
            System.out.println(hashMap.size());
            for (Map.Entry<String, NewWebSocket> item : hashMap.entrySet()) {
                try {
                    item.getValue().sendMessage("{\n" +
                            "  \"code\": 0,\n" +
                            "  \"msg\": \"success\",\n" +
                            "  \"data\": {\n" +
                            "    \"id\":\"" + id+ "\",\n" +
                            "    \"message\":\"" + message + "\"\n" +
                            "  }\n" +
                            "}");
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    /**
     * 指定用户发送设备管理消息
     * @param ownId 客户端编号
     * @param message 客户端发送过来的消息
     */
    public void sendDeviceMessageToOne(String id,String ownId,String message){
        for (Map.Entry<String, NewWebSocket> item : getUserWebSocket(ownId).entrySet()) {
            try {
                item.getValue().sendMessage("{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"success\",\n" +
                        "  \"data\": {\n" +
                        "    \"id\":\"" + id+ "\",\n" +
                        "    \"ownId\":\"" + ownId + "\",\n" +
                        "    \"message\":\"" + message + "\"\n" +
                        "  }\n" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    /**
     * 指定用户发送推拉流消息
     * @param ownId 客户端编号
     * @param message 客户端发送过来的消息
     */
    public void sendMessageToOne(String id, String ownId,String address,String message){
        for (Map.Entry<String, NewWebSocket> item : getUserWebSocket(ownId).entrySet()) {
            try {
                item.getValue().sendMessage("{\n" +
                        "  \"code\": 0,\n" +
                        "  \"msg\": \"success\",\n" +
                        "  \"data\": {\n" +
                        "    \"id\":\"" + id+ "\",\n" +
                        "    \"address\":\"" + address + "\",\n" +
                        "    \"message\":\"" + message + "\"\n" +
                        "  }\n" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getAsyncRemote().sendText(message);
    }


    public void delUserWebSocket(String ownId, String sessionId) {
        String key = ownId;
        if (webSocketHashMap.containsKey(key)) {
            HashMap<String, NewWebSocket> hashMap = webSocketHashMap.get(key);
            hashMap.remove(sessionId);
            webSocketHashMap.put(key, hashMap);
            if (webSocketHashMap.get(key).size() == 0) {
                webSocketHashMap.remove(key);
            }
        }
    }

    public HashMap<String, NewWebSocket> getUserWebSocket(String ownId) {

        String key = ownId;

        HashMap<String, NewWebSocket> hashMap = new HashMap<String, NewWebSocket>();

        if (webSocketHashMap.containsKey(key)) {
            hashMap = webSocketHashMap.get(key);
        }
        return hashMap;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        NewWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        NewWebSocket.onlineCount--;
    }

}
