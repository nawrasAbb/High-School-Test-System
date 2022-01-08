package FINAL.project;
import java.io.IOException;
import java.util.List;

//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.util.Pair;

public class principleHomePageController {

    @FXML
    void DisplayData(ActionEvent event) throws IOException {
    	App.setRoot("DisplayData");
    }

    @FXML
    void DisplayRequests(ActionEvent event) throws IOException, InterruptedException {
    	
    	HandleRequests.initializeRequests();
    }
    	
    	
    static void ShowRequests(List<Request> allRequests1 ) throws IOException, InterruptedException {	
    	Pair<Parent,Object> confirmPair=App.getFxmlAndController("ConfirmRequests");
    	ConfirmRequestsController confirmController=(ConfirmRequestsController)confirmPair.getValue();
    	HandleRequests handleRequests=new HandleRequests();
    	handleRequests.initializeRequestsForPrinciple(allRequests1 ,confirmController);
    	App.setRoot2(confirmPair.getKey());
    }
  
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	LogIn.logout();
    	App.setRoot("LogIn");
    }

}
