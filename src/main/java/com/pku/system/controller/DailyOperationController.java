package com.pku.system.controller;


import com.pku.system.model.DailyOperation;
import com.pku.system.service.CarService;
import com.pku.system.service.DailyOperationService;
import com.pku.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Api (value="车辆记录",tags = {"车辆记录API"},description = "描述信息")
@RestController
@RequestMapping (value = "/dailyOperation")
public class DailyOperationController {
   @Autowired
   DailyOperationService dailyOperationService;
   @Autowired
   UserService userService;
   @Autowired
   CarService carService;

   @ApiOperation(value = "获得车辆记录列表", notes = "获得车辆记录列表notes", produces = "application/json")
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String getAllDailyOperation() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      List<DailyOperation> repairList = dailyOperationService.dealDailyOperation(dailyOperationService.getAllDailyOperation(1));
      List<DailyOperation> oilList = dailyOperationService.dealDailyOperation(dailyOperationService.getAllDailyOperation(2));
      List<DailyOperation> ruleList = dailyOperationService.dealDailyOperation(dailyOperationService.getAllDailyOperation(3));

      jsonData.put("repairList", repairList);
      jsonData.put("oilList", oilList);
      jsonData.put("ruleList", ruleList);

      jsonObject.put("data", jsonData);
      return jsonObject.toString();

   }

   @ApiOperation(value = "添加车辆记录信息", notes = "添加车辆记录信息notes", produces = "application/json")
   @RequestMapping(value = "/dailyOperationAdd", method = RequestMethod.POST)
   @ResponseBody
   public String AddDailyOperation(@RequestBody DailyOperation dailyOperation) {

      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      dailyOperation.setCreate_time(new Timestamp(System.currentTimeMillis()));


      if (dailyOperation.getType() != 1 && dailyOperation.getType() != 2 && dailyOperation.getType() != 3 ){
         //判断type是否合法
         jsonData.put("judge", "-1");
      } else {
         try {
            dailyOperationService.addDailyOperation(dailyOperation);
            //添加成功
            jsonData.put("judge", "0");
         } catch (DataAccessException e) {
            //添加失败
            jsonData.put("judge", "-9");
         }
      }

      jsonObject.put("data", jsonData);
      return jsonObject.toString();

   }

   @ApiOperation(value = "根据id删除车辆记录信息", notes = "根据id删除车辆记录信息notes", produces = "application/json")
   @RequestMapping(value = "/{dailyOperationId}", method = RequestMethod.DELETE)
   public String deleteDailyOperation(@PathVariable("dailyOperationId") int id) {
      // 处理"/users/{id}"的DELETE请求，用来删除User
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();


      DailyOperation dailyOperation = dailyOperationService.selectById(id);
      if (dailyOperation == null) {
         jsonData.put("judge", "-1");
      } else {
         try {
            dailyOperationService.deleteDailyOperation(id);
            //删除成功
            jsonData.put("judge", "0");
         } catch (DataAccessException e) {
            //删除失败
            jsonData.put("judge", "-9");

         }
      }
      jsonObject.put("data", jsonData);
      return jsonObject.toString();

   }

   @ApiOperation(value = "根据id修改车辆记录信息", notes = "根据id修改车辆记录信息notes", produces = "application/json")
   @RequestMapping(value = "/dailyOperationUpdate/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public String putDailyOperation(@PathVariable("id") int id,
                                   @RequestBody DailyOperation dailyOperation) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      if (dailyOperation.getType() != 1 && dailyOperation.getType() != 2 && dailyOperation.getType() != 3) {
         //判断type不合法
         jsonData.put("judge", "-1");
      } else if(dailyOperationService.selectById(id) == null) {
         //判断运维信息为空
         jsonData.put("judge", "-2");
      }else{
         try {
            dailyOperationService.updateDailyOperation(dailyOperation);
            //删除成功
            jsonData.put("judge", "0");
         } catch (DataAccessException e) {
            //删除失败
            jsonData.put("judge", "-9");

         }
      }

      jsonObject.put("data", jsonData);
      return jsonObject.toString();

   }
}
