package com;


import com.Product;

import java.util.List;

public class CombinedData {
    private List<Product> transactions;
    public List<Product> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Product> transactions) {
		this.transactions = transactions;
	}
	public Statistics getStatistics() {
		return statistics;
	}
	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	public List<BarChartData> getBarChartData() {
		return barChartData;
	}
	public void setBarChartData(List<BarChartData> barChartData) {
		this.barChartData = barChartData;
	}
	public List<PieChartData> getPieChartData() {
		return pieChartData;
	}
	public void setPieChartData(List<PieChartData> pieChartData) {
		this.pieChartData = pieChartData;
	}
	private Statistics statistics;
    private List<BarChartData> barChartData;
    private List<PieChartData> pieChartData;

    // Getters and Setters
}

