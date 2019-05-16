package com.djcps.djvideo.provider;

import com.djcps.djvideo.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 有缘
 */
public class VideoProvider {
    /**
     * 更新video动态语句
     * @param video
     * @return
     */
    public String updateVideo(final Video video){
        return new SQL(){{
            UPDATE("video");

            //条件写法.
            if(video.getTitle()!= null){
                SET("title=#{title}");
            }
            if(video.getCoverImg()!= null){
                SET("coverImg=#{coverImg}");
            }
            if(video.getOnline()!= null){
                SET("online=#{online}");
            }
            if(video.getPoint()!= null){
                SET("point=#{point}");
            }
            if(video.getPrice()!= null){
                SET("price=#{price}");
            }
            if(video.getSummary()!= null){
                SET("summary=#{summary}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }
}
