package com.pku.system.util;

import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DealMessage {
    /**
     * 添加消息到列表中
     * @param ownId
     * @param buildClass
     * @param messageList
     * @param deviceInfo
     * @param wSocketMessageListCenter
     */
    public  void addMessageList(String ownId, String buildClass, List<WSocketMessage> messageList, DeviceInfo deviceInfo, List<WSocketMessage> wSocketMessageListCenter){
        WSocketMessage wSocketMessage = new WSocketMessage();
        //获取当前系统时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String time=format.format(date);

        wSocketMessage.setNowTime(time);
        wSocketMessage.setOwnId(ownId);
        wSocketMessage.setBuildClass(buildClass);
        wSocketMessage.setDeviceInfo(deviceInfo);
        //将获取的信息加入列表
        messageList.add(wSocketMessage);
        wSocketMessageListCenter.add(wSocketMessage);
    }

    /**
     * 添加消息到列表中
     * @param judge
     * @param tab
     * @param message
     * @param ownId
     * @param buildClass
     * @param wSocketMessageList
     * @param deviceInfo
     * @param wSocketMessageListCenter
     */
    public void addMessageList(String judge,String tab,String message,String ownId,String buildClass,List<WSocketMessage> wSocketMessageList,DeviceInfo deviceInfo,List<WSocketMessage> wSocketMessageListCenter){
        WSocketMessage wSocketMessage = new WSocketMessage();
        wSocketMessage.setMessage(message);
        //获取当前系统时间
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        wSocketMessage.setNowTime(time);
        wSocketMessage.setOwnId(ownId);
        wSocketMessage.setBuildClass(buildClass);
        wSocketMessage.setJudge(judge);
        wSocketMessage.setDeviceInfo(deviceInfo);
        wSocketMessage.setTab(tab);
        //将获取的信息加入列表
        wSocketMessageList.add(wSocketMessage);
        wSocketMessageListCenter.add(wSocketMessage);
    }

    /**
     * 添加消息到列表中
     * @param judge
     * @param message
     */
    public WSocketMessage addMessageList(String judge,String message){

        WSocketMessage wSocketMessage = new WSocketMessage();

        wSocketMessage.setMessage(message);
        wSocketMessage.setJudge(judge);

        return wSocketMessage;
    }

    /**
     * 处理设备管理返回前端的消息以及数据库
     * @param msgp
     * @param deviceInfo
     * @param msg
     * @param ownId
     * @param wSocketMessageList
     * @param dic
     * @param wSocketMessageListCenter
     */
    public WSocketMessage deviceOperation(String[] msgp, DeviceInfo deviceInfo, String msg, String ownId, List<WSocketMessage> wSocketMessageList, Map<String, String> dic, List<WSocketMessage> wSocketMessageListCenter){
        //处理设备开关
        WSocketMessage wSocketMessage = new WSocketMessage();

        if(msgp[0].equals("success")){
            if(msgp[2].equals("all")){//操作全部
                addMessageList("success","device",msgp[1].equals("open")?"开启所有成功":"关闭所有成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success",msgp[1].equals("open")?"开启所有成功":"关闭所有成功");
                deviceInfo.setComputerStatus(msgp[1].equals("open")?1:0);
                deviceInfo.setProjectorStatus(msgp[1].equals("open")?1:0);
            }
            else{//处理电脑，投影仪开关
                String device = msgp[2].substring(0,1).toUpperCase()+msgp[2].substring(1);//首字母大写
                try {
                    Method method = deviceInfo.getClass().getMethod("set"+device+"Status",int.class);//反射机制
                    method.invoke(deviceInfo,msgp[1].equals("open")?1:0);//1在线，0离线
                    addMessageList("success","device",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"成功":"关闭"+dic.get(msgp[2])+"成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    wSocketMessage = addMessageList("success",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"成功":"关闭"+dic.get(msgp[2])+"成功");

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

        }else if(msgp[0].equals("fail")){

            if(msg.contains("singlechip")){//一旦收到操作单片机失败，即把其状态置为异常
                deviceInfo.setSinglechipStatus(2);
                addMessageList("fail","device","单片机异常",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("fail","单片机异常");
            }

            String returnM = msgp[1].equals("open")?"开启所有失败 ":"关闭所有失败 ";

            if(msgp[2].equals("all")){
                if(!msg.contains("computer")){
                    deviceInfo.setComputerStatus(msgp[1].equals("open")?1:0);
                    returnM +=  msgp[1].equals("open")?"开启电脑成功":"关闭电脑成功";
                }else if(msg.contains("computer")){
                    deviceInfo.setComputerStatus(2);
                }

                if(!msg.contains("projector")){
                    deviceInfo.setProjectorStatus(msgp[1].equals("open")?1:0);
                    returnM += msgp[1].equals("open")?"开启投影仪成功":"关闭投影仪成功";
                }else if(msg.contains("projector")){
                    deviceInfo.setProjectorStatus(2);
                }

                addMessageList("fail","device",returnM,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("fail",returnM);


            }else{//单一操作，即操作单个设备
                String device = msgp[2].substring(0,1).toUpperCase()+msgp[2].substring(1);//首字母大写

                try {
                    Method method = deviceInfo.getClass().getMethod("set"+device+"Status",int.class);
                    method.invoke(deviceInfo,2);
                    addMessageList("fail","device",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"失败":"关闭"+dic.get(msgp[2])+"失败",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    wSocketMessage = addMessageList("fail",msgp[1].equals("open")?"开启"+dic.get(msgp[2])+"失败":"关闭"+dic.get(msgp[2])+"失败");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


            }
        }
        return wSocketMessage;
    }

    /**
     * 处理视频管理返回前端的消息以及数据库的处理
     * @param msgp
     * @param deviceInfo
     * @param msg
     * @param ownId
     * @param wSocketMessageList
     * @param wSocketMessageListCenter
     */
    public WSocketMessage streamOperation(String[] msgp, DeviceInfo deviceInfo,String msg, String ownId, List<WSocketMessage> wSocketMessageList, List<WSocketMessage> wSocketMessageListCenter){
        WSocketMessage wSocketMessage = new WSocketMessage();
        //处理推拉流
        if(msgp[0].equals("start")){

            if(msg.contains("broadcast")){//start_push_broadcast
                addMessageList("success","video","开始广播",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success","开始广播");
                deviceInfo.setRaspberryStreamStatus(5);//正在广播
                deviceInfo.setRaspberryStatus(1);

            }else{
                //start_push,start_pull
                addMessageList("success","video",msg.contains("push")?"开始推送本教室视频":"开始播放其他教室视频",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success",msg.contains("push")?"开始推送本教室视频":"开始播放其他教室视频");
                deviceInfo.setRaspberryStreamStatus(msg.contains("push")?3:4);//开始推拉流，则把树莓派状态置为正在推流或正在拉流
                deviceInfo.setRaspberryStatus(1);
            }
        }else if(msgp[0].equals("success")){
            if(msg.contains("broadcast")){//success_push_boradcast,success_stop_push_boradcast
                addMessageList("success","video",msg.contains("stop")?"广播结束":"广播成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success",msg.contains("stop")?"广播结束":"广播成功");
                deviceInfo.setRaspberryStreamStatus(1);
                deviceInfo.setRaspberryStatus(1);
            }
            if(msgp.length==2){//success_push,success_pull
                addMessageList("success","video",msg.contains("push")?"推送本教室视频成功":"播放其他教室视频成功",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success",msg.contains("push")?"推送本教室视频成功":"播放其他教室视频成功");
                deviceInfo.setRaspberryStreamStatus(1);//成功推拉流，则把树莓派状态置为空闲（在线）
                deviceInfo.setRaspberryStatus(1);
            }else if(msgp.length==3 && msg.contains("stop")){//success_stop_push,success_stop_pull
                addMessageList("success","video",msg.contains("push")?"推送本教室视频结束":"播放其他教室视频结束",ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                wSocketMessage = addMessageList("success",msg.contains("push")?"推送本教室视频结束":"播放其他教室视频结束");
                deviceInfo.setRaspberryStreamStatus(1);//成功推拉流，则把树莓派状态置为空闲（在线）
                deviceInfo.setRaspberryStatus(1);
            }

        }else if(msgp[0].equals("fail")){
            //推流，广播，拉流判断
            if(msg.contains("stop")){//fail_stop_push,fail_stop_pull,fail_stop_push_broadcast
                if(msg.contains("broadcast")){
                    addMessageList("fail","video",Constant.FAILSTOPPUSHBROADCAST,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    wSocketMessage = addMessageList("fail",Constant.FAILSTOPPUSHBROADCAST);
                    deviceInfo.setRaspberryStreamStatus(5);//停止广播失败，则把树莓派状态置为正在广播
                    deviceInfo.setRaspberryStatus(1);
                }else{
                    addMessageList("fail","video",msg.contains("push")?Constant.FAILSTOPPUSH:Constant.FAILSTOPPULL,ownId,deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),wSocketMessageList,deviceInfo,wSocketMessageListCenter);
                    wSocketMessage = addMessageList("fail",msg.contains("push")?Constant.FAILSTOPPUSH:Constant.FAILSTOPPULL);
                    deviceInfo.setRaspberryStreamStatus(msg.contains("push")?3:4);//停止推拉流失败，则把树莓派状态置为正在推流或正在拉流
                    deviceInfo.setRaspberryStatus(1);
                }
            }

        }
        return wSocketMessage;
    }



}
