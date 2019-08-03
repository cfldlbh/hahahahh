package com.lbh.cfld.model;

public class NewsJob {
    private Integer id;
    private String className;
    private Integer typeId;
    private String description;
    private NewsType newsType;
    private String url;
    private String analysisClass;


    public String getAnalysisClass() {
        return analysisClass;
    }

    public void setAnalysisClass(String analysisClass) {
        this.analysisClass = analysisClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
