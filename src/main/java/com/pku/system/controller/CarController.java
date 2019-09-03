package com.pku.system.controller;

import com.pku.system.model.Building;
import com.pku.system.model.Car;
import com.pku.system.model.Classroom;
import com.pku.system.model.DeviceInfo;
import com.pku.system.service.BuildingService;
import com.pku.system.service.CarService;
import com.pku.system.service.ClassroomService;
import com.pku.system.service.DeviceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.pool.sizeof.SizeOf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="车辆管理",tags = {"车辆管理API"},description = "描述信息")
@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @ApiOperation(value = "获得车辆信息列表", notes = "获得车辆信息列表notes", produces = "application/json")
    @RequestMapping(value="/CarList", method= RequestMethod.GET)
    public String getAllcar(){
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

    @ApiOperation(value = "获得教学楼信息列表", notes = "获得教学楼信息列表notes", produces = "application/json")
    @RequestMapping(value="/buildings", method= RequestMethod.GET)
    public String getAllBuilding(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();


        List<Building> buildingList= buildingService.getAllBuilding();

        jsonData.put("buildingList",buildingList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "添加教学楼教室信息", notes = "添加教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public String postBuildingClassroom(@RequestBody Building building) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(building.getBuildingNum().length()==0){
            //判断教学楼是否为空
            jsonData.put("judge","-1");
        }else if(building.getClassroomList().size()==0){
            //判断教室是否为空
            jsonData.put("judge","-2");
        }

        Building buildingInfo = buildingService.selectByName(building.getBuildingNum());
        if(buildingInfo == null){
            //判断教学楼是否存在，不存在则新增
            try{
                buildingService.addBuilding(building);
                buildingInfo = buildingService.selectByName(building.getBuildingNum());
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        if(buildingInfo != null){
            //判断教学楼是否存在，存在则新增教室
            for(int i=0;i<building.getClassroomList().size();i++){
                if(classroomService.selectByNameAndBid(building.getClassroomList().get(i).getClassroomNum(),buildingInfo.getId())!=null){
                    jsonData.put("judge","-3");
                }else{
                    try{
                        Classroom classroom = new Classroom();
                        classroom.setClassroomNum(building.getClassroomList().get(i).getClassroomNum());
                        classroom.setB_id(buildingInfo.getId());

                        classroomService.addClassroom(classroom);
                        //添加成功
                        jsonData.put("judge","0");
                    }catch (DataAccessException e){
                        //添加失败
                        jsonData.put("judge","-9");
                    }
                }
            }

        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id查询教学楼教室信息", notes = "根据id查询教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.GET)
    public String getBuildingClassroom(@PathVariable("cid") int cid) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Classroom classroom = classroomService.selectById(cid);
        Building building = buildingService.selectById(classroom.getB_id());

        if(classroom == null||building == null){
            jsonData.put("judge","-9");
        }else{
            jsonData.put("building",building);
            jsonData.put("classroom",classroom);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改教学楼信息", notes = "根据id修改教学楼信息notes", produces = "application/json")
    @RequestMapping(value="/buildings/{bid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putBuilding(@PathVariable("bid") int bid,
                              @RequestBody Building building) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(building.getBuildingNum()==null){
            //判断教学楼是否为空
            jsonData.put("judge","-1");
        }else if(buildingService.selectByName(building.getBuildingNum())!=null){
            //教学楼重名
            jsonData.put("judge","-2");
        }else{
            try{
                Building buildingOld = buildingService.selectById(bid);

                buildingOld.setBuildingNum(building.getBuildingNum());

                buildingService.updateBuilding(buildingOld);

                //修改成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //修改失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改教学楼教室信息", notes = "根据id修改教学楼教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putBuildingClassroom(@PathVariable("cid") int cid,
                                       @RequestBody Classroom classroom) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(classroom.getBuilding().getBuildingNum().length()==0){
            //判断教学楼是否为空
            jsonData.put("judge","-1");
        }else if(classroom.getClassroomNum().length()==0){
            //判断教室是否为空
            jsonData.put("judge","-2");
        }

        Building building = buildingService.selectByName(classroom.getBuilding().getBuildingNum());

        if(building ==null){
            //判断教学楼是否存在，不存在则新增
            try{
                Building buildingNew = new Building();
                buildingNew.setBuildingNum(classroom.getBuilding().getBuildingNum());
                buildingService.addBuilding(buildingNew);
                //添加成功
                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }

        }else{
            //教学楼存在，修改教学楼教室
            if(classroomService.selectByNameAndBid(classroom.getClassroomNum(),building.getId())!=null){
                //教室存在
                jsonData.put("judge","-3");
            }else if(classroomService.selectById(cid)==null){
                jsonData.put("judge","-4");
            }else{
                try{
                    Classroom classroomOld = classroomService.selectById(cid);

                    classroomOld.setClassroomNum(classroom.getClassroomNum());
                    classroomOld.setB_id(building.getId());

                    classroomService.updateClassroom(classroom);

                    //修改成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //修改失败
                    jsonData.put("judge","-9");
                }
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id删除教室信息", notes = "根据id删除教室信息notes", produces = "application/json")
    @RequestMapping(value="/{cid}", method=RequestMethod.DELETE)
    public String deleteBuildingClassroom(@PathVariable("cid") int cid) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Classroom classroom = classroomService.selectById(cid);

        if(classroom == null){
            jsonData.put("judge","-1");
        }else{
            try{
                classroomService.deleteClassroom(cid);

                Building building = buildingService.selectById(classroom.getB_id());

                DeviceInfo deviceInfo = deviceInfoService.selectByBuildingClassroom(building.getBuildingNum(),classroom.getClassroomNum());
                deviceInfoService.deleteDeviceInfo(deviceInfo.getId());

                List<Classroom> classroomList = classroomService.selectByBuildingId(classroom.getB_id());

                if(classroomList.size() == 0){
                    buildingService.deleteBuilding(classroom.getB_id());
                }

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

}
