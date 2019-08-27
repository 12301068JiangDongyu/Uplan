package com.pku.system.controller;

import com.pku.system.model.*;
import com.pku.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="设备信息管理",tags = {"设备信息管理API"},description = "描述信息")
@RestController
@RequestMapping("/deviceInfos")// 通过这里配置使下面的映射都在/deviceInfos下
public class DeviceInfoController {
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

    @ApiOperation(value = "获得设备列表", notes = "获得设备列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllDevice(){
        // 处理"/deviceInfos/"的GET请求，用来获取角色列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<CameraType> cameraTypeList= cameraTypeService.getAllCameraType();
        List<ComputerType> computerTypeList= computerTypeService.getAllComputerType();
        List<ProjectorType> projectorTypeList = projectorTypeService.getAllProjectorType();
        List<RaspberryType> raspberryTypeList = raspberryTypeService.getAllRaspberryType();
        List<SinglechipType> singlechipTypeList = singlechipTypeService.getAllSinglechipType();

        jsonData.put("projectorTypeList",projectorTypeList);
        jsonData.put("cameraTypeList",cameraTypeList);
        jsonData.put("computerTypeList",computerTypeList);
        jsonData.put("raspberryTypeList",raspberryTypeList);
        jsonData.put("singleChipTypeList",singlechipTypeList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "添加电脑类型", notes = "添加电脑类型notes", produces = "application/json")
    @RequestMapping(value="/computerType/", method=RequestMethod.POST)
    @ResponseBody
    public String postComputerType(@RequestBody ComputerType computerType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(computerType.getComputerTypeName().length()==0){
            //判断电脑类型是否为空
            jsonData.put("judge","-1");
        }else if(computerType.getMemorySize().length()==0){
            //判断内存大小是否为空
            jsonData.put("judge","-2");
        }else if(computerType.getDiskSize().length()==0){
            //判断硬盘大小是否为空
            jsonData.put("judge","-3");
        }else if(computerType.getOperatingSystem().length()==0){
            //判断操作系统信息是否为空
            jsonData.put("judge","-4");
        }else if(computerTypeService.selectByAll(computerType.getComputerTypeName(),computerType.getMemorySize(),computerType.getDiskSize(),computerType.getOperatingSystem())!=null){
            //判断电脑设备是否存在
            jsonData.put("judge","-5");
        }else{
            try{
                computerTypeService.addComputerType(computerType);
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

    @ApiOperation(value = "根据id查询电脑类型", notes = "根据id查询电脑类型notes", produces = "application/json")
    @RequestMapping(value="/computerType/{coid}", method=RequestMethod.GET)
    public String getComputerType(@PathVariable("coid") int coid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        ComputerType computerType = computerTypeService.selectById(coid);
        if(computerType == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("computer",computerType);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改电脑类型", notes = "根据id修改电脑类型notes", produces = "application/json")
    @RequestMapping(value="/computerType/{coid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putComputerType(@PathVariable("coid") int coid,
                                  @RequestBody ComputerType computerType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String computerTypeName = computerType.getComputerTypeName();
        String memorySize = computerType.getMemorySize();
        String diskSize = computerType.getDiskSize();
        String operatingSystem = computerType.getOperatingSystem();

        if(computerTypeName.length()==0){
            //判断电脑类型是否为空
            jsonData.put("judge","-1");
        }else if(memorySize.length()==0){
            //判断内存大小是否为空
            jsonData.put("judge","-2");
        }else if(diskSize.length()==0){
            //判断硬盘大小是否为空
            jsonData.put("judge","-3");
        }else if(operatingSystem.length()==0){
            //判断操作系统信息是否为空
            jsonData.put("judge","-4");
        }else if(computerTypeService.selectByAll(computerTypeName,memorySize,diskSize,operatingSystem)!=null){
            //判断电脑设备是否存在
            jsonData.put("judge","-5");
        }else if(computerTypeService.selectById(coid) == null){
            jsonData.put("judge","-6");
        }else{
            try{
                ComputerType computerTypeOld = computerTypeService.selectById(coid);
                computerTypeOld.setComputerTypeName(computerTypeName);
                computerTypeOld.setMemorySize(memorySize);
                computerTypeOld.setDiskSize(diskSize);
                computerTypeOld.setOperatingSystem(operatingSystem);

                computerTypeService.updateComputerType(computerTypeOld);
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

    @ApiOperation(value = "添加树莓派类型", notes = "添加树莓派类型notes", produces = "application/json")
    @RequestMapping(value="/raspberryType/", method=RequestMethod.POST)
    @ResponseBody
    public String postRaspberryType(@RequestBody RaspberryType raspberryType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String raspberryTypeName = raspberryType.getRaspberryTypeName();

        if(raspberryTypeName.length()==0){
            //判断树莓派类型是否为空
            jsonData.put("judge","-1");
        }else if(raspberryTypeService.selectByName(raspberryTypeName)!=null){
            //判断树莓派类型是否存在
            jsonData.put("judge","-5");
        }else{
            try{
                raspberryTypeService.addRaspberryType(raspberryType);
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

    @ApiOperation(value = "根据id查询树莓派类型", notes = "根据id查询树莓派类型notes", produces = "application/json")
    @RequestMapping(value="/raspberryType/{raid}", method=RequestMethod.GET)
    public String getRaspberryType(@PathVariable("raid") int raid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        RaspberryType raspberryType = raspberryTypeService.selectById(raid);

        if(raspberryType == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("raspberry",raspberryType);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改树莓派类型", notes = "根据id修改树莓派类型notes", produces = "application/json")
    @RequestMapping(value="/raspberryType/{raid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putRaspberryType(@PathVariable("raid") int raid,
                                   @RequestBody RaspberryType raspberryType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String raspberryTypeName = raspberryType.getRaspberryTypeName();

        if(raspberryTypeName.length()==0){
            //判断树莓派类型是否为空
            jsonData.put("judge","-1");
        }else if(raspberryTypeService.selectByName(raspberryTypeName)!=null){
            //判断电脑设备是否存在
            jsonData.put("judge","-5");
        }else if(raspberryTypeService.selectById(raid) == null){
            jsonData.put("judge","-2");
        }else{
            try{
                RaspberryType raspberryTypeOld = raspberryTypeService.selectById(raid);
                raspberryTypeOld.setRaspberryTypeName(raspberryTypeName);

                raspberryTypeService.updateRaspberryType(raspberryTypeOld);
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

    @ApiOperation(value = "添加单片机类型", notes = "添加单片机类型notes", produces = "application/json")
    @RequestMapping(value="/singleChipType/", method=RequestMethod.POST)
    @ResponseBody
    public String postSingleChipType(@RequestBody SinglechipType singlechipType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String singlechipTypeName = singlechipType.getSinglechipTypeName();

        if(singlechipTypeName.length()==0){
            //判断单片机类型是否为空
            jsonData.put("judge","-1");
        }else if(singlechipTypeService.selectByName(singlechipTypeName)!=null){
            //判断单片机类型是否存在
            jsonData.put("judge","-5");
        }else{
            try{
                singlechipTypeService.addSinglechipType(singlechipType);
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

    @ApiOperation(value = "根据id查询单片机类型", notes = "根据id查询单片机类型notes", produces = "application/json")
    @RequestMapping(value="/singleChipType/{sid}", method=RequestMethod.GET)
    public String getSingleChipType(@PathVariable("sid") int sid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        SinglechipType singlechipType = singlechipTypeService.selectById(sid);

        if(singlechipType == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("singleChip",singlechipType);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改单片机类型", notes = "根据id修改单片机类型notes", produces = "application/json")
    @RequestMapping(value="/singleChipType/{sid}", method=RequestMethod.PUT)
    public String putSingleChipType(@PathVariable("sid") int sid,
                                    @RequestBody SinglechipType singlechipType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String singlechipTypeName = singlechipType.getSinglechipTypeName();

        if(singlechipTypeName.length()==0){
            //判断单片机类型是否为空
            jsonData.put("judge","-1");
        }else if(singlechipTypeService.selectByName(singlechipTypeName)!=null){
            //判断单片机是否存在
            jsonData.put("judge","-5");
        }else if(singlechipTypeService.selectById(sid) == null){
            jsonData.put("judge","-2");
        }else{
            try{
                SinglechipType singlechipTypeOld = singlechipTypeService.selectById(sid);
                singlechipTypeOld.setSinglechipTypeName(singlechipTypeName);

                singlechipTypeService.updateSinglechipType(singlechipTypeOld);
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

    @ApiOperation(value = "添加摄像头类型", notes = "添加摄像头类型notes", produces = "application/json")
    @RequestMapping(value="/cameraType/", method=RequestMethod.POST)
    @ResponseBody
    public String postCameraType(@RequestBody CameraType cameraType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String cameraTypeName = cameraType.getCameraTypeName();

        if(cameraTypeName.length()==0){
            //判断摄像头类型是否为空
            jsonData.put("judge","-1");
        }else if(cameraTypeService.selectByName(cameraTypeName)!=null){
            //判断摄像头类型是否存在
            jsonData.put("judge","-5");
        }else{
            try{
                cameraTypeService.addCameraType(cameraType);
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

    @ApiOperation(value = "根据id查询摄像头类型", notes = "根据id查询摄像头类型notes", produces = "application/json")
    @RequestMapping(value="/cameraType/{cid}", method=RequestMethod.GET)
    public String getCameraType(@PathVariable("cid") int cid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        CameraType cameraType = cameraTypeService.selectById(cid);

        if(cameraType == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("camera",cameraType);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改摄像头类型", notes = "根据id修改摄像头类型notes", produces = "application/json")
    @RequestMapping(value="/cameraType/{cid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putCameraType(@PathVariable("cid") int cid,
                                   @RequestBody CameraType cameraType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String cameraTypeName = cameraType.getCameraTypeName();

        if(cameraTypeName.length()==0){
            //判断摄像头类型是否为空
            jsonData.put("judge","-1");
        }else if(cameraTypeService.selectByName(cameraTypeName)!=null){
            //判断摄像头是否存在
            jsonData.put("judge","-5");
        }else if(cameraTypeService.selectById(cid) == null){
            jsonData.put("judge","-2");
        }else{
            try{
                CameraType cameraTypeOld = cameraTypeService.selectById(cid);
                cameraTypeOld.setCameraTypeName(cameraTypeName);

                cameraTypeService.updateCameraType(cameraTypeOld);
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

    @ApiOperation(value = "添加投影仪类型", notes = "添加投影仪类型notes", produces = "application/json")
    @RequestMapping(value="/projectorType/", method=RequestMethod.POST)
    @ResponseBody
    public String postProjectorType(@RequestBody ProjectorType projectorType) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String projectorTypeName = projectorType.getProjectorTypeName();

        if(projectorTypeName.length()==0){
            //判断投影仪类型是否为空
            jsonData.put("judge","-1");
        }else if(projectorTypeService.selectByName(projectorTypeName)!=null){
            //判断投影仪类型是否存在
            jsonData.put("judge","-5");
        }else{
            try{
                projectorTypeService.addProjectorType(projectorType);
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

    @ApiOperation(value = "根据id查询投影仪类型", notes = "根据id查询投影仪类型notes", produces = "application/json")
    @RequestMapping(value="/projectorType/{pid}", method=RequestMethod.GET)
    public String getProjectorType(@PathVariable("pid") int pid){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        ProjectorType projectorType = projectorTypeService.selectById(pid);

        if(projectorType == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("projector",projectorType);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改投影仪类型", notes = "根据id修改投影仪类型notes", produces = "application/json")
    @RequestMapping(value="/projectorType/{pid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putProjectorType(@PathVariable("pid") int pid,
                                   @RequestBody ProjectorType projectorType) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        String projectorTypeName = projectorType.getProjectorTypeName();

        if(projectorTypeName.length()==0){
            //判断投影仪类型是否为空
            jsonData.put("judge","-1");
        }else if(projectorTypeService.selectByName(projectorTypeName)!=null){
            //判断投影仪类型是否存在
            jsonData.put("judge","-5");
        }else if(projectorTypeService.selectById(pid) == null){
            jsonData.put("judge","-2");
        }else{
            try{
                ProjectorType projectorTypeOld = projectorTypeService.selectById(pid);
                projectorTypeOld.setProjectorTypeName(projectorTypeName);

                projectorTypeService.updateProjectorType(projectorTypeOld);
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

    @ApiOperation(value = "根据id删除设备", notes = "根据id删除设备notes", produces = "application/json")
    @RequestMapping(value="/{did}", method=RequestMethod.DELETE)
    public String deleteDevice(@PathVariable("did") int did,
                               @RequestParam(value="device")String device) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(device.equals("computer")){
            if(computerTypeService.selectById(did)==null){
                //判断用户名是否存在
                jsonData.put("judge","-1");
            }else{
                try{

                    computerTypeService.deleteComputerType(did);
                    //删除成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //删除失败
                    jsonData.put("judge","-9");
                }
            }
        }else if(device.equals("raspberry")){
            if(raspberryTypeService.selectById(did)==null){
                //判断用户名是否存在
                jsonData.put("judge","-1");
            }else{
                try{

                    raspberryTypeService.deleteRaspberryType(did);
                    //删除成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //删除失败
                    jsonData.put("judge","-9");
                }
            }
        }else if(device.equals("projector")){
            if(projectorTypeService.selectById(did)==null){
                //判断用户名是否存在
                jsonData.put("judge","-1");
            }else{
                try{

                    projectorTypeService.deleteProjectorType(did);
                    //删除成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //删除失败
                    jsonData.put("judge","-9");
                }
            }
        }else if(device.equals("camera")){
            if(cameraTypeService.selectById(did)==null){
                //判断用户名是否存在
                jsonData.put("judge","-1");
            }else{
                try{

                    cameraTypeService.deleteCameraType(did);
                    //删除成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //删除失败
                    jsonData.put("judge","-9");
                }
            }
        }else if(device.equals("singlechip")){
            if(singlechipTypeService.selectById(did)==null){
                //判断用户名是否存在
                jsonData.put("judge","-1");
            }else{
                try{

                    singlechipTypeService.deleteSinglechipType(did);
                    //删除成功
                    jsonData.put("judge","0");
                }catch (DataAccessException e){
                    //删除失败
                    jsonData.put("judge","-9");
                }
            }
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


}
