package com;


public class Statistics {
    private double totalSaleAmount;
    private long totalSoldItems;
    public double getTotalSaleAmount() {
		return totalSaleAmount;
	}
	public void setTotalSaleAmount(double totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	public long getTotalSoldItems() {
		return totalSoldItems;
	}
	public void setTotalSoldItems(long totalSoldItems) {
		this.totalSoldItems = totalSoldItems;
	}
	public long getTotalNotSoldItems() {
		return totalNotSoldItems;
	}
	public void setTotalNotSoldItems(long totalNotSoldItems) {
		this.totalNotSoldItems = totalNotSoldItems;
	}
	private long totalNotSoldItems;

    // Getters and Setters
}

