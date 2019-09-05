package com.pku.system.service;

import com.pku.system.model.DailyOperation;

import java.util.Date;
import java.util.List;

public interface DailyOperationService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public DailyOperation selectById(int id);
    /**
     *
     * @param
     * @return
     */
    public DailyOperation selectRemark (String remark);
    /**
     * 获得所有
     * @return
     */
    public List<DailyOperation> getAllDailyOperation();
    /**
     * 增加
     * @param
     */
    public void addDailyOperation (DailyOperation dailyOperation);
    /**
     * 更新
     * @param
     */
    public void updateDailyOperation (DailyOperation dailyOperation);
    /**
     * 删除
     * @param id
     */
    public void deleteDailyOperation (int id);
}
