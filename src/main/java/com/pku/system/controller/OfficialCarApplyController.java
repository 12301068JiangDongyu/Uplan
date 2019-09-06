package com.pku.system.controller;

import com.pku.system.model.*;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import com.pku.system.service.OfficialCarApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    // ---------------- 张 强 --------------------------------------------------------------------------

    // 提交用车申请的表单数据
    @ApiOperation(value = "用车申请", notes= "用车申请", produces = "application/json")
    @RequestMapping(value = "/officialCarApply/carApply",method = RequestMethod.POST)
    @ResponseBody
    public String carApply(@RequestBody OfficialCarApply officialCarApply) {

        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("judge","1");

        List<OfficialCarApply> officialCarApplies = officialCarApplyService.queryByCarId(officialCarApply.getCar_id());
        for (OfficialCarApply of :
                officialCarApplies) {
            SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
            if (df.format(of.getStart_time()).equals(df.format(officialCarApply.getStart_time())) && of.getStatus() == 1){
                jsonObject.put("msg", officialCarApply.getStart_time()+"已经存在申请");
                jsonObject.put("judge","0");
                jsonReturn.put("data",jsonObject);
                return jsonReturn.toString();
            }
        }

        //获得车信息
        Car car = carService.selectById(officialCarApply.getCar_id());
        //获得品牌型号
        CarType carType = carTypeService.selectById(car.getCar_type_id());

        officialCarApply.setBrand(carType.getBrand());
        officialCarApply.setCreate_time(new Date());
        officialCarApply.setUpdate_time(new Date());
        officialCarApply.setStatus(0);
        try {
            officialCarApplyService.addOfficialCarApply(officialCarApply);
        }catch (Exception e) {
            jsonObject.put("msg","调用失败");
            jsonObject.put("judge","-1");
        }
        jsonReturn.put("data",jsonObject);
        return jsonReturn.toString();
    }

    // 撤销掉 “未审核” 状态的单 为 “撤销” 状态
    @ApiOperation(value = "用车申请撤销", notes= "用车申请撤销", produces = "application/json")
    @RequestMapping(value = "/officialCarApply/carRepeal",method = RequestMethod.POST)
    @ResponseBody
    public String carRepeal(@RequestBody  OfficialCarApply officialCarApply) {

        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("judge","1");
        officialCarApply.setStatus(3);

        try {
            officialCarApplyService.updateOfficialCarApplyById(officialCarApply);
        }catch (Exception e) {
            jsonObject.put("msg","调用失败");
            jsonObject.put("judge","-1");
        }
        jsonReturn.put("data",jsonObject);
        return jsonReturn.toString();
    }

    // 通过用户ID获取其提交的用车申请单（所有状态的记录都会获取到）
    @RequestMapping(value = "/officialCarApply/carListByUser",method = RequestMethod.GET)
    public String carListByUserId(int user_id){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("judge","1");
        try{
            List<OfficialCarApply> officialCarApplyList = officialCarApplyService.queryByUserId(user_id);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
//            JSONArray jsonArray = new JSONArray();
//            for (OfficialCarApply expl :
//                    officialCarApplyList) {
//                JSONObject jsonObject1 = JSONObject.fromObject(expl);
//                jsonObject1.put("start_time",simpleDateFormat.format(expl.getStart_time()));
//                jsonArray.add(jsonObject1);
//
//            }
            jsonObject.put("data",officialCarApplyList);
        } catch(Exception e){
            jsonObject.put("msg","调用失败");
            jsonObject.put("judge","-1");
        }
        return jsonObject.toString();
    }

    // 通过时间参数获取当前可用的车辆清单列表
    @RequestMapping(value = "/officialCarApply/getAvailCarList",method = RequestMethod.GET)
    public String getAvailCarList(String dateInfo){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-M-d");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("judge","1");
        try{
            Date date = sdf.parse(dateInfo);
            List<QueryAvailcarList> list = officialCarApplyService.queryAvailabilityCarList(date);
            jsonObject.put("data",list);
        } catch (Exception e){
            jsonObject.put("msg","调用失败");
            jsonObject.put("judge","-1");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/officialCarApply/getAllCarListInfo",method = RequestMethod.GET)
    public String queryAllCarListInfo(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("judge","1");
        try{
            List<carListInfoByUserID> list = officialCarApplyService.queryCarListInfoByUserId();
            jsonObject.put("data",list);
        } catch (Exception e){
            jsonObject.put("msg","调用失败");
            jsonObject.put("judge","-1");
        }
        return jsonObject.toString();
    }

}
