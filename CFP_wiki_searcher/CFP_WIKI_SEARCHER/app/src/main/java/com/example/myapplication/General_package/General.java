package com.example.myapplication.General_package;

import org.w3c.dom.Text;

public class General {
    private int id;
    private String name;
    private int start;
    private int end;
    private String where;
    private int deadline;
    private String brief;
    public General(int id, String name, int start, int end, String where, int deadline, String brief){
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.where = where;
        this.deadline = deadline;
        this.brief = brief;
    }
    @Override
    public String toString() {
        return "General{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", where='" + where + '\'' +
                ", deadline=" + deadline +
                ", brief=" + brief +
                '}';
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getStart() {return start;}
    public int getEnd() {return end;}
    public String getWhere() {
        return where;
    }
    public int getDeadline() { return deadline; }
    public String getBrief() {
        return brief;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setStart(String name) {
        this.start = start;
    }
    public void setEnd(String name) {
        this.end = end;
    }
    public void setWhere(String where) {
        this.where = where;
    }
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
}
