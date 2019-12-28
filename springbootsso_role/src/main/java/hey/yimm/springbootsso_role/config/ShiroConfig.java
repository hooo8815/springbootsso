package hey.yimm.springbootsso_role.config;

import hey.yimm.springbootsso_role.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("jwtInterceptor",getLoginInterceptor());
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        shiroFilterFactoryBean.setLoginUrl("/login.html");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/main.html");
//        shiroFilterFactoryBean.setSuccessUrl("/home.html");


        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        filterChainDefinitionMap.put("/staticResource/**","anon");
        filterChainDefinitionMap.put("/pages/error/**","anon");
        filterChainDefinitionMap.put("/user/login","anon");
//        filterChainDefinitionMap.put("/main.html","anon");
//        filterChainDefinitionMap.put("/login.html","anon");
        filterChainDefinitionMap.put("/pages/**","jwtInterceptor");
//        filterChainDefinitionMap.put("/**","authc");
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(getMyRealm());
        return defaultWebSecurityManager;
    }

    @Bean("myRealm")
    public MyRealm getMyRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(getHashedCredentialsMatcher());
        return myRealm;
    }

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
        hashedCredentialsMatcher.setHashIterations(2019);
        return hashedCredentialsMatcher;
    }


    @Bean("basicHttpAuthenticationFilter")
    public LoginInterceptor getLoginInterceptor(){
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return loginInterceptor;
    }

}
