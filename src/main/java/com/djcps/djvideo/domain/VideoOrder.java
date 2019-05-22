package com.djcps.djvideo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 * @author 有缘
 */
public class VideoOrder implements Serializable {

  private Integer id;
  private String openid;
  private String outTradeNo;
  private Integer state;
  private Date createTime;
  private Date notifyTime;
  private BigDecimal totalFee;
  private String nickname;
  private String headImg;
  private Integer videoId;
  private String videoTitle;
  private String videoImg;
  private Integer userId;
  private String ip;
  private Integer del;

  public VideoOrder() {
  }

  public VideoOrder(Integer id, String outTradeNo, Integer state, Date createTime,
                    BigDecimal totalFee, String nickname, Integer videoId, String videoTitle, Integer userId) {
    this.id = id;
    this.outTradeNo = outTradeNo;
    this.state = state;
    this.createTime = createTime;
    this.totalFee = totalFee;
    this.nickname = nickname;
    this.videoId = videoId;
    this.videoTitle = videoTitle;
    this.userId = userId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }


  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }


  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }


  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Date getNotifyTime() {
    return notifyTime;
  }

  public void setNotifyTime(Date notifyTime) {
    this.notifyTime = notifyTime;
  }


  public BigDecimal getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(BigDecimal totalFee) {
    this.totalFee = totalFee;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getHeadImg() {
    return headImg;
  }

  public void setHeadImg(String headImg) {
    this.headImg = headImg;
  }


  public Integer getVideoId() {
    return videoId;
  }

  public void setVideoId(Integer videoId) {
    this.videoId = videoId;
  }


  public String getVideoTitle() {
    return videoTitle;
  }

  public void setVideoTitle(String videoTitle) {
    this.videoTitle = videoTitle;
  }


  public String getVideoImg() {
    return videoImg;
  }

  public void setVideoImg(String videoImg) {
    this.videoImg = videoImg;
  }


  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }


  public Integer getDel() {
    return del;
  }

  public void setDel(Integer del) {
    this.del = del;
  }

  @Override
  public String toString() {
    return "VideoOrder{" +
            "id=" + id +
            ", openid='" + openid + '\'' +
            ", outTradeNo='" + outTradeNo + '\'' +
            ", state=" + state +
            ", createTime=" + createTime +
            ", notifyTime=" + notifyTime +
            ", totalFee=" + totalFee +
            ", nickname='" + nickname + '\'' +
            ", headImg='" + headImg + '\'' +
            ", videoId=" + videoId +
            ", videoTitle='" + videoTitle + '\'' +
            ", videoImg='" + videoImg + '\'' +
            ", userId=" + userId +
            ", ip='" + ip + '\'' +
            ", del=" + del +
            '}';
  }
}
