package com.pku.system.controller;

import com.pku.system.model.*;
import com.pku.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value="分配设备",tags = {"分配设备API"},description = "描述信息")
@RestController
@RequestMapping(value = "/assignDevice")
public class AssignDeviceController {
    @Autowired
    CameraTypeService cameraTypeService;
    @Autowired
    ComputerTypeService computerTypeService;
    @Autowired
    ProjectorTypeService projectorTypeService;
    @Autowired
    RaspberryTypeService raspberryTypeService;
    @Autowired
    SinglechipTypeService singlechipTypeService;

    @Autowired
    CameraService cameraService;

    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    ClassroomService classroomService;

    /**
     * 分配设备
     * @return
     */
    @ApiOperation(value = "获得教室下所有已分配设备列表", notes = "获得教室下所有已分配设备列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllAssignDevice(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonData = new JSONObject();

        List<DeviceInfo> deviceInfoList= deviceInfoService.getAllDeviceInfo();

        for(int i = 0;i < deviceInfoList.size();i++){
            System.out.println(deviceInfoList.get(i).getId());
            List<Camera> cameraList = cameraService.selectByDeviceId(deviceInfoList.get(i).getId());

            for(Camera camera:cameraList){
                CameraType cameraType = cameraTypeService.selectById(camera.getCameraTypeId());
                camera.setCameraTypeName(cameraType.getCameraTypeName());
            }

            SinglechipType singlechipType = singlechipTypeService.selectById(deviceInfoList.get(i).getSinglechipTypeId());
            RaspberryType raspberryType = raspberryTypeService.selectById(deviceInfoList.get(i).getRaspberryTypeId());
            ComputerType computerType = computerTypeService.selectById(deviceInfoList.get(i).getComputerTypeId());
            ProjectorType projectorType = projectorTypeService.selectById(deviceInfoList.get(i).getProjectorTypeId());

            deviceInfoList.get(i).setCameraList(cameraList);
            deviceInfoList.get(i).setSinglechipType(singlechipType);
            deviceInfoList.get(i).setRaspberryType(raspberryType);
            deviceInfoList.get(i).setComputerType(computerType);
            deviceInfoList.get(i).setProjectorType(projectorType);

            jsonArray.add(deviceInfoList.get(i));
            jsonData.put("deviceInfoList",jsonArray);
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 添加教室所对应的设备,获取数据库中的设备型号列表，教学楼教室列表
     * @return
     */
    @ApiOperation(value = "获取数据库中的设备型号列表，教学楼教室列表", notes = "获取数据库中的设备型号列表，教学楼教室列表notes", produces = "application/json")
    @RequestMapping(value="/deviceList", method= RequestMethod.GET)
    public String addAssignDevice(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        //设备型号
        List<CameraType> cameraTypeList= cameraTypeService.getAllCameraType();
        List<ComputerType> computerTypeList= computerTypeService.getAllComputerType();
        List<ProjectorType> projectorTypeList = projectorTypeService.getAllProjectorType();
        List<RaspberryType> raspberryTypeList = raspberryTypeService.getAllRaspberryType();
        List<SinglechipType> singleChipTypeList = singlechipTypeService.getAllSinglechipType();

        //教学楼教室，默认加载第一个教学楼下的所有教室
        List<Building> buildingList = buildingService.getAllBuilding();
        List<Classroom> classroomList = classroomService.selectByBuildingId(1);

        jsonData.put("cameraTypeList",cameraTypeList);
        jsonData.put("computerTypeList",computerTypeList);
        jsonData.put("projectorTypeList",projectorTypeList);
        jsonData.put("raspberryTypeList",raspberryTypeList);
        jsonData.put("singleChipTypeList",singleChipTypeList);
        jsonData.put("buildingList",buildingList);
        jsonData.put("classroomList",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }

    /**
     * 更换教学楼，对应的教室号变化
     * @param buildingNum
     * @return
     */
    @ApiOperation(value = "更换教学楼，对应的教室号变化", notes = "更换教学楼，对应的教室号变化notes", produces = "application/json")
    @RequestMapping(value="/ajax_change_building", method= RequestMethod.GET)
    public String ajaxChangeBuilding(@RequestParam(value="buildingNum")String buildingNum){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Building building = buildingService.selectByName(buildingNum);
        List<Classroom> classroomList = classroomService.selectByBuildingId(building.getId());
        jsonData.put("classroomList",classroomList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


    @ApiOperation(value = "添加教室中的设备", notes = "添加教室中的设备notes", produces = "application/json")
    @RequestMapping(value="/", method=RequestMethod.POST)
    public @ResponseBody
    String ajaxAddClassroom(@RequestBody DeviceInfo deviceInfo){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(deviceInfo.getBuildingNum().length()==0){
            //判断教学楼号是否为空
            jsonData.put("judge","-1");
        }else if(deviceInfo.getClassroomNum().length()==0){
            //判断教室号是否为空
            jsonData.put("judge","-2");
        }else if(deviceInfoService.selectByBuildingClassroom(deviceInfo.getBuildingNum(),deviceInfo.getClassroomNum())!=null){
            //判断同一个教学楼的教室是否已经分配设备
            jsonData.put("judge","-3");
        }else{
            try{
                Camera camera = new Camera();

                deviceInfoService.addDeviceInfo(deviceInfo);

                DeviceInfo deviceInfoC = deviceInfoService.selectByBuildingClassroom(deviceInfo.getBuildingNum(),deviceInfo.getClassroomNum());

                for(int i=0;i<deviceInfo.getCameraList().size();i++){
                    camera.setCameraTypeId(deviceInfo.getCameraList().get(i).getCameraTypeId());
                    camera.setCameraAngle(deviceInfo.getCameraList().get(i).getCameraAngle());
                    camera.setDid(deviceInfoC.getId());
                    cameraService.addCamera(camera);
                }

                jsonData.put("judge","0");
            }catch (DataAccessException e){
                //添加失败
                jsonData.put("judge","-9");
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 更新教室所对应的设备,获得设备类型列表
     * @return
     */
    @ApiOperation(value = "更新教室所对应的设备,获得设备类型列表", notes = "更新教室所对应的设备,获得设备类型列表notes", produces = "application/json")
    @RequestMapping(value="/typeList", method= RequestMethod.GET)
    public String editAssignDevice(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<CameraType> cameraTypeList= cameraTypeService.getAllCameraType();
        List<ComputerType> computerTypeList= computerTypeService.getAllComputerType();
        List<ProjectorType> projectorTypeList = projectorTypeService.getAllProjectorType();
        List<RaspberryType> raspberryTypeList = raspberryTypeService.getAllRaspberryType();
        List<SinglechipType> singleChipTypeList = singlechipTypeService.getAllSinglechipType();

        jsonData.put("cameraTypeList",cameraTypeList);
        jsonData.put("computerTypeList",computerTypeList);
        jsonData.put("projectorTypeList",projectorTypeList);
        jsonData.put("raspberryTypeList",raspberryTypeList);
        jsonData.put("singleChipTypeList",singleChipTypeList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 根据did获取为教室分配设备信息
     * @param did
     */
    @ApiOperation(value = "根据did获取为教室分配设备信息", notes = "根据did获取为教室分配设备信息notes", produces = "application/json")
    @RequestMapping(value="/{did}", method= RequestMethod.GET)
    public String ajaxGetAddClassroomInfo(@PathVariable(value="did")int did){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        DeviceInfo deviceInfo = deviceInfoService.selectById(did);

        if(deviceInfo==null){
            jsonData.put("judge","-1");
        }else{
            try{
                List<Camera> cameraList = cameraService.selectByDeviceId(deviceInfo.getId());
                deviceInfo.setCameraList(cameraList);
                jsonData.put("deviceInfo",deviceInfo);
                jsonData.put("judge","0");

            }catch (DataAccessException e){
                //修改失败
                jsonData.put("judge","-9");
            }
        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


    @ApiOperation(value = "根据id更新教室中的设备", notes = "根据id更新教室中的设备notes", produces = "application/json")
    @RequestMapping(value="/{did}", method=RequestMethod.PUT)
    @ResponseBody
    public String ajaxEditAddClassroom(@PathVariable(value="did")int did,
                                       @RequestBody DeviceInfo deviceInfo){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(deviceInfo.getBuildingNum().length()==0){
            //判断教学楼号是否为空
            jsonData.put("judge","-1");
        }else if(deviceInfo.getClassroomNum().length()==0){
            //判断教室号是否为空
            jsonData.put("judge","-2");
        }else if(deviceInfoService.selectById(did) == null){
            jsonData.put("judge","-4");
        }else{
            try{
                DeviceInfo deviceInfoOld = deviceInfoService.selectById(did);
                deviceInfoOld.setComputerTypeId(deviceInfo.getComputerTypeId());
                deviceInfoOld.setSinglechipTypeId(deviceInfo.getSinglechipTypeId());
                deviceInfoOld.setProjectorTypeId(deviceInfo.getProjectorTypeId());
                deviceInfoOld.setRaspberryTypeId(deviceInfo.getRaspberryTypeId());

                deviceInfoService.updateDeviceInfo(deviceInfoOld);

                Camera camera = new Camera();

                List<Camera> cameraList = cameraService.selectByDeviceId(did);

                for(int i=0;i<cameraList.size();i++){
                    cameraService.deleteCamera(cameraList.get(i).getCameraId());
                }

                for(int i=0;i<deviceInfo.getCameraList().size();i++) {
                    camera.setCameraTypeId(deviceInfo.getCameraList().get(i).getCameraTypeId());
                    camera.setCameraAngle(deviceInfo.getCameraList().get(i).getCameraAngle());
                    camera.setDid(did);
                    cameraService.addCamera(camera);
                }
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

    @ApiOperation(value = "根据id更新树莓派状态", notes = "根据id更新树莓派状态notes", produces = "application/json")
    @RequestMapping(value="/raspberry/{did}", method=RequestMethod.PUT)
    @ResponseBody
    public String ajaxEditRaspberry(@PathVariable(value="did")int did){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        DeviceInfo deviceInfo = deviceInfoService.selectById(did);

        if(deviceInfo == null){
            jsonData.put("judge","-1");
        }else{
            try{
                deviceInfo.setRaspberryStatus(1);
                deviceInfo.setRaspberryStreamStatus(1);

                deviceInfoService.updateDeviceInfoStatus(deviceInfo);
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

    /**
     * 删除教室中的设备
     * @param did
     */
    @ApiOperation(value = "根据id教室中的设备", notes = "根据id教室中的设备notes", produces = "application/json")
    @RequestMapping(value="/{did}", method=RequestMethod.DELETE)
    public String ajaxDeleteClassroom(@PathVariable(value="did")int did){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(deviceInfoService.selectById(did)==null){
            //判断用户名是否存在
            jsonData.put("judge","-1");
        }else{
            try{
                List<Camera> cameraList = cameraService.selectByDeviceId(did);

                for(int i=0;i<cameraList.size();i++){
                    cameraService.deleteCamera(cameraList.get(i).getCameraId());
                }

                deviceInfoService.deleteDeviceInfo(did);
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
