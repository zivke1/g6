package clientTry;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 *
 * @author Idan
 * class Controller for update parameters page 
 *
 */

public class paraController {
	private String parkName, discount, duration, gap, maxCapacity;
	private LocalDate from, until;
	private boolean chosenDuration = false, chosenDiscount = false, chosenGap = false, chosenCapacity = false;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private RadioButton setMaxVisit;

	@FXML
	private ToggleGroup setParm;

	@FXML
	private RadioButton setVisitDur;

	@FXML
	private RadioButton setMaxOrder;

	@FXML
	private RadioButton setDiscount;

	@FXML
	private Button conBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private AnchorPane anchorSetDiscount;

	@FXML
	private Label discountLabel;

	@FXML
	private Label fromLabel;

	@FXML
	private DatePicker untilDate;

	@FXML
	private DatePicker fromDate;

	@FXML
	private Label untilLabel;

	@FXML
	private TextField discountField;

	@FXML
	private Button btnSaveDiscount;

	@FXML
	private AnchorPane anchorSetMaxVisit;

	@FXML
	private Label maxVisitorsLabel;

	@FXML
	private TextField maxVisitField;

	@FXML
	private Button btnSaveMaxVisitor;

	@FXML
	private AnchorPane anchorSetDuration;

	@FXML
	private Label visitDurLabel;

	@FXML
	private TextField visitDurField;

	@FXML
	private Button btnSaveDuration;

	@FXML
	private AnchorPane anchorSetGapNum;

	@FXML
	private Label maxOrderLabel;

	@FXML
	private TextField maxOrderField;

	@FXML
	private Button btnSaveMaxOrder;

	@FXML
	private Label parks_name;

	@FXML
	private Label errorMsg;

/**
 * @author Idan
 * @param event show event  of click on back button
 */
	@FXML
	void backClicked(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("HomePage");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
// need to complete
	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}
// need to complete
	@FXML
	void helpBtnPressed(MouseEvent event) {

	}
/**
 * @author Idan
 * @param event show event of click on send to confirmation button
 * @ the method send to ClientMain the currently parameter that need confirmation to update
 */ 
	@FXML
	void sendToDepMan(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("sendToDeparmentManager");
		Date d = new Date();
		if (chosenCapacity) {
			arr.add(parkName);
			arr.add("MaxCapacity");
			arr.add(maxCapacity);
			arr.add(d.toString());
			ClientMain.chat.accept(arr);
		}
		if (chosenDiscount) {
			arr.add(parkName);
			arr.add("Discount");
			arr.add(discount);
			arr.add(d.toString());
			arr.add(from.toString());
			arr.add(until.toString());
			ClientMain.chat.accept(arr);
		}
		if (chosenDuration) {
			arr.add(parkName);
			arr.add("Duration");
			arr.add(duration);
			arr.add(d.toString());
			ClientMain.chat.accept(arr);
		}
		if (chosenGap) {
			arr.add(parkName);
			arr.add("Gap");
			arr.add(gap);
			arr.add(d.toString());
			ClientMain.chat.accept(arr);

		}
	}
/**
 * @author Idan	
 * @param event show event of click on set discount Radio button
 */

	@FXML
	void setDiscount(MouseEvent event) {
		anchorSetDiscount.setVisible(true);
		anchorSetDuration.setVisible(false);
		anchorSetGapNum.setVisible(false);
		anchorSetMaxVisit.setVisible(false);
	}
/**
 * @author Idan
 * @param event show event of click on set set order Radio button
 */

	@FXML
	void setMaxOrder(MouseEvent event) {
		anchorSetDiscount.setVisible(false);
		anchorSetDuration.setVisible(false);
		anchorSetGapNum.setVisible(false);
		anchorSetMaxVisit.setVisible(true);

	}
/**
 * @author Idan	
 * @param  event show event of click on set Max visit(gap) Radio button
 */

	@FXML
	void setMaxVisit(MouseEvent event) {
		anchorSetDiscount.setVisible(false);
		anchorSetDuration.setVisible(false);
		anchorSetGapNum.setVisible(false);
		anchorSetMaxVisit.setVisible(true);

	}
/**
 * @author Idan
 * @param  event show event of click on set visit duration Radio button
 */
	@FXML
	void setVisitDur(MouseEvent event) {
		anchorSetDiscount.setVisible(false);
		anchorSetDuration.setVisible(true);
		anchorSetGapNum.setVisible(false);
		anchorSetMaxVisit.setVisible(false);

	}
/**
 * @author Idan
 * @param event show event of click on save button in case of set capacity
 */
	@FXML
	void saveCapacity(MouseEvent event) {
		maxCapacity = maxVisitField.getText();
		if (maxCapacity.toString().length() == 0)
			errorMsg.setText("Please fill all filed");
		else
			chosenCapacity = true;
	}
/**
 * 	@author Idan
 * @param event show event of click on save button in case of set discount
 */
	@FXML
	void saveDiscount(MouseEvent event) {
		discount = discountField.getText();
		from = fromDate.getValue();
		until = untilDate.getValue();
		if (until.toString().length() == 0 || from.toString().length() == 0 || discount.length() == 0)
			errorMsg.setText("Please fill all filed");
		else
			chosenDiscount = true;
	}
/**
 * @author Idan
 * @param event  show event of click on save button in case of set duration
 */

	@FXML
	void saveDuration(MouseEvent event) {
		duration = visitDurField.getText();
		if (duration.toString().length() == 0)
			errorMsg.setText("Please fill all filed");
		else
			chosenDuration = true;
	}
/**
 * @author Idan
 * @param event show event of click on save button in case of Set gap between max capacity to max amount of orders
 */
	@FXML
	void saveGap(MouseEvent event) {
		gap = maxOrderField.getText();
		if (gap.toString().length() == 0)
			errorMsg.setText("Please fill all filed");
		else
			chosenGap = true;
	}
/**
 * @author Idan
 * @param parkName the park name of the manager 
 * the method get the park name of the manager park
 */
	public void sendToParaController(String parkName) {
		this.parkName = parkName;
		parks_name.setText(parkName);

	}

}
