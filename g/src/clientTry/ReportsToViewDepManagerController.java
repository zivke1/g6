package clientTry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.NextStages;
/**
 * 
 * with this page the department manager can see his reports
 *
 */
public class ReportsToViewDepManagerController {

    @FXML
    private Text headIncomeRep;

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Label crumIncomeRep;

    @FXML
    private Text headUsageRep;

    @FXML
    private Text headVisitRep;

    @FXML
    private Label crumUsageRep;

    @FXML
    private Label crumVisitRep;

    @FXML
    private Button exitReport;

    @FXML
    private Button helpBtn;

    @FXML
    private AnchorPane anchorVisitorReport;

    @FXML
    private AnchorPane anchorIncomeReport;

    @FXML
    private AnchorPane anchoreUsageReport;

    @FXML
    void exitWindow(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", "");
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }

}
