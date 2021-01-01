package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.NextStages;
import util.OrderToView;
import util.TableViewOrders;
import util.ViewReports;

public class ViewReportDepartmentManagerController implements Initializable {

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private TableView<ViewReports> tableViewReport;

    @FXML
    private Label emptyTableMsg;

	private MouseEvent m_previousPage;

	private String m_fName, m_lName, m_role, m_userID, m_parkName;

    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_previousPage.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", m_userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
    
	public void setPreviousPage(MouseEvent event) {
		m_previousPage = event;
	}
	
	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		m_fName = fName;
		m_lName = lName;
		m_role = role;
		m_userID = userID;
		m_parkName = parkName;  //DepManager
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("ReportsToView");
		ClientMain.chat.accept(arr);
		ArrayList<ViewReports> temp = ChatClient.dataInArrayListReport;
		if (!temp.isEmpty()) {
			// Report name Column
			TableColumn<ViewReports, String> reportNamecolumn = new TableColumn<>("Report Name");
			reportNamecolumn.setMinWidth(150);
			reportNamecolumn.setCellValueFactory(new PropertyValueFactory<>("reportName"));

			// park name Column
			TableColumn<ViewReports, String> parkNameColumn = new TableColumn<>("Park Name");
			parkNameColumn.setMinWidth(150);
			parkNameColumn.setCellValueFactory(new PropertyValueFactory<>("parkName"));

			// Month Column
			TableColumn<ViewReports, String> monthColumn = new TableColumn<>("Month");
			monthColumn.setMinWidth(150);
			monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
			
			// Year Column
			TableColumn<ViewReports, String> yearColumn = new TableColumn<>("Year");
			yearColumn.setMinWidth(150);
			yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
			
			tableViewReport.setItems(getOrders(temp));
			
			tableViewReport.setRowFactory(tv -> {
				TableRow<ViewReports> row = new TableRow<>();
				row.setOnMouseClicked(evento -> {
					if (evento.getClickCount() == 2 && (!row.isEmpty())) {
						ViewReports rowData = row.getItem();
						
						tableViewReport.setVisible(false);
					}
				});
				return row;
			});

			tableViewReport.getColumns().addAll(reportNamecolumn, parkNameColumn, monthColumn, yearColumn);
			tableViewReport.setVisible(true);
		} else {
			emptyTableMsg.setVisible(true);
		}
	}
	
	public ObservableList<ViewReports> getOrders(ArrayList<ViewReports> temp){
		ObservableList<ViewReports> orders = FXCollections.observableArrayList();
		
		for(int i = 0; i < temp.size(); i++) {
			if(temp.get(i) != null)
				orders.add(temp.get(i));
		}
		return orders;
	}
}
