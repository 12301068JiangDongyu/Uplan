package com.pku.system.util;

import com.pku.system.controller.NewWebSocket;
import com.pku.system.model.DeviceInfo;
import com.pku.system.model.WSocketMessage;
import com.pku.system.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TimerTask {

    public static List<WSocketMessage> messageList = new ArrayList<WSocketMessage>();
    public static List<WSocketMessage> messageListCenter = new ArrayList<WSocketMessage>();

    @Autowired
    DeviceInfoService deviceInfoService;

    List<DeviceInfo> deviceInfoList;
    DealMessage dealMessage = new DealMessage();

    Time time = new Time();

    /**
     * 每天0点启动任务
     */
    @Scheduled(cron = "0 0 0 ? * *")
    public void clear()
    {
        NewWebSocket.wSocketMessageListCenter.clear();
        System.out.println("清空消息中心！");
    }

    /**
     * 每5min启动任务
     */
    @Scheduled(cron = "* */30 * * * ?")
    public void detect()
    {
        String id = time.getCurrentTime();

        NewWebSocket nbs = new NewWebSocket();

        deviceInfoList = deviceInfoService.getAllDeviceInfoStatus();

        for(int i=0;i<deviceInfoList.size();i++){
            nbs.sendDeviceMessageToOne(id+i,deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(), Constant.DETECTONLINE);
            dealMessage.addMessageList(deviceInfoList.get(i).getBuildingNum()+"_"+deviceInfoList.get(i).getClassroomNum(),deviceInfoList.get(i).getBuildingNum()+deviceInfoList.get(i).getClassroomNum(),messageList,deviceInfoList.get(i),messageListCenter);
        }

        long curTime = time.getCurrentTimeRough();

        for(int j=0;j<deviceInfoList.size();j++){//每个树莓派
            for(int i=0;i<messageList.size();i++){
                if(messageList.get(i).getOwnId().equals(deviceInfoList.get(j).getBuildingNum()+"_"+deviceInfoList.get(j).getClassroomNum()))
                {
                    long timeDelay = curTime-Long.parseLong(messageList.get(i).getNowTime());

                    if(timeDelay < Constant.MESSAGETIMEOUT){
//                        System.out.println("第一条记录未到时间");
                        break;
                    }else if(deviceInfoList.get(j).getRaspberryStatus() == 2){
                        System.out.println("树莓派已异常");
                        break;
                    }
                    else{
                        dealMessage.addMessageList("fail","all","树莓派异常",messageList.get(i).getOwnId(),deviceInfoList.get(j).getBuildingNum()+deviceInfoList.get(j).getClassroomNum(),NewWebSocket.wSocketMessageList,deviceInfoList.get(j),NewWebSocket.wSocketMessageListCenter);
                        deviceInfoList.get(j).setRaspberryStatus(2);
                        deviceInfoList.get(j).setRaspberryStreamStatus(2);
                        deviceInfoService.updateDeviceInfoStatus(deviceInfoList.get(j));
                        System.out.println("树莓派异常");
                        break;
                    }

                }
            }


        }
    }
}
