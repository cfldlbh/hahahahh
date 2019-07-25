package com.lbh.cfld.job;

import com.lbh.cfld.analysis.AnalysisUtils;

import java.util.List;

public abstract class Callback {
   abstract List<String> getUtl_List(String url);

   public String get(String url){
       String http = AnalysisUtils.getHttp(url);
       return http;
   }
}
