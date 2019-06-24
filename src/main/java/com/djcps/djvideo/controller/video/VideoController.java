package com.djcps.djvideo.controller.video;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.dto.VideoQuery;
import com.djcps.djvideo.service.VideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 有缘
 * @date  19/5/21
 */
@RestController
@RequestMapping("/api/v1/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 分页
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/page")
    public RetResult<PageInfo> findAll(@RequestParam(value = "page" ,defaultValue = "1") int page,
                                          @RequestParam(value = "size",defaultValue = "10") int size) {
        PageHelper.startPage(page,size);
        List<Video> list = videoService.findAll();
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        if (list.size() < 1) {
            return RetResponse.makeErrRsp("请求失败!");
        } else {
            return RetResponse.makeOKRsp(pageInfo);
        }
    }

    /**
     * 根据id查询视频
     * @param videoid
     * @return
     */
    @GetMapping("/find_by_id")
    public RetResult<Video> findByid(@RequestParam(value = "video_id",required = true) int videoid) {
        Video video = videoService.findByid(videoid);
        if (video == null) {
            return RetResponse.makeErrRsp("请求失败！");
        } else {
            return RetResponse.makeOKRsp(video);
        }
    }

    public RetResult<PageInfo> getVideoByCondition(@RequestParam("pageSize") int pageSize,
                                           @RequestParam("pageNum")int pageNum,
                                           @RequestBody VideoQuery videoQuery){
        PageHelper.startPage(pageNum,pageSize);
        List<Video> list = videoService.findByCondition(videoQuery);
        PageInfo<Video> pageInfo = new PageInfo<>(list);
        if(list.size() < 1){
           return  RetResponse.makeOKRsp("数据查询不到");
        }else {
           return RetResponse.makeOKRsp(pageInfo);
        }
    }


}
