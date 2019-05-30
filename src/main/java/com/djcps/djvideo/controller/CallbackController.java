package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.enums.PayStateEnum;
import com.djcps.djvideo.service.VideoOrderService;
import com.djcps.djvideo.utils.ComonUtils;
import com.djcps.djvideo.utils.WxPayUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Security;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author 有缘
 * @date 19/5/21
 */
@RestController
@RequestMapping("/api/v1/order/")
public class CallbackController {

    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private VideoOrderService videoOrderService;


    /**
     * 支付异步通知回调
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wxCallback")
    public RetResult<Object> wxCallback(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String xmlData = ComonUtils.readData(request);
        Map<String, String> callback = WxPayUtils.xmlToMap(xmlData);
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

    /**
     * 微信退款异步回调通知
     *
     * @param
     * @return
     * @throws
     */
    @RequestMapping(value = "/refundWxCallBack")
    public String refundWxCallBack(HttpServletRequest request) {
        try {
            String xmlMsg = ComonUtils.readData(request);
            if (StringUtils.isBlank(xmlMsg)) {
                return ("退款失败!");
            }
            Map<String, String> resultMap = WxPayUtils.xmlToMap(xmlMsg);
            String return_code = resultMap.get("return_code");
            // logger.debug("微信退款通知-return_code：" + return_code);
            /*在return_code为SUCCESS才有返回数据*/
            if (!("SUCCESS").equals(return_code) || StringUtils.isBlank(return_code)) {
                return ("退款失败，返回支付！");
            }
            String base64Result = resultMap.get("req_info").toString();
            /* logger.debug("微信退款-base64Result:" + base64Result);*/
            Security.addProvider(new BouncyCastleProvider());
            //进行数据64解密 md5解密 aes解密   weixinpayutil 添加类
            String result = ComonUtils.decryptData(base64Result);
            //对结果数据进行xml转map     WxPaySignUtil core包下的
            Map<String, String> map = WxPayUtils.xmlToMap(result);
            if (map.isEmpty()) {
                return ("订单不存在！");
            }
            //商户订单号
            String outTradeNo = map.get("out_trade_no");
            //状态码
            String refund_status = map.get("refund_status");
            /*logger.info("微信退款通知-refund_status：" + refund_status);*/
            if (!("SUCCESS").equals(refund_status) || StringUtils.isBlank(refund_status)) {
                return ("退款失败！");
            }
            //退款通知信息接收成功  更改数据库订单状态为退款成功
            int row = videoOrderService.updatePayState(outTradeNo, PayStateEnum.REFUND.getState());
            if (row > 0) {
                return ("退款成功！");
            }
            return "退款失败！";
        } catch (Exception e) {
            /* logger.info("微信退款回调发生错误：", e);*/
            return "退款失败，返回支付！";
        }
    }

}
