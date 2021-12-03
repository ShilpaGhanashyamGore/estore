package com.example.estore.exceptions;

public class DependenciesFoundException extends RuntimeException{

    public DependenciesFoundException(){
        super();
    }

    public DependenciesFoundException(String message){
        super(message);
    }
}
