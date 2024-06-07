import javax.swing.*;
import java.awt.*;

public class PortfolioFrame extends JFrame {
    private JLabel totalSharesLabel;
    private JLabel remainingMoneyLabel;
    public PortfolioFrame(int totalShares, double remainingMoney) {
        setTitle("Portfolio");
        setSize(200, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a JPanel to hold the labels
        JPanel portfolioPanel = new JPanel();
        portfolioPanel.setLayout(new GridLayout(4, 1));

        // Create and add the labels
        JLabel titleLabel = new JLabel("	User's Portfolio:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        portfolioPanel.add(titleLabel);

        totalSharesLabel = new JLabel("Total Shares: " + totalShares);
        portfolioPanel.add(totalSharesLabel);

        remainingMoneyLabel = new JLabel("Remaining Money: " + remainingMoney);
        portfolioPanel.add(remainingMoneyLabel);

        // Set the layout of the frame to BorderLayout
        setLayout(new BorderLayout());

        // Add the portfolioPanel to the center of the frame
        add(portfolioPanel, BorderLayout.CENTER);
    }
}
