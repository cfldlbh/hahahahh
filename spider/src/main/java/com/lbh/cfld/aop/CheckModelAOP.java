package com.lbh.cfld.aop;

import com.lbh.cfld.analysis.AnalysisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import com.lbh.cfld.model.NewsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class CheckModelAOP {
    private static final Logger log = LoggerFactory.getLogger(CheckModelAOP.class);
    @Pointcut("execution(* com.lbh.cfld.service.NewsDataServiceImpl.insertOne(..)))")
    public void pointCutMethod( ){}

    @Around("pointCutMethod()")
    /**
     * @Description: 入库前检查null值
     * @author: cfld 
     * @date:  2019/7/25 21:30
     * @param: [jp] 
     * @return: void
     **/
    public void beforeAop(ProceedingJoinPoint jp ){
        Object[] args = jp.getArgs();
        NewsData data = (NewsData) args[0];
        if(data.getContent()==null || data.getTitle() == null){
             log.error("获取失败错误的url===【{}】",data.getUrl());
            return;
        }else {
            try {
                jp.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
