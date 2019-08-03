package com.lbh.cfld;


import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.job.MainJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.lbh.cfld.job.SouHuCallback;
import com.lbh.cfld.model.AcfunNews;
import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.service.NewsDataServiceImpl;


import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdScheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StartMain{

    public static void main(String[] arg) throws IOException, SchedulerException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfigure.class);
        StdScheduler scheduler = (StdScheduler)applicationContext.getBean("schedulerFactoryBean");
        JobDetail jobDetail = JobBuilder.newJob(MainJob.class).build();
        SimpleTrigger build = TriggerBuilder.newTrigger().withSchedule(simpleSchedule().withIntervalInHours(2).repeatForever()).build();
        scheduler.scheduleJob(jobDetail, build);
    }
    @Test
    public void testStartMethod(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfigure.class);
        NewsDataServiceImpl serviceImpl = (NewsDataServiceImpl)context.getBean("newsDataServiceImpl");
        List<String> utl_list = new SouHuCallback().getUtl_List("http://v2.sohu.com/integration-api/mix/region/157?callback=jQuery112405480996193168846_1563372278864&adapter=pc&pvId=15633722788781Zm8eSV&page=1&size=100&requestId=0448c9f2557c0561_1563372311852&mpId=326562500&channel=43&refer=&spm=smpc.content%252F64_4.tw.1.1563293971054WxW6g2m&_=1563372278865");
        serviceImpl.start(utl_list);
    }
    @Test
    public void sendNewsData() throws IOException, InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfigure.class);
        NewsDataServiceImpl serviceImpl = (NewsDataServiceImpl)context.getBean("newsDataServiceImpl");
        ArrayList<NewsData> newsDataList = serviceImpl.getNewsDataList();
        Iterator<NewsData> iterator = newsDataList.iterator();
        AnalysisUtils.loginAcfun();
        while (iterator.hasNext()){
            NewsData next = iterator.next();
            HttpPost httpPost = AnalysisUtils.packagForm(next);
            String s = AnalysisUtils.requestExecuteToString(httpPost);
            JSONObject o = JSONObject.parseObject(s);
            Integer errno = o.getInteger("errno");
            if(errno == 0 && o.getJSONObject("data").getInteger("result")==0){
                AcfunNews acfunNews = new AcfunNews();
                acfunNews.setTime(new Timestamp(new Date().getTime()));
                String aid = String.valueOf(o.getJSONObject("data").getInteger("articleId"));
                acfunNews.setAcfunId(aid);
                acfunNews.setAcfunUrl("https://www.acfun.cn/a/ac"+aid);
                acfunNews.setTypeId(next.getTypeName());
                acfunNews.setNewsId(next.getId());
                serviceImpl.insertAcfunNews(acfunNews);
                System.out.println(s);
            }

        }
    }
}
