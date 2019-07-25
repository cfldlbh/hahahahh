package com.lbh.cfld.analysis;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AnalysisUtils {
    private static final Logger log = LoggerFactory.getLogger(AnalysisUtils.class);
    public static final CloseableHttpClient client = HttpClients.createDefault();

//    public static NewsData simple(String url, JSONObject xpathFormat){//  新闻解析
//        String html = getHttp(url);
//        NewsData resultData = new NewsData();
//        getResultData(html,xpathFormat,resultData);
//        resultData.setUrl(url);
//        resultData.setTime(new Timestamp(new Date().getTime()));
//
//        return resultData;
//    }

//    public static void getResultData(String html, JSONObject xpathFormat,NewsData entity){
//        JSONArray excludeStr = xpathFormat.getJSONArray("excludeStr");
//        Set<String> setKey = xpathFormat.keySet();
//        setKey.remove("excludeStr");
//        Iterator<String> keyIterator = setKey.iterator();
//        try {
//            while (keyIterator.hasNext()){
//                String next = keyIterator.next();
//                StringBuilder re = xpthMethod(html, xpathFormat.getString(next), excludeStr);
//                Field field = entity.getClass().getDeclaredField(next);
//                field.setAccessible(true);
//                field.set(entity,re.toString());
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    @SuppressWarnings("all")
//    public static StringBuilder xpthMethod(String html ,String xpath,JSONArray excludeStr){
//        JXDocument htmlDocument = JXDocument.create(html);
//        List<Object> sel = htmlDocument.sel(xpath);
//        StringBuilder tagStr = new StringBuilder();
//        Iterator<Object> iterator = sel.iterator();
//        while (iterator.hasNext()){
//            Object next = iterator.next();
//            boolean contains = false;
//            for(Object s : excludeStr){
//                if(contains){
//                }else{
//                    contains = ((String) next).contains(((String)s));
//                }
//            }
//            if(!contains){
//                tagStr.append((String) next);
//            }
//        }
//        return tagStr;
//    }
    public static String getHttp(String url){
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse execute = client.execute(httpGet);
            log.info("时间【{}】访问【"+url+"】成功,返回状态码{}",new SimpleDateFormat("yyyy-MM-dd :ss").format(new Date()),execute.getStatusLine().getStatusCode());
            HttpEntity entity = execute.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            log.error("异常:{}",e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

}
