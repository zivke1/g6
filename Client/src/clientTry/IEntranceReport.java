package clientTry;

import java.util.ArrayList;

import util.HourAmount;

public interface IEntranceReport {

	void extractedChat(ArrayList<String> arr);

	ArrayList<HourAmount> extractedHourAmountArray();
	
	//public void logic(String parkName,String date,String type);
}