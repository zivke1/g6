package clientTry;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class WaitingListController implements Initializable {

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private TableView<freeOrder> freePlaceTable;

    @FXML
    private TableColumn<freeOrder, String> dateCol;

    @FXML
    private TableColumn<freeOrder, String> timeCol;

    private ObservableList<freeOrder> list1 = FXCollections.observableArrayList();
   
    class freeOrder{
    	private SimpleStringProperty date;
    	private SimpleStringProperty  time;
    	
    	public freeOrder(String date, String time) {
    		this.date = new SimpleStringProperty(date);
    		this.time = new SimpleStringProperty(time);
    	}
    	
    	public SimpleStringProperty getDateProperty() {
    		return date;
    	}
    	
     	public SimpleStringProperty getTimeProperty() {
    		return time;
    	}
    	public String getDate() {
    		return date.get();
    	}
    	
     	public String getTime() {
    		return time.get();
     	}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		freePlaceTable.setItems(list1);
		list1.add(new freeOrder("hello", "world"));
	}
    
    @FXML
    void backClicked(MouseEvent event) {

    }

    @FXML
    void finishOrderClicked(MouseEvent event) {

    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }

}
