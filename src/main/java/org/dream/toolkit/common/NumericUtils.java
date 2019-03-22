package org.dream.toolkit.common;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author tobiasy
 * @date 2019/1/16
 */
public class NumericUtils {

    public static String format(Integer number) {
        return NumberFormat.getInstance().format(number);
    }

    public static Number parse(String source) {
        try {
            return NumberFormat.getInstance().parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatNumber(Integer number, Integer size) {
        return String.format("%0" + size + "d", number);
    }

    public static String getFormatNumber(Integer number, Integer size) {
        return String.format("%" + size + "d", number);
    }
} 