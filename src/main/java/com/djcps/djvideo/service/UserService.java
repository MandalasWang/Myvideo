package com.djcps.djvideo.service;


import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.domain.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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

    /**
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    RetResult save(MultipartFile file, HttpServletRequest request);
}
