package com.pku.system.dao;

import com.pku.system.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionDao {
    @Select("select * from tb_permission where id = #{id}")
    public Permission selectById(int id);

    @Select("select * from tb_permission")
    public List<Permission> getAllPermission();
}
