package com.pku.system.schedule;

import com.pku.system.model.OfficialCarApply;
import com.pku.system.service.OfficialCarApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class UpateOfficialCarApplyScheduleTask {

    @Autowired
    OfficialCarApplyService officialCarApplyService;

    // 每天的23点更新一次 official_car_apply status状态
    @Scheduled(cron = "0 15 14 * * ?")
    private void configureTasks() {
        int status = 3;
        officialCarApplyService.updateOfficialCarApplyStatusSchedule(status);
        System.out.println("开始执行定时了。");
    }
}
