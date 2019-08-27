package com.pku.system.service.impl;

import com.pku.system.dao.PermissionDao;
import com.pku.system.model.Permission;
import com.pku.system.service.PermissionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    PermissionDao permissionDao;

    @Override
    public Permission selectById(int id) {
        return permissionDao.selectById(id);
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionDao.getAllPermission();
    }

    @Override
    public Set<String> findPermissions(Set<Integer> p_ids) {
        Set<String> permissions = new HashSet<String>();
        for(Integer p_id : p_ids) {
            Permission permission = selectById(p_id);
            if(permission != null && !StringUtils.isEmpty(permission.getPermission())) {
                permissions.add(permission.getPermission());
            }
        }
        return permissions;
    }
}
