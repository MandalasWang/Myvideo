package com.djcps.djvideo.service.impl;

import com.djcps.djvideo.common.RetResponse;
import com.djcps.djvideo.common.RetResult;
import com.djcps.djvideo.config.WeChatConfig;
import com.djcps.djvideo.domain.User;
import com.djcps.djvideo.mapper.UserMapper;
import com.djcps.djvideo.service.UserService;
import com.djcps.djvideo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 有缘
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveWeChatUser(String code) {
        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(), weChatConfig.getOpenAppid(), 
                weChatConfig.getOpenAppsecret(), code);
        //获取access_token
        Map<String, Object> baseMap = HttpUtils.doGet(accessTokenUrl);

        if (baseMap == null || baseMap.isEmpty()) {
            return null;
        }
        String accessToken = (String) baseMap.get("access_token");
        String openId = (String) baseMap.get("openid");
        User dbUser = userMapper.findByopenid(openId);
        //更新用户，直接返回
        if (dbUser != null) {
            return dbUser;
        }
        //获取用户基本信息
        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(), accessToken, openId);
        //获取access_token
        Map<String, Object> baseUserMap = HttpUtils.doGet(userInfoUrl);
        if (baseUserMap == null || baseUserMap.isEmpty()) {
            return null;
        }
        String nickname = (String) baseUserMap.get("nickname");
        Double sexTemp = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String) baseUserMap.get("province");
        String city = (String) baseUserMap.get("city");
        String country = (String) baseUserMap.get("country");
        String headimgurl = (String) baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        try {
            //解决乱码
            nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setName(nickname);
        user.setHeadImg(headimgurl);
        user.setCity(finalAddress);
        user.setOpenid(openId);
        user.setSex(sex);
        user.setCreateTime(new Date());
        userMapper.save(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(User user) {
        return userMapper.update(user);
    }

    @Override
    public RetResult save(MultipartFile file, HttpServletRequest request) {
        User user = new User();
        user.setName(request.getParameter("userName"));
        user.setCity(request.getParameter("City"));
        user.setSex(Integer.valueOf(request.getParameter("sex")));
        user.setPhone(request.getParameter("phone"));
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString()+suffixName;
        user.setHeadImg(fileName);
        user.setOpenid(UUID.randomUUID().toString());
        user.setRight(0);
        int row = userMapper.save(user);
        if(row > 0){
            return RetResponse.makeOKRsp("注册成功！");
        }
        return RetResponse.makeErrRsp("注册失败！");
    }

    @Override
    public RetResult login(User user) {
        return userMapper.login(user)==null?RetResponse.makeErrRsp("用户未登录"):RetResponse.makeOKRsp();
    }


}
