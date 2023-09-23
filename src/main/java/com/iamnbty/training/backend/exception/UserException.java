package com.iamnbty.training.backend.exception;

public class UserException extends BaseException {

    public UserException(String code) {
        super("user." + code);
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException emailNull() {
        return new UserException("register.email.null");
    }


    // CREATE
    public static UserException CreateEmailNull() {
        return new UserException("create.email.null");
    }

    public static UserException CreateEmailDuplicated() {
        return new UserException("create.email.duplicated");
    }

    public static UserException CreatePasswordNull() {
        return new UserException("create.password.null");
    }

    public static UserException CreateNameNull() {
        return new UserException("create.name.null");
    }

    // LOGIN
    public static UserException LoginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException LoginFailEmailPasswordIncorrect() {
        return new UserException("login.fail");
    }

}