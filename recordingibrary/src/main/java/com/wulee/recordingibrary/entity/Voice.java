package com.wulee.recordingibrary.entity;

/**
 * 类名：Voice.java
 */

public class Voice {
    private String filePath;
    private long length;
    private String strLength;

    public Voice(long length, String strLength, String filePath){
        this.length=length;
        this.strLength=strLength;
        this.filePath=filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getStrLength() {
        return strLength;
    }

    public void setStrLength(String strLength) {
        this.strLength = strLength;
    }
}
