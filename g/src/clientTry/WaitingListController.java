package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WaitingListController implements Initializable {
	String m_fName,m_lName,m_role,m_userID,m_parkName;
    MouseEvent m_previousPage,m_eventMain;
    ArrayList<String> m_invite;

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
    		return date.getValue();
    	}
    	
     	public String getTime() {
    		return time.getValue();
     	}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{
			freeOrder x = new freeOrder("hello", "world");
		list1.add(x);
		freePlaceTable.setItems(list1);
		});
	}
    
    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_previousPage.getSource()).getScene().getWindow()).show();
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

    public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		m_fName = fName;
		m_lName = lName;
		m_role = role;
		m_userID = userID;
		m_parkName = parkName;

	}
    
    public void setOrderDetails(ArrayList<String> invite, String price) {
    	m_invite=invite;
    	m_invite.add(price);

    	m_invite.add(0,"getFreePlace");
    	ClientMain.chat.accept(m_invite);
    	
    	
    	//TODO check set the values in the chart
    	
    }
    


	public void setPreviousPage(MouseEvent event) {
		// TODO Auto-generated method stub
		m_previousPage = event;
	}
	
	public void setMainPage(MouseEvent event) {
		m_eventMain=event;
	}
    
}
