package com.djcps.djvideo.service.impl;

import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.dto.VideoQuery;
import com.djcps.djvideo.mapper.VideoMapper;
import com.djcps.djvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 有缘
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> findAll() {
        return videoMapper.list();
    }

    @Override
    public Video findByid(int id) {
        return videoMapper.findByid(id);
    }

    @Override
    public int update(Video video) {
        return videoMapper.update(video);
    }

    @Override
    public int delete(int id) {
        return videoMapper.delete(id);
    }

    @Override
    public int save(Video video) {
        return videoMapper.save(video);
    }

    @Override
    public List<Video> findByCondition(VideoQuery videoQuery) {
        return videoMapper.getVideoByCondition(videoQuery);
    }
}
