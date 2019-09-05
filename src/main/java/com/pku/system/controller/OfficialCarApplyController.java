package com.pku.system.controller;

import com.pku.system.model.Car;
import com.pku.system.model.CarType;
import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import com.pku.system.service.OfficialCarApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.pool.sizeof.SizeOf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.sql.Timestamp;
import java.text.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

    @ApiOperation(value = "用车申请", notes= "用车申请", produces = "application/json")
    @RequestMapping(value = "/carApply",method = RequestMethod.POST)
    @ResponseBody
    public String carApply(OfficialCarApply officialCarApply) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");

        List<OfficialCarApply> officialCarApplies = officialCarApplyService.queryByCarId(officialCarApply.getCar_id());
        for (OfficialCarApply of :
                officialCarApplies) {
            SimpleDateFormat df = new SimpleDateFormat("YYYY MM DD");
            if (df.format(of.getStart_time()).equals(df.format(officialCarApply.getStart_time())) && of.getStatus() < 2){
                jsonObject.put("msg", officialCarApply.getStart_time()+"已经存在申请");
                return jsonObject.toString();
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
        return jsonObject.toString();
    }

    @ApiOperation(value = "用车申请撤销", notes= "用车申请撤销", produces = "application/json")
    @RequestMapping(value = "/carRepeal",method = RequestMethod.GET)
    public String carRepeal(OfficialCarApply officialCarApply) {

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
        return jsonObject.toString();
    }

}