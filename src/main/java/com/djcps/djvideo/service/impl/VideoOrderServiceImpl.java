package com.djcps.djvideo.service.impl;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
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
import com.djcps.djvideo.utils.HttpUtils;
import com.djcps.djvideo.utils.WxPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 有缘
 */
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
    public String save(VideoOrderDto videoOrderDto) {
        Video video = videoMapper.findByid(videoOrderDto.getVideoId());
        User user = userMapper.findByid(videoOrderDto.getUserId());
        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setVideoId(video.getId());
        videoOrder.setCreateTime(new Date());
        videoOrder.setOpenid(user.getOpenid());

        videoOrder.setState(0);
        videoOrder.setUserId(user.getId());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setNickname(user.getName());
        videoOrder.setDel(0);
        videoOrder.setOutTradeNo(ComonUtils.generateUUID());
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrderMapper.insert(videoOrder);
        String codeUrl = "";
        try {
          codeUrl =  unifiedOrder(videoOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取codeurl

        return codeUrl;
    }

    @Override
    public VideoOrder findOrderByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findOrderByOutTradeNo(outTradeNo);
    }

    @Override
    public int updatePayState(String outTradeNo, int state) {
       VideoOrder videoOrder = new VideoOrder();
       videoOrder.setOpenid(weChatConfig.getOpenAppid());
       videoOrder.setOutTradeNo(outTradeNo);
       videoOrder.setDel(0);
       videoOrder.setState(0);
       videoOrder.setNotifyTime(new Date());
       return videoOrderMapper.updateVideoOderByOutTradeNo(videoOrder);
    }

    /**
     * 统一下单
     *
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
        String orderreturn = HttpUtils.doPost(WeChatConfig.getUnifiredOrderUrl(), requestXMl, 4000);
        if(orderreturn == null){
            return null;
        }
        Map<String ,String> unifredmap = WxPayUtils.xmlToMap(orderreturn);
        //下单成功返回一个二维码
        if(unifredmap !=null){
            return unifredmap.get("code_url");
        }else {
            return null;
        }

    }

    @Override
    public int updateOrder(VideoOrderDto videoOrderDto) {
       VideoOrder order = videoOrderMapper.findById(Integer.valueOf(videoOrderDto.getId()));
       if(order == null){
           return 2 ;
       }
       else {
           return videoOrderMapper.UpdateOrder(videoOrderDto);
       }
    }

    @Override
    public RetResult delete(VideoOrderDto videoOrderDto) {
        int i = videoOrderMapper.delete(videoOrderDto.getId());
        return i==1?RetResponse.makeOKRsp():RetResponse.makeErrRsp("删除失败");
    }
}
