package com.example.googlemap.googlemap;

/**
 * Created by SCITMASTER on 2018-02-27.
 */

public class ChatData {
    private String userName;
    private String message;
    private String coupleID;
    private int readcheck;
    private String date;

    public int getReadcheck() {
        return readcheck;
    }

    public void setReadcheck(int readcheck) {
        this.readcheck = readcheck;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ChatData(String userName, String message, String coupleID, int readcheck, String date) {

        this.userName = userName;
        this.message = message;
        this.coupleID = coupleID;
        this.readcheck = readcheck;
        this.date = date;
    }

    public ChatData() { }

    public String getCoupleID() {
        return coupleID;
    }

    public void setCoupleID(String coupleID) {
        this.coupleID = coupleID;
    }

    public ChatData(String userName, String message, String coupleID) {
        this.userName = userName;
        this.message = message;
        this.coupleID = coupleID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}