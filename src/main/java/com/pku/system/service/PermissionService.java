package com.pku.system.service;

import com.pku.system.model.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Permission selectById(int id);

    /**
     * 查询全部
     * @return
     */
    public List<Permission> getAllPermission();

    /**
     * 得到资源对应的权限字符串
     * @param p_ids
     * @return
     */
    Set<String> findPermissions(Set<Integer> p_ids);

}
