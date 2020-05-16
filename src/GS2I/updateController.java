package GS2I;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;

import DBConnection.connection;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class updateController {
	connection conobj=new connection();
	Connection conn;
	@FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainContainer;

    @FXML
    private Button saveButton;

    @FXML
    private TextField cn;

    @FXML
    private TextField b;

    @FXML
    private TextField sn;

    @FXML
    private TextArea pb;

    @FXML
    private TextArea diag;

    @FXML
    private DatePicker ed;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker rd;

    @FXML
    private TextField oldID;

    @FXML
    void addMember(ActionEvent event) throws SQLException, ClassNotFoundException {

conn=conobj.getConnection();
PreparedStatement pst;
LocalDate entry=ed.getValue();
LocalDate retn=rd.getValue();
String sql="UPDATE DATA SET COMPANY=?,BRAND=?,ENTRY_DATE=?,RETURN_DATE=?,PROBLEM=?,SERIAL_NUMBER=?,DIAGNOTICS=? WHERE ID=?";
pst=conn.prepareStatement(sql);	
try {
 if(oldID.getText().isEmpty() || cn.getText().isEmpty() ||b.getText().isEmpty()||diag.getText().isEmpty()||pb.getText().isEmpty()||ed.getValue()==null||rd.getValue()==null)
	{
	 Notifications nb=Notifications.create()
 		.title("All fields are required")
 		.text("Please enter all information about the device")
 		.graphic(null)
 		.position(Pos.TOP_CENTER);
 		nb.showWarning();
	}
 else if(rd.getValue().compareTo(ed.getValue())<0)
 {
	 Notifications nb=Notifications.create()
  		.title("Error")
  		.text("Return Date can not be less than Entry Date")
  		.graphic(null)
  		.position(Pos.TOP_CENTER);
  		nb.showWarning();
 }
 else {
  pst.setString(1, cn.getText());
  pst.setString(2, b.getText());
  pst.setDate(3, Date.valueOf(entry));
  pst.setDate(4, Date.valueOf(retn));
  pst.setString(5, pb.getText());
  pst.setString(6, sn.getText());
  pst.setString(7, diag.getText());
 pst.setString(8, oldID.getText());
 pst.executeUpdate();
 creatDiag("Status","Update complete!");
 }
}
catch(SQLException ex)
{
	ex.printStackTrace();
	throw ex;
	}
    }
    
    @FXML
    void back(ActionEvent event) {
    	
    	makeFadeOt();

    }
    private void makeFadeOt() {
		// TODO Auto-generated method stub
		FadeTransition ft= new FadeTransition();
		ft.setDuration(Duration.millis(250));
		ft.setNode(rootPane);
		ft.setFromValue(1);
		ft.setToValue(0);
		
		ft.setOnFinished((ActionEvent e) ->
		{
			try {
				Rectangle2D s=Screen.getPrimary().getVisualBounds();
	    		Parent homeView = FXMLLoader.load(getClass().getResource("home.fxml"));
	    		Scene sceneHome= new Scene(homeView,s.getWidth(),s.getHeight());
	    		Stage curStage= (Stage) rootPane.getScene().getWindow();
	    		//curStage.setMaximized(true);
	    		curStage.setScene(sceneHome);
	    		
	    	} catch (IOException ex) {
	    		// TODO Auto-generated catch block
	    		ex.printStackTrace();
	    	}
		});
		ft.play();
	}
    public void creatDiag(String title,String body)
    {
 	   BoxBlur blur = new BoxBlur(3,3,3);
 	   JFXDialogLayout content=new JFXDialogLayout();
 	   content.setHeading(new Text(title));
 	   content.setBody(new Text(body));
 	   JFXDialog dialog=new JFXDialog(rootPane,content,JFXDialog.DialogTransition.CENTER);
 	   JFXButton btn = new JFXButton("Okay");
 	   btn.setStyle("-fx-background-color:blue; -fx-text-fill:white");
 	   btn.setOnAction(new EventHandler<ActionEvent>()
 			   {

 				@Override
 				public void handle(ActionEvent event) {
 					dialog.close();	
 				}
 			   });
 	   content.setActions(btn);
 	   dialog.show();
 	   dialog.setOnDialogClosed((JFXDialogEvent event1)->
 	   {mainContainer.setEffect(null);}
 			   );
 	   
 	   mainContainer.setEffect(blur);
    }



}
