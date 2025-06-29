package es.ulpgc.dis.PatronObserver;

public class DocumentPresenter implements Observer{
    private final Document document;
    private final String idObserver;
    public DocumentPresenter(Document document, String idObserver){
        this.document = document;
        this.idObserver = idObserver;
    }

    @Override
    public void update() {
        //logica q realizara el documentPresenter al ser notificado
        //DocumentDisplay.updateText(document.text())
        System.out.println("soy el document presenter: " + this.idObserver + " " + document.text());
    }
}
