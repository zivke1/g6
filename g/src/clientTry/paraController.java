package clientTry;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * @author Idan class Controller for update parameters page
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
	 * @param event show event of click on back button
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
	 * @param event show event of click on send to confirmation button @ the method
	 *              send to ClientMain the currently parameter that need
	 *              confirmation to update
	 */
	@FXML
	void sendToDepMan(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<>();
		errorMsg.setText("");
		LocalDateTime d;
		if (chosenCapacity) {
			arr.add("sendToDeparmentManager");
			// arr.add(parkName);
			arr.add("Haalid park");
			arr.add("capacity");
			arr.add(maxCapacity);
			d = LocalDateTime.now();
		//	String str= d.getYear()+"-"+d.getDay()+"-"+d.getMonth()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
			arr.add(d.toString());
			arr.add(null);
			arr.add(null);
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.get(0).equals("True"))
				errorMsg.setText(errorMsg.getText()
						+ "\n the parameter of Capacity update request \n has been sent to the department manager\n");
			arr.clear();
			maxVisitField.clear();
		}
		if (chosenDiscount) {
			arr.add("sendToDeparmentManager");
			// arr.add(parkName);
			arr.add("Hallid is Acbar");
			arr.add("Discount");
			arr.add(discount);
			d = LocalDateTime.now();
			//String str= d.getYear()+"-"+d.getDay()+"-"+d.getMonth()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
			arr.add(d.toString());
			arr.add(from.toString());
			arr.add(until.toString());
			System.out.println(from + " haalid  " + until);
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.get(0).equals("True"))
				errorMsg.setText(errorMsg.getText()
						+ "\n the parameter Discount update request \n has been sent to the department manager\n");
			arr.clear();
			discountField.clear();
		}
		if (chosenDuration) {
			arr.add("sendToDeparmentManager");
			// arr.add(parkName);
			arr.add("Haalid park");
			arr.add("Duration");
			arr.add(duration);
			d = LocalDateTime.now();
			//
			arr.add(d.toString());
			arr.add(null);
			arr.add(null);
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.get(0).equals("True"))
				errorMsg.setText(errorMsg.getText()
						+ "\n the parameter Duration update request \n has been sent to the department manager\n");
			arr.clear();
			visitDurField.clear();
		}
		if (chosenGap) {
			arr.add("sendToDeparmentManager");
			// arr.add(parkName);
			arr.add("Haalid park");
			arr.add("Gap");
			arr.add(gap);
			d = LocalDateTime.now();
//			String str= d.getYear()+"-"+d.getDay()+"-"+d.getMonth()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
			arr.add(d.toString());
			arr.add(null);
			arr.add(null);
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.get(0).equals("True"))
				errorMsg.setText(errorMsg.getText()
						+ "\n the parameter Gap between \n max orders and max visitors update request has been sent to the department manager\n");
			arr.clear();
			maxOrderField.clear();

		}
		if (!chosenCapacity && !chosenDiscount && !chosenDuration && !chosenGap)
			errorMsg.setText("please fill all the required fields");
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
		anchorSetGapNum.setVisible(true);
		anchorSetMaxVisit.setVisible(false);

	}

	/**
	 * @author Idan
	 * @param event show event of click on set Max visit(gap) Radio button
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
	 * @param event show event of click on set visit duration Radio button
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
		errorMsg.setText("");
		maxCapacity = maxVisitField.getText();
		if (maxCapacity.length() == 0)
			errorMsg.setText("\nPlease fill all filed\n");
		int capaInt=Integer.parseInt(maxCapacity);
		if(capaInt<=0)
			errorMsg.setText("\nThe capacity value must be possitve number\n");
		boolean flag = true;
		char chars[] = maxCapacity.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid Capacity value\n");
				flag = false;
				break;
			}
		}

		if (flag)
			chosenCapacity = true;
	}

	/**
	 * @author Idan
	 * @param event show event of click on save button in case of set discount
	 */
	@FXML
	void saveDiscount(MouseEvent event) {
		errorMsg.setText("");
		discount = discountField.getText();
		from = fromDate.getValue();
		until = untilDate.getValue();
		if (until.toString().length() == 0 || from.toString().length() == 0 || discount.length() == 0) {
			errorMsg.setText("\nPlease fill all filed\n");
			return;
		}
		if (from.compareTo(until) > 0)
			errorMsg.setText("\"from\" date must be earlier than the \"until\" date \n");
		int discountInt=Integer.parseInt(discount);
		if(discountInt<=0)
			errorMsg.setText("\nThe discount value must be possitve number\n");
		Date d=new Date();
		if(LocalDate.now().compareTo(fromDate.getValue())>0)
			errorMsg.setText(errorMsg.getText() + "Please enter a valid from date\n");
		if(fromDate.getValue().compareTo(untilDate.getValue())>0)
			errorMsg.setText(errorMsg.getText() + "Please enter a valid until date\n");
		boolean flag = true;
		char chars[] = discount.toCharArray();
		for (int i = 0; i < chars.length - 1; i++) {
			if (!Character.isDigit(chars[i])) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid discount\n");
				flag = false;
				break;
			}
		}
		if (flag)
			chosenDiscount = true;
		else
			errorMsg.setText(errorMsg.getText() + "Please enter a valid discount\nfor example 10%");
	}

	/**
	 * @author Idan
	 * @param event show event of click on save button in case of set duration
	 */

	@FXML
	void saveDuration(MouseEvent event) {
		errorMsg.setText("");
		duration = visitDurField.getText();
		if (duration.length() == 0)
			errorMsg.setText("\nPlease fill all filed\n");
		int durInt=Integer.parseInt(duration);
		if(durInt<=0)
			errorMsg.setText("\nThe duration must be possitve number\n");
		boolean flag = true;
		char chars[] = duration.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid visit duration\n");
				flag = false;
				break;
			}
		}

		if (flag)
			chosenDuration = true;
	}

	/**
	 * @author Idan
	 * @param event show event of click on save button in case of Set gap between
	 *              max capacity to max amount of orders
	 */
	@FXML
	void saveGap(MouseEvent event) {
		errorMsg.setText("");
		gap = maxOrderField.getText();
		if (gap.length() == 0)
			errorMsg.setText("\nPlease fill all filed\n");
		int gapInt=Integer.parseInt(gap);
		if(gapInt<=0)
			errorMsg.setText("\nThe gap must be possitve number\n");
		boolean flag = true;
		char chars[] = gap.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid gap value\n");
				flag = false;
				break;
			}
		}

		if (flag)
			chosenGap = true;
	}

	/**
	 * @author Idan
	 * @param parkName the park name of the manager the method get the park name of
	 *                 the manager park
	 */
	public void sendToParaController(String parkName) {
		this.parkName = parkName;
		parks_name.setText(parkName);

	}

}
