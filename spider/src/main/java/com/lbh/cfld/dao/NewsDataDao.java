package com.lbh.cfld.dao;


import com.lbh.cfld.model.NewsData;
import com.lbh.cfld.model.NewsType;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface NewsDataDao {
    @Insert("insert into news_data " +
            "select null,#{url},#{title},#{content},#{time},#{typeName} from dual where NOT EXISTS (SELECT * FROM " +
            "news_data where url = #{url})")
    Integer insertOne(NewsData data);


    @Select("SELECT n.* FROM news_data AS n WHERE NOT EXISTS(SELECT * FROM acfun_news WHERE news_id =n.id ) AND n.title !='' and !ISNULL(n.title)")
    @Results({
            @Result(property="typeName",column="typeName"),
            @Result(property="newsType",column="typeName",one=@One(select="com.lbh.cfld.dao.NewsJobDao.getNewsType"))
    })
    ArrayList<NewsData> getList();

}
