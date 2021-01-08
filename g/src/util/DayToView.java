package util;

import java.io.Serializable;
/**
 * class to assist in present 
 * usage report table
 *
 */
public class DayToView implements Serializable {

	private static final long serialVersionUID = 1L;
	private String day;
	private String h1, h2, h3, h4, h5, h6, h7, h8, h9;

	public DayToView() {
		day = "";
		h1 = "8:00";
		h2 = "9:00";
		h3 = "10:00";
		h4 = "11:00";
		h5 = "12:00";
		h6 = "13:00";
		h7 = "14:00";
		h8 = "15:00";
		h9 = "16:00";
	}

	public DayToView(String day, String h1, String h2, String h3, String h4, String h5, String h6, String h7,
			String h8,String h9) {
		super();
		this.day = day;
		this.h1 = h1;
		this.h2 = h2;
		this.h3 = h3;
		this.h4 = h4;
		this.h5 = h5;
		this.h6 = h6;
		this.h7 = h7;
		this.h8 = h8;
		this.h9 = h9;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getH1() {
		return h1;
	}

	public void setH1(String h1) {
		this.h1 = h1;
	}

	public String getH2() {
		return h2;
	}

	public void setH2(String h2) {
		this.h2 = h2;
	}

	public String getH3() {
		return h3;
	}

	public void setH3(String h3) {
		this.h3 = h3;
	}

	public String getH4() {
		return h4;
	}

	public void setH4(String h4) {
		this.h4 = h4;
	}

	public String getH5() {
		return h5;
	}

	public void setH5(String h5) {
		this.h5 = h5;
	}

	public String getH6() {
		return h6;
	}

	public void setH6(String h6) {
		this.h6 = h6;
	}

	public String getH7() {
		return h7;
	}

	public void setH7(String h7) {
		this.h7 = h7;
	}

	public String getH8() {
		return h8;
	}

	public void setH8(String h8) {
		this.h8 = h8;
	}
	public String getH9() {
		return h9;
	}

	public void setH9(String h9) {
		this.h9 = h9;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
