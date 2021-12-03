package com.example.estore.exceptions;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(){
        super();
    }

    public DuplicateResourceException(String msg){
        super(msg);
    }


}
