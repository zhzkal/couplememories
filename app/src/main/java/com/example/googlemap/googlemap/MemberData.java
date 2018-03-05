package com.example.googlemap.googlemap;

/**
 * Created by SCITMASTER on 2018-02-28.
 */

public class MemberData {

    String id;
    String pw;
    String name;
    String coupleid;



    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoupleid(String coupleid) {
        this.coupleid = coupleid;
    }

    public String getId() {

        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getCoupleid() {
        return coupleid;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", coupleid='" + coupleid + '\'' +
                '}';
    }

    MemberData(String name, String pw, String id, String coupleid) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.coupleid = coupleid;
    }
    MemberData(){}
}
