package com.example.myapplication.Content_package;

import org.w3c.dom.Text;

public class ContentText {
    private int id;
    private String name;
    private String nameList;
    private String url;
    private int notiDue;
    private int fvDue;
    private String content;

    public ContentText(int id, String name, String nameList, String url, int notiDue, int fvDue, String content){
        this.id = id;
        this.name = name;
        this.nameList = nameList;
        this.url = url;
        this.notiDue = notiDue;
        this.fvDue = fvDue;
        this.content = content;
    }
    @Override
    public String toString() {
        return "ContentText{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameList='" + nameList + '\'' +
                ", url=" + url +
                ", notiDue=" + notiDue +
                ", fvDue=" + fvDue +
                ", content='" + content + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNameList() {
        return nameList;
    }
    public void setNameList(String nameList) {
        this.nameList = nameList;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String geturl() {
        return url;
    }
    public void seturl(String url) {
        this.url = url;
    }
    public int getNotiDue() {
        return notiDue;
    }
    public void setNotiDue(int notiDue) {
        this.notiDue = notiDue;
    }
    public int getFvDue() {
        return fvDue;
    }
    public void setFvDue(int fvDue) {
        this.fvDue = fvDue;
    }

}
