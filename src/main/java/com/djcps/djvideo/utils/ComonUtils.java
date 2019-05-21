package com.djcps.djvideo.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 常用的工具类的封装
 */
public class ComonUtils {
    /**
     * 生成一个uuid
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return
     */
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 32);
        return uuid;
    }

    /**
     * MD5常用工具类
     *
     * @return
     */
    public static String MD5(String data) {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;

    }

}
