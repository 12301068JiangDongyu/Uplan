package com.pku.system.controller;

import com.pku.system.model.Camera;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.CameraService;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.util.Constant;
import com.pku.system.util.DealMessage;
import com.pku.system.util.Time;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.pku.system.controller.NewWebSocket.wSocketMessageList;
import static com.pku.system.controller.NewWebSocket.wSocketMessageListCenter;

@Api(value="远程设备管理",tags = {"远程设备管理API"},description = "描述信息")
@RestController
@RequestMapping("/deviceMonitor")// 通过这里配置使下面的映射都在/deviceMonitor下
public class DeviceMonitorController {
    //自动注入业务层的deviceInfoService类
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    CameraService cameraService;

    Time time = new Time();

    DeviceInfo deviceInfo;
    Camera camera;
    List<DeviceInfo> deviceInfoList;
    DealMessage dealMessage = new DealMessage();

    public static List<WSocketMessage> messageList = new ArrayList<>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<>();


    @ApiOperation(value = "获得设备管理列表", notes = "获得设备管理列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllDeviceMonitor(){
        // 处理"/deviceMonitor/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<DeviceInfo> deviceStatusList = deviceInfoService.getAllDeviceInfoStatus();

        for(int i = 0;i < deviceStatusList.size();i++){
            System.out.println(deviceStatusList.get(i).getId());
            List<Camera> cameraList = cameraService.selectByDeviceId(deviceStatusList.get(i).getId());
            deviceStatusList.get(i).setCameraList(cameraList);

            jsonArray.add(deviceStatusList.get(i));
            jsonData.put("deviceStatusList",jsonArray);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 根据did获取为教室分配设备信息
     * @param did
     */
    @ApiOperation(value = "根据did获取为教室分配设备信息", notes = "根据did获取为教室分配设备信息notes", produces = "application/json")
    @RequestMapping(value="/device/{did}", method= RequestMethod.GET)
    public String getAddClassroomInfo(@PathVariable(value="did")int did){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        DeviceInfo deviceInfo = deviceInfoService.selectById(did);

        if(deviceInfo==null){
            jsonData.put("judge","-1");
        }else{
            try{
                List<Camera> cameraList = cameraService.selectByDeviceId(deviceInfo.getId());
                deviceInfo.setCameraList(cameraList);
                jsonData.put("judge","0");
                jsonData.put("deviceInfo",deviceInfo);

            }catch (DataAccessException e){
                //修改失败
                jsonData.put("judge","-9");
            }
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * open_computer,close_computer,open_projector,close_projector,open_all,close_all
     * @param did
     * @param device
     * @param operation
     * @return
     */
    @ApiOperation(value = "根据id发送修改单个教室设备状态消息", notes = "根据id发送修改单个教室设备状态消息notes", produces = "application/json")
    @RequestMapping(value="/{did}", method= RequestMethod.GET)
    public String sendDeviceMonitorSingle(@PathVariable("did") int did,
                                          @RequestParam(value="device")String device,
                                          @RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        System.out.println(did+" "+device);
        deviceInfo = deviceInfoService.selectById(did);
        //List<Camera> cameraList = cameraService.selectByDeviceId(did);//根据did获得所有摄像头

        NewWebSocket nbs = new NewWebSocket();

        String orderOpen = Constant.OPEN+"_"+device;
        String orderClose = Constant.CLOSE+"_"+device;

        String id = time.getCurrentTime();

        //Long timeBefore = time.getCurrentTimeRough();

        if(device.length()!=0 && !device.equals("camera")){//操作单个设备，不包含摄像头
            nbs.sendDeviceMessageToOne(id,deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),
                    operation.equals("close")?orderClose:orderOpen);//在线时，可以将设备关闭；离线或异常时，可开启
        }else{
            //根据前端返回的信息，判断是开启全部，还是关闭全部
            nbs.sendDeviceMessageToOne(id,deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),
                    operation.equals("open")?Constant.OPENALL:Constant.CLOSEALL);
        }

        dealMessage.addMessageList(deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),messageList,deviceInfo,messageListCenter);

//        while(true){
//
//            if(messageMap.get(id) == null){//消息未返回
//                if(time.getCurrentTimeRough()-timeBefore < Constant.MESSAGETIMEOUT){//未超时
//                   continue;
//
//                }else{//超时
//                    deviceInfo.setRaspberryStatus(2);
//                    deviceInfo.setRaspberryStreamStatus(2);
//
//                    deviceInfo.setCameraList(cameraList);
//
//                    deviceInfoService.updateDeviceInfoStatus(deviceInfo);
//
//                    wSocketMessageReturn.setJudge("offline");
//                    wSocketMessageReturn.setMessage("树莓派异常");
//                }
//
//            }else{
//                try{
//                    String[] msgp = messageMap.get(id).split("_");//树莓派返回消息字符串截取
//
//                    //解析设备管理
//                    if(messageMap.get(id).contains("open")||messageMap.get(id).contains("close")){
//                        wSocketMessageReturn = dealMessage.deviceOperation(msgp,deviceInfo,messageMap.get(id),deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),wSocketMessageList,dic,wSocketMessageListCenter,cameraList);
//                    }
//
//                    deviceInfoService.updateDeviceInfoStatus(deviceInfo);
//
//                    for(Camera camera:cameraList){
//                        cameraService.updateCamera(camera);
//                    }
//
//                    deviceInfo.setCameraList(cameraList);
//
//                    //修改成功
//                    jsonData.put("judge","0");
//                }catch (DataAccessException e){
//                    //修改失败
//                    jsonData.put("judge","-9");
//                }
//            }

//            jsonData.put("wSocketMessage",wSocketMessageReturn);
//            jsonData.put("deviceInfo",deviceInfo);
//
//            jsonObject.put("data",jsonData);
//            return jsonObject.toString();
//        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }

    @ApiOperation(value = "根据id发送修改单个教室摄像头状态消息", notes = "根据id发送修改单个教室摄像头状态消息notes", produces = "application/json")
    @RequestMapping(value="/camera/{cid}", method= RequestMethod.GET)
    public String sendCameraMonitorSingle(@PathVariable("cid") int cid,
                                          @RequestParam(value="code")int code,
                                          @RequestParam(value="did")int did,
                                          @RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        System.out.println(cid);

        camera = cameraService.selectById(cid);

        deviceInfo = deviceInfoService.selectById(did);
//        List<Camera> cameraList = cameraService.selectByDeviceId(did);//根据did获得所有摄像头

        NewWebSocket nbs = new NewWebSocket();

        String id = time.getCurrentTime();

//        Long timeBefore = time.getCurrentTimeRough();

        String orderOpen = Constant.OPEN+"_camera_"+code;
        String orderClose = Constant.CLOSE+"_camera_"+code;

        nbs.sendDeviceMessageToOne(id,deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),
                operation.equals("close")?orderClose:orderOpen);//在线时，可以将设备关闭；离线或异常时，可开启

        dealMessage.addMessageList(deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),deviceInfo.getBuildingNum()+deviceInfo.getClassroomNum(),messageList,deviceInfo,messageListCenter);

//        while(true){
//            if(messageMap.get(id) == null){
//                if(time.getCurrentTimeRough()-timeBefore < Constant.MESSAGETIMEOUT){//未超时
//                    continue;
//
//                }else{//超时
//                    deviceInfo.setRaspberryStatus(2);
//                    deviceInfo.setRaspberryStreamStatus(2);
//
//                    deviceInfo.setCameraList(cameraList);
//
//                    deviceInfoService.updateDeviceInfoStatus(deviceInfo);
//
//                    wSocketMessageReturn.setMessage("树莓派异常");
//                    wSocketMessageReturn.setJudge("offline");
//                }
//
//            }else{
//                try{
//                    String[] msgp = messageMap.get(id).split("_");//树莓派返回消息字符串截取
//                    wSocketMessageReturn = dealMessage.deviceOperation(msgp,deviceInfo,messageMap.get(id),deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),wSocketMessageList,dic,wSocketMessageListCenter,cameraList);
//
//                    deviceInfoService.updateDeviceInfoStatus(deviceInfo);
//
//                    for(Camera camera:cameraList){
//                        cameraService.updateCamera(camera);
//                    }
//
//                    deviceInfo.setCameraList(cameraList);
//
//                    //修改成功
//                    jsonData.put("judge","0");
//                }catch (DataAccessException e){
//                    //修改失败
//                    jsonData.put("judge","-9");
//                }
//            }

//            jsonData.put("wSocketMessage",wSocketMessageReturn);
//            jsonData.put("deviceInfo",deviceInfo);
//
//            jsonObject.put("data",jsonData);
//            return jsonObject.toString();
//        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }

    @ApiOperation(value = "发消息修改全部教室设备状态", notes = "发消息修改全部教室设备状态notes", produces = "application/json")
    @RequestMapping(value="/all", method= RequestMethod.GET)
    public String sendDeviceMonitorAll(@RequestParam(value="operation")String operation) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        NewWebSocket nbs = new NewWebSocket();

        deviceInfoList = deviceInfoService.getAllDeviceInfoStatus();

        String id = time.getCurrentTime();

        for(int i=0;i<deviceInfoList.size();i++){
            nbs.sendDeviceMessageToOne(id+i,deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(),
                    operation.equals("open")?Constant.OPENALL:Constant.CLOSEALL);

            dealMessage.addMessageList(deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(),deviceInfoList.get(i).getBuildingNum()+deviceInfoList.get(i).getClassroomNum(),messageList,deviceInfoList.get(i),messageListCenter);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();


    }


}
