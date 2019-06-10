package com.djcps.djvideo.mapper;


import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.provider.VideoProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 有缘
 * 视频dao层
 */
public interface VideoMapper {
    /**
     * 查询video list
     * @return
     */
    @Select("select title,summary,cover_img,price,point  from video ")
    List<Video> list();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select title,summary,cover_img,price,point,online,create_time from video where id =#{id}")
    Video findByid(int id);

    /**
     * 修改video
     * @param video
     * @return
     */
    @UpdateProvider(type = VideoProvider.class,method = "updateVideo")
    int update(Video video);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Delete("DELETE FROM video WHERE id =#{id}")
    int delete(int id);

    /**
     * 保存video
     * @param video
     * @return
     */
    @Insert("INSERT INTO `video` ( `title`, `summary`, " +
            "`cover_img`, `view_num`, `price`, `create_time`," +
            " `online`, `point`)" +
            "VALUES" +
            "(#{title}, #{summary}, #{coverImg}, #{viewNum}, #{price},#{createTime}" +
            ",#{online},#{point});")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int save(Video video);
}
