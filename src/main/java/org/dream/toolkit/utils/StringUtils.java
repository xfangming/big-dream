package org.dream.toolkit.utils;


import org.dream.toolkit.consts.SymbolConst;
import org.dream.toolkit.reflect.ReflectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String对象的常用方法封装
 *
 * @author tobiasy
 */
public class StringUtils {
    private static final Integer MAX_NUMBER = 9;
    private static final Integer MIN_NUMBER = 0;

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String capitalize(String str) {
        char[] ch = str.toCharArray();
        char a = 'a';
        char z = 'z';
        if (ch[0] >= a && ch[0] <= z) {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 首字母大写
     *
     * @param str 可以是任意带下划线的字符串
     * @return
     */
    public static String capitalizeCase(String str) {
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

    /**
     * 驼峰转下划线
     *
     * @param humpString
     * @return
     */
    public static String underlineCase(String humpString) {
        if (StringUtils.isEmpty(humpString)) {
            return "";
        }
        String regexStr = "[A-Z]";
        Matcher matcher = Pattern.compile(regexStr).matcher(humpString);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String g = matcher.group();
            matcher.appendReplacement(sb, SymbolConst.UNDERLINE + g.toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.charAt(0) == SymbolConst.UNDERLINE_CHAR) {
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    /**
     * 根据位数 生成随机数
     *
     * @param number 随机数位数
     * @return
     */
    public static String createRandom(int number) {
        if (number > MAX_NUMBER || number <= MIN_NUMBER) {
            throw new RuntimeException("非法参数！整形随机数位数范围(0,9]");
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

    /**
     * 是否包含大写
     *
     * @param word
     * @return
     */
    public static boolean containUpperCase(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含小写
     *
     * @param word
     * @return
     */
    public static boolean containLowerCase(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取首个大写字母所在位置
     *
     * @param word
     * @return
     */
    public static int getUpperCaseIndex(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isEmpty(String str) {
        return str == null || SymbolConst.EMPTY.equals(str);
    }

    /**
     * 检查字符串是否包含有效字符
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

    public static boolean isBlank2(String str) {
        boolean empty = isEmpty(str);
        if (empty) {
            return empty;
        }
        return isEmpty(str.trim());
    }

    /**
     * 把Object数组转化为符号mark分开的字符串
     * 相当于split的逆向操作
     *
     * @param mark    连接符
     * @param objects 基本数据类型（不能为对象数组）
     * @return
     */
    public static String join(String mark, Object... objects) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (Object o : objects) {
            if (flag) {
                sb.append(mark);
            }
            sb.append(o);
            flag = true;
        }
        return sb.toString();
    }

    /**
     * 在字符串origin后面追加一个数组，mark分隔
     *
     * @param origin 原始字符串
     * @param mark   分隔符
     * @param value  追加数组
     * @return
     */
    public static String append(String origin, String mark, Object... value) {
        if (!isBlank(origin)) {
            StringBuilder buff = new StringBuilder(origin);
            for (Object o : value) {
                buff.append(mark).append(o);
            }
            return buff.toString();
        } else {
            return append(mark, value);
        }
    }

    public static String append(String mark, Object... value) {
        StringBuilder origin = new StringBuilder();
        boolean flag = true;
        for (Object o : value) {
            if (flag) {
                origin.append(o);
                flag = false;
            } else {
                origin.append(mark).append(o);
            }
        }
        return origin.toString();
    }

    /**
     * 拼接对象中多个方法名执行结果
     *
     * @param object
     * @param separator
     * @param params
     * @return
     */
    public static String joint(Object object, String separator, String... params) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String param : params) {
            if (!first) {
                builder.append(separator);
            }
            builder.append(ReflectUtils.invoke(object, param));
            first = false;
        }
        return builder.toString();
    }

    /**
     * 字符串split返回List用法
     *
     * @param string 字符串
     * @param split  分隔符
     * @return
     */
    public static List<String> splitToList(String string, String split) {
        if (isBlank(string)) {
            return new ArrayList<>();
        }
        String[] strings = string.split(split);
        return ArrayUtils.arrayToList(strings);
    }

    /**
     * 字符串反转
     *
     * @param s
     * @return
     */
    public static String reverse(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            builder.append(s.charAt(i));
        }
        return builder.toString();
    }

    /**
     * 字符串根据起始位置截短
     *
     * @param str          操作字符串
     * @param abbrevMarker 省略的标志符号
     * @param offset       开始位置
     * @param maxWidth     最大长度
     * @return
     */
    public static String abbreviate(String str, String abbrevMarker, int offset, int maxWidth) {
        if (!isEmpty(str) && !isEmpty(abbrevMarker)) {
            int abbrevMarkerLength = abbrevMarker.length();
            int minAbbrevWidth = abbrevMarkerLength + 1;
            int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;
            if (maxWidth < minAbbrevWidth) {
                throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
            } else if (str.length() <= maxWidth) {
                return str;
            } else {
                if (offset > str.length()) {
                    offset = str.length();
                }

                if (str.length() - offset < maxWidth - abbrevMarkerLength) {
                    offset = str.length() - (maxWidth - abbrevMarkerLength);
                }

                if (offset <= abbrevMarkerLength + 1) {
                    return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
                } else if (maxWidth < minAbbrevWidthOffset) {
                    throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", minAbbrevWidthOffset));
                } else {
                    return offset + maxWidth - abbrevMarkerLength < str.length() ? abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength) : abbrevMarker + str.substring(str.length() - (maxWidth - abbrevMarkerLength));
                }
            }
        } else {
            return str;
        }
    }

    public static String abbreviate(String str, String abbrevMarker, int maxWidth) {
        return abbreviate(str, abbrevMarker, 0, maxWidth);
    }

    public static void main(String[] args) {
        System.out.println(splitToList("account,user", ","));
        System.out.println(reverse("hello"));

    }

}
