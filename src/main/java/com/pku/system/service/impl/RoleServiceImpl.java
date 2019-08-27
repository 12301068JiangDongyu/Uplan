package com.pku.system.service.impl;

import com.pku.system.dao.RoleDao;
import com.pku.system.model.Role;
import com.pku.system.service.PermissionService;
import com.pku.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionService permissionService;

    @Override
    public Role selectById(int id) {
        return roleDao.selectById(id);
    }

    @Override
    public Role selectByRoleName(String roleName) {
        return roleDao.selectByRoleName(roleName);
    }

    @Override
    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);

    }

    @Override
    public void updateRole(Role role) {
        roleDao.updateRole(role);

    }

    @Override
    public void deleteRole(int id) {
        roleDao.deleteRole(id);

    }

    @Override
    public Set<String> findPermissions(int id) {
        Set<Integer> permissionIds = new HashSet<Integer>();
        Role role = selectById(id);
        if(role != null) {
            permissionIds.addAll(role.getP_ids());
        }
        return permissionService.findPermissions(permissionIds);
    }
}
