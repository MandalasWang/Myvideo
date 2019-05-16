package com.djcps.djvideo.service.impl;

import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.service.UserService;
import com.djcps.djvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 有缘
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatConfig weChatConfig;

    @Override
    public int saveWeChatUser(String code) {
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),weChatConfig.getOpenAppid(),weChatConfig.getOpenAppsevcret(),code);

        //获取access_token
        Map<String ,Object> baseMap =  HttpUtils.doGet(accessTokenUrl);

        if(baseMap == null || baseMap.isEmpty()){ return  0; }
        String accessToken = (String)baseMap.get("access_token");
        String openId  = (String) baseMap.get("openid");


        //获取用户基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);
        //获取access_token
        Map<String ,Object> baseUserMap =  HttpUtils.doGet(userInfoUrl);

        if(baseUserMap == null || baseUserMap.isEmpty()){ return  0; }
        String nickname = (String)baseUserMap.get("nickname");
        try{
            //解决nickname乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
        }catch (Exception e){
            e.getMessage();
        }
        //将double类型的sex转换成int
        Double sexTemp = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");
        String headimgurl = (String)baseUserMap.get("headimgurl");

        return 0;
    }
}
