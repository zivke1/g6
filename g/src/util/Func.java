package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Func {

	public static String fixDate(Date d2)
	{
		String d=d2.getDate()+"-"+(d2.getMonth()+1)+"-"+(d2.getYear()+1900);
		return d;
	}
	
	public static void main(String[] args) {
		Date d=new Date();
		Calendar c=Calendar.getInstance();
		SimpleDateFormat dateOnly = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(dateOnly.format(c.getTime()));
		//System.out.println(fixDate(d));
	}
}
