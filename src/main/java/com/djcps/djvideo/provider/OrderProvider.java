package com.djcps.djvideo.provider;

import com.djcps.djvideo.domain.VideoOrder;
import org.apache.ibatis.jdbc.SQL;


/**
 * @author 有缘
 */
public class OrderProvider {
    /**
     * 更新video动态语句
     * @param videoOrder
     * @return
     */
    public String updateOrder(final VideoOrder videoOrder){
        return new SQL(){{
            UPDATE("video_order");

            //条件写法.
            if(videoOrder.getState()!= null){
                SET("state=#{state}");
            }
            if(videoOrder.getCreateTime()!= null){
                SET("create_time=#{createTime}");
            }
            if(videoOrder.getTotalFee()!= null){
                SET("total_fee=#{totalFee}");
            }
            if(videoOrder.getNotifyTime()!= null){
                SET("notify_time=#{notifyTime}");
            }
            if(videoOrder.getOpenid()!= null){
                SET("openid=#{openid}");
            }
            WHERE("out_trade_no=#{outTradeNo} and state = 0 and del=0");
        }}.toString();
    }



}
