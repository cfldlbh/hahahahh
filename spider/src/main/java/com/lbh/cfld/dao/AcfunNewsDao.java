package com.lbh.cfld.dao;

import com.lbh.cfld.model.AcfunNews;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * ClassName: AcfunNewsDao
 * Description:
 * date: 2019/7/29 21:49
 *
 * @author cfld
 * @since JDK 1.8
 */
public interface AcfunNewsDao {
    @Select("SELECT * FROM acfun_news WHERE news_id = #{newsId}")
    public AcfunNews getBynewsId(Integer newsId);

    @Insert("INSERT INTO acfun_news SELECT null,#{typeId},#{acfunUrl},#{acfunId},#{time},#{newsId}" +
            " FROM dual WHERE NOT EXISTS(SELECT time FROM acfun_news WHERE news_id = #{newsId})")
    Integer insertOne(AcfunNews acfunNews);
}
