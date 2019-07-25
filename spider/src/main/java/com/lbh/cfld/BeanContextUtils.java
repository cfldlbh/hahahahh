package com.lbh.cfld;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


public class BeanContextUtils implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    public static Object getBean(String name) {
        return beanFactory.getBean(name);
    }
    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }
}
