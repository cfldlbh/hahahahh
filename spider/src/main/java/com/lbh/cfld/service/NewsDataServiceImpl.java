package com.lbh.cfld.service;

import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.analysis.AbstractAnalysis;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.dao.NewsDataDao;
import com.lbh.cfld.dao.NewsJobDao;
import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.model.NewsJob;
import com.lbh.cfld.model.NewsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class NewsDataServiceImpl {
    @Autowired
    private NewsDataDao newsDataDao;
    @Autowired
    private NewsJobDao newsJobDao;

    public Integer insertOne(NewsData data){
        return newsDataDao.insertOne(data);
    }

    public void start(List<String> list){
        NewsType newsType = newsJobDao.getJobByClassName("com.lbh.cfld.job.SouHuJob");
        if (newsType==null){
            return;
        }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            NewsData newsData = new AbstractAnalysis().startAnalysis(iterator.next(), JSONObject.parseObject(newsType.getFormat()));
            newsData.setTypeName(newsType.getId());
            newsDataDao.insertOne(newsData);
        }

    }
}
