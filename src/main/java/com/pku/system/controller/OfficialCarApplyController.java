package com.pku.system.controller;

import com.pku.system.model.Car;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import com.pku.system.service.OfficialCarApplyService;
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

@Api(value="用车审批",tags = {"用车审批API"},description = "描述信息")
@RestController
public class OfficialCarApplyController {
    @Autowired
    OfficialCarApplyService officialCarApplyService;
    @Autowired
    CarService carService;
    @Autowired
    CarTypeService carTypeService;

    @ApiOperation(value = "获取表单列表", notes = "获取所有的表单信息", produces = "application/json")
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public String getAllOfficialCarApply() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        //获得到所有的申请表单,
        List<OfficialCarApply> applyList = officialCarApplyService.getAllOfficialCarApply();
        if(applyList.size() == 0) {
            jsonData.put("judge", "-1");
        }
        else{
            jsonData.put("judge", "1");
        }
        jsonData.put("officialCarApplyList", applyList);
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

        if (officialCarApply == null)
//          申请不存在
            jsonData.put("judge", "-1");
        else {
            Car car = carService.selectCarById(officialCarApply.getCar_id());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (car == null)
//              车不存在
                jsonData.put("judge", "-2");
            else {
                if(status == 1){
                    if (car.getStatus() != 1) {
//                      代表车不可借。
                        jsonData.put("judge", "-3");
                    }
                    else{
                        java.util.Date updateTime=null;
                        try {
                            updateTime = dateFormat.parse(dateFormat.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        officialCarApply.setUpdate_time(updateTime);
                        officialCarApply.setStatus(status);
                        officialCarApplyService.updateOfficialCarApply(officialCarApply);
//                      操作成功
                        jsonData.put("judge", "1");
                    }
                }
                else{
                    java.util.Date updateTime = null;
                    try {
                        updateTime = dateFormat.parse(dateFormat.format(new Date()));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    officialCarApply.setUpdate_time(updateTime);
                    officialCarApply.setStatus(status);

                    officialCarApplyService.updateOfficialCarApply(officialCarApply);
                    jsonData.put("judge", "1");
                }
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
