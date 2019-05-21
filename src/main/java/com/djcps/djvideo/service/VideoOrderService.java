package com.djcps.djvideo.service;

import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.dto.VideoOrderDto;

public interface VideoOrderService {
    /**
     * 下单
     * @param videoOrderDto
     * @return
     */
    VideoOrder save(VideoOrderDto videoOrderDto);
}
