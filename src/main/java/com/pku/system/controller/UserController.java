package com.pku.system.controller;

import net.sf.json.JSONObject;
import com.pku.system.model.Role;
import com.pku.system.model.User;
import com.pku.system.service.RoleService;
import com.pku.system.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Api(value="用户管理",tags = {"用户管理API"},description = "描述信息")
@RestController
@RequestMapping(value = "/users") // 通过这里配置使下面的映射都在/users下
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "获得用户列表", notes = "获得用户列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllUser(){
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<User> userList= userService.getAllUser();
        List<Role> roleList = roleService.getAllRole();

        for(int i=0;i<userList.size();i++){
            Role role = roleService.selectById(userList.get(i).getR_id());
            userList.get(i).setRole(role);
        }

        jsonData.put("userList",userList);
        jsonData.put("roleList",roleList);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "添加用户", notes = "添加用户notes", produces = "application/json")
    @RequestMapping(value="/", method=RequestMethod.POST)
    @ResponseBody
    public String postUser(@RequestBody User user) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(user.getUsername().length()==0){
            //判断用户名是否为空
            jsonData.put("judge","-1");
        }else if(user.getPassword().length()==0){
            //判断密码是否为空
            jsonData.put("judge","-2");
        }else if(userService.selectByName(user.getUsername())!=null){
            //判断用户名是否存在
            jsonData.put("judge","-3");
        }else{
            try{
                user.setCreateTime(new Timestamp(System.currentTimeMillis()));
                userService.addUser(user);
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

    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户notes", produces = "application/json")
    @RequestMapping(value="/{uid}", method=RequestMethod.GET)
    public String getUser(@PathVariable("uid") int uid) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        User user = userService.selectById(uid);
        if(user == null){
            jsonData.put("judge","-9");
        }else{
            jsonData.put("user",user);
            jsonData.put("judge","0");
        }
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "根据id修改用户", notes = "根据id修改用户notes", produces = "application/json")
    @RequestMapping(value="/{uid}", method=RequestMethod.PUT)
    @ResponseBody
    public String putUser(@PathVariable("uid") int uid,
                          @RequestBody User user,
                          @RequestParam(value="judge")boolean judge) {//判断username有无变更 默认为变了
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(user.getUsername().length()==0){
            //判断用户名是否为空
            jsonData.put("judge","-1");
        }else if(user.getPassword().length()==0){
            //判断密码是否为空
            jsonData.put("judge","-2");
        }else if(userService.selectByName(user.getUsername())!=null&&judge){
            //判断用户名是否存在
            jsonData.put("judge","-3");
        }else if(userService.selectById(uid) == null){
            //判断用户是否存在
            jsonData.put("judge","-4");
        }else{
            try{
                user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userService.updateUser(user);
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

    @ApiOperation(value = "根据id删除用户", notes = "根据id删除用户notes", produces = "application/json")
    @RequestMapping(value="/{uid}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable("uid") int uid) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(userService.selectById(uid)==null){
            //判断用户名是否存在
            jsonData.put("judge","-1");
        }else{
            try{

                userService.deleteUser(uid);
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
