package com.lbh.cfld.model;

import java.sql.Timestamp;

/**
 * ClassName: AcfunNews
 * Description:
 * date: 2019/7/29 21:47
 *
 * @author cfld
 * @since JDK 1.8
 */
public class AcfunNews {
    private Integer id;
    private String acfunUrl;
    private String acfunId;
    private Integer typeId;
    private Timestamp time;
    private Integer newsId;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAcfunUrl() {
        return acfunUrl;
    }

    public void setAcfunUrl(String acfunUrl) {
        this.acfunUrl = acfunUrl;
    }

    public String getAcfunId() {
        return acfunId;
    }

    public void setAcfunId(String acfunId) {
        this.acfunId = acfunId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
