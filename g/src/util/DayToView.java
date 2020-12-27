package util;

import java.io.Serializable;

public class DayToView implements Serializable{
	
	    private static final long serialVersionUID = 1L;
		private String day;
		private String usage;
		
		public DayToView() 
		{
			this.usage = "0%";
			this.day = "00";
		}
		
		public DayToView(String day,String usage) 
		{
			this.usage = usage;
			this.day = day;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getUsage() {
			return usage;
		}

		public void setUsage(String usage) {
			this.usage = usage;
		}		
}
