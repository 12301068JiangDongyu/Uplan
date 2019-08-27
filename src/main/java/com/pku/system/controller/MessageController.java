package com.pku.system.controller;

import com.pku.system.model.WSocketMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Api(value="消息管理",tags = {"消息管理API"},description = "描述信息")
@RestController
@RequestMapping("/messages")// 通过这里配置使下面的映射都在/messages下
public class MessageController {

    @ApiOperation(value = "获得消息列表", notes = "获得消息列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllMessage(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<WSocketMessage> messageListCenter = NewWebSocket.wSocketMessageListCenter;
        jsonData.put("messageListCenter",messageListCenter);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "获得全部消息", notes = "获得全部消息notes", produces = "application/json")
    @RequestMapping(value="/allList", method=RequestMethod.GET)
    public @ResponseBody
    String ajaxGetMessageList(){
        List<WSocketMessage> list = NewWebSocket.wSocketMessageList;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        jsonData.put("list",list);
        jsonData.put("length",NewWebSocket.wSocketMessageListCenter.size());
        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "获得消息中心消息", notes = "获得消息中心消息notes", produces = "application/json")
    @RequestMapping(value="/centerList", method=RequestMethod.GET)
    public @ResponseBody String ajaxGetMessageListCenter(){
        List<WSocketMessage> listCenter = NewWebSocket.wSocketMessageListCenter;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        jsonData.put("listCenter",listCenter);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }

    @ApiOperation(value = "删除消息", notes = "删除消息notes", produces = "application/json")
    @RequestMapping(value="/deleteList", method=RequestMethod.GET)
    public @ResponseBody String ajaxDeleteMessageList(@RequestParam(value="ownId")String ownId,
                                                      @RequestParam(value="time")String time){
        List<WSocketMessage> list = NewWebSocket.wSocketMessageList;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();
        //删除前端已显示的消息
        for(int i=0;i<list.size();i++){
            if(list.get(i).getOwnId().equals(ownId)&&list.get(i).getNowTime().equals(time))
            {
                list.remove(i);
                jsonData.put("judge","0");
            }else
                jsonData.put("judge","-1");

        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }


}
