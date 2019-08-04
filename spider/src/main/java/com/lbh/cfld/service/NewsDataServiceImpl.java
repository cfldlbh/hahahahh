package com.lbh.cfld.service;

import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.analysis.AbstractAnalysis;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.analysis.SouHuAnalysis;
import com.lbh.cfld.dao.AcfunNewsDao;
import com.lbh.cfld.dao.NewsDataDao;
import com.lbh.cfld.dao.NewsJobDao;
import com.lbh.cfld.model.AcfunNews;
import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.model.NewsJob;
import com.lbh.cfld.model.NewsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NewsDataServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(NewsDataServiceImpl.class);
    @Autowired
    private NewsDataDao newsDataDao;
    @Autowired
    private NewsJobDao newsJobDao;
    @Autowired
    private AcfunNewsDao acfunNewsDao;

    public Integer insertOne(NewsData data){
        int i =0;
        try{
            i = newsDataDao.insertOne(data);
        }catch (Exception e) {
        if (e instanceof SQLException) {
            System.out.println("插入数据库异常"+data.getUrl());
        }
        }
        return i;
    }

    public void start(List<String> list){
        NewsType newsType = newsJobDao.getJobByClassName("com.lbh.cfld.job.SouHuJob");
        if (newsType==null){
            return;
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            NewsData newsData = new SouHuAnalysis().startAnalysis(iterator.next(), JSONObject.parseObject(newsType.getFormat()));
            newsData.setTypeName(newsType.getId());
            try{
                newsDataDao.insertOne(newsData);
            }catch (Exception e){
                if(e instanceof SQLException){
                    log.error("数据库插入异常{}=={}",newsData.getUrl(),newsData.getContent());
                }
            }

        }
    }

    public ArrayList<NewsData> getNewsDataList(){
        return newsDataDao.getList();
    }
    public Integer insertAcfunNews(AcfunNews acfunNews){
        Integer integer = acfunNewsDao.insertOne(acfunNews);
        return integer;
    }
}
