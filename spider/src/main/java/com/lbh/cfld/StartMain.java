package com.lbh.cfld;


import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.analysis.AnalysisInterface;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.dao.NewsJobDao;
import com.lbh.cfld.job.Callback;
import com.lbh.cfld.job.MainJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import com.lbh.cfld.job.SouHuCallback;
import com.lbh.cfld.model.AcfunNews;
import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.model.NewsJob;
import com.lbh.cfld.model.NewsType;
import com.lbh.cfld.service.NewsDataServiceImpl;


import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdScheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartMain{

    private static Logger log = LoggerFactory.getLogger(StartMain.class);
    @Test
    public void testLog(){
        log.info("日志测试");
    }
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
    @SuppressWarnings("all")
    @Test
    public void testJob() throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfigure.class);
        NewsJobDao newsJobDao = (NewsJobDao)context.getBean(NewsJobDao.class);

        final NewsDataServiceImpl newsDataServiceImpl = (NewsDataServiceImpl)context.getBean("newsDataServiceImpl");
        List<NewsJob> newsJobList = newsJobDao.getNewsJobList();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for(int c = 0;c<newsJobList.size();c++) {
            final NewsJob newsJob = newsJobList.get(c);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String className = newsJob.getClassName();
                        String analysisClass = newsJob.getAnalysisClass();
                        NewsType newsType = newsJob.getNewsType();
                        String xpathformat = newsType.getFormat();
                        //Class.forName() object.class  newsJob.getClass()
                        Class<?> aClass = Class.forName(className);
                        Callback o = (Callback)aClass.newInstance();
                        Method getUtl_list = aClass.getMethod("getUtl_List", String.class);
                        List<String> urlList = (List<String>) getUtl_list.invoke(o,newsJob.getUrl());
                        Class<?> analysisClazz = Class.forName(analysisClass);
                        AnalysisInterface o1 =(AnalysisInterface) analysisClazz.newInstance();
                        Method startAnalysis = analysisClazz.getMethod("startAnalysis",String.class,JSONObject.class);
                        Iterator<String> iterator = urlList.iterator();
                        while (iterator.hasNext()) {

                            NewsData newsData = (NewsData) startAnalysis.invoke(o1,iterator.next(), JSONObject.parseObject(xpathformat));
                            newsData.setTypeName(newsType.getId());

                                newsDataServiceImpl.insertOne(newsData);

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Thread.sleep(300000);
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
            Thread.sleep(10000);
        }
    }
}
