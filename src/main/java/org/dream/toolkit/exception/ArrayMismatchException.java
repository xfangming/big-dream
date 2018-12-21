package org.dream.toolkit.exception;

/**
 * @author tobiasy
 */
public class ArrayMismatchException extends RuntimeException{
    public ArrayMismatchException(String msg){
        super(msg);
    }
    public ArrayMismatchException(Throwable throwable){
        super(throwable);
    }
}
