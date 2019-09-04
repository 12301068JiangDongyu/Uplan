package com.pku.system.controller;


import com.pku.system.model.DailyOperation;
import com.pku.system.service.DailyOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api (value="日常运维",tags = {"日常运维API"},description = "描述信息")
@RestController
@RequestMapping (value = "/dailyOperation")
public class DailyOperationController {
   @Autowired
   DailyOperationService dailyOperationService;


   @ApiOperation(value = "获得日常运维列表", notes = "获得日常运维列表notes", produces = "application/json")
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String getAllDailyOperation() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      List<DailyOperation> dailyOperationList = dailyOperationService.getAllDailyOperation();
      jsonData.put("dailyOperationList", dailyOperationList);
      jsonObject.put("data", jsonData);
      return jsonObject.toString();

   }

   @ApiOperation(value = "添加日常运维信息", notes = "添加日常运维信息notes", produces = "application/json")
   @RequestMapping(value = "/dailyOperationAdd", method = RequestMethod.POST)
   @ResponseBody
   public String AdddailyOperation(@RequestBody DailyOperation dailyOperation) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      if (dailyOperation.getRemark().length() == 0) {
         //判断备注信息是否为空
         jsonData.put("judge", "-1");

      } else if (dailyOperation.getType() != 1 && dailyOperation.getType() != 2 && dailyOperation.getType() != 3 ) {
         //判断日常运维类型是否合法
         jsonData.put("judge", "-3");
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

   @ApiOperation(value = "根据id删除日常运维信息", notes = "根据id删除日常运维信息notes", produces = "application/json")
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

   @ApiOperation(value = "根据id修改运维信息", notes = "根据id修改运维信息notes", produces = "application/json")
   @RequestMapping(value = "/dailyOperationUpdate/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public String putdailyOperation(@PathVariable("id") int id,
                                   @RequestBody DailyOperation dailyOperation) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("msg", "调用成功");
      jsonObject.put("code", "0000");
      JSONObject jsonData = new JSONObject();

      if (dailyOperation.getRemark().length() == 0) {
         //判断备注信息为空
         jsonData.put("judge", "-1");
      } else if (dailyOperation.getType() != 1 && dailyOperation.getType() != 2 && dailyOperation.getType() != 3) {
         jsonData.put("judge", "-3");
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
}
