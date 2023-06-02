package com.faas;

import spark.utils.StringUtils;

/**
 * FaaS函数工具类
 *
 * @author FAAS函数计算
 */
public class FaaSUtils {

    private FaaSUtils() {

    }

    public static String getEnv(String name, String defaultValue) {
        String result = System.getenv(name);
        if (!StringUtils.isBlank(result)) {
            return result;
        }
        return defaultValue;
    }

}
