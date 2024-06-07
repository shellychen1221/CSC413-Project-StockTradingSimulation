import java.awt.event.ActionListener;

public interface Subject {
    void registerObserver(ActionListener actionListener);
    void removeObserver(Observer observer);
    void notifyObservers();
    
}
