import java.util.ArrayList;
import java.util.Random;

public class StockMarket {
    private ArrayList<Company> companies;

    public StockMarket() {
        companies = new ArrayList<>();
        generateRandomCompanies();
    }

    private void generateRandomCompanies() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            String companyName = "Company " + (i + 1);
            double price = 10.0 + random.nextDouble() * 90.0; // Random price between 10.0 and 100.0

            Company company = new Company(companyName, price);
            companies.add(company);
        }
    }
    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public int buyStock(Company company, int quantity, double currency) {
        double price = company.getCurrentPrice();
        int maxQuantity = (int) (currency / price);
        int remainingQuantity = Math.max(quantity - maxQuantity, 0);
        return remainingQuantity;
    }


    public void sellStock(Company company, int quantity) {
    	double totalPrice = company.getCurrentPrice() * quantity;
        company.setQuantity(company.getQuantity() + quantity);
        }    
}