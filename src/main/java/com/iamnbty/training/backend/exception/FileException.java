package com.iamnbty.training.backend.exception;

public class FileException extends BaseException {

    public FileException(String code) {
        super("user." + code);
    }

    public static FileException fileNull() {
        return new FileException("file.null");
    }

    public static FileException fileMaxSize() {
        return new FileException("file.max.size");
    }
    public static FileException unsupported() {
        return new FileException("file.unsupported");
    }

}