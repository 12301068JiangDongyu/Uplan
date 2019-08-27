package com.pku.system.controller;

import com.pku.system.model.Role;
import com.pku.system.model.User;
import com.pku.system.service.RoleService;
import com.pku.system.service.UserService;
import com.pku.system.exception.UnauthorizedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="注册登录注销",tags = {"注册登录注销API"},description = "描述信息")
@Controller
public class LoginController {
    //自动注入业务层的userService类
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private Logger logger = Logger.getLogger(getClass());

    @ApiOperation(value = "注册", notes = "注册notes", produces = "application/json")
    @RequestMapping(value="/login",method = RequestMethod.GET)
    @ResponseBody
    public String login(User user, HttpServletRequest request, HttpServletResponse response){
        //调用login方法来验证是否是注册用户
        boolean loginType = userService.login(user.getUsername(),user.getPassword());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        if(!loginType){
            jsonData.put("judge","-1");
            jsonObject.put("data",jsonData);
            return jsonObject.toString();
        }

        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);   //完成登录

        User userReturn = new User();
        User userComplete = userService.selectByName(user.getUsername());

        userReturn.setId(userComplete.getId());
        userReturn.setUsername(userComplete.getUsername());
        userReturn.setR_id(userComplete.getR_id());

        Role role = roleService.selectById(userComplete.getR_id());
        userReturn.setRole(role);

        //如果验证通过,则将用户信息传到前台
        request.setAttribute("user",userReturn);
        request.getSession().setAttribute("user",userReturn);

        jsonData.put("user",userReturn);
        jsonData.put("judge","0");

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    /**
     * 注销
     * @return
     */
    @ApiOperation(value = "注销", notes = "注销notes", produces = "application/json")
    @RequestMapping(value="/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        jsonData.put("judge","0");

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


    public void testException() throws UnauthorizedException{
        try{
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken("","");
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);   //完成登录
        }catch(Exception e){
            throw new UnauthorizedException("401", "NoSession");
        }
    }
    /**
     * 注销
     * @return
     */
    @RequestMapping(value="/error", method = RequestMethod.GET)
    @ResponseBody
    public String error(){

        try{
            testException();
        }catch (UnauthorizedException e){

            System.out.println("MsgDes\t"+e.getMsgDes());
            System.out.println("RetCd\t"+e.getRetCd());
            return e.toString();

        }
        return "";
    }

}
