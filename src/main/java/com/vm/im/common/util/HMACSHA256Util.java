package com.vm.im.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACSHA256Util {

    private static Logger logger = LoggerFactory.getLogger(HMACSHA256Util.class);

    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            logger.error("Error HmacSHA256",e);
        }
        return hash;
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static void main(String[] args) {
//        long l = System.currentTimeMillis();
//        System.out.println(l);
        System.out.println(sha256_HMAC("{\"uid\":\"180188\",\"groupName\":\"111111\",\"groupId\":\"1\",\"avatar\":\"111\",\"type\":1,\"timestamp\":\"1551336980339\"}", "afad71e8a8f944f0afc4e79e9520d71d"));
    }
}
