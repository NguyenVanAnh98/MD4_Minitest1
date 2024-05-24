package com.example.md4_minitest_w2.exception;

public class ComputerIdNotFound extends RuntimeException {
    public ComputerIdNotFound(String message) {
        super(message);
    }
}
