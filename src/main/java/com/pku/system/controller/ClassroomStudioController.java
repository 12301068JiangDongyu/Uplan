package com.pku.system.controller;

import com.pku.system.model.ClassroomStudio;
import com.pku.system.model.DeviceInfo;
import com.pku.system.service.DeviceInfoService;
import com.pku.system.util.Constant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(value="空中教室",tags = {"空中教室API"},description = "描述信息")
@RestController
@RequestMapping(value = "/classroomStudio")
public class ClassroomStudioController {
    @Autowired
    DeviceInfoService deviceInfoService;

    @ApiOperation(value = "获得正在推流的教室的拉流地址", notes = "获得正在推流的教室的拉流地址notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllPushClassroomPullAddress() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        ClassroomStudio classroomStudio = new ClassroomStudio();
        List<DeviceInfo> raspberryStreamStatusList = deviceInfoService.getPushClassroomList(3);//获得正在推流的教学楼的信息

        for(DeviceInfo deviceInfo:raspberryStreamStatusList){
            try {
                List<String> addressList = new ArrayList<>();

                String a = Constant.STREAMADDRESSLIVE+ URLEncoder.encode(deviceInfo.getBuildingNum()+"_"+deviceInfo.getClassroomNum(),"utf-8");
                addressList.add(a.replaceAll("%","0")+"_1");
                addressList.add(a.replaceAll("%","0")+"_2");
                addressList.add(a.replaceAll("%","0")+"_3");

                classroomStudio.setBuildingName(deviceInfo.getBuildingNum());
                classroomStudio.setClassroomName(deviceInfo.getClassroomNum());
                classroomStudio.setAddressList(addressList);

                jsonArray.add(classroomStudio);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        jsonObject.put("data",jsonArray);
        return jsonObject.toString();
    }

}
