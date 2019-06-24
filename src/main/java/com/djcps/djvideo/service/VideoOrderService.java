package com.djcps.djvideo.service;

import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.dto.VideoOrderDto;

/**
 * @author 有缘
 */
public interface VideoOrderService {
    /**
     * 下单
     * @param videoOrderDto
     * @return
     */

    String save(VideoOrderDto videoOrderDto);

    /**
     * 根据三方订单号查询
     * @param outTradeNo
     * @return
     */
    VideoOrder findOrderByOutTradeNo(String outTradeNo);

    /**
     * 根据三方订单号更新订单的交易信息
     * @param outTradeNo
     * @param state
     * @return
     */
    int updatePayState(String outTradeNo, int state);

    /**
     * 跟新用户订单信息
     * @param oid
     * @return
     */
    int updateOrder(VideoOrderDto videoOrderDto);

    /**
     * 删除订单
     * @param videoOrderDto
     * @return
     */
    RetResult delete(VideoOrderDto videoOrderDto);
}
