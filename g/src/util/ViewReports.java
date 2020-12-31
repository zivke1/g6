package util;

public class ViewReports {
	private String year;
	private String month;
	private String parkName;
	private String reportName;	
	
	public ViewReports(String year, String month, String parkName, String reportName) {
		this.year = year;
		this.month = month;
		this.parkName = parkName;
		this.reportName = reportName;
	} 
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
