package org.dream.toolkit.enums;

/**
 * @author tobiasy
 * @date 2018/10/14
 */
public enum ClassEnum {
    STRING,
    BYTE,
    SHORT,
    INT,
    INTEGER,
    LONG,
    LONG_MAX,
    FLOAT,
    FLOAT_MAX,
    DOUBLE,
    DOUBLE_MAX,
    BOOLEAN,
    BOOLEAN_MAX,
    DATE,
    CHAR,
    CHAR_MAX,
    OBJECT;
    public static ClassEnum getInstance(String name){
        try{
            return valueOf(name);
        }catch (RuntimeException e) {
            return ClassEnum.OBJECT;
        }
    }

}
