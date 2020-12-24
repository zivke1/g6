package clientTry;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import util.NextStages;
import util.OrderToView;
import util.TableViewOrders;
import javafx.stage.Stage;

/**
 * class to enter customer ID and check if he\she have existing orders
 * if so- present their orders in table and enable viewing them 
 * by clicking the right row in the table - which goes to view order control
 * @author shani
 *
 */
public class EmployeeEnterCustomerIDController {
	private String fName, lName, role, userID, parkName, customerID;
	MouseEvent m_event;
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button helpBtn;

    @FXML
    private TextField enterID;

    @FXML
    private TableView<OrderToView> existingOrdersTable;

    @FXML
    private Label NoExistOrderMsg;

    @FXML
    private Button btnCheck;
    
    @FXML
    void backClicked(MouseEvent event) {
    	setAllUnvisible();
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_event.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order");
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }

    @FXML /// maybe delete
    void viewOrder(MouseEvent event) {

    }
    
    @FXML
    void CheckIDInOrders(MouseEvent event) {
    	// if customer Id is in orders table -> set visible tblExisitingOrder
    	customerID = enterID.getText();
    		ArrayList<String> arr = new ArrayList<>();
    			arr.add(customerID);
    			arr.add("ReturnUserIDInTableOrders");
    			ClientMain.chat.accept(arr);
    			ArrayList<OrderToView> temp = ChatClient.dataInArrayListObject;
    			if (!temp.isEmpty()) {
    				// order ID Column
    				TableColumn<OrderToView, String> orderIDcolumn = new TableColumn<>("Order ID");
    				orderIDcolumn.setMinWidth(150);
    				orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));

    				// Status Column
    				TableColumn<OrderToView, String> statusColumn = new TableColumn<>("Status");
    				statusColumn.setMinWidth(150);
    				statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    				// Date Column
    				TableColumn<OrderToView, String> dateColumn = new TableColumn<>("Date");
    				dateColumn.setMinWidth(150);
    				dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    				TableViewOrders obsList = new TableViewOrders();
    				// tblExistingOrder = new TableView<>();
    				existingOrdersTable.setItems(obsList.getOrders(temp));
    				
    				existingOrdersTable.setRowFactory(tv -> {
    					TableRow<OrderToView> row = new TableRow<>();
    					row.setOnMouseClicked(evento -> {
    						if (evento.getClickCount() == 2 && (!row.isEmpty())) {
    							OrderToView rowData = row.getItem();
    							NextStages nextStages = new NextStages("/fxmlFiles/ViewOrder.fxml", "View Order");
    							FXMLLoader loader = nextStages.goToNextStage(evento);
    							setAllUnvisible();
    							ViewOrderController viewOrderControl = loader.getController();
    							viewOrderControl.setDetails(fName, lName, role, userID, parkName, rowData.getOrderID());
    							viewOrderControl.setPreviousPage(event);
    						}
    					});
    					return row;
    				});

    				existingOrdersTable.getColumns().addAll(orderIDcolumn, statusColumn, dateColumn);
    				existingOrdersTable.setVisible(true);

    			} else {
    				NoExistOrderMsg.setVisible(true);
    			}
    }
    void setDetails(String fName,String lName,String role,String userID,String parkName){
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkName = parkName;
    }
    
	public void setAllUnvisible() {
		existingOrdersTable.setVisible(false);
		NoExistOrderMsg.setVisible(false);
	}

	public void setPreviousPage(MouseEvent event) {
		m_event=event;
	}

}
