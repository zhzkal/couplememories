package com.example.googlemap.googlemap;

import java.io.Serializable;

/**
 * Created by SCITMASTER on 2018-03-09.
 */

public class SpdayData implements Serializable {
    String spday;
    String key;

    public String getKey() {
        return key;
    }
    SpdayData(){}

    public void setKey(String key) {
        this.key = key;
    }

    public String getSpday() {
        return spday;
    }

    public void setSpday(String spday) {
        this.spday = spday;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SpdayData(String spday, String content) {

        this.spday = spday;

        this.content = content;
    }


    String content;

}
