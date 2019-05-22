package com.djcps.djvideo.controller.user;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.domain.User;
import com.djcps.djvideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 有缘
 * @date 19/5/22
 */
@RestController
@RequestMapping(value = "user/api/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/update")
    public RetResult updateuserMsg(@RequestBody User user) {
        int row = userService.updateUser(user);
        if (row == 0) {
            RetResponse.makeErrRsp("用户信息更新失败！");
        }
        return RetResponse.makeOKRsp();
    }
}
