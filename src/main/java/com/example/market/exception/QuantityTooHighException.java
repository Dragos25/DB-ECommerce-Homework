package com.example.market.exception;

public class QuantityTooHighException extends Exception{
    public QuantityTooHighException(String error){
        super(error);
    }
}
