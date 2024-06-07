class Company {
    private String name;
    private double currentPrice;
    private int quantity;
    public Company(String name, double currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }
    

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return name;
    }

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}