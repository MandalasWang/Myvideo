package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.dto.VideoOrderDto;
import com.djcps.djvideo.service.VideoOrderService;
import com.djcps.djvideo.utils.IpUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
                                       HttpServletRequest request, HttpServletResponse response) {
        /*String ip = IpUtils.getIpAddr(request);*/
        /*int userId = request.getAttribute("user_id");*/
        int userId = 1;
        String ip = "192.168.2.92";
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

       String codeUrl =  videoOrderServicel.save(videoOrderDto);
       //生成二维码
        if(codeUrl == null) {
            throw new  NullPointerException();
        }

        try{
            //生成二维码配置
            Map<EncodeHintType,Object> hints =  new HashMap<>();

            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            //编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out =  response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);

        }catch (Exception e){
            e.printStackTrace();
        }
        return RetResponse.makeOKRsp("下单成功!");
    }
}
