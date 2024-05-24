package com.example.md4_minitest_w2.exception;

public class ComputerCodeExists extends RuntimeException {
    public ComputerCodeExists(String message) {
        super(message);
    }
}
