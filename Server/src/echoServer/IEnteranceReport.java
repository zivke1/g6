package echoServer;

import java.sql.SQLException;
import java.util.ArrayList;

import util.HourAmount;
import util.TypeOfOrder;

public interface IEnteranceReport {

	ArrayList<HourAmount> depManVisitRep( ArrayList<String> arr) ;

}