package util;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class return ObservableList of OrderToView
 * for view orders table
 * @return ObservableList
 */
public class TableViewOrders {
	public ObservableList<OrderToView> getOrders(ArrayList<OrderToView> temp){
		ObservableList<OrderToView> orders = FXCollections.observableArrayList();
		
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != null)
				orders.add(temp.get(i));
		}
		
		return orders;
	}
}
