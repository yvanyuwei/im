package com.vm.im.common.util;

import org.apache.commons.lang.RandomStringUtils;

import java.util.UUID;
import java.util.regex.Pattern;


public class UUIDUtil {

    public UUIDUtil() {
    }

    /**
     * 生成32位uuid (去中划线)
     *
     * @return
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 匹配是否为uuid 例如格式(8bde33210899469285c698d3bbc7ef7b)
     *
     * @param uuid
     * @return
     */
    public static boolean check32UUID(String uuid){
        String regex = "[0-9a-f]{32}";
        return Pattern.matches(regex, uuid);
    }


    public static void main(String[] args) {
        System.out.println("UUID1 : " + get32UUID());
        System.out.println("UUID2 : " + get32UUID());
        System.out.println("PWD : " + RandomStringUtils.random(24, true, true));
        System.out.println("IV : " + RandomStringUtils.random(8, true, true));
    }
}
