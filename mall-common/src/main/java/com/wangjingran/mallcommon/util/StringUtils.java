package com.wangjingran.mallcommon.util;

import cn.hutool.core.util.StrUtil;

public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return !StrUtil.isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return StrUtil.isEmpty(str);
    }

    public static String toSnakeCase(String camelCase) {
        if (isEmpty(camelCase)) {
            return camelCase;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append("_");
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
