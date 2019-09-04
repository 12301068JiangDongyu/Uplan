package com.pku.system.dao;

import com.pku.system.model.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleDao {
    @Select("select r_id,r_name,p_ids as p_idsStr from tb_role where r_id = #{r_id}")
    public Role selectById(int id);

    @Select("select r_id,r_name,p_ids as p_idsStr from tb_role where r_name = #{r_name}")
    public Role selectByRoleName(String roleName);

    @Select("select r_id,r_name,p_ids as p_idsStr from tb_role")
    public List<Role> getAllRole();

    @Insert("insert into tb_role (r_id,r_name,p_ids) values (#{r_id},#{r_name},#{p_idsStr})")
    public void addRole(Role role);

    @Update("update tb_role set r_name=#{r_name},p_ids=#{p_idsStr} where r_id=#{r_id}")
    public void updateRole(Role role);

    @Delete("delete from tb_role where r_id=#{r_id}")
    public void deleteRole(int id);
}
