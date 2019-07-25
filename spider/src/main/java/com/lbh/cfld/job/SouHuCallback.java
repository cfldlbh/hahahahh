package com.lbh.cfld.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lbh.cfld.analysis.AnalysisUtils;
import com.lbh.cfld.model.NewsData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SouHuCallback extends Callback {

    @Override
    /**
     * Description:
     * @author: cfld
     * @date:  2019/7/25 21:06
     * @param: [url]
     * @return: java.util.List<java.lang.String>
     **/
    public List<String> getUtl_List(String url) {
        List<String> result = new ArrayList<String>();
        String s = this.get(url);
        String substring = s.substring(s.indexOf("["), s.lastIndexOf("]") + 1);
        JSONArray objects = JSONObject.parseArray(substring);
        Iterator<Object> iterator = objects.iterator();
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
