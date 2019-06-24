package com.djcps.djvideo.dto;


public class VideoQuery {
    /**
     * 产品名
     */
    private String videoName;
    /**
     * 上架时间
     */
    private String startTime;
    /**
     * 下架时间
     */
    private String endTime;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
