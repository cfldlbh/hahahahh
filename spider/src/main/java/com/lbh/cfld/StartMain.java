package com.lbh.cfld;


import com.lbh.cfld.job.MainJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.lbh.cfld.job.SouHuCallback;
import com.lbh.cfld.service.NewsDataServiceImpl;


import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdScheduler;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
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
}
