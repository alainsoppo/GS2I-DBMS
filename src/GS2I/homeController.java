package GS2I;

import java.io.IOException;
import java.util.Optional;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class homeController {
    @FXML
    public Pane header;
	 @FXML
	 private Button produce;
	 
    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnDashboard;

    @FXML
    private FontAwesomeIconView showData;

    @FXML
    private Button btnRecord;

    @FXML
    private FontAwesomeIconView addData;

    @FXML
    private Button btn_Timetable;

    @FXML
    private FontAwesomeIconView deleteData;

    @FXML
    private Button aboutMe;

    @FXML
    private FontAwesomeIconView about;

    @FXML
    private Button btnUpdate;

    @FXML
    private FontAwesomeIconView updateData;

    @FXML
    private Button btnClasses;

    @FXML
    private FontAwesomeIconView showUsers;
    
    @FXML
    void update(ActionEvent event) {
    	makeFadeOt("update.fxml");
    }

    @FXML
    void ProduceScore(ActionEvent event) {

try {
	        Stage primaryStage=new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Report.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/resources/dark-theme.css").toExternalForm());
			primaryStage.setTitle("Generate Report");
			primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    	
    	
    }


    @FXML
    void addRecord(ActionEvent event) {
    	makeFadeOt("add.fxml");
    }

    @FXML
    void handleButtonClicks(ActionEvent event) {
    	makeFadeOt("query.fxml");
    }
    @FXML
    void showTables(ActionEvent event) {
    	makeFadeOt("showRecord.fxml");
    }

    @FXML
    void logout(ActionEvent event) {
    	creatAlertInfo("Are you sure you want to logout?\n"+"Press OK to confirm","You are about to logout..");
    }
    public void nav(String fxml)
    {
    try {
    	Rectangle2D s=Screen.getPrimary().getVisualBounds();
		Parent regView = FXMLLoader.load(getClass().getResource(fxml));
		Scene sceneReg= new Scene(regView,s.getWidth(),s.getHeight());
		Stage curStage= (Stage) rootPane.getScene().getWindow();
		curStage.setScene(sceneReg);
		//curStage.setMaximized(true);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    public void creatAlertInfo(String content,String head)
    {
    	Stage stage= (Stage) rootPane.getScene().getWindow();
    	AlertType type = AlertType.WARNING;
    	Alert alert = new Alert(type,"");
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.getDialogPane().setContentText(content);
    	alert.getDialogPane().setHeaderText(head);
    	Optional<ButtonType> opt=alert.showAndWait();
    	if (opt.get()==ButtonType.OK) {
    		FadeTransition ft= new FadeTransition();
    		ft.setDuration(Duration.millis(750));
    		ft.setNode(rootPane);
    		ft.setFromValue(1);
    		ft.setToValue(0);
    		
    		ft.setOnFinished((ActionEvent e) ->
    		{
    			try {
    				Parent homeView = FXMLLoader.load(getClass().getResource("login.fxml"));
    				Scene sceneLog= new Scene(homeView);
    				Stage stage1= new Stage();
    				stage1.initStyle(StageStyle.UNDECORATED);
    				stage1.setScene(sceneLog);
    				stage1.show();
    				rootPane.getScene().getWindow().hide();
    				
    			} catch (IOException ex) {
    				// TODO Auto-generated catch block
    				ex.printStackTrace();
    				
    	}
    		});
    		ft.play();
		}
    	
	  	
    }
    private void makeFadeOt(String fxml) {
		// TODO Auto-generated method stub
		FadeTransition ft= new FadeTransition();
		ft.setDuration(Duration.millis(250));
		ft.setNode(rootPane);
		ft.setFromValue(1);
		ft.setToValue(0);
		
		ft.setOnFinished((ActionEvent e) ->
		{
			nav(fxml);
		});
		ft.play();
	}

}
