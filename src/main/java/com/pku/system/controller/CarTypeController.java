package com.pku.system.controller;


import com.pku.system.model.Car;
import com.pku.system.model.CarType;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(value="车辆类型管理",tags = {"车辆类型管理API"},description = "描述信息")
@RestController
@RequestMapping("/carType")
public class CarTypeController {
    @Autowired
    private CarTypeService carTypeService;

    @ApiOperation(value = "获得车辆类型信息列表", notes = "获得车辆类型信息列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllCarType(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();


        List<CarType> carTypeList = carTypeService.getAllCarType();
        //String A=carList.toString();

        jsonData.put("carTypeList",carTypeList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


    @ApiOperation(value = "添加车辆类型信息信息", notes = "添加车辆类型信息notes", produces = "application/json")
    @RequestMapping(value="/carTypeAdd", method=RequestMethod.POST)
    @ResponseBody
    public String AddCarType(@RequestBody CarType carType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        //获取当前系统date类型时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time=null;
        try {
            time= sdf.parse(sdf.format(new Date()));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        carType.setCreate_time(time);
        carType.setUpdate_time(time);

        if(carType.getBrand().length()==0){
            //判断品牌型号为空
            jsonData.put("judge","-1");
        }else if( carType.getCapacity().length()==0){
            //判断汽车排量是否为空
            jsonData.put("judge","-2");

        }else if(carType.getOil_type() != 0 && carType.getOil_type() != 1){
             //判断用油类型是否非法
             jsonData.put("judge","-3");
         } else{
            try{
                carTypeService.addCarType(carType);
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();

        }

    @ApiOperation(value = "根据id删除公车类型信息", notes = "根据id删除公车类型信息notes", produces = "application/json")
    @RequestMapping(value="/{carTypeId}", method=RequestMethod.DELETE)
    public String deleteCarType(@PathVariable("carTypeId") int id) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        CarType carType = carTypeService.selectById(id);

        if(carType == null){
            jsonData.put("judge","-1");
        }else{
            try{
                carTypeService.deleteCarType(id);
                //删除成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //删除失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改车辆类型信息", notes = "根据id修改车辆类型信息notes", produces = "application/json")
    @RequestMapping(value="/carTypeUpdate/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public String putCameraType(@PathVariable("id") int id,
                                @RequestBody CarType carType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String brand = carType.getBrand();
        String capacity = carType.getCapacity();
        int oil_type = carType.getOil_type();


        //获取当前系统date类型时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time=null;
        try {
            time= sdf.parse(sdf.format(new Date()));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        carType.setUpdate_time(time);

        if(brand.length() ==0 ){
            //判断品牌型号为空
            jsonData.put("judge","-1");
        }else if( capacity.length()==0){
            //判断汽车排量是否为空
            jsonData.put("judge","-2");

        }else if(oil_type != 0 && carType.getOil_type() != 1){
            //判断用油类型是否非法
            jsonData.put("judge","-3");
        } else if(carTypeService.selectById(id) == null) {
            //判断公车类型是否存在
            jsonData.put("judge", "-4");
        } else{
            try{
                carTypeService.updateCarType(carType);
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }
}
