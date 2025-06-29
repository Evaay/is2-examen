package es.ulpgc.dis.PatronObserver;

import java.util.ArrayList;
import java.util.List;

public class Document implements Observable{
    private final List<Observer> observers = new ArrayList<>();
    private String text = "";

    public void changeDocumentText(String newText) {
        //cambia texto del document
        this.text = newText;
        notifyObservers();
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers){
            observer.update();
        }
    }

    public String text() {
        return text;
    }
}
