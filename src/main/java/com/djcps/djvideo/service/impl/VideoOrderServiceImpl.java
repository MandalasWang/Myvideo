package com.djcps.djvideo.service.impl;

import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.domain.User;
import com.djcps.djvideo.domain.Video;
import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.dto.VideoOrderDto;
import com.djcps.djvideo.mapper.UserMapper;
import com.djcps.djvideo.mapper.VideoMapper;
import com.djcps.djvideo.mapper.VideoOrderMapper;
import com.djcps.djvideo.service.VideoOrderService;
import com.djcps.djvideo.utils.ComonUtils;
import com.djcps.djvideo.utils.WxPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;


@Service
public class VideoOrderServiceImpl implements VideoOrderService {
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public VideoOrder save(VideoOrderDto videoOrderDto)  {
        Video video = videoMapper.findByid(videoOrderDto.getVideoId());
        User user  = userMapper.findByid(videoOrderDto.getUserId());
        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setHeadImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setVideoId(video.getId());
        videoOrder.setCreateTime(new Date());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());
        videoOrder.setDel(0);
        videoOrder.setOutTradeNo(ComonUtils.generateUUID());
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrderMapper.insert(videoOrder);
        try {
            unifiedOrder(videoOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取codeurl

        //生成二维码

        return videoOrder;
    }

    /**
     * 统一下单
     * @param videoOrder
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder) throws Exception {
        //生成签名
        SortedMap<String, String> params = new TreeMap<>();
        //公众账号ID
        params.put("appid", weChatConfig.getAppId());
        //商户号
        params.put("mch_id", weChatConfig.getMchId());
        //随机字符串
        params.put("nonce_str", ComonUtils.generateUUID());
        // 商品描述
        params.put("body", videoOrder.getVideoTitle());
        //商户订单号,商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
        params.put("out_trade_no", videoOrder.getOutTradeNo());
        //标价金额	分
        params.put("total_fee", videoOrder.getTotalFee().toString());
        params.put("spbill_create_ip", videoOrder.getIp());
        //通知地址
        params.put("notify_url", weChatConfig.getPayCallbackUrl());
        //交易类型 JSAPI 公众号支付 NATIVE 扫码支付 APP APP支付
        params.put("trade_type", "NATIVE");
        //生成sign签名
        String sign = WxPayUtils.createSign(params, weChatConfig.getKey());
        params.put("sign", sign);
        //参数转xml
        String requestXMl = WxPayUtils.mapToXml(params);

        //统一下单
        return requestXMl;
    }
}
