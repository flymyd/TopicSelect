package com.tjrac.topicselect.util.shiro;

import com.tjrac.topicselect.service.SysuserService;
import com.tjrac.topicselect.service.seviceimpl.SysuserServiceImpl;
import com.tjrac.topicselect.util.shiro.filter.AnyRolesAuthorizationFilter;
import com.tjrac.topicselect.util.shiro.filter.JwtAuthFilter;
import com.tjrac.topicselect.util.shiro.realm.DbShiroRealm;
import com.tjrac.topicselect.util.shiro.realm.JWTShiroRealm;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;


/**
 * @author myd
 */
@Configuration
public class ShiroConfigurationNxGen {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager,SysuserService userService) throws Exception{
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter)shiroFilter(securityManager, userService).getObject());
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public Authenticator authenticator(SysuserService userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    @Bean("dbRealm")
    public Realm dbShiroRealm(SysuserService userService) {
        return new DbShiroRealm(userService);
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(SysuserService userService) {
        return new JWTShiroRealm(userService);
    }

    /**
     *  Shiro 安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        SysuserService sysuserService = new SysuserServiceImpl();
        //设置realm
        HashSet<Realm> realms = new HashSet();
        realms.add(dbShiroRealm(sysuserService));
        realms.add(jwtShiroRealm(sysuserService));
        securityManager.setRealms(realms);
        return securityManager;
    }


    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, SysuserService userService) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter(userService));
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());

        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/loginAction", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/appLoginAction", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/users/appLoginAction", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/users/getCaptcha", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/getCaptcha", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/static", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/api/user/**", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/api/admin/uploadUserExcel", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/admin/exporttopicresult", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/api/admin/**", "noSessionCreation,authcToken,anyRole[0]");
        chainDefinition.addPathDefinition("/api/teacher/**", "noSessionCreation,authcToken,anyRole[1]");
        chainDefinition.addPathDefinition("/api/student/**", "noSessionCreation,authcToken,anyRole[2]");
        chainDefinition.addPathDefinition("/changepassword", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/changeinfo", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/teacher/**", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/student/**", "noSessionCreation,anon");
        return chainDefinition;
    }

    protected JwtAuthFilter createAuthFilter(SysuserService userService){
        return new JwtAuthFilter(userService);
    }

    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }

}
