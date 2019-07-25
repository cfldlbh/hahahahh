package com.lbh.cfld;

import com.alibaba.druid.pool.DruidDataSource;
import com.lbh.cfld.aop.CheckModelAOP;
import org.aspectj.lang.annotation.Before;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
@MapperScan("com.lbh.cfld.dao")
@EnableScheduling
public class RootConfigure {
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    public CheckModelAOP checkModelAOP(){
        CheckModelAOP checkModelAOP = new CheckModelAOP();
        return checkModelAOP;
    }
    @Bean(name = "dataSource")
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://118.24.95.77:3306/acfun?characterEncoding=utf8");//改
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("19961023Lbh@");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(druidDataSource());
    }
}
