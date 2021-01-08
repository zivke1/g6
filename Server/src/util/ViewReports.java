package util;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class holds information of a specific report 
 * created by park manager and saved for department manager:
 * usage Or visitors amount Or income report.
 */
public class ViewReports implements Serializable {

	private static final long serialVersionUID = 1L;
	private String year;
	private String month;
	private String parkName;
	private String reportName;
	private ArrayList<ArrayList<String>> usagePerHour;
	//private ArrayList<String> usagePerHour;
	private ArrayList<Integer> dayOfUsage;
	private String income;
	private String totalVisitor;
	private ArrayList<String> groupDays;
	private ArrayList<String> userDays;
	private ArrayList<String> memberDays;

	
	public ViewReports(String year, String month, String parkName, String reportName) {
		this.year = year;
		this.month = month;
		this.parkName = parkName;
		this.reportName = reportName;
	} 
	
	public void setDataUsageReport(ArrayList<ArrayList<String>> usagePerHour, ArrayList<Integer> dayOfUsage) {
		this.usagePerHour = usagePerHour;
		this.dayOfUsage = dayOfUsage;
	}
	
	public void setDataIncomeReport(String income) {
		this.income = income;
	}
	
	public void setDataVisitReport(String totalVisitor, ArrayList<String> groupDays, ArrayList<String> userDays, ArrayList<String> memberDays) {
		this.totalVisitor = totalVisitor;
		this.groupDays = groupDays;
		this.userDays = userDays;
		this.memberDays = memberDays;
	}

	public String getTotalVisitor() {
		return totalVisitor;
	}

	public ArrayList<String> getGroupDays() {
		return groupDays;
	}

	public ArrayList<String> getUserDays() {
		return userDays;
	}

	public ArrayList<String> getMemberDays() {
		return memberDays;
	}

	
	public String getYear() {
		return year;
	}

	public String getMonth() {
		return month;
	}

	public String getParkName() {
		return parkName;
	}

	public String getReportName() {
		return reportName;
	}

	public ArrayList<ArrayList<String>> getUsagePerHour() {
		return usagePerHour;
	}

	public ArrayList<Integer> getDayOfUsage() {
		return dayOfUsage;
	}

	public String getIncome() {
		return income;
	}

}