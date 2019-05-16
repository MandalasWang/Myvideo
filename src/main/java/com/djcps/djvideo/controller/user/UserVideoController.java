package com.djcps.djvideo.controller.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 有缘
 */
@RestController
@RequestMapping(value = "/api/v1/wechat/user")
public class UserVideoController {

    public void callback(@RequestParam(value = "code",required = true) String code){

    }

}
