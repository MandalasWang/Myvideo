package com.djcps.djvideo.utils;

import com.djcps.djvideo.config.WeChatConfig;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.Security;
import java.util.UUID;

/**
 * 常用的工具类的封装
 * @author 有缘
 * @date  19/5/22
 */
public class ComonUtils {
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";


    private static Logger logger = LoggerFactory.getLogger(ComonUtils.class);

    /**
     * 获取请求参数 读取xml格式数据并返回一个String格式的数据
     *
     * @param request
     * @return
     */
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder ret;
            br = request.getReader();
            String line = br.readLine();
            if (line != null) {
                ret = new StringBuilder();
                ret.append(line);
            } else {
                return "";
            }
            while ((line = br.readLine()) != null) {
                ret.append('\n').append(line);
            }
            return ret.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 生成一个uuid
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     *
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
    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        // 初始化
        WeChatConfig weixinConfig = new WeChatConfig();
        SecretKeySpec key = new SecretKeySpec((ComonUtils.MD5(weixinConfig.getKey())).getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] str = Base64Utils.encode(cipher.doFinal(data.getBytes()));
        String decode = new String(str);
        return decode;
    }

    /**
     * AES解密
     *
     * @param
     * @return
     * @throws Exception
     */
    public static String decryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        WeChatConfig weixinConfig = new WeChatConfig();
        SecretKeySpec key = new SecretKeySpec((ComonUtils.MD5(weixinConfig.getKey())).getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64Utils.decode(data.getBytes())));
    }


}
