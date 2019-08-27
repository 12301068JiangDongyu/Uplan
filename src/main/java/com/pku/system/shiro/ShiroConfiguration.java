package com.pku.system.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfiguration {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/websocket/**", "anon");
        filterChainDefinitionMap.put("/classroomStudio/**", "anon");

        //方法的控制,你拥有个权限，你才能访问这个方法
        //fixme 为了swagger临时注释掉
//        filterChainDefinitionMap.put("/deviceInfos/**", "perms[deviceInfos:*]");
//        filterChainDefinitionMap.put("/assignDevice/**", "perms[assignDevice:*]");
//        filterChainDefinitionMap.put("/deviceMonitor/**", "perms[deviceMonitor:*]");
//        filterChainDefinitionMap.put("/videos/**", "perms[videos:*]");
//        filterChainDefinitionMap.put("/buildingClassrooms/**", "perms[buildingClassrooms:*]");
//        filterChainDefinitionMap.put("/users/**", "perms[users:*]");
//        filterChainDefinitionMap.put("/roles/**", "perms[roles:*]");
//        filterChainDefinitionMap.put("/messages/**", "perms[messages:*]");
//
//        filterChainDefinitionMap.put("/**", "authc");//所有地址访问都必须经过认证

        shiroFilterFactoryBean.setLoginUrl("/error");//认证相关：当用户没有登陆就访问资源时，跳转到此页面
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");//授权相关：当用户访问没有权限的资源时，跳转到此页面
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    @Bean
    public MyRealm myRealm(){
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
