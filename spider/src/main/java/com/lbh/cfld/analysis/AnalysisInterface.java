package com.lbh.cfld.analysis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.NewsData;
import org.seimicrawler.xpath.JXDocument;

public interface AnalysisInterface {

    NewsData startAnalysis(String url, JSONObject xpathFormat);

    @SuppressWarnings("all")
    StringBuilder getContent(JXDocument htmlDocument, String xpath, JSONArray excludeStr);

    String getTitle(JXDocument htmlDocument, String xpath);
}
