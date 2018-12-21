package org.dream.toolkit.exception;

/**
 * @author tobiasy
 */
public class JsonAdditionMismatchException extends RuntimeException{
    public JsonAdditionMismatchException(String msg){
        super(msg);
    }
    public JsonAdditionMismatchException(Throwable throwable){
        super(throwable);
    }
}
