package com.pku.system.controller;

import com.pku.system.model.Car;
import com.pku.system.model.CarType;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.model.QueryAvailcarList;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import com.pku.system.service.OfficialCarApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.pool.sizeof.SizeOf;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.sql.Timestamp;
import java.text.*;

import java.util.*;

/**
 * (OfficialCarApply)表控制层
 *
 * @author makejava
 * @since 2019-09-03 15:00:58
 */

@Api(value="用车申请",tags = {"用车申请API"},description = "描述用车申请信息")
@RestController
@RequestMapping("/officialCarApply")
public class OfficialCarApplyController {

    @Autowired
    private OfficialCarApplyService officialCarApplyService;

    @Autowired
    CarTypeService carTypeService;

    @Autowired
    CarService carService;

    // 提交用车申请的表单数据
    @ApiOperation(value = "用车申请", notes= "用车申请", produces = "application/json")
    @RequestMapping(value = "/carApply",method = RequestMethod.POST)
    @ResponseBody
    public String carApply(@RequestBody OfficialCarApply officialCarApply) {

        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");

        List<OfficialCarApply> officialCarApplies = officialCarApplyService.queryByCarId(officialCarApply.getCar_id());
        for (OfficialCarApply of :
                officialCarApplies) {
            SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
            if (df.format(of.getStart_time()).equals(df.format(officialCarApply.getStart_time())) && of.getStatus() < 2){
                jsonObject.put("msg", officialCarApply.getStart_time()+"已经存在申请");
                jsonReturn.put("data",jsonObject);
                return jsonReturn.toString();
            }
        }

        //获得车信息
        Car car = carService.selectById(officialCarApply.getCar_id());
        //获得品牌型号
        CarType carType = carTypeService.selectById(car.getCar_Type_Id());

        officialCarApply.setBrand(carType.getBrand());
        officialCarApply.setCreate_time(new Date());
        officialCarApply.setUpdate_time(new Date());
        officialCarApply.setStatus(0);
        try {
            officialCarApplyService.addOfficialCarApply(officialCarApply);
        }catch (Exception e) {
            jsonObject.put("msg","调用失败");
            jsonObject.put("code","1000");
        }
        jsonReturn.put("data",jsonObject);
        return jsonReturn.toString();
    }

    // 撤销掉 “未审核” 状态的单 为 “撤销” 状态
    @ApiOperation(value = "用车申请撤销", notes= "用车申请撤销", produces = "application/json")
    @RequestMapping(value = "/carRepeal",method = RequestMethod.GET)
    public String carRepeal(OfficialCarApply officialCarApply) {

        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        officialCarApply.setStatus(3);

        try {
            officialCarApplyService.updateOfficialCarApply(officialCarApply);
        }catch (Exception e) {
            jsonObject.put("msg","调用失败");
            jsonObject.put("code","1000");
        }
        jsonReturn.put("data",jsonObject);
        return jsonReturn.toString();
    }

    // 通过用户ID获取其提交的用车申请单（所有状态的记录都会获取到）
    @RequestMapping(value = "/carListByUser",method = RequestMethod.GET)
    public String carListByUserId(int user_id){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        try{
            List<OfficialCarApply> officialCarApplyList = new ArrayList<OfficialCarApply>();
            officialCarApplyList = officialCarApplyService.queryByUserId(user_id);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            JSONArray jsonArray = new JSONArray();
            for (OfficialCarApply expl :
                    officialCarApplyList) {
                JSONObject jsonObject1 = JSONObject.fromObject(expl);
                jsonObject1.put("start_time",simpleDateFormat.format(expl.getStart_time()));
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("data",jsonArray);
        } catch(Exception e){
            jsonObject.put("msg","调用失败");
            jsonObject.put("code","1000");
        }
        return jsonObject.toString();
    }

    // 通过时间参数获取当前可用的车辆清单列表
    @RequestMapping(value = "/getAvailCarList",method = RequestMethod.GET)
    public String getAvailCarList(String dateinfo){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        try{
            Date date = sdf.parse(dateinfo);
            List<QueryAvailcarList> list = officialCarApplyService.queryAvailabilityCarList(date);
            jsonObject.put("data",list);
        } catch (Exception e){
            jsonObject.put("msg","调用失败");
            jsonObject.put("code","1000");
        }
        return jsonObject.toString();
    }

}
