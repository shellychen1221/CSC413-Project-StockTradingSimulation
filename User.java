import java.util.HashMap;
import javax.swing.JOptionPane;

class User {
    private double currency;
    private HashMap<Company, Integer> stocks;

    public User() {
        currency = 1000.00;
        stocks = new HashMap<>();
    }

    public double getCurrency() {
        return currency;
    }

    public int getStockQuantity(Company company) {
        return stocks.getOrDefault(company, 0);
    }

    public void buyStock(Company company, int quantity) {
        double price = company.getCurrentPrice();
        double cost = price * quantity;
        currency -= cost;

        int currentQuantity = stocks.getOrDefault(company, 0);
        stocks.put(company, currentQuantity + quantity);

    }

    public void sellStock(Company company, int quantity) {
        double price = company.getCurrentPrice();
        double sale = price * quantity;
        currency += sale;

        int currentQuantity = stocks.getOrDefault(company, 0);
        stocks.put(company, currentQuantity - quantity);
    }

    public HashMap<Company, Integer> getPortfolio() {
        HashMap<Company, Integer> portfolio = null;
		return portfolio;
    }

}