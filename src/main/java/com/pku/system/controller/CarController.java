package com.pku.system.controller;

import com.pku.system.model.Car;
//import com.pku.system.model.CarType;
import com.pku.system.model.CarType;
import com.pku.system.service.CarService;
import com.pku.system.service.CarTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;


import java.security.Timestamp;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(value="车辆管理",tags = {"车辆管理API"},description = "描述信息")
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarTypeService carTypeService;

    @ApiOperation(value = "获得车辆信息列表", notes = "获得车辆信息列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllCar(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
//        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();


        List<Car> carList = carService.getAllCar();
        //String A=carList.toString();

        jsonData.put("carList",carList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "通过Id获得车辆信息列表", notes = "通过Id获得车辆信息列表notes", produces = "application/json")
    @RequestMapping(value="/getCar/{id}", method= RequestMethod.GET)
    public String getCar(@PathVariable("id") int id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        //JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();
       // JSONObject jsonData2 = new JSONObject();

        if(carService.selectById(id) ==null)
            //判断公车是否存在
            jsonData.put("judge", "-1");

        Car car = carService.selectById(id);
        //int a=car.getCar_Type_Id();
        CarType carType = carTypeService.selectById(car.getCarTypeId());

        jsonData.put("carList",car);
        jsonData.put("carTypeList",carType);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "添加车辆信息信息", notes = "添加车辆信息notes", produces = "application/json")
    @RequestMapping(value="/carAdd", method=RequestMethod.POST)
    @ResponseBody
    public String AddCar(@RequestBody Car car) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        //获取当前系统date类型时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date time=null;
        try {
            time= sdf.parse(sdf.format(new Date()));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        car.setCreate_time(time);
        car.setUpdate_time(time);

         if(car.getLicense_plate_num().length()==0){
            //判断车牌号为空
            jsonData.put("judge","-1");
        }else if( car.getType() !=1 && car.getType()!= 2){
            //判断公车类型是否非法
            jsonData.put("judge","-2");
        }else if(car.getStatus()!=1 && car.getStatus()!=2 && car.getStatus()!=3){
             //判断公车状态是否非法
             jsonData.put("judge","-3");
         } else{
            try{
                carService.addCar(car);
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

    @ApiOperation(value = "根据id删除公车信息信息", notes = "根据id删除公车信息notes", produces = "application/json")
    @RequestMapping(value="/{carId}", method=RequestMethod.DELETE)
    public String deleteCar(@PathVariable("carId") int id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Car car = carService.selectById(id);

        if(car == null){
            jsonData.put("judge","-1");
        }else{
            try{
                carService.deleteCar(id);
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

    @ApiOperation(value = "根据id修改车辆信息", notes = "根据id修改车辆信息notes", produces = "application/json")
    @RequestMapping(value="/carUpdate/{id}", method=RequestMethod.PUT)
    @ResponseBody
    public String putCameraType(@PathVariable("id") int id,
                                @RequestBody Car car) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        String license_Plate_Num = car.getLicense_plate_num();
        int type = car.getType();
        int status = car.getStatus();

        //获取当前系统date类型时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date time=null;
        try {
            time= sdf.parse(sdf.format(new Date()));

        } catch (ParseException e) {

            e.printStackTrace();
        }
        car.setUpdate_time(time);

        //car.setUpdate_time(new Timestamp(System.currentTimeMillis()));
       // user.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        if(license_Plate_Num.length()==0){
            //判断车牌号为空
            jsonData.put("judge","-1");
        } else if( type !=1 && car.getType()!= 2){
            //判断公车类型是否非法
            jsonData.put("judge","-2");
        }else if(status !=1 && car.getStatus()!=2 && car.getStatus()!=3){
            //判断公车状态是否非法
            jsonData.put("judge","-3");
        } else if(carService.selectById(id) ==null) {
            //判断公车是否存在
            jsonData.put("judge", "-4");
        }else{
            try{
                carService.updateCar(car);
                //更改成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //更改失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }
}
