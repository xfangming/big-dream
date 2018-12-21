package org.dream.toolkit.enums;

/**
 * @author tobiasy
 * @date 2018/10/15
 */
public enum BooleanEnum {
    TRUE("true", true),
    FALSE("false", false);
    private String key;
    private Boolean value;

    BooleanEnum(String key, Boolean value) {
        this.key = key;
        this.value = value;
    }

    public static Boolean getInstance(String key) throws RuntimeException {
        BooleanEnum[] enums = BooleanEnum.values();
        for (BooleanEnum e : enums) {
            if (e.getKey().equalsIgnoreCase(key)) {
                return e.getValue();
            }
        }
        throw new RuntimeException(key + " can not cast to Boolean");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}