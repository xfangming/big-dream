package org.dream.toolkit.bean;

import org.dream.toolkit.common.CollectionUtils;
import org.dream.toolkit.common.DateUtils;
import org.dream.toolkit.utils.StringUtils;
import org.dream.toolkit.enums.BeanPrefix;
import org.dream.toolkit.enums.ClassEnum;
import org.dream.toolkit.reflect.ReflectUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import static org.dream.toolkit.utils.StringUtils.capitalizeCase;
import static org.dream.toolkit.reflect.ReflectUtils.invoke;

/**
 * @author tobiasy
 */
public class BeanUtils {

    public static Long copyProperties(Object source, Object target) {
        Long start = System.currentTimeMillis();
        if (source == null) {
            return System.currentTimeMillis() - start;
        }
        Class<? extends Object> sourceClass = source.getClass();
        Class<? extends Object> targetClass = target.getClass();
        List<Field> sourceField = ReflectUtils.getGlobalFields(sourceClass);
        List<Field> targetField = ReflectUtils.getGlobalFields(targetClass);
        for (Field field : sourceField) {
            String fieldName = field.getName();
            for (Field trg : targetField) {
                String fieldName1 = trg.getName();
                Boolean f = field.getType().getName().equals(trg.getType().getName());
                if (fieldName.equals(fieldName1) && f) {
                    String methodSuffix = capitalizeCase(fieldName);
                    try {
                        Object s1;
                        s1 = invoke(source, BeanPrefix.GET.getKey() + methodSuffix);
                        if (s1 != null) {
                            invoke(target, BeanPrefix.SET.getKey() + methodSuffix, trg.getType(), s1);
                        }
                    } catch (RuntimeException e) {
                        continue;
                    }
                }
            }
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * 字符串转化为Integer，如果带有小数点自动忽略
     *
     * @param s
     * @return
     */
    public static Integer parseInt(String s) {
        Long l = Math.round(Double.parseDouble(s));
        return Integer.parseInt(String.valueOf(l));
    }

    public static Integer parseInt(Object o) {
        return parseInt(String.valueOf(o));
    }

    /**
     * 检查对象是否为空对象
     * (暂时不支持有继承关系的对象)
     *
     * @param object
     * @return
     */
    public static Boolean isBlank(Object object) {
        if (isPrimitive(object)) {
            return primitiveIsBlank(object);
        }
        if (object == null) {
            return true;
        }
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            try {
                if (name.startsWith(BeanPrefix.GET.getKey())) {
                    Object value = invoke(object, name);
                    boolean flag = isBlank(value);
                    if (!flag) {
                        String msg = StringUtils.initialCase(
                                name.replaceAll(BeanPrefix.GET.getKey(), "")) + ":" + value;
                        System.out.println(msg);
                        return false;
                    }
                }
            } catch (RuntimeException e) {
                continue;
            }
        }
        return true;
    }

    /**
     * 检查基本类型中是否存在
     *
     * @param object
     * @return
     */
    private static boolean primitiveIsBlank(Object object) {
        if (object == null) {
            return true;
        }
        Class<?> clazz = object.getClass();
        if (isPrimitive(object)) {
            if (clazz == int.class || clazz == Integer.class) {
                Integer integer = (Integer) object;
                if (integer != 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        throw new RuntimeException("这里只能传输基本类型（boolean、char、byte、short、int、long、float、double）");
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型
     * 原始类型（boolean、char、byte、short、int、long、float、double）
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        try {
            Class clazz = obj.getClass();
            Field type = clazz.getField("TYPE");
            Object o = type.get(null);
            return ((Class<?>) o).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 流的关闭操作
     *
     * @param obj 流对象
     */
    public static void close(Object obj) {
        if (obj != null) {
            try {
                invoke(obj, "close");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public static char parseChar(Object object) {
        String s = object.toString();
        char[] chars = s.toCharArray();
        return chars[0];
    }

    /**
     * 将Object对象转化为Class指定类型
     *
     * @param value Object原始对象
     * @param clazz 转化类型
     * @return 转化后的Object对象
     */
    public static Object getType(Object value, Class clazz) {
        return getType(value, clazz, false);
    }

    public static Object getJsonType(Object value, Class clazz) {
        return getType(value, clazz, true);
    }

    private static Object getType(Object value, Class clazz, boolean isJson) {
        String simpleType = clazz.getSimpleName().toUpperCase();
        ClassEnum classEnum = ClassEnum.getInstance(simpleType);
        if (ClassEnum.STRING.name().equalsIgnoreCase(classEnum.name())) {
            return String.valueOf(value);
        } else if (ClassEnum.BYTE.name().equalsIgnoreCase(classEnum.name())) {
            byte param = (byte) value;
            return param;
        } else if (ClassEnum.SHORT.name().equalsIgnoreCase(classEnum.name())) {
            short param = (short) value;
            return param;
        } else if (ClassEnum.INT.name().equalsIgnoreCase(classEnum.name())) {
            Integer integer = BeanUtils.parseInt(value);
            int param = integer;
            return param;
        } else if (ClassEnum.INTEGER.name().equalsIgnoreCase(classEnum.name())) {
            Integer param = BeanUtils.parseInt(value);
            return param;
        } else if (ClassEnum.LONG.name().equalsIgnoreCase(classEnum.name())) {
            long param = Long.parseLong(value.toString());
            return param;
        } else if (ClassEnum.LONG_MAX.name().equalsIgnoreCase(classEnum.name())) {
            Long param = (Long) value;
            return param;
        } else if (ClassEnum.BOOLEAN.name().equalsIgnoreCase(classEnum.name())) {
            boolean param = (boolean) value;
            return param;
        } else if (ClassEnum.BOOLEAN_MAX.name().equalsIgnoreCase(classEnum.name())) {
            Boolean param = (Boolean) value;
            return param;
        } else if (ClassEnum.DOUBLE.name().equalsIgnoreCase(classEnum.name())) {
            double param;
            if (value instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) value;
                param = decimal.doubleValue();
            } else {
                param = (double) value;
            }
            return param;
        } else if (ClassEnum.DOUBLE_MAX.name().equalsIgnoreCase(classEnum.name())) {
            Double param = (Double) value;
            return param;
        } else if (ClassEnum.FLOAT.name().equalsIgnoreCase(classEnum.name())) {
            float param = (float) value;
            return param;
        } else if (ClassEnum.FLOAT_MAX.name().equalsIgnoreCase(classEnum.name())) {
            Float param = (Float) value;
            return param;
        } else if (ClassEnum.DATE.name().equalsIgnoreCase(classEnum.name())) {
            if (isJson) {
                if (value instanceof Long) {
                    Date param = DateUtils.stampToDate((Long) value);
                    return param;
                } else if (value instanceof Date) {
                }
                Date param = (Date) value;
                return DateUtils.dateToStamp(param);
            } else {
                if (value instanceof Long) {
                    Date date = DateUtils.stampToDate((Long) value);
                    return date;
                }
                Date date = (Date) value;
                return date;
            }
        } else if (ClassEnum.CHAR_MAX.name().equalsIgnoreCase(classEnum.name())) {
            Character param = (Character) value;
            return param;
        } else if (ClassEnum.CHAR.name().equalsIgnoreCase(classEnum.name())) {
            char param = BeanUtils.parseChar(value);
            return param;
        }
        return value;
    }

    public static <T> Map<String, Object> beanToMap(T obj) throws Exception {
        if (obj == null) {
            return null;
        } else {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Object> map = new HashMap(fields.length);
            Field[] var3 = fields;
            int var4 = fields.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                field.setAccessible(true);
                Object o = field.get(obj);
                if (o != null) {
                    map.put(field.getName(), o);
                }
            }

            return map;
        }
    }

    public static <T> List<Map<String, Object>> beanListToMapList(List<T> list) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        } else {
            List<Map<String, Object>> maps = new ArrayList(list.size());
            Iterator var2 = list.iterator();

            while (var2.hasNext()) {
                Object tem = var2.next();
                maps.add(beanToMap(tem));
            }
            return maps;
        }
    }

}
