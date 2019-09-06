package com.pku.system.controller;

import com.pku.system.model.Car;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import com.pku.system.service.OfficialCarApplyService;
import com.pku.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * judge里-1表示不存在，1表示存在，0表示更新成功。
 */
@Api(value="用车审批",tags = {"用车审批API"},description = "描述信息")
@RestController
public class OfficialCarApplyController {
    @Autowired
    OfficialCarApplyService officialCarApplyService;
    @Autowired
    CarService carService;
    @Autowired
    CarTypeService carTypeService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "获取表单列表", notes = "获取所有的表单信息", produces = "application/json")
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getAllOfficialCarApply() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        //获得到所有的申请表单,
        List<OfficialCarApply> applyList = officialCarApplyService.getAllOfficialCarApply();
        //对表单进行操作后最终存入officialCarApplyList中
        if(applyList.size() == 0) {
            jsonData.put("judge", "-1");
            jsonData.put("officialCarApplyList", applyList);
        }
        else{
            for(int i = 0; i < applyList.size(); i++) {
                OfficialCarApply officialCarApply = applyList.get(i);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = null;

                String s = null;

                if(officialCarApply.getCreate_time() != null){
                    s = sdf.format(officialCarApply.getCreate_time());
                    officialCarApply.setCreateTime(s);
                }
                else
                    officialCarApply.setCreateTime("");

                if(officialCarApply.getStart_time() != null){
                    s = sdf.format(officialCarApply.getStart_time());
                    officialCarApply.setStartTime(s);
                }
                else
                    officialCarApply.setStartTime("");

                if(officialCarApply.getEnd_time() != null){
                    s = sdf.format(officialCarApply.getEnd_time());
                    officialCarApply.setEndTime(s);
                }
                else
                    officialCarApply.setEndTime("");

                if(officialCarApply.getUpdate_time() != null){
                    s = sdf.format(officialCarApply.getUpdate_time());
                    officialCarApply.setUpdateTime(s);
                }
                else
                    officialCarApply.setUpdateTime("");

                jsonData.put("officialCarApply"+i, officialCarApply);

            }
            jsonData.put("judge", "1");
        }
        jsonObject.put("data", jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "查找申请表单", notes = "输入id来查找对应的表单信息", produces = "application/json")
    @RequestMapping(value = "searchOfficialCarApply", method = RequestMethod.POST)
    public String selectOfficialCarApplyById(@RequestParam int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        OfficialCarApply officialCarApply = officialCarApplyService.selectOfficialCarApplyById(id);
//-1表示不存在
        if (officialCarApply == null)
            jsonData.put("judge", "-1");
        else
            jsonData.put("judge", "1");
        jsonData.put("officialCarApply", officialCarApply);
        jsonObject.put("data", jsonData);

        return jsonObject.toString();
    }

    @ApiOperation(value = "审核表单操作", notes = "将id为${id}的表单的status字段设置为${status}", produces = "application/json")
    @RequestMapping(value = "/check", method = {RequestMethod.POST, RequestMethod.GET})
    public String checkOfficialCarApply(@RequestParam int id, @RequestParam int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        OfficialCarApply officialCarApply = officialCarApplyService.selectOfficialCarApplyById(id);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (officialCarApply == null)
            jsonData.put("judge", "-1");
        else {
            Car car = carService.selectCarById(officialCarApply.getCar_id());

//            status：1通过，2不通过。
//            jsonData.put("judge", "1");
            if(status == 1){
                if (car.getStatus() != 1) {
//                代表车不可借。
                    jsonData.put("judge", "-2");
                }
                else{
                    java.util.Date time=null;
                    try {
                        time= sdf.parse(sdf.format(new Date()));

                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    officialCarApply.setUpdate_time(time);

                    officialCarApply.setStatus(status);
                    officialCarApplyService.updateOfficialCarApply(officialCarApply);

                    jsonData.put("judge", "1");
//                  通过某个申请后，需要将对应的车的状态也更新为使用中。
//                    int type = car.getType();
//                  1:班车，2:公车
//                    if (type == 2){
//                        //update car status.
////                      1:空闲，2:使用中，3：维修中
//                        car.setStatus(2);
//
//                        java.util.Date carUpdatetime=null;
//                        try {
//                            carUpdatetime= sdf.parse(sdf.format(new Date()));
//
//                        } catch (ParseException e) {
//
//                            e.printStackTrace();
//                        }
//                        car.setUpdate_time(carUpdatetime);
//
//                        carService.updateCarStatusById(car);
//                        jsonData.put("judge", "2");
//                    }
                }
            }
            else
                if(status == 3){
                    //这里管理员并不需要考虑这个状态。
                    System.out.println(20000);
                }
                else {
                //这里还需要更新update_time字段。
                    java.util.Date time1=null;
                    try {
                        time1= sdf.parse(sdf.format(new Date()));

                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    officialCarApply.setUpdate_time(time1);

                    officialCarApply.setStatus(status);
                    officialCarApplyService.updateOfficialCarApply(officialCarApply);
                    jsonData.put("judge", "1");
                }
        }

        jsonObject.put("data", jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "查找申请表单", notes = "输入status来查找对应的表单信息", produces = "application/json")
    @RequestMapping(value = "/searchOfficialCarApplyByStatus", method = RequestMethod.POST)
    public String selectOfficialCarApplyByStatus(@RequestParam int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "调用成功");
        jsonObject.put("code", "0000");
        JSONObject jsonData = new JSONObject();

        List<OfficialCarApply> officialCarApplyList = officialCarApplyService.selectOfficialCarApplyByStatus(status);

        jsonData.put("officialCarApplyList", officialCarApplyList);
        jsonObject.put("data", jsonData);

        return jsonObject.toString();
    }
}
