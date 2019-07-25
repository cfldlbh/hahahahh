package com.lbh.cfld.analysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.NewsData;
import org.seimicrawler.xpath.JXDocument;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class  AbstractAnalysis implements AnalysisInterface {



    @Override
    /**
     * @Description: 解析网页封装成对象
     * @author: cfld
     * @date:  2019/7/25 21:23
     * @param: [url, xpathFormat]
     * @return: com.lbh.cfld.model.NewsData
     **/
    public NewsData startAnalysis(String url, JSONObject xpathFormat){
        String content = xpathFormat.getString("content");
        String title = xpathFormat.getString("title");
        JSONArray excludeStr = xpathFormat.getJSONArray("excludeStr");
        String http = AnalysisUtils.getHttp(url);
        JXDocument jxDocument = JXDocument.create(http);
        StringBuilder strContent = getContent(jxDocument, content, excludeStr);
        String strTitle = getTitle(jxDocument, title);
        NewsData newsData = new NewsData();
        newsData.setTitle(strTitle);
        newsData.setContent(strContent.toString());
        newsData.setUrl(url);
        newsData.setTime(new Timestamp(new Date().getTime()));
        return newsData;
    }

    @Override
    @SuppressWarnings("all")
    public StringBuilder getContent(JXDocument htmlDocument, String xpath, JSONArray excludeStr){
        List<Object> sel = htmlDocument.sel(xpath);
        StringBuilder tagStr = new StringBuilder();
        Iterator<Object> iterator = sel.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            boolean contains = false;
            for(Object s : excludeStr){
                if(contains){
                }else{
                    contains = ((String) next).contains(((String)s));
                }
            }
            if(!contains){
                tagStr.append((String) next);
            }
        }
        return tagStr;
    }
    @Override
    public String getTitle(JXDocument htmlDocument, String xpath){
        return htmlDocument.selOne(xpath).toString();
    }
}
