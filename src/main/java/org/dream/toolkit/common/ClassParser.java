package org.dream.toolkit.common;

import org.dream.toolkit.enums.ClassEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tobiasy
 * @date 2019/3/4
 */
public class ClassParser {

    public static <T> T[] parseObject(Object[] objects, Class<T> clazz, T[] result) {
        for (int i = 0; i < objects.length; i++) {
            result[i] = parse(objects[i], clazz);
        }
        return result;
    }

    public static <T> List<T> parseObject(Object[] objects, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < objects.length; i++) {
            list.add(parse(objects[i], tClass));
        }
        return list;
    }

    public static <T> T parseByType(Object value, Class<T> clazz) {
        T t = (T) value;
        return t;
    }

    public static <T> T parse(Object value, Class<T> clazz) {
        Object type = getType(value, clazz);
        T t = (T) type;
        return t;
    }

    public static Object getType(Object value, Class clazz) {
        String simpleType = clazz.getSimpleName().toUpperCase();
        ClassEnum classEnum = ClassEnum.getInstance(simpleType);
        if (ClassEnum.STRING.name().equalsIgnoreCase(classEnum.name())) {
            return String.valueOf(value);
        } else if (ClassEnum.BYTE.name().equalsIgnoreCase(classEnum.name())) {
            return parseByte(value);
        } else if (ClassEnum.SHORT.name().equalsIgnoreCase(classEnum.name())) {
            return parseShort(value);
        } else if (ClassEnum.INT.name().equalsIgnoreCase(classEnum.name())) {
            return parseInt(value);
        } else if (ClassEnum.INTEGER.name().equalsIgnoreCase(classEnum.name())) {
            return parseInt(value);
        } else if (ClassEnum.LONG.name().equalsIgnoreCase(classEnum.name())) {
            return parseLong(value);
        } else if (ClassEnum.LONG_MAX.name().equalsIgnoreCase(classEnum.name())) {
            throw new RuntimeException("LONG_MAX");
        } else if (ClassEnum.BOOLEAN.name().equalsIgnoreCase(classEnum.name())) {
            return parseBoolean(value);
        } else if (ClassEnum.BOOLEAN_MAX.name().equalsIgnoreCase(classEnum.name())) {
            throw new RuntimeException("BOOLEAN_MAX");
        } else if (ClassEnum.DOUBLE.name().equalsIgnoreCase(classEnum.name())) {
            return parseDouble(value);
        } else if (ClassEnum.DOUBLE_MAX.name().equalsIgnoreCase(classEnum.name())) {
            throw new RuntimeException("DOUBLE_MAX");
        } else if (ClassEnum.FLOAT.name().equalsIgnoreCase(classEnum.name())) {
            return parseFloat(value);
        } else if (ClassEnum.FLOAT_MAX.name().equalsIgnoreCase(classEnum.name())) {
            throw new RuntimeException("FLOAT_MAX");
        } else if (ClassEnum.DATE.name().equalsIgnoreCase(classEnum.name())) {
            return DateUtils.parse(value);
        } else if (ClassEnum.CHAR_MAX.name().equalsIgnoreCase(classEnum.name())) {
            throw new RuntimeException("CHAR_MAX");
        } else if (ClassEnum.CHAR.name().equalsIgnoreCase(classEnum.name())) {
            return parseChar(value);
        }
        return value;
    }

    /**
     * 字符串转化为Integer，如果带有小数点自动忽略
     *
     * @param s
     * @return
     */
    public static Integer parseInt(String s) {
        Number parse = NumericUtils.parse(s);
        return parse.intValue();
    }

    public static Integer parseInt(Long s) {
        return parseInt(String.valueOf(s));
    }

    public static Integer parseInt(Object o) {
        return parseInt(String.valueOf(o));
    }

    public static char parseChar(Object object) {
        String s = object.toString();
        char[] chars = s.toCharArray();
        return chars[0];
    }

    public static Byte parseByte(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return Byte.parseByte((String) object);
        } else if (object instanceof Integer) {
            return Byte.parseByte(String.valueOf(object));
        }
        return (Byte) object;
    }

    public static Short parseShort(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return Short.parseShort((String) object);
        } else if (object instanceof Integer) {
            return Short.parseShort(String.valueOf(object));
        }
        return (Short) object;
    }

    public static Long parseLong(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return Long.parseLong((String) object);
        } else if (object instanceof Long) {
            return (Long) object;
        } else {
            return Long.parseLong(String.valueOf(object));
        }
    }

    public static Double parseDouble(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) object;
            return decimal.doubleValue();
        } else if (object instanceof Double) {
            return (double) object;
        } else if (object instanceof String) {
            return Double.parseDouble((String) object);
        } else {
            return Double.parseDouble(String.valueOf(object));
        }
    }

    public static Float parseFloat(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) object;
            return decimal.floatValue();
        } else if (object instanceof Float) {
            return (Float) object;
        } else if (object instanceof String) {
            return Float.parseFloat((String) object);
        } else {
            return Float.parseFloat(String.valueOf(object));
        }
    }

    public static Boolean parseBoolean(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return Boolean.parseBoolean((String) o);
        } else if (o instanceof Integer) {
            Integer i = (Integer) o;
            return i == 1 ? true : false;
        } else {
            return (Boolean) o;
        }
    }
} 