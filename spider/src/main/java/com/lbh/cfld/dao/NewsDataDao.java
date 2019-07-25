package com.lbh.cfld.dao;


import com.lbh.cfld.model.NewsData;
import org.apache.ibatis.annotations.Insert;

public interface NewsDataDao {
    @Insert("insert into news_data " +
            "select null,#{url},#{title},#{content},#{time},#{typeName} from dual where NOT EXISTS (SELECT * FROM " +
            "news_data where url = #{url})")
    Integer insertOne(NewsData data);
}
