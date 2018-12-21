package org.dream.toolkit.utils;


import org.dream.toolkit.enums.SortEnum;
import org.dream.toolkit.exception.ArrayMismatchException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author tobiasy
 */
public class ArrayUtils {

    /**
     * 数组转化为集合
     *
     * @param args 数组
     * @return 集合
     */
    public static <T extends Object> List<T> arrayToList(T[] args) {
        List<T> list = new ArrayList<>();
        for (T o : args) {
            list.add(o);
        }
        return list;
    }

    /**
     * List 集合转化为 Object[] 数组
     *
     * @param list 集合
     * @return Object类型数组
     */
    public static String[] listToStringArray(List<? extends Object> list) {
        int i = 0;
        String[] args = new String[list.size()];
        for (Object o : list) {
            args[i++] = (String) o;
        }
        return args;
    }

    /**
     * 集合转化为数组，需要先初始化集合中的类型
     *
     * @param list
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends Object> T[] listToArray(List<T> list, T[] t) {
        if (list.size() != t.length) {
            try {
                throw new Exception("初始化数组：" + t.getClass() + "长度不匹配！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        for (T o : list) {
            t[i++] = o;
        }
        return t;
    }
    public static <T> Object[] listToArray(List<T> list) {
        Object[] objects = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            objects[i] = t;
        }
        return objects;
    }

    /**
     * char类型数组转化为字符串数组
     *
     * @param chars
     * @return
     */
    public static String[] charToString(char[] chars) {
        String[] arr = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            arr[i] = String.valueOf(chars[i]);
        }
        return arr;
    }

    public static <T> boolean inArray(List<T> ojs, T value) {
        for (T o : ojs) {
            if (o.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> arrayAddList(T[] arr, List<T> object) {
        for (T t : arr) {
            object.add(t);
        }
        return object;
    }

    public static <T> Object[] listAddArray(T[] arr, List<T> object) {
        for (T t : arr) {
            object.add(t);
        }
        return object.toArray();
    }

    public static <T> Object[] listAddArray(T[] arr, List<T> object, T[] result) {
        if (result.length != (arr.length+object.size())) {
            throw new ArrayMismatchException(new Throwable(result.getClass()+"长度跟被复制长度不一致！"));
        }
        for (T t : arr) {
            object.add(t);
        }
        for (T t : result) {
            object.add(t);
        }
        return object.toArray();
    }



    /**
     * 数组默认排序 -- 升序
     *
     * @param arr
     */
    public static void sort(Integer[] arr) {
        sort(arr, SortEnum.ASC);
    }

    /**
     * 排列，需要指定升序还是降序
     *
     * @param arr
     * @param sort
     */
    public static void sort(Integer[] arr, SortEnum sort) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (SortEnum.DESC.name().equals(sort.name())) {
                    if (arr[i] < arr[j]) {
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                } else {
                    if (arr[i] > arr[j]) {
                        int temp = arr[i];
                        arr[i] = arr[j];
                        arr[j] = temp;
                    }
                }

            }
        }

    }

    /**
     * 字符串集合排序
     *
     * @param list
     * @return
     */
    public static List<String> sort(List<String> list) {
        Collections.sort(list);
        return list;
    }

    /**
     * 字符串数组排序
     *
     * @param list
     * @return
     */
    public static List<String> sort(String[] list) {
        Arrays.sort(list);
        return arrayToList(list);
    }

    public static <T> Object[] append(T[] objs, T t){
        List<T> list=new ArrayList(Arrays.asList(objs));
        list.add(t);
        Object[] objects = list.toArray();
        return objects;
    }


}
