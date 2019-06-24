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
     *修改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 保存用户信息
     * @param file
     * @param request
     * @return
     */
    RetResult save(MultipartFile file, HttpServletRequest request);

    /**
     *用户登录
     * @param user
     */
    RetResult login(User user);


}
