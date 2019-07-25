package com.lbh.cfld.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.BeanContextUtils;
import com.lbh.cfld.RootConfigure;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.dao.NewsDataDao;
import com.lbh.cfld.model.NewsData;
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
import java.util.Iterator;

public class MainJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(MainJob.class);
    @Autowired
    private com.lbh.cfld.service.NewsDataServiceImpl newsDataServiceImpl;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    /*
    冗余代码*************************
     */
//        log.info("开始任务");
//        HttpGet httpGet = new HttpGet("http://temp.163.com/special/00804KV1/post1603_api_all.js?callback=callback");
//        CloseableHttpResponse execute = null;
//        try {
//            execute = AnalysisUtils.client.execute(httpGet);
//        } catch (IOException e) {
//
//        }
//        HttpEntity entity = execute.getEntity();
//        String s = null;
//        try {
//            s = EntityUtils.toString(entity);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String substring = s.substring(s.indexOf("["), s.lastIndexOf("]") + 1);
//        JSONArray objects = JSONObject.parseArray(substring);
//        Iterator<Object> iterator = objects.iterator();
//        while (iterator.hasNext()){
//            JSONObject next = (JSONObject)iterator.next();
//            String url =(String) next.get("url");
//            NewsData simple = AnalysisUtils.simple(url);
//            newsDataServiceImpl.insertOne(simple);
//        }
   }
}
