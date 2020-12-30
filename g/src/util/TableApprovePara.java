package util;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Idan
 *Class for table view in approve parameters page for department manager 
 */

public class TableApprovePara {
	public ObservableList<ParameterToView> getParamters(ArrayList<ParameterToView> temp){
		ObservableList<ParameterToView> parameters = FXCollections.observableArrayList();
		
		for( int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != null)
				parameters.add(temp.get(i));
		}
		
		return parameters;
	}

}
