package sample.interfaces;

public interface Observable {
    void attach(Observer observerInterface);
    void detach(Observer observerInterface);
    void notifyObservers();
}
