package org.dream.toolkit.exception;

/**
 * @author tobiasy
 * @date 2018/12/13
 */
public class NoEnumException extends RuntimeException{
    public NoEnumException(String msg){
        super(msg);
    }
    public NoEnumException(Throwable throwable){
        super(throwable);
    }
} 