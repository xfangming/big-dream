package org.dream.toolkit.common;

import org.dream.toolkit.enums.BeanPrefix;

/**
 * @author tobiasy
 */
public class Generate {
    public static String toGetter(String fieldName) {
        String get = BeanPrefix.GET.getKey();
        return get + StringUtils.capitalizeCase(fieldName);
    }

    public static String toSetter(String fieldName) {
        String set = BeanPrefix.SET.getKey();
        return set + StringUtils.capitalizeCase(fieldName);
    }
}
