package com.cecilia.work.config;

import com.cecilia.work.common.Realm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
//    ShiroFilterFactoryBean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("SecurityManager") DefaultWebSecurityManager SecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(SecurityManager);
//        anon: 无需认证可以访问
//        authc: 必须认证了才能访问
//        user: 必须拥有记住我
//        perms: 拥有对某个资源的权限才能访问
//        role: 拥有某个角色权限才能访问

        LinkedHashMap<String, String> filter = new LinkedHashMap<>();
        filter.put("/user/login","anon");
        filter.put("/user/register","anon");
        filter.put("/user/*","authc");
        filter.put("/work/*","authc");

        filter.put("/user/adminusers","perms[user:adminusers]");
        bean.setFilterChainDefinitionMap(filter);

//        设置登录请求
        bean.setLoginUrl("/user/login");
        return bean;
    }
//    DefaultWebSecurityManager
    @Bean(name = "SecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("realm") Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
//    创建Realm对象
    @Bean
    public Realm realm(){
        Realm realm = new Realm();
        return realm;
    }


}
