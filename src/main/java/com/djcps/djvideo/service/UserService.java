package com.djcps.djvideo.service;


import com.djcps.djvideo.domain.User;

/**
 * @author 有缘
 */
public interface UserService {
    /**
     * 保存code中的用户信息
     * @param code
     * @return
     */
    User saveWeChatUser(String code);
}
