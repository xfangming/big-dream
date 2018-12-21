package org.dream.toolkit.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author tobiasy
 */
public class MapUtils {

    public static Object[] getKeys(Map<? extends Object, ? extends Object> map){
        Set set = map.keySet();
        Object[] res = new Object[set.size()];
        Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Object object = iterator.next();
            res[i++] = object;
        }
        return res;
    }
}
