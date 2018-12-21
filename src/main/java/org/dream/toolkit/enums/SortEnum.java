package org.dream.toolkit.enums;

/**
 * @author tobiasy
 */
public enum SortEnum {
    ASC(1,"asc"),
    DESC(2,"desc");
    SortEnum(Integer code, String discription) {
        this.code = code;
        this.discription = discription;
    }
    private Integer code;
    private String discription;

    public Integer getCode() {
        return code;
    }

    public String getDiscription() {
        return discription;
    }
}
