package com.lbh.cfld.analysis;


import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.model.AcfunNewBean;
import com.lbh.cfld.model.NewsData;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AnalysisUtils {
    private static String userName = "15879586926";
    private static String passWord = "19961023";
    private static final Logger log = LoggerFactory.getLogger(AnalysisUtils.class);
    public static final BasicCookieStore cookieStore = new BasicCookieStore();
    public static final CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

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
    public static HttpPost packagForm(NewsData bean) throws UnsupportedEncodingException {
        AcfunNewBean acfunNewBean = new AcfunNewBean();
        acfunNewBean.setTitle(bean.getTitle());
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> objects = new ArrayList();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("orderId","");
        jsonObject1.put("title","");
        jsonObject1.put("txt",  bean.getContent());
        jsonObject1.put("desc","");
        objects.add(jsonObject1);
        jsonObject.put("bodyList",objects);
        acfunNewBean.setDetail(jsonObject.toJSONString());
        acfunNewBean.setCreationType("1");
        acfunNewBean.setRealmId("5");
        acfunNewBean.setChannelId("110");
        acfunNewBean.setCertified("qwertyuiozxc==");
        BasicClientCookie basicClientCookie = new BasicClientCookie(" stochastic", "qwertyuiozxc==");
        basicClientCookie.setDomain("www.acfun.cn");
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        HttpPost httpPost = new HttpPost("https://www.acfun.cn/contribute/article");
        LinkedList<NameValuePair> objects1 = new LinkedList();
        objects1.add(new BasicNameValuePair("title",acfunNewBean.getTitle()));
        objects1.add(new BasicNameValuePair("detail",acfunNewBean.getDetail()));
        objects1.add(new BasicNameValuePair("creationType",acfunNewBean.getCreationType()));
        objects1.add(new BasicNameValuePair("channelId",acfunNewBean.getChannelId()));
        objects1.add(new BasicNameValuePair("realmId",acfunNewBean.getRealmId()));
        objects1.add(new BasicNameValuePair("certified",acfunNewBean.getCertified()));
        httpPost.setEntity(new UrlEncodedFormEntity(objects1,"UTF-8"));
        return httpPost;
    }
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

    public static void loginAcfun() throws IOException, InterruptedException {
        HttpPost login = new HttpPost("https://id.app.acfun.cn/rest/web/login/signin");
        LinkedList<BasicNameValuePair> basicNameValuePairs = new LinkedList();
        basicNameValuePairs.add(new BasicNameValuePair("username",userName));
        basicNameValuePairs.add(new BasicNameValuePair("password",passWord));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairs,"UTF-8");
        login.setEntity(entity);
        requestExecuteToString(login);
    }
    public static String requestExecuteToString(HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse execute = client.execute(requestBase);
        HttpEntity entity = execute.getEntity();
        String s = EntityUtils.toString(entity,"UTF-8");

        return s;
    }

}
