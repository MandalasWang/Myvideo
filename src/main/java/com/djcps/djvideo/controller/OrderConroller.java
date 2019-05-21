package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.dto.VideoOrderDto;
import com.djcps.djvideo.service.VideoOrderService;
import com.djcps.djvideo.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyy
 * @date 19/5/21
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderConroller {
    @Autowired
    private VideoOrderService videoOrderServicel;

    /**
     *
     * @param videoId
     * @param request
     * @return
     */
    @GetMapping(value = "/add")
    public RetResult<Object> saveOrder(@RequestParam(value = "video_id", required = true) int videoId,
                                       HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        //int userId = request.getAttribute("user_id");
        int userId = 1;
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        videoOrderServicel.save(videoOrderDto);
        return RetResponse.makeOKRsp("下单成功!");
    }
}
