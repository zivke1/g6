package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 
 * Class to present date by order
 * of day - month - year
 *
 */
public class Func {

	public static String fixDate(Date d2)
	{
		String d=d2.getDate()+"-"+(d2.getMonth()+1)+"-"+(d2.getYear()+1900);
		return d;
	}
	
	public static String fixDateString(String date)
	{
		String year=date.substring(0, 4),month=date.substring(5, 7),day=date.substring(8, 10),res=day+"-"+month+"-"+year;
		return res;
	}
	
	public static String unFixDate(String date)
	{
		String day=date.substring(0, 2),month=date.substring(3, 5),year=date.substring(6, 10),res=year+"-"+month+"-"+day;
		return res;
	}

	public static void main(String[] args) {
		Date d=new Date();
//		Calendar c=Calendar.getInstance();
//		SimpleDateFormat dateOnly = new SimpleDateFormat("dd/MM/yyyy");
//		System.out.println(dateOnly.format(c.getTime()));
		System.out.println(fixDateString(unFixDate(fixDate(d))));
	}
}
