package org.dream.toolkit.common;


import java.util.*;

/**
 * @author tobiasy
 * @date 2018/11/6
 */
public class CollectionUtils {
    private static Integer INTEGER_ONE = 1;

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 2个数组取并集
     * @param a
     * @param b
     * @return
     */
    public static Collection union(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;
            for(int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 2个数组取交集
     * @param a
     * @param b
     * @return
     */
    public static Collection intersection(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;
            for(int m = Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 2个数组取交集 的补集
     * @param a
     * @param b
     * @return
     */
    public static Collection disjunction(Collection a, Collection b) {
        ArrayList list = new ArrayList();
        Map mapa = getCardinalityMap(a);
        Map mapb = getCardinalityMap(b);
        Set elts = new HashSet(a);
        elts.addAll(b);
        Iterator it = elts.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            int i = 0;
            for(int m = Math.max(getFreq(obj, mapa), getFreq(obj, mapb)) - Math.min(getFreq(obj, mapa), getFreq(obj, mapb)); i < m; ++i) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * arrayA去除arrayB
     * @param a
     * @param b
     * @return
     */
    public static Collection subtract(Collection a, Collection b) {
        List list = new ArrayList(a);
        Iterator it = b.iterator();
        while(it.hasNext()) {
            list.remove(it.next());
        }
        return list;
    }

    private static final int getFreq(Object obj, Map freqMap) {
        Integer count = (Integer)freqMap.get(obj);
        return count != null ? count : 0;
    }

    public static Map getCardinalityMap(Collection coll) {
        Map count = new HashMap();
        Iterator it = coll.iterator();
        while(it.hasNext()) {
            Object obj = it.next();
            Integer c = (Integer)(count.get(obj));
            if (c == null) {
                count.put(obj, INTEGER_ONE);
            } else {
                count.put(obj, new Integer(c + 1));
            }
        }
        return count;
    }
} 