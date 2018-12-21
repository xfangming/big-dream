package org.dream.toolkit.enums;

/**
 * @author tobiasy
 */
public enum BeanPrefix {
    SET("set"),
    GET("get");

    private String key;

    BeanPrefix(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
