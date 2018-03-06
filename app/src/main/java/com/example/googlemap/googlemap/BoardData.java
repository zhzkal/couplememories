package com.example.googlemap.googlemap;

/**
 * Created by SCITMASTER on 2018-02-28.
 */

public class BoardData {

    private String title;
    private String ID;
    private String picture;
    private String content;
    private String coupleid;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BoardData(String title, String ID, String picture, String content, String coupleid, String date) {
        this.title = title;
        this.ID = ID;
        this.picture = picture;
        this.content = content;
        this.coupleid = coupleid;
        this.date = date;
    }

    public BoardData(String title, String ID, String picture, String content, String coupleid) {
        this.title = title;
        this.picture = picture;
        this.content = content;
        this.ID = ID;
        this.coupleid = coupleid;
    }

    public BoardData(){};

    @Override
    public String toString() {
        return "BoardData{" +
                "title='" + title + '\'' +
                ", ID='" + ID + '\'' +
                ", picture='" + picture + '\'' +
                ", content='" + content + '\'' +
                ", coupleid='" + coupleid + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID() {
        return ID;
    }


    public String getContent() {
        return content;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCoupleid() {
        return coupleid;
    }

    public void setCoupleid(String coupleid) {
        this.coupleid = coupleid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
}
