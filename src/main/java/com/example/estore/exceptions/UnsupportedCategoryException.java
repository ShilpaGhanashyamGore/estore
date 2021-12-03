package com.example.estore.exceptions;

public class UnsupportedCategoryException extends RuntimeException{

    public UnsupportedCategoryException(){
        super();
    }

    public UnsupportedCategoryException(String message){
        super(message);
    }
}
