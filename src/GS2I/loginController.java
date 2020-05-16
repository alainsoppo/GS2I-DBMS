package GS2I;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import DBConnection.connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginController {
connection conObj=new connection();
	Connection conn;
	PreparedStatement pst;
	 @FXML
	    private StackPane stack;
	   @FXML
	    private AnchorPane anchorpane;

	    @FXML
	    private JFXTextField username;

	    @FXML
	    private JFXPasswordField password;
	    
	    @FXML
	    private VBox vBox;

	    @FXML
	    void handleCancelButtonAction(ActionEvent event) {
     System.exit(0);
	    }

	    @FXML
	    void handleLoginButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException {

	    	
	    conn=conObj.getConnection();
	    String select="SELECT *FROM users WHERE Username=? and MD5(?)=Password";
	     pst=conn.prepareStatement(select);
	     pst.setString(1, username.getText());
	     pst.setString(2, password.getText());
	     ResultSet result = pst.executeQuery();
	     
	     if(password.getText().isEmpty() && username.getText().isEmpty())
	    	{
	    		 Notifications nb=Notifications.create()
		    	    		.title("Please enter your credentials")
		    	    		.text("Username and Password can not be empty")
		    	    		.graphic(null)
		    	    		.position(Pos.TOP_CENTER);
		    	    		nb.showWarning();
	    	}
	    	
	     else if(username.getText().isEmpty())
	    	{
	    		 Notifications nb=Notifications.create()
		    	    		.title("Username can not be empty")
		    	    		.text("Please enter your username")
		    	    		.graphic(null)
		    	    		.position(Pos.TOP_CENTER);
		    	    		nb.showWarning();
	    	}
	    	else if (password.getText().isEmpty())
	    	{
	    		 Notifications nb=Notifications.create()
		    	    		.title("Password can not be empty")
		    	    		.text("Please enter your password")
		    	    		.graphic(null)
		    	    		.position(Pos.TOP_CENTER);
		    	    		nb.showWarning();
	    	}
	    	
	    	
	    	
	    	
	     int flag=0;
	     while(result.next())
	     {
	    	 flag=flag+1;
	     }
	     if(flag==1)
	     {
	    	
	    	 nav_splash();
	    	 
	     }
	     else
	     {
	    	 Notifications nb=Notifications.create()
	    	    		.title("Failed to login")
	    	    		.text("Username or password is incorrect\n"
	    		    			 +"Please create an account if you are not a user yet")
	    	    		.graphic(null)
	    	    		.position(Pos.TOP_CENTER);
	    	    		nb.showError();
	     }
	    }
	    @FXML
	    void go_to_Reg(ActionEvent event) {
	    	try {
				Parent regView = FXMLLoader.load(getClass().getResource("register.fxml"));
				Scene sceneReg= new Scene(regView);
				Stage curStage= (Stage) anchorpane.getScene().getWindow();
				curStage.setScene(sceneReg);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    public void nav_splash()
	    {
	    	try {
				Parent splashScreen = FXMLLoader.load(getClass().getResource("splash.fxml"));
				Scene splash= new Scene(splashScreen);
				Stage stage=new Stage();
				stage.setScene(splash);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				anchorpane.getScene().getWindow().hide();
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    /*
	    public void creatAlertError(String content,String head)
	    {
	    	Stage stage= (Stage) stack.getScene().getWindow();
	    	AlertType type = AlertType.ERROR;
	    	Alert alert = new Alert(type,"");
	    	alert.initModality(Modality.APPLICATION_MODAL);
	    	alert.initOwner(stage);
	    	alert.getDialogPane().setContentText(content);
	    	alert.getDialogPane().setHeaderText(head);
	    	alert.showAndWait();
	    }*/
}
