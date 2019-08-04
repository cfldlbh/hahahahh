package com.lbh.cfld.analysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.NewsData;
import org.jsoup.nodes.Element;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public abstract class  AbstractAnalysis implements AnalysisInterface {

    private static final Logger log = LoggerFactory.getLogger(AbstractAnalysis.class);


    @Override
    /**
     * @Description: 解析网页封装成对象
     * @author: cfld
     * @date:  2019/7/25 21:23
     * @param: [url, xpathFormat]
     * @return: com.lbh.cfld.model.NewsData
     **/
    public NewsData startAnalysis(String url, JSONObject xpathFormat){
        String strTitle;
        StringBuilder strContent;
        String content = xpathFormat.getString("content");
        String title = xpathFormat.getString("title");
        JSONArray excludeStr = xpathFormat.getJSONArray("excludeStr");
        String http = AnalysisUtils.getHttp(url);
        JXDocument jxDocument = JXDocument.create(http);
        if(jxDocument==null){
            strContent = new StringBuilder("");
            strTitle = "";
            log.error("空指针{}",url);
        }else{
            strContent = getContent(jxDocument, content, excludeStr);
            strTitle = getTitle(jxDocument, title);
        }
        NewsData newsData = new NewsData();
        if(strContent.toString().contains("embed")){
            newsData.setTitle("");
        }else{
            newsData.setTitle(strTitle);
        }
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
            Element next = (Element)iterator.next();
            boolean contains = false;
            for(Object s : excludeStr){
                if(contains){
                }else{
                    contains = ((String) next.toString()).contains(((String)s));
                }
            }
            if(!contains){
                tagStr.append((String) next.toString());
            }
        }
        return tagStr;
    }
    @Override
    public String getTitle(JXDocument htmlDocument, String xpath){
        Object o = htmlDocument.selOne(xpath);
        if(o == null||o.equals("")){
            return "";
        }
        return o.toString();
    }
}
