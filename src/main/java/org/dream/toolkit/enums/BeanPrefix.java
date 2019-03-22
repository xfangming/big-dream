package org.dream.toolkit.enums;

/**
 * @author tobiasy
 */
public enum BeanPrefix {
    /**
     * set方法前缀
     */
    SET("set"),
    /**
     * get方法前缀
     */
    GET("get");

    private String key;

    BeanPrefix(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
