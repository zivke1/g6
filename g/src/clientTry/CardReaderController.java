package clientTry;

import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import util.Func;
import util.NextStages;
import util.OrderToView;
import util.Role;
import util.TableViewOrders;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * this controller simulate enter and exit from the park if there is order for
 * this ID that the user enter
 *
 */
public class CardReaderController implements Initializable {
	@FXML
	private ImageView imgContactUs;

	@FXML
	private AnchorPane orderDetails;

	@FXML
	private Label orderID;

	@FXML
	private Label orderStatus;

	@FXML
	private Label totalCost;

	@FXML
	private Label email;

	@FXML
	private Label typeOfOr;

	@FXML
	private Label pName;

	@FXML
	private Label amOfVisit;

	@FXML
	private Label date;

	@FXML
	private Label hour;

	@FXML
	private Label typeOfOrder;

	@FXML
	private Label numVisit;

	@FXML
	private Label textGetIn;

	@FXML
	private AnchorPane textGetOut;

	@FXML
	private Label textErrNumberVisitors;

	@FXML
	private AnchorPane enterUserID;

	@FXML
	private AnchorPane anchorCustomer;

	@FXML
	private TableView<OrderToView> tblExistingOrder;

	@FXML
	private Label NoExistOrderMsg;

	@FXML
	private AnchorPane tyForVisitorOut;

	@FXML
	private TextField textUserID;

	@FXML
	private RadioButton rbUserId;

	@FXML
	private ToggleGroup op;

	@FXML
	private RadioButton rbCard;

	@FXML
	private Button btnEnterUserID;

	@FXML
	private Button btnEnterCard;

	@FXML
	private Label textUserID2;

	@FXML
	private Label txtUserIdAboveTable;

	@FXML
	private TextField amountActual;

	@FXML
	private AnchorPane checkAmountAncor;

	@FXML
	private Label howMuchTxt;

	@FXML
	private Label timePass;

	@FXML
	private Label isntTime;

	@FXML
	private Button btnBack;

	@FXML
	private Button OkBtn;

	private String fNameH;

	private String lNameH;

	private String roleH;

	private String userIDH;

	private String parkNameH;

	private String orderIDH;

	private String orderStatusH;
	TableColumn<OrderToView, String> orderIDcolumn;
	TableColumn<OrderToView, String> statusColumn;
	TableColumn<OrderToView, String> dateColumn;
	ArrayList<String> arrtmp = new ArrayList<>();

	MouseEvent m_event;

	
	
	
	@FXML
	void btnCheckActualVisitors(ActionEvent event) {
		if (amountActual.getText().length() > 0) {
			int amountActualInt;
			if (onlyNumber(amountActual.getText())) {
				amountActualInt = Integer.parseInt(amountActual.getText());
				int maxAmount = Integer.valueOf(numVisit.getText());
				if (amountActualInt > maxAmount || amountActualInt == 0) {
					textErrNumberVisitors.setVisible(true);
					return;
				} else {
					arrtmp.add(amountActualInt + "");
					ClientMain.chat.accept(arrtmp);
					textErrNumberVisitors.setVisible(false);
					textGetIn.setVisible(true);
					btnBack.setVisible(false);
					OkBtn.setVisible(true);
					checkAmountAncor.setVisible(false);
					howMuchTxt.setVisible(false);

				}

			} else
				textErrNumberVisitors.setVisible(true);
		} else
			textErrNumberVisitors.setVisible(true);
	}

	public boolean onlyNumber(String str) {
		char[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		boolean check = false;
		for (int i = 0; i < str.length(); i++) {
			for (int j = 0; j < nums.length; j++) {
				if (str.charAt(i) == nums[j]) {
					check = true;
				}
			}
			if (check == false) {
				return false;
			}
			check = false;
		}
		return true;
	}

	@FXML
	void enterCardsimulate(ActionEvent event) {
		ArrayList<String> arr = new ArrayList<>();

		arr.add("simulationCardReader");
		ClientMain.chat.accept(arr);
		if (ChatClient.dataInArrayList.size() > 0) {
			userIDH = ChatClient.dataInArrayList.get(0);
			textUserID2.setVisible(true);
			txtUserIdAboveTable.setText(userIDH);
			arr.clear();
			getToTable();
		}
		else
			NoExistOrderMsg.setVisible(true);
			

	}

	public void getToTable() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(userIDH);
		arr.add("ReturnUserIDInTableOrdersForCardReader");
		ClientMain.chat.accept(arr);
		ArrayList<OrderToView> temp = ChatClient.dataInArrayListObject;

		if (!temp.isEmpty()) {
			TableViewOrders obsList = new TableViewOrders();
			for (int i = 0; i < tblExistingOrder.getItems().size(); i++) {
				tblExistingOrder.getItems().clear();
			}
			for (int i = 0; i < temp.size(); i++) {
				if (temp.get(i).getStatus().equals("active")) {
					String orderID = temp.get(i).getOrderID();
					enterUserID.setVisible(false);
					orderDetails.setVisible(true);
					arr.clear();
					arr.add("ViewOrder");
					arr.add(temp.get(i).getOrderID());
					ClientMain.chat.accept(arr);
					arr = ChatClient.dataInArrayList;
					this.orderID.setText(arr.get(0));
					pName.setText(arr.get(1));
					hour.setText(arr.get(2));
					date.setText(Func.fixDateString(arr.get(3)));
					numVisit.setText(arr.get(4));
					typeOfOrder.setText(arr.get(5) + "'s order");
					orderStatus.setText("finished");
					this.orderStatusH = orderStatus.getText();
					totalCost.setText(arr.get(7) + "$");
					email.setText(arr.get(8));
					textGetOut.setVisible(true);
					arr.clear();
					arr.add("updateToFinished");
					arr.add(orderID);
					ClientMain.chat.accept(arr);
					OkBtn.setVisible(true);
					i = temp.size();
				}
			}
			tblExistingOrder.setItems(obsList.getOrders(temp));
			tblExistingOrder.setRowFactory(tv -> {
				TableRow<OrderToView> row = new TableRow<>();
				row.setOnMouseClicked(evento -> {// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>start the double
													// click on order
					if (evento.getClickCount() == 2 && (!row.isEmpty())) {
						OrderToView rowData = row.getItem();
						arrtmp.clear();
						arrtmp.add("ViewOrder");
						arrtmp.add(rowData.getOrderID());
						ClientMain.chat.accept(arrtmp);
						arrtmp = ChatClient.dataInArrayList;
						LocalTime timeOfChossenOrder = LocalTime.of(Integer.valueOf(arrtmp.get(2).substring(0, 2)),
								Integer.valueOf(arrtmp.get(2).substring(3, 5)),
								Integer.valueOf(arrtmp.get(2).substring(6, 8)));
						LocalTime currentTime = LocalTime.now();
						LocalTime timeOfChossenOrderPlusH = timeOfChossenOrder.plusHours(3);
						if (currentTime.compareTo(timeOfChossenOrder) >= 0
								&& currentTime.compareTo(timeOfChossenOrderPlusH) < 0)/** if the visitor came between the
																						* time
																						* of orders up to hour late*/
						{
							this.orderID.setText(arrtmp.get(0));
							pName.setText(arrtmp.get(1));
							hour.setText(arrtmp.get(2));
							date.setText(Func.fixDateString(arrtmp.get(3)));
							numVisit.setText(arrtmp.get(4));
							typeOfOrder.setText(arrtmp.get(5) + "'s order");
							orderStatus.setText("active");
							this.orderStatusH = orderStatus.getText();
							totalCost.setText(arrtmp.get(7) + "$");
							email.setText(arrtmp.get(8));
							textGetOut.setVisible(true);
							arrtmp.clear();
							arrtmp.add("updateToActive");
							arrtmp.add(rowData.getOrderID());
							enterUserID.setVisible(false);
							orderDetails.setVisible(true);
							checkAmountAncor.setVisible(true);
							howMuchTxt.setVisible(true);
							textGetOut.setVisible(false);
							btnBack.setVisible(true);
							OkBtn.setVisible(false);
							timePass.setVisible(false);
							isntTime.setVisible(false);
							amountActual.setText("");
						} else {
							if (currentTime.compareTo(timeOfChossenOrder) >= 0) {
								timePass.setText("Your order was to " + timeOfChossenOrder.toString()
										+ "\nThe time of the visit is pass. sorry");
								isntTime.setVisible(false);
								timePass.setVisible(true);
							} else {
								isntTime.setText("The time of the order has not yet arrived\n please wait to "
										+ timeOfChossenOrder.toString());
								timePass.setVisible(false);
								isntTime.setVisible(true);
							}
						}
					}
				});
				return row;
			});

			NoExistOrderMsg.setVisible(false);
			tblExistingOrder.setVisible(true);
			temp.clear();
		} else {
			tblExistingOrder.setVisible(false);
			NoExistOrderMsg.setVisible(true);
		}
	}

	@FXML
	void enterUserIdManual(ActionEvent event) {
		if (textUserID.getText().length() > 0) {
			if (onlyNumber(textUserID.getText())) {
				timePass.setVisible(false);
				isntTime.setVisible(false);
				userIDH = textUserID.getText();
				textUserID2.setVisible(true);
				txtUserIdAboveTable.setText(userIDH);
				getToTable();
			}
		}
	}

	@FXML
	void backBtn(ActionEvent event) {
		btnBack.setVisible(false);
		enterUserID.setVisible(true);
		orderDetails.setVisible(false);
		tblExistingOrder.setVisible(true);
	}

	@FXML
	void OkBtnPress(ActionEvent event) {
		btnBack.setVisible(false);
		enterUserID.setVisible(true);
		orderDetails.setVisible(false);
		tblExistingOrder.setVisible(false);
		textUserID2.setVisible(false);
		txtUserIdAboveTable.setText("");
		textUserID.setText("");
		textGetIn.setVisible(false);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName, String orderID) {
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", "");
		nextStages.openPopUp();
	}

	@FXML
	void rbUserIDPress(ActionEvent event) {
		textUserID.setText("");
		textUserID.setVisible(true);
		btnEnterUserID.setVisible(true);
		btnEnterCard.setVisible(false);
		timePass.setVisible(false);
		isntTime.setVisible(false);
		textUserID2.setVisible(false);
		txtUserIdAboveTable.setText("");
		NoExistOrderMsg.setVisible(false);
		tblExistingOrder.setVisible(false);
	}

	@FXML
	void rdEnterCard(ActionEvent event) {
		textUserID.setVisible(false);
		btnEnterUserID.setVisible(false);
		btnEnterCard.setVisible(true);
		timePass.setVisible(false);
		isntTime.setVisible(false);
		textUserID2.setVisible(false);
		txtUserIdAboveTable.setText("");
		NoExistOrderMsg.setVisible(false);
		tblExistingOrder.setVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/** order ID Column*/
		orderIDcolumn = new TableColumn<>("Order ID");
		orderIDcolumn.setMinWidth(150);
		orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));

		/**Status Column*/
		statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(150);
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		/** Date Column*/
		dateColumn = new TableColumn<>("Date");
		dateColumn.setMinWidth(150);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		tblExistingOrder.getColumns().addAll(orderIDcolumn, statusColumn, dateColumn);

	}

}
