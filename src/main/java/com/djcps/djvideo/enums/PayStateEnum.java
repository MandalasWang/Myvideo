package com.djcps.djvideo.enums;

/**
 * @author 有缘
 * @date  19/5/21
 */
public enum PayStateEnum {
    /**初始化**/
    @Deprecated
    INITIALIZE(0),
    /**成功**/
    SUCCEED(1),
    /**处理中**/
    PROCESSED(2),
    /**失败**/
    FAILURE(3),
    /**退款**/
    REFUND(4);

    private int state;

    PayStateEnum(int state){
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
