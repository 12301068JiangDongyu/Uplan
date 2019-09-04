package com.pku.system.controller;

import com.pku.system.dto.StatisticDto;
import com.pku.system.model.Statistic;
import com.pku.system.service.OfficialCarApplyService;
import com.pku.system.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName StatisticController
 * @Description: TODO
 * @Author jiangdongyu
 * @Date 2019/9/2
 * @Version V1.0
 **/
@Api(value="统计分析",tags = {"统计分析API"},description = "描述信息")
@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private OfficialCarApplyService officialCarApplyService;

    @ApiOperation(value = "获得统计分析列表", notes = "获得统计分析列表notes", produces = "application/json")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String getAllStatistic(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","调用成功");
        jsonObject.put("code","0000");
        JSONObject jsonData = new JSONObject();

        List<StatisticDto> carTypeCount = officialCarApplyService.getAllBrandCount();
        List<StatisticDto> userCount = officialCarApplyService.getAllUserCount();

        jsonData.put("carTypeCount",carTypeCount);
        jsonData.put("userCount",userCount);

        jsonObject.put("data",jsonData);
        return jsonObject.toString();

    }
}
