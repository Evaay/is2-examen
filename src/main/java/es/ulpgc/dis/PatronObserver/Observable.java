package es.ulpgc.dis.PatronObserver;

import java.util.ArrayList;
import java.util.List;

public interface Observable {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifyObservers();
}
