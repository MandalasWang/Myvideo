package com.djcps.djvideo.service;


/**
 * @author 有缘
 */
public interface UserService {
    /**
     * 保存code中的用户信息
     * @param code
     * @return
     */
    int saveWeChatUser(String code);
}
