package com.example.myapplication.KeyWord_package;

public class KeyWordSet {
    private int id;
    private String name;
    private String keyword;

    public KeyWordSet(int id, String name, String keyword){
        this.id = id;
        this.name = name;
        this.keyword = keyword;
    }
    public KeyWordSet(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return "KeyWordSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }
    public String getKeyword() {
        return keyword;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
