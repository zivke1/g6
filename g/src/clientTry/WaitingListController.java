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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.FreePlaceInPark;
import util.Func;
import util.NextStages;
import util.OrderToView;
/**
 * this page show the date and time that you can do your invite 
 * and ask you to make your order in this date or register to waiting list
 * 
 *
 */
public class WaitingListController implements Initializable {
	String m_fName,m_lName,m_role,m_userID,m_parkName;
    MouseEvent m_previousPage,m_eventMain;
    ArrayList<String> m_invite;
    String m_orderDetails="";

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button enterWaitingListBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private TableView<FreePlaceInPark> freePlaceTable;

//    @FXML
//    private TableColumn<FreePlaceInPark, String> dateCol;

//    @FXML
//    private TableColumn<FreePlaceInPark, String> timeCol;

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
    void enterWaitingListClicked(MouseEvent event) {
     	m_invite.add(0,"setInWaitingList");
    	ClientMain.chat.accept(m_invite);
    	String orderNumber = ChatClient.dataInArrayList.get(0);
     	NextStages nextStages = new NextStages("/fxmlFiles/EnterWaitingListPageSuccessPage.fxml", "Waiting List", m_userID);
    	FXMLLoader loader = nextStages.goToNextStage(event);
    	
    	EnterWaitingListPageSuccessController enterWaitingListPageSuccessController = loader.getController();
    	enterWaitingListPageSuccessController.setMainPage(m_eventMain);
    	enterWaitingListPageSuccessController.setOrderNumber(orderNumber);
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", m_userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	Tooltip tt = new Tooltip();
		tt.setText("This page shows the available dates \nfor a visit to the park according to \nthe original booking details\n"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
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
    	ArrayList<FreePlaceInPark>timeToTableArrayList = ChatClient.dataInArrayListFreePlaceInParks;
    	//TODO check set the values in the chart
    	setTableOfFreePlace(timeToTableArrayList);
    	timeToTableArrayList.clear();	
    }

	private void setTableOfFreePlace(ArrayList<FreePlaceInPark> timeToTableArrayList) {
		// TODO Auto-generated method stub
		ObservableList<FreePlaceInPark> timeToTable  = FXCollections.observableArrayList();
	
		ArrayList<FreePlaceInPark> toTable= new ArrayList<FreePlaceInPark>();
		for(FreePlaceInPark temp: timeToTableArrayList) {
			toTable.add(new FreePlaceInPark(temp.getTime(), Func.fixDateString(temp.getDate())));
		}
	
		for( int i = 0; i < toTable.size(); i++) {
			if(toTable.get(i) != null)
				timeToTable.add(toTable.get(i));
		}
		freePlaceTable.setItems(timeToTable);
		freePlaceTable.setRowFactory(tv -> {
			TableRow<FreePlaceInPark> row = new TableRow<>();
			row.setOnMouseClicked(evento -> {
				if (evento.getClickCount() == 2 && (!row.isEmpty())) {
					FreePlaceInPark rowData = row.getItem();
					NextStages nextStages = new NextStages("/fxmlFiles/PaymentPage.fxml", "Payment Page", m_userID);
					FXMLLoader loader = nextStages.goToNextStage(evento);
					PaymentPageController paymentPageController = loader.getController();
					paymentPageController.setDetails(m_fName, m_lName, m_role, m_userID , m_parkName);
					paymentPageController.setPreviousPage(evento) ;
					paymentPageController.setMainPage(m_eventMain);
					paymentPageController.setOrderDetails(m_orderDetails);
					paymentPageController.setOccasional(false);
					m_invite.set(2, rowData.getTime());
					m_invite.set(3, Func.unFixDate(rowData.getDate()));
					
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
	
	public void setOrderDetails(String orderDetails) {
		m_orderDetails=orderDetails;
	}
    
}
