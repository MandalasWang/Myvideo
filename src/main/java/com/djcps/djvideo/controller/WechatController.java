package com.djcps.djvideo.controller;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RequestMapping(value = "/api/v1/wechat")
@Controller
public class WechatController {
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login_url")
    @ResponseBody
    public RetResult<String> loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) {
        //获取开放平台重定向地址
        String redirectUrl = weChatConfig.getOpenRedirectUrl();
        String callbackUrl = null;
        try {
            //进行编码
            callbackUrl  = URLEncoder.encode(redirectUrl, "GBK");
        }catch (Exception e){

        }
        String qrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(), weChatConfig.getOpenAppid(),callbackUrl,accessPage);

        return RetResponse.makeOKRsp(qrcodeUrl);
    }

    @GetMapping("/user/callback")
    public void wechatUserCallback(@RequestParam(value = "code", required = true) String code,
                                   String state, HttpServletResponse response) {
        userService.saveWeChatUser(code);

    }
}
