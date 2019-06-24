package com.djcps.djvideo.provider;

import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.dto.VideoQuery;
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

    /**
     * 条件查询SQL语句
     * @param video
     * @return
     */
    public String selectBycondition(final VideoQuery videoQuery){
        return new SQL(){{
            SELECT("title","summary","cover_img","view_num","price","create_time","online","point");
            FROM("video");
            //条件写法.
            WHERE("title  like ${%videoName%}" );
            OR();
            WHERE("create_time between #{startTime} and #{endTime}" );
        }}.toString();
    }
}
