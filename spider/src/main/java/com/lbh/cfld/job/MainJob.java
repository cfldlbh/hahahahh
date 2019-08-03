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

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
