package com.company;

public class FileInvalidException extends Exception{
    public FileInvalidException(String errorMessage){
        super(errorMessage);
    }
}
