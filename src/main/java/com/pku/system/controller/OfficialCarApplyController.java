package com.pku.system.controller;

import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.OfficialCarApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.pool.sizeof.SizeOf;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.security.Timestamp;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (OfficialCarApply)表控制层
 *
 * @author makejava
 * @since 2019-09-03 15:00:58
 */

@Api(value="用车申请",tags = {"用车申请API"},description = "描述用车申请信息")
@RestController
@RequestMapping("/officialCarApply")
public class OfficialCarApplyController {

    @Autowired
    private OfficialCarApplyService officialCarApplyService;

    @ApiOperation(value = "根据用户ID (user_id)查询列表", notes= "根据用户ID (user_id)查询列表", produces = "application/json")
    @RequestMapping(value = "/{user_id}",method = RequestMethod.GET)
    public String queryByUserId(@PathVariable("user_id") int user_id){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<OfficialCarApply> officialCarApplies = officialCarApplyService.queryByUserId(user_id);
        if(user_id == null){

        }else{

        }

        jsonObject.put("data",jsonData);
        return jsonObject.toString();
    }




}