package com.bitech.joke.utils;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * <p></p>
 * Created on 2016/4/12 17:36.
 *
 * @author Lucy
 */
public class Encrypt {
    private static final String[] HEXDIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static final String SECRET_KEY = Constants.APP_NAME;
    private static KeyGenerator generator;
    private static SecretKey desKey;
    private static Cipher cipher;
    /**
     * 3DES:DESede
     * AES:高级加密标准，AES
     * DES:DES
     * 上面三个都是对称加密，使用方法类型。
     *
     * 非对称加密算法：即有一对公钥和私钥
     * RSA:公钥加密算法，其实最安全的加密算法
     * DSA：
     * */
    static {
        try {
            // Security.addProvider(new com.sun.crypto.provider.SunJCE());

            generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(SECRET_KEY.getBytes()));
            desKey = generator.generateKey();
            cipher = Cipher.getInstance("DES");

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 对字符串进行MD5编码
     * */
    @SuppressLint("DefaultLocale")
    public static String encodeByMD5(String origin) {
        if (origin != null) {

            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(origin.getBytes("UTF-8"));
                String result = byteArrayToHexString(results);
                return result.toUpperCase();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return null;
    }

    /**
     * 转换字节数组为十六进制字符串
     * */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append(byteToHexString(b[i]));
        }
        return buffer.toString();
    }

    /**
     * 将一个字节转换成十六进制字符串
     * */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

    /**
     * DES加密
     * */
    public static byte[] encryteByDES(String str) {

        try {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] src = str.getBytes();
            // 进行加密
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * DES解密
     * */
    public static byte[] decryteByDES(String str) {

        try {
            cipher.init(Cipher.DECRYPT_MODE, desKey);// 解密模式
            return cipher.doFinal(str.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
