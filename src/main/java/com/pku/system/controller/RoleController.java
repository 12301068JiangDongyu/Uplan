package com.pku.system.controller;

import com.pku.system.model.User;
import com.pku.system.service.UserService;
import net.sf.json.JSONObject;
import com.pku.system.model.Permission;
import com.pku.system.model.Role;
import com.pku.system.service.PermissionService;
import com.pku.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="角色管理",tags = {"角色管理API"},description = "描述信息")
@RestController
@RequestMapping("/roles")// 通过这里配置使下面的映射都在/roles下
public class RoleController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "获得角色列表", notes = "获得角色列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllRole(){
        // 处理"/roles/"的GET请求，用来获取角色列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<Role> roleList = roleService.getAllRole();

        jsonData.put("roleList",roleList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }

    @ApiOperation(value = "添加角色", notes = "添加角色notes", produces = "application/json")
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public String postRole(@RequestBody Role role) {
        // 处理"/roles/"的POST请求，用来创建Role
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(role.getR_name().length()==0){
            jsonData.put("judge","-1");
        }else if(roleService.selectByRoleName(role.getR_name())!=null){
            jsonData.put("judge","-2");
        }else{
            try{
                roleService.addRole(role);
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

    @ApiOperation(value = "根据id查询角色", notes = "根据id查询角色notes", produces = "application/json")
    @RequestMapping(value="/{rid}", method=RequestMethod.GET)
    public String getRole(@PathVariable("rid") int rid) {
        // 处理"/roles/{id}"的GET请求，用来获取url中id值的Role信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        Role role = roleService.selectById(rid);
        if(role == null){
            jsonData.put("judge","-1");
        }else{
            jsonData.put("role",role);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改角色", notes = "根据id修改角色notes", produces = "application/json")
    @RequestMapping(value="/{rid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putRole(@PathVariable("rid") int rid,
                          @RequestBody Role role,
                          @RequestParam(value="judge")boolean judge) {
        // 处理"/roles/{id}"的PUT请求，用来更新Role信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(role.getR_name().length()==0){
            //判断角色名为空
            jsonData.put("judge","-1");
        }else if(roleService.selectByRoleName(role.getR_name())!=null&&judge){
            //判断角色名是否存在
            jsonData.put("judge","-2");
        }else if(roleService.selectById(rid) == null){
            //判断角色是否存在
            jsonData.put("judge","-3");
        }else{
            try{
                Role roleOld = roleService.selectById(rid);
                roleOld.setR_name(role.getR_name());
                roleOld.setP_ids(role.getP_ids());

                roleService.updateRole(roleOld);
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

    @ApiOperation(value = "根据id删除角色", notes = "根据id删除角色notes", produces = "application/json")
    @RequestMapping(value="/{rid}", method=RequestMethod.DELETE)
    public String deleteRole(@PathVariable("rid") int rid) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(roleService.selectById(rid)==null){
            jsonData.put("judge","-1");
        }else{
            try{
                List<User> userList = userService.selectByRId(rid);

                for(User user:userList){
                    user.setR_id(0);
                    userService.updateUser(user);
                }

                roleService.deleteRole(rid);
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

    @ApiOperation(value = "获得权限列表", notes = "获得权限列表notes", produces = "application/json")
    @RequestMapping(value="/permissions/", method= RequestMethod.GET)
    public String getPermissionTree(){
        // 处理"/roles/permissions/"的GET请求，用来获取权限列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<Permission> permissionList = permissionService.getAllPermission();
        jsonData.put("permissionList",permissionList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }



}
