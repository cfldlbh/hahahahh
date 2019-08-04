package com.lbh.cfld.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XinLangCallback extends SouHuCallback {
    @Override
    public List<String> getUtl_List(String url) {
        List<String> result = new ArrayList<String>();
        String s = this.get(url);
        String substring = s.substring(s.indexOf("(")+1, s.lastIndexOf(")") );
        JSONObject objects = JSONObject.parseObject(substring);
        JSONArray data = objects.getJSONArray("data");
        Iterator<Object> iterator = data.iterator();
        while (iterator.hasNext()){
            JSONObject next = (JSONObject)iterator.next();
            String url2 = next.getString("url");
            if(url2!=null){
                boolean url1 =url2 .startsWith("//");
                if(url1){
                    url2 = "http:"+url2;
                }
                result.add(url2);
            }
        }
        return result;
    }
}
