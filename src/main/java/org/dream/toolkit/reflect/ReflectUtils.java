package org.dream.toolkit.reflect;

import org.dream.toolkit.bean.BeanUtils;
import org.dream.toolkit.common.StringUtils;
import org.dream.toolkit.enums.ModifierEnum;
import org.dream.toolkit.utils.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tobiasy
 */
public class ReflectUtils {
    private static final String MODIFIER_FIELD_PREFIX = "is";
    private static final String MODIFIERS_METHOD_NAME = "getModifiers";

    /**
     * 获取当前Class中的所有属性
     *
     * @param clazz Class
     * @return List<Field>
     */
    public static List<Field> getDeclaredFields(Class clazz) {
        Field[] targetField = clazz.getDeclaredFields();
        return ArrayUtils.arrayToList(targetField);
    }

    /**
     * 获取class及其父类所有属性
     *
     * @param clazz Class对象
     * @return List<Field>
     */
    public static List<Field> getGlobalFields(Class clazz) {
        List<Field> fieldList = getDeclaredFields(clazz);
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            fieldList.addAll(getGlobalFields(superclass));
        }
        return fieldList;
    }

    /**
     * 按照属性名称检索当前对象中的属性域
     *
     * @param clazz     当前对象
     * @param fieldName 属性名称
     * @return Field
     */
    public static Field findDeclaredField(Class clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * 根据属性名fieldName查找指定类clazz及其父类中的属性域Field
     *
     * @param clazz     Class对象
     * @param fieldName 属性名称
     * @return Field
     */
    public static Field findField(Class clazz, String fieldName) {
        Field field = findDeclaredField(clazz, fieldName);
        if (field != null) {
            return field;
        }
        return findGlobalField(clazz, fieldName);
    }

    /**
     * 根据属性名fieldName查找指定类clazz或者父类中的属性域Field
     *
     * @param clazz     Class
     * @param fieldName 属性名
     * @return Field
     */
    public static Field findGlobalField(Class clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException var4) {
            return clazz.getSuperclass() != null ? findGlobalField(clazz.getSuperclass(), fieldName) : null;
        }
    }

    /**
     * 按照枚举类型检索class中满足条件的的属性
     *
     * @param clazz        Class
     * @param modifierEnum 枚举
     * @return List<Field>
     */
    public static List<Field> getDeclaredFieldsByModifier(Class clazz, ModifierEnum... modifierEnum) {
        List<Field> fieldList = ReflectUtils.getDeclaredFields(clazz);
        return filtrateByModifier(fieldList, modifierEnum);
    }


    /**
     * *******************************   Method   **************************************
     */

    /**
     * 执行静态方法（一个参数）
     *
     * @param clazz         Class对象
     * @param methodName    方法名称
     * @param parameterType 参数类型
     * @param value         参数值
     * @return Object
     */
    public static Object invokeStatic(Class clazz, String methodName, Class<?> parameterType, Object value) {
        Class<?>[] parameterTypes = new Class[]{parameterType};
        Object[] values = new Object[]{value};
        return invokeStatic(clazz, methodName, parameterTypes, values);
    }

    public static Object invokeStatic(Class clazz, String methodName, Class<?>[] parameterTypes, Object[] values) {
        Object value;
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            value = method.invoke(null, values);
            return value;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前Class对象中的所有方法
     *
     * @param clazz Class对象
     * @return List<Method>
     */
    public static List<Method> getDeclaredMethods(Class clazz) {
        return ArrayUtils.arrayToList(clazz.getDeclaredMethods());
    }

    /**
     * @param list         传入进行检索的属性列表或者方法列表
     * @param modifierEnum 修饰符枚举
     * @return
     */
    public static <T> List<T> filtrateByModifier(List<T> list, ModifierEnum... modifierEnum) {
        List<T> resultList = new ArrayList<>();
        for (T t : list) {
            for (ModifierEnum modifier : modifierEnum) {
                Boolean boo = isModifier(t, modifier);
                if (boo) {
                    resultList.add(t);
                }
            }
        }
        return resultList;
    }

    /**
     * 判断Field对象或者Method对象是否满足枚举类型中的检索
     *
     * @param methodField 对象（只能是 Method  | Field ）
     * @param modifier    枚举
     * @return Boolean
     */
    public static Boolean isModifier(Object methodField, ModifierEnum modifier) {
        Integer modifiers = (Integer) ReflectUtils.invoke(methodField, MODIFIERS_METHOD_NAME);
        String name = MODIFIER_FIELD_PREFIX + StringUtils.capitalizeCase(modifier.name().toLowerCase());
        return (Boolean) ReflectUtils.invokeStatic(Modifier.class, name, int.class, modifiers);
    }

    /**
     * 检索public修饰的指定方法域
     *
     * @param clazz      Class对象
     * @param methodName 属性名
     * @param paramTypes 参数类型
     * @return Method
     */
    public static Method findPublicMethod(Class clazz, String methodName, Class... paramTypes) {
        return findPublicMethod(clazz, methodName, true, paramTypes);
    }

    /**
     * 通过方法名称从Class和其父类对象中检索
     *
     * @param clazz      Class对象
     * @param methodName 方法名称
     * @param paramTypes 参数类型
     * @return
     */
    public static Method findMethod(Class clazz, String methodName, Class... paramTypes) {
        Method method = findPublicMethod(clazz, methodName, paramTypes);
        if (method != null) {
            return method;
        }
        return findExtendsMethod(clazz, methodName, paramTypes);
    }

    /**
     * 获取方法域
     *
     * @param clazz      Class
     * @param methodName 方法名称
     * @param printStack 是否需要抛异常
     * @param paramTypes Method类型
     * @return
     */
    private static Method findPublicMethod(Class clazz, String methodName, Boolean printStack, Class<?>... paramTypes) {
        try {
            return clazz.getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            if (printStack) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 先从本类对象中找该方法，若没找到就循环从父类对象中去找
     *
     * @param clazz      指定类
     * @param methodName 方法名称
     * @param paramTypes 参数类别，可多个
     * @return Method
     */
    public static Method findExtendsMethod(Class<?> clazz, String methodName, Class... paramTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, paramTypes);
        } catch (NoSuchMethodException var4) {
            return clazz.getSuperclass() != null ? findExtendsMethod(clazz.getSuperclass(), methodName, paramTypes) : null;
        }
    }

    /**
     * 执行一个无参方法
     *
     * @param obj        操作对象
     * @param methodName 属性名
     * @return Object
     * @throws RuntimeException
     */
    public static Object invoke(Object obj, String methodName) throws RuntimeException {
        if (obj == null || methodName == null) {
            return null;
        }
        Object value = null;
        try {
            Method getMethod = obj.getClass().getMethod(methodName);
            if (getMethod == null) {
                return null;
            }
            getMethod.setAccessible(true);
            value = getMethod.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 一个参数
     *
     * @param object     invoke对象
     * @param methodName 方法名称
     * @param paramType  参数类型
     * @param parameter  参数
     * @return Object
     */
    public static Object invokeByType(Object object, String methodName, Class paramType, Object parameter) {
        Class[] paramTypes = {paramType};
        Object[] parameters = {parameter};
        return invoke(object, methodName, paramTypes, parameters);
    }

    /**
     * 利用反射执行类中指定方法，多个参数
     *
     * @param object     类对象
     * @param methodName 方法名称
     * @param paramTypes 参数类型
     * @param parameters 参数
     * @return Object
     */
    public static Object invokeByType(Object object, String methodName, Class[] paramTypes, Object[] parameters) {
        Class<?> clazz = object.getClass();
        Method method = findMethod(clazz, methodName, paramTypes);
        if (method == null) {
            return null;
        }
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 一个参数自动转型
     *
     * @param object     操作对象
     * @param methodName 方法名
     * @param paramType  参数类型
     * @param parameter  参数
     * @return Object
     */
    public static Object invoke(Object object, String methodName, Class paramType, Object parameter) {
        return invokeByType(object, methodName, paramType, parameter);
    }

    /**
     * 根据类型自动转型
     *
     * @param object     操作对象
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @param parameters 参数
     * @return Object
     */
    public static Object invoke(Object object, String methodName, Class[] paramTypes, Object[] parameters) {
        for (int i = 0; i < paramTypes.length; i++) {
            parameters[i] = BeanUtils.getType(parameters[i], paramTypes[i]);
        }
        return invokeByType(object, methodName, paramTypes, parameters);
    }

}
