package app.service.utils;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyUsers();
}