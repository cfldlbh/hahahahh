package com.lbh.cfld.analysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.NewsData;
import org.seimicrawler.xpath.JXDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName: XinLangAnalysis
 * Description: 新浪新闻 解析类
 * date: 2019/7/25 21:26
 *
 * @author cfld
 * @since JDK 1.8
 */
public class XinLangAnalysis extends  AbstractAnalysis {
    private static final Logger log = LoggerFactory.getLogger(XinLangAnalysis.class);
    @Override
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
        newsData.setTitle(strTitle);
        newsData.setContent(strContent.toString());
        newsData.setUrl(url);
        newsData.setTime(new Timestamp(new Date().getTime()));
        return newsData;
    }
}
