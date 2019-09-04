package com.pku.system.model;

import java.util.Date;
import java.io.Serializable;

/**
 * (TbUser)实体类
 *
 * @author makejava
 * @since 2019-09-02 20:58:02
 */
public class TbUser implements Serializable {
    private static final long serialVersionUID = -96038033237110790L;
    //用户id
    private Integer id;
    //用户账号
    private String username;
    //密码
    private String password;
    //角色id
    private Integer rId;
    //性别 1:男, 0:女
    private Integer sex;
    //驾驶证号码
    private String license;
    //部门名称
    private String departmentName;
    //数据创建时间
    private Date creatTime;
    //数据更新时间
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}