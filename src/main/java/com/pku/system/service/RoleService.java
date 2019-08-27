package com.pku.system.service;

import com.pku.system.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    public Role selectById(int id);

    /**
     * 根据角色名查询
     * @param roleName
     * @return
     */
    public Role selectByRoleName(String roleName);

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> getAllRole();

    /**
     * 增加角色
     * @param role
     */
    public void addRole(Role role);

    /**
     * 更新角色
     * @param role
     */
    public void updateRole(Role role);

    /**
     * 删除角色
     * @param id
     */
    public void deleteRole(int id);

    /**
     * 根据角色编号得到权限字符串列表
     * @param id
     * @return
     */
    Set<String> findPermissions(int id);
}
