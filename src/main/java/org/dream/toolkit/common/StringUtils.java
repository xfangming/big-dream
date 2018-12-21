package org.dream.toolkit.common;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String对象的常用方法封装
 *
 * @author tobiasy
 */
public class StringUtils {
    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalizeCase(String str) {
        char[] ch = str.toCharArray();
        char a = 'a';
        char z = 'z';
        if (ch[0] >= a && ch[0] <= z) {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 根据位数 生成随机数
     *
     * @param number 随机数位数
     * @return
     */
    public static String createRandom(int number) {
        if (number > 9 || number <= 0) {
            return "非法参数！整形随机数位数范围(0,9]";
        }
        int l = 1;
        for (int i = 0; i < number; i++) {
            l = 10 * l;
        }
        int max = l - 1;
        int min = l / 10;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }

    /**
     * 转驼峰命名
     *
     * @param str
     * @return
     */
    public static String camelcase(String str) {
        String line = "_";
        if (str.endsWith(line)) {
            str = str.substring(0, str.lastIndexOf(line));
        }
        if (str.startsWith(line)) {
            str = str.substring(1);
        }
        while (str.contains(line)) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if ('_' == c) {
                    String str1 = str.substring(0, i) + str.substring(i + 1, i + 2).toUpperCase() + str.substring(i + 2);
                    str = str1;
                }
            }
        }
        return str;
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母大写
     *
     * @param str 可以是任意带下划线的字符串
     * @return
     */
    public static String capitalize(String str) {
        String str1 = camelcase(str);
        return str1.substring(0, 1).toUpperCase() + str1.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String initialCase(String str) {
        String str1 = camelcase(str);
        return str1.substring(0, 1).toLowerCase() + str1.substring(1);
    }

    /**
     * 驼峰命名转下划线‘_’+小写保存
     *
     * @param className
     * @return
     */
    public static String underline(String className) {
        className = className.substring(0, 1).toLowerCase()
                + className.substring(1);
        for (int i = 0; i < className.length(); i++) {
            char c = className.charAt(i);
            if (Character.isUpperCase(c)) {
                className = className.substring(0, i) + "_"
                        + className.substring(i, i + 1).toLowerCase()
                        + className.substring(i + 1);
            }
        }
        return className.toLowerCase();
    }

    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 驼峰转下划线,效率比上面高
     */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 检查字符串中是否
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(String cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                char c = cs.charAt(i);
                if (!Character.isWhitespace(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String clearBlank(String str) {
        str = str.replaceAll(" ", "");
        return str;
    }

}
