package com.djcps.djvideo.service;

import com.djcps.djvideo.domain.Video;

import java.util.List;

/**
 * @author 有缘
 */
public interface VideoService {

    List<Video> findAll();

    Video findByid(int id);

    int update(Video video);

    int delete(int id);

    int save(Video video);
}
