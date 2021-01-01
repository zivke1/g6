package util;

import java.util.ArrayList;

public class ViewReports {
	private String year;
	private String month;
	private String parkName;
	private String reportName;
	private ArrayList<String> usagePerDay;
	private String income;
	
	
	public ViewReports(String year, String month, String parkName, String reportName) {
		this.year = year;
		this.month = month;
		this.parkName = parkName;
		this.reportName = reportName;
	} 
	
	public void setDataUsageReport(ArrayList<String> usagePerDay) {
		this.usagePerDay = usagePerDay;
	}
	
	public void setDataIncomeReport(String income) {
		this.income = income;
	}
	
	public void setDataVisitReport() {}
}
