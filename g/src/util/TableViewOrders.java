package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableViewOrders {
	public ObservableList<OrderToView> getOrders(){
		ObservableList<OrderToView> orders = FXCollections.observableArrayList();
		
		orders.add(new OrderToView());
		
		
		
		
		return orders;
	}
}
