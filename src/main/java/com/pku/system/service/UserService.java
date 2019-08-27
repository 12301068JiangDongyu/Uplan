package com.pku.system.service;

import com.pku.system.model.User;
//import org.apache.shiro.subject.Subject;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public interface UserService {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username,String password);

    /**
     * 查询全部用户
     * @return
     */
    public List<User> getAllUser();

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User selectById(int id);

    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    public User selectByName(String username);

    /**
     * 根据角色id查找用户
     * @param r_id
     * @return
     */
    public List<User> selectByRId(int r_id);

    /**
     * 增加用户
     * @param user
     */
    public void addUser(User user);

    /**
     * 跟新用户
     * @param user
     */
    public void updateUser(User user);

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(int id);

    /**
     * 根据账号查找角色名称
     * @param username
     * @return
     */
    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);
}
