package com.pku.system.controller;

import com.pku.system.model.Car;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.impl.CarServiceImpl;
import com.pku.system.service.impl.CarTypeServiceImpl;
import com.pku.system.service.impl.OfficialCarApplyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * judge里-1表示不存在，1表示存在，0表示更新成功。
 */
@Api(value="用车审批",tags = {"用车审批API"},description = "描述信息")
@RestController
public class OfficialCarApplyController {
    @Autowired
    OfficialCarApplyServiceImpl officialCarApplyServiceImpl;
    @Autowired
    CarServiceImpl carServiceImpl;
    @Autowired
    CarTypeServiceImpl carTypeServiceImpl;

    @ApiOperation(value = "获取表单列表", notes = "获取所有的表单信息", produces = "application/json")
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getAllOfficialCarApply() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        //获得到所有的申请表单
        List<OfficialCarApply> officialCarApplyList = officialCarApplyServiceImpl.getAllOfficialCarApply();

        if(officialCarApplyList.size() == 0)
            jsonData.put("judge", "-1");
        else
            jsonData.put("judge", "1");
        jsonData.put("officialCarApplyList", officialCarApplyList);
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
        OfficialCarApply officialCarApply = officialCarApplyServiceImpl.selectOfficialCarApplyById(id);
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
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkOfficialCarApply(@RequestParam int id, @RequestParam int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        OfficialCarApply officialCarApply = officialCarApplyServiceImpl.selectOfficialCarApplyById(id);

        if (officialCarApply == null)
            jsonData.put("judge", "-1");
        else {
            Car car = carServiceImpl.selectCarById(officialCarApply.getCar_id());

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//            status：1通过，2不通过。
//            jsonData.put("judge", "1");
            if(status == 1){
                if (car.getStatus() != 1) {
//                代表车不可借。
                    jsonData.put("judge", "-2");
                }
                else{
                    officialCarApplyServiceImpl.updateOfficialCarApply(id, status);
//                    通过某个申请后，需要将对应的车的状态也更新为使用中。
                    int type = car.getType();
//                1:班车，2:公车
                    if (type == 2){
                        //update car status.
//                    1:空闲，2:使用中，3：维修中
                        carServiceImpl.updateCarStatusById(car.getId(), 2);
                        jsonData.put("judge", "1");
                    }
                }
            }
            else
            if (status == 2){
                //这里还需要更新update_time字段。
                officialCarApplyServiceImpl.updateOfficialCarApply(id, status);
                jsonData.put("judge", "1");
            }
        }

        jsonObject.put("data", jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "查找申请表单", notes = "输入status来查找对应的表单信息", produces = "application/json")
    @RequestMapping(value = "searchOfficialCarApplyByStatus", method = RequestMethod.POST)
    public String selectOfficialCarApplyByStatus(@RequestParam int status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "调用成功");
        jsonObject.put("code", "0000");
        JSONObject jsonData = new JSONObject();

        List<OfficialCarApply> officialCarApplyList = officialCarApplyServiceImpl.selectOfficialCarApplyByStatus(status);

        jsonData.put("officialCarApplyList", officialCarApplyList);
        jsonObject.put("data", jsonData);

        return jsonObject.toString();
    }
}
