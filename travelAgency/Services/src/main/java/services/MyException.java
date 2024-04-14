package app.services;

public class MyException extends Exception{
    public MyException(){}

    public MyException(String msg) {
        super(msg);
    }

    public MyException(String msg, Throwable cause){
        super(msg, cause);
    }
}