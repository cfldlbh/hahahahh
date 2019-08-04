package com.lbh.cfld.dao;

import com.lbh.cfld.model.NewsJob;
import com.lbh.cfld.model.NewsType;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsJobDao {
    @Select("SELECT * FROM news_type WHERE id = (SELECT type_id FROM news_job WHERE className = #{className})")
    NewsType getJobByClassName(String className);

    //@Select("SELECT * FROM news_job,news_type WHERE news_job.id = news_type.id")
    @Select("SELECT * FROM  news_job ")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property="newsType",column="typeId",one=@One(select="com.lbh.cfld.dao.NewsJobDao.getNewsType"))
    })
    List<NewsJob> getNewsJobList();
    @Select("SELECT * FROM news_type WHERE id = #{id}")
    NewsType getNewsType(Integer id);
}
