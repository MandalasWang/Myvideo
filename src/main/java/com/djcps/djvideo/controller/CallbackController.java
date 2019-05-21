package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.enums.PayStateEnum;
import com.djcps.djvideo.service.VideoOrderService;
import com.djcps.djvideo.utils.WxPayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 有缘
 * @date  19/5/21
 */
@RestController
@RequestMapping("/api/v1/order/")
public class CallbackController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private VideoOrderService videoOrderService;

@RequestMapping(value = "/wxCallback")
    public RetResult<Object> wxCallback(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String xmlData = WxPayUtils.readData(request);
        Map<String,String> callback = WxPayUtils.xmlToMap(xmlData);
        callback.remove("sign");
        SortedMap<String, String> sortedMap = new TreeMap<>(callback);
        boolean flag = false;
        flag = WxPayUtils.isCorrectSign(sortedMap, weChatConfig.getKey());
        //商户订单号
        String outTradeNo = callback.get("out_trade_no");
        //获取数据库的订单信息
        VideoOrder videoOrder = videoOrderService.findOrderByOutTradeNo(outTradeNo);
        if (flag) {
             //判断是否重复接收通知
            if (videoOrder.getState().equals(PayStateEnum.SUCCEED.getState())) {
                return RetResponse.makeErrRsp("请勿重复支付");
            }
            if (videoOrder == null) {
               /* logger.debug("根据微信异步回调:返回的商户订单号查询支付数据库的支付数据出来的结果为null");*/
                return RetResponse.makeErrRsp("请勿重复支付");
            }
            if (!videoOrder.getTotalFee().equals(callback.get("total_fee"))) {
              /*  logger.debug("微信异步回调:返回的订单金额与商户支付数据的订单金额不一致");*/
                return RetResponse.makeErrRsp("请勿重复支付");
            }
            //更改支付数据库的支付状态
            int row = videoOrderService.updatePayState(outTradeNo, PayStateEnum.SUCCEED.getState());
            if (row != 1) {
                /*logger.debug("微信异步回调:更新数据库支付失败");*/
                return RetResponse.makeErrRsp("订单信息更新失败");
            }
        }
        return RetResponse.makeErrRsp();
    }
}
