package es.ulpgc.dis.PatronObserver;

public class MainObserver {
    public static void main(String[] args) {
        Document document = new Document();
        DocumentPresenter documentPresenter1 = new DocumentPresenter(document, "presenter1");
        DocumentPresenter documentPresenter2 = new DocumentPresenter(document, "presenter2");
        document.subscribe(documentPresenter1);
        document.subscribe(documentPresenter2);
        document.changeDocumentText("nuevo texto");
    }
}
