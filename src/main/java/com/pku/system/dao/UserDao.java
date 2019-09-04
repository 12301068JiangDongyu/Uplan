package com.pku.system.dao;

import com.pku.system.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface UserDao{
    @Select("SELECT u.id,u.username,u.password,r.r_id,r.r_name,u.license FROM tb_user u INNER JOIN tb_role r ON u.r_id=r.r_id")
    public List<User> getAllUser();

    @Select("select * from tb_user where id = #{id}")
    public User selectById(int id);

    @Select("select u.id, u.username,u.password,r.r_id,r.r_name,u.license from tb_user u INNER JOIN tb_role r ON u.r_id=r.r_id where username = #{username}")
    public User selectByName(String username);

    @Select("select * from tb_user where r_id = #{r_id}")
    public List<User> selectByRId(int r_id);

    @Insert("insert into tb_user (id,username,password,r_id,license,create_time,update_time) values (#{id},#{username},#{password},#{r_id},#{license},#{createTime},#{updateTime})")
    public void addUser(User user);

    @Update("update tb_user set username=#{username},password=#{password},r_id=#{r_id},license=#{license},update_time=#{updateTime} where id=#{id}")
    public void updateUser(User user);

    @Delete("delete from tb_user where id=#{id}")
    public void deleteUser(int id);

    @Select("select r.r_name from tb_user u,tb_role r where u.r_id=r.r_id and u.username=#{username}")
    Set<String> findRoles(String username);


}
