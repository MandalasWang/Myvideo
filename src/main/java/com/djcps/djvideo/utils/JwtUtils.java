package com.djcps.djvideo.utils;

import com.djcps.djvideo.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author 有缘
 * jwt工具类
 */
public class JwtUtils {
    //header
    public static final String SUBJECT = "djcps";
    //过期时间，毫秒，一小时
    public static final long EXPIRE = 1000 * 60 * 60;
    //秘钥
    public static final String APPSECRET = "wyy";

    /**
     * 生成jwt
     *传入一个用户信息生成一个token
     * @param user
     * @return
     */
    public static String geneJsonWebToken(User user) {
        if (user == null || user.getId() == null || user.getName() == null
                || user.getHeadImg() == null) {
            return null;
        }
        String token = Jwts.builder().setSubject(SUBJECT).claim("id", user.getId())
                .claim("name", user.getName())
                .claim("img", user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, APPSECRET).compact();
        return token;
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return claims;

        } catch (Exception e) {
        }
        return null;

    }

}
