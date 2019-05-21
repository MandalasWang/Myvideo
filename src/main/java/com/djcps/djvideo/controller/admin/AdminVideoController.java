package com.djcps.djvideo.controller.admin;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 有缘
 * @date 19/5/21
 */
@RequestMapping("admin/api/v1/video")
@RestController
public class AdminVideoController {
    @Autowired
    private VideoService videoService;


    /**
     * 更新视频
     * @param video
     * @return
     */
    @PutMapping("/update_by_id")
    public RetResult<String> update(@RequestBody Video video) {
        if (videoService.update(video) > 0) {
            return RetResponse.makeErrRsp("视频更新失败！");
        } else {
            return RetResponse.makeOKRsp("视频更新成功!");
        }
    }

    /**
     * 根据id删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("del_by_id")
    public RetResult<String> delByid(@RequestParam("video_id") int videoId) {
        if (videoService.delete(videoId) == 0) {
            return RetResponse.makeErrRsp("视频删除失败！");
        } else {
            return RetResponse.makeOKRsp("视频删除成功");
        }
    }

    /**
     * 保存视频
     * @param video
     * @return
     */
    @PostMapping("save")
    public RetResult<String> save(@RequestBody Video video) {
        if (videoService.save(video) == 0) {
            return RetResponse.makeErrRsp("数据保存失败!");
        } else {
            return RetResponse.makeOKRsp();
        }
    }
}
