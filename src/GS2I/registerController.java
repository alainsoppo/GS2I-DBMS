package GS2I;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import DBConnection.connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class registerController {
	Connection conn;
	PreparedStatement pst;
	PreparedStatement pst1;
	connection conobj=new connection();
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private AnchorPane mainContainer;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton gotoLog;

    @FXML
    void addMember(ActionEvent event) throws SQLException, ClassNotFoundException {
    	conn=conobj.getConnection();
	    	if(name.getText().isEmpty() || pass.getText().isEmpty() ||mobile.getText().isEmpty())
	    	{
	    		Notifications nb=Notifications.create()
	    	    		.title("All fields are required")
	    	    		.text("Please enter your credentials")
	    	    		.graphic(null)
	    	    		.position(Pos.TOP_CENTER);
	    	    		nb.showWarning();
	    	    		}
	    	else if(checkUser()==true)
	    	{
	    		Notifications nb=Notifications.create()
	    	    		.title("Error")
	    	    		.text("Username already exist")
	    	    		.graphic(null)
	    	    		.position(Pos.TOP_CENTER);
	    	    		nb.showWarning();
	    	}
	    
   		 else
	    	    {
	    	    	 String insert = "INSERT INTO users(Phone,Password,Username) values(?,MD5(?),?)";
	        	     pst=conn.prepareStatement(insert);	
	        	  
	        	     pst.setString(1, mobile.getText());
	        	     pst.setString(2, pass.getText());
	        	     pst.setString(3, name.getText());
	        	    pst.executeUpdate();
	    		Notifications nb=Notifications.create()
	    		.title("Signup successful")
	    		.text("Login to get started!!")
	    		.graphic(null)
	    		.position(Pos.TOP_CENTER);
	    		nb.showConfirm();
	    		nav_log();
	    	    }
    
    }
    public boolean checkUser() throws SQLException, ClassNotFoundException
    {
    	conn=conobj.getConnection();
    	String check="SELECT *FROM users";
	    pst1=conn.prepareStatement(check);
	   ResultSet rs=pst1.executeQuery();
    	boolean userExit=false;
	    while(rs.next()) {
	    	String user=rs.getString("Username");
	    	if(name.getText().equals(user))
	    	{
	    	    		userExit=true;
	    	}
	    }	
	   
    	
    	return userExit;
    }

    @FXML
    void cancel(ActionEvent event) {
      System.exit(1);
    }

    @FXML
    void nav_to_Login(ActionEvent event) {
    	nav_log();
    }
    public void nav_log()
    {
    	try {
			Parent logView = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene sceneLog= new Scene(logView);
			Stage curStage= (Stage) rootPane.getScene().getWindow();
			curStage.setScene(sceneLog);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void creatAlert(String content,String head)
    {
    	Stage stage= (Stage) rootPane.getScene().getWindow();
    	AlertType type = AlertType.WARNING;
    	Alert alert = new Alert(type,"");
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.initOwner(stage);
    	alert.getDialogPane().setContentText(content);
    	alert.getDialogPane().setHeaderText(head);
    	alert.showAndWait();
    }
}
