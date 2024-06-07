import java.util.List;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Stock implements Subject {
    private String name;
    private double price;
    private List<Observer> observers;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
        this.observers = new ArrayList<>();
    }

    // Implement the Subject interface methods
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(name, price);
        }
    }

    // Add a method to update the stock price
    public void updatePrice(double newPrice) {
        this.price = newPrice;
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    
	}

	@Override
	public void registerObserver(ActionListener actionListener) {
		// TODO Auto-generated method stub
		
	}


}
