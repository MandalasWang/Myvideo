package com.djcps.djvideo.service;

import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.dto.VideoQuery;

import java.util.List;

/**
 * @author 有缘
 */
public interface VideoService {
    /**
     * 查询所有订单
     * @return
     */
    List<Video> findAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Video findByid(int id);

    /**
     * 更新订单
     * @param video
     * @return
     */
    int update(Video video);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 保存订单
     * @param video
     * @return
     */
    int save(Video video);

    /**
     *
     * @return
     */
    List<Video> findByCondition(VideoQuery videoQuery);
}
