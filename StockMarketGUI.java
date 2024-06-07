import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class StockMarketGUI extends JFrame {
    private JComboBox<Company> companyComboBox;
    private JLabel priceLabel;
    private JLabel quantityLabel;
    private JLabel currencyLabel;
    private JTextArea logTextArea;
    private int count=0;
    private double money=0;
    private JLabel stockLabel; 
    private StockMarket stockMarket;
    private User user;
    private Company selectedCompany;

    public StockMarketGUI() {
        setTitle("Stock Market");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        stockMarket = new StockMarket();
        user = new User();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel companyLabel = new JLabel("Company:");
        topPanel.add(companyLabel);

        companyComboBox = new JComboBox<>();
        ArrayList<Company> companies = stockMarket.getCompanies();

        for (Company company : companies) {
            companyComboBox.addItem(company);
        }
        companyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedCompany = (Company) companyComboBox.getSelectedItem();
                updatePriceLabel(selectedCompany);
                updateQuantityLabel(selectedCompany);
            }
        });

        topPanel.add(companyComboBox);

        priceLabel = new JLabel();
        topPanel.add(priceLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Company selectedCompany = (Company) companyComboBox.getSelectedItem();
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(StockMarketGUI.this, "Enter the number of shares to buy:"));
                    double currency = user.getCurrency();

                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(StockMarketGUI.this, "Invalid input! Please enter a positive quantity.");
                        return;
                    }

                    if (currency >= selectedCompany.getCurrentPrice() * quantity) {
                        int remainingQuantity = stockMarket.buyStock(selectedCompany, quantity, currency);
                        count+=quantity;
                        if (remainingQuantity >= 0) {
                            user.buyStock(selectedCompany, quantity - remainingQuantity);
                            logTextArea.append("Bought " + (quantity - remainingQuantity) + " shares of " + selectedCompany.getName()
                                    + " at $" + formatCurrency(selectedCompany.getCurrentPrice()) + " each. Total cost: $"
                                    + formatCurrency(selectedCompany.getCurrentPrice() * (quantity - remainingQuantity)) + "\n");
                            updateQuantityLabel(selectedCompany);
                            updateCurrencyLabel();
                        } else {
                            JOptionPane.showMessageDialog(StockMarketGUI.this, "Insufficient quantity available to buy " + quantity + " shares of " + selectedCompany.getName() + "\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(StockMarketGUI.this, "Insufficient currency to buy " + quantity + " shares of " + selectedCompany.getName() + "\n");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StockMarketGUI.this, "Invalid input! Please enter a valid quantity.");
                }
            }
        });
        centerPanel.add(buyButton);
        JButton portfolioButton = new JButton("Portfolio");
        portfolioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPortfolio();
            }
        });

        // Add the button to the frame
        centerPanel.add(portfolioButton);
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Company selectedCompany = (Company) companyComboBox.getSelectedItem();
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog(StockMarketGUI.this, "Enter the number of shares to sell:"));

                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(StockMarketGUI.this, "Invalid input! Please enter a positive quantity.");
                        return;
                    }

                    int currentQuantity = user.getStockQuantity(selectedCompany);
                    if (currentQuantity >= quantity) {
                        count-=quantity;
                        user.sellStock(selectedCompany, quantity);
                        double price = selectedCompany.getCurrentPrice();
                        double totalPrice = price * quantity;
                        logTextArea.append("Sold " + quantity + " shares of " + selectedCompany.getName()
                                + " at $" + formatCurrency(price) + " each. Total sale: $" + formatCurrency(totalPrice) + "\n");
                        updateQuantityLabel(selectedCompany);
                        updateCurrencyLabel();
                    } else {
                        JOptionPane.showMessageDialog(StockMarketGUI.this, "Insufficient shares of " + selectedCompany.getName() + " to sell " + quantity + "\n");

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(StockMarketGUI.this, "Invalid input! Please enter a valid quantity.");
                }
            }
        });
        centerPanel.add(sellButton);

        quantityLabel = new JLabel();
        centerPanel.add(quantityLabel);


        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        currencyLabel = new JLabel();
        updateCurrencyLabel();
        bottomPanel.add(currencyLabel, BorderLayout.NORTH);

        logTextArea = new JTextArea(10, 40);
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        bottomPanel.add(logScrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    
        stockLabel = new JLabel();
        centerPanel.add(stockLabel); 
    }
    private void updatePriceLabel(Company company) {
        double price = company.getCurrentPrice();
        priceLabel.setText("Current Price: $" + formatCurrency(price));
        Stock stock = new Stock(company.getName(), price);  
        String stockName = stock.getName();
        double stockPrice = stock.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        stockPrice = Double.parseDouble(decimalFormat.format(stockPrice));
		stockLabel.setText("The price of "+stockName +":  $"+ stockPrice);
		
		
    }

    // Implement the Observer interface method
    public void update(String stockName, double price) {
        if (stockName.equals(companyComboBox.getSelectedItem().toString())) {
        	if (selectedCompany != null && stockName.equals(selectedCompany.getName())) {
                priceLabel.setText("Current Price: $" + formatCurrency(price));
        	}
        }
    }
    public void showPortfolio() {
        // Get the total shares, company list, and remaining money from your calculations
        int totalShares = count; // Calculate the total shares
        double remainingMoney = money; // Calculate the remaining money

        // Create an instance of PortfolioFrame and pass the variables
        PortfolioFrame portfolioFrame = new PortfolioFrame(totalShares, remainingMoney);

        // Show the portfolio frame
        portfolioFrame.setVisible(true);
    }


    private void updateQuantityLabel(Company company) {
        int quantity = user.getStockQuantity(company);
        quantityLabel.setText("Your Quantity: " + quantity);
    }
    
    private void updateCurrencyLabel() {
        double currency = user.getCurrency();
        currencyLabel.setText("Available Currency: $" + formatCurrency(currency));
        money=currency;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        money = Double.parseDouble(decimalFormat.format(money));

    }

    private String formatCurrency(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(value);
    }

    public static void main(String[] args) {
        new StockMarketGUI();
        
    }
}
