package com.lbh.cfld.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.BeanContextUtils;
import com.lbh.cfld.RootConfigure;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.dao.NewsDataDao;
import com.lbh.cfld.dao.NewsJobDao;
import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.model.NewsJob;
import com.lbh.cfld.service.NewsDataServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(MainJob.class);
    @Autowired
    private NewsJobDao newsJobDao;
    @Autowired
    private com.lbh.cfld.service.NewsDataServiceImpl newsDataServiceImpl;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<NewsJob> newsJobList = newsJobDao.getNewsJobList();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int c = 0;c<newsJobList.size();c++){
            final NewsJob newsJob = newsJobList.get(c);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String className = newsJob.getClassName();
                        String analysisClass = newsJob.getAnalysisClass();
                        //Class.forName() object.class  newsJob.getClass()
                        Class<?> aClass = Class.forName(className);
                        Method getUtl_list = aClass.getMethod("getUtl_List", String.class);
                        List<String> urlList = (List<String>)getUtl_list.invoke(newsJob.getUrl());
                        Class<?> analysisClazz = Class.forName(analysisClass);
                        analysisClazz.getMethod("");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
