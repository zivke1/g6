package clientTry;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.HourAmount;
import util.NextStages;
import util.OrderToView;
import util.ParameterToView;
import util.TableApprovePara;
import util.TableViewOrders;
/**
 * 
 * 
 *Controller for GUI Approve Parameters Page for department manager.
 *include the ability to approve and deny offers to change parameters or make new discount    
 */
public class ApproveParametersController {

	private String fNameH, lNameH, roleH, userIDH, parkNameH;
	private MouseEvent m_event;
	@FXML
	private ImageView imgContactUs;

    @FXML
    private Label explenationLabel;
    
	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Label errorMsg;

	@FXML
	private TableView<ParameterToView> table;

	@FXML
	private TableColumn<?, ?> parkName;

	@FXML
	private TableColumn<?, ?> parameter;

	@FXML
	private TableColumn<?, ?> newValue; 

	@FXML
	private TableColumn<?, ?> dateOfRequest;

	@FXML
	private TableColumn<?, ?> fromDate;

	@FXML
	private TableColumn<?, ?> approve;

	@FXML
	private TableColumn<?, ?> untilDate;

	@FXML
	private TableColumn<?, ?> reject;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

	public void setMainPage(MouseEvent event) {
		m_event = event;
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", userIDH);
		nextStages.openPopUp();
	} 

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This table represents all the parks managers' requests\nto change parameters in their parks"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		setTable();
	}
/**
 * build the table of approving parameters
 */
	private void setTable() {

		ArrayList<String> arr = new ArrayList<>();
		try {
			arr.add("approveParaTable");
			ClientMain.chat.accept(arr);
			ArrayList<ParameterToView> answer = ChatClient.dataInArrayListParameter;
			if (!answer.isEmpty()) {
				errorMsg.setText("");
				for (ParameterToView p : answer) {
					p.setApproveButton(new Button("Accept"));
					p.setRejectButton(new Button("Deny"));
				}
				// park name column
				TableColumn<ParameterToView, String> parkNamecolumn = new TableColumn<>("Park Name");
				parkNamecolumn.setMinWidth(100);
				parkNamecolumn.setCellValueFactory(new PropertyValueFactory<>("parkName"));

				// parameter column
				TableColumn<ParameterToView, String> parametercolumn = new TableColumn<>("Parameter");
				parametercolumn.setMinWidth(100);
				parametercolumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));

				// new value column
				TableColumn<ParameterToView, Integer> newValuecolumn = new TableColumn<>("New Value");
				newValuecolumn.setMinWidth(100);
				newValuecolumn.setCellValueFactory(new PropertyValueFactory<>("newValue"));

				// request date column
				TableColumn<ParameterToView, String> requestcolumn = new TableColumn<>("Request");
				requestcolumn.setMinWidth(100);
				requestcolumn.setCellValueFactory(new PropertyValueFactory<>("request"));

				// from date column
				TableColumn<ParameterToView, String> fromcolumn = new TableColumn<>("From");
				fromcolumn.setMinWidth(119);
				fromcolumn.setCellValueFactory(new PropertyValueFactory<>("from"));

				// to date column
				TableColumn<ParameterToView, String> tocolumn = new TableColumn<>("To");
				tocolumn.setMinWidth(120);
				tocolumn.setCellValueFactory(new PropertyValueFactory<>("to"));

				// reject column
				TableColumn<ParameterToView, String> rejectcolumn = new TableColumn<>();
				rejectcolumn.setMinWidth(100);
				rejectcolumn.setCellValueFactory(new PropertyValueFactory<>("rejectButton"));

				// approve column
				TableColumn<ParameterToView, Void> approvecolumn = new TableColumn<>();
				approvecolumn.setMinWidth(100);
				approvecolumn.setCellValueFactory(new PropertyValueFactory<>("approveButton"));

				TableApprovePara obsList = new TableApprovePara();
				table.setItems(obsList.getParamters(answer));

				table.getColumns().addAll(parkNamecolumn, parametercolumn, newValuecolumn, requestcolumn, fromcolumn,
						tocolumn, rejectcolumn, approvecolumn);

			} else {
				errorMsg.setText("You don't have parameters to approve.");
				explenationLabel.setVisible(false);

				table.setVisible(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
