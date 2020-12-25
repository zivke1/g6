package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.FreePlaceInPark;
import util.NextStages;
import util.OrderToView;

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
    private TableView<FreePlaceInPark> freePlaceTable;

    @FXML
    private TableColumn<FreePlaceInPark, String> dateCol;

    @FXML
    private TableColumn<FreePlaceInPark, String> timeCol;

//    private ObservableList<freeOrder> list1 = FXCollections.observableArrayList();
   

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn<FreePlaceInPark, String> orderTimecolumn = new TableColumn<>("Time");
		orderTimecolumn.setMinWidth(100);
		orderTimecolumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		
		TableColumn<FreePlaceInPark, String> orderDatecolumn = new TableColumn<>("Date");
		orderDatecolumn.setMinWidth(100);
		orderDatecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		freePlaceTable.getColumns().addAll(orderTimecolumn, orderDatecolumn);
		
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
    	m_invite.remove(0);
    	ArrayList<FreePlaceInPark>timeToTableArrayList =  ChatClient.dataInArrayListFreePlaceInParks;
    	//TODO check set the values in the chart
    	setTableOfFreePlace(timeToTableArrayList);
    	
    	
    }
    


	private void setTableOfFreePlace(ArrayList<FreePlaceInPark> timeToTableArrayList) {
		// TODO Auto-generated method stub
		ObservableList<FreePlaceInPark> timeToTable  = FXCollections.observableArrayList();
		for( int i = 0; i < timeToTableArrayList.size(); i++) {
			if(timeToTableArrayList.get(i) != null)
				timeToTable.add(timeToTableArrayList.get(i));
		}
		freePlaceTable.setItems(timeToTable);
		freePlaceTable.setRowFactory(tv -> {
			TableRow<FreePlaceInPark> row = new TableRow<>();
			row.setOnMouseClicked(evento -> {
				if (evento.getClickCount() == 2 && (!row.isEmpty())) {
					FreePlaceInPark rowData = row.getItem();
					NextStages nextStages = new NextStages("/fxmlFiles/PaymentPage.fxml", "Payment Page");
					FXMLLoader loader = nextStages.goToNextStage(evento);
					PaymentPageController paymentPageController = loader.getController();
					paymentPageController.setDetails(m_fName, m_lName, m_role, m_userID , m_parkName);
					paymentPageController.setPreviousPage(evento) ;
					paymentPageController.setMainPage(m_eventMain);
					m_invite.set(2, rowData.getTime());
					m_invite.set(3, rowData.getDate());
					
					paymentPageController.setOrderDetails(m_invite,"");
				}
			});
			return row;
		});
		
//		freePlaceTable.getColumns().addAll(orderTimecolumn, orderDatecolumn);
	}

	public void setPreviousPage(MouseEvent event) {
		// TODO Auto-generated method stub
		m_previousPage = event;
	}
	
	public void setMainPage(MouseEvent event) {
		m_eventMain=event;
	}
    
}
