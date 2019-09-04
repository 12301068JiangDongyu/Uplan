package com.pku.system.service;

import com.pku.system.model.OfficialCarApply;

import java.sql.Timestamp;
import java.util.List;

public interface OfficialCarApplyService {
    /**
     * 获得所有申请条目。
     * @return
     */
    public List<OfficialCarApply> getAllOfficialCarApply();

    /**
     * 根据申请单号查询申请条目。
     * @param id
     * @return
     */
    public OfficialCarApply selectOfficialCarApplyById(int id);

    /**
     * 审核更新，将申请条目的状态置为通过或者未通过。
     * @param id
     * @param status
     * @return
     */
//    这里还要加时间戳
    public void updateOfficialCarApply(int id, int status);

    /**
     * 通过状态值来查询对应的所有表单信息。
     * @param status
     * @return
     */
    public List<OfficialCarApply> selectOfficialCarApplyByStatus(int status);

}
