package com.djcps.djvideo.provider;

import com.djcps.djvideo.domain.VideoOrder;
import com.djcps.djvideo.dto.VideoOrderDto;
import org.apache.ibatis.jdbc.SQL;


/**
 * @author 有缘
 */
public class OrderProvider {
    /**
     * 更新video动态语句
     * @param
     * @return
     */
    public String updateOrder(final VideoOrderDto videoOrderDto){
        return new SQL(){{
            UPDATE("video_order");

            //条件写法.
            if(videoOrderDto.getState()!= null){
                SET("state=#{state}");
            }
            if(videoOrderDto.getCreateTime()!= null){
                SET("create_time=#{createTime}");
            }
            if(videoOrderDto.getTotalFee()!= null){
                SET("total_fee=#{totalFee}");
            }
            if(videoOrderDto.getNotifyTime()!= null){
                SET("notify_time=#{notifyTime}");
            }
            if(videoOrderDto.getOpenid()!= null){
                SET("openid=#{openid}");
            }
            WHERE("out_trade_no=#{outTradeNo} and state = 0 and del=0");
        }}.toString();
    }



}
