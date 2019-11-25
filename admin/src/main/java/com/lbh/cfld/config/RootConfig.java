package com.lbh.cfld.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lbh.cfld.shiro.filterChain.FilterAuthc;
import com.lbh.cfld.shiro.shiroRealm.RealmAdmin;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@Configuration
@ComponentScan(value="com.lbh.cfld",excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = EnableWebMvc.class)})
@MapperScan("com.lbh.cfld.dao")//spring的configuration,mybatis的MapperScan
public class RootConfig {
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        filterFactory.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
        filterMap.put("authc",filterAuthc());
        filterFactory.setFilters(filterMap);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl

        filterChainDefinitionMap.put("/user/login","anon");
        filterChainDefinitionMap.put("/**","authc");
        filterFactory.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filterFactory;
    }

    @Bean
    public SecurityManager  securityManager(RealmAdmin realmAdmin, SessionManager sessionManager){
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(realmAdmin);
        defaultSecurityManager.setSessionManager(sessionManager);
        return defaultSecurityManager;
    }
    @Bean(name = "dataSource")
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://118.24.95.77:3306/mall?characterEncoding=utf8");//改
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("19961023Lbh@");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setMapperLocations();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(druidDataSource());
    }
    @Bean(name="filterAuthc")
    public FilterAuthc filterAuthc(){
        return new FilterAuthc();
    }
}
