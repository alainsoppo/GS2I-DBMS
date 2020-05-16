package GS2I;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class splashController implements Initializable {


    @FXML
    private StackPane rootPane;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		new SplashScreen().start();
	}

	
	class SplashScreen extends Thread{
		@Override
		public void run()
		{
			try {
				Thread.sleep(5000);
				
				Platform.runLater(new Runnable()
				{
				@Override
				public void run()
				{
					Parent welcome = null;
				 try {
					 welcome = FXMLLoader.load(getClass().getResource("welcome.fxml"));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					Logger.getLogger(splashController.class.getName()).log(Level.SEVERE,null,ex);
					}
				Scene welcomeScene= new Scene(welcome);
				Stage stage=new Stage();
				stage.setScene(welcomeScene);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.show();
				 Notifications nb=Notifications.create()
			 	    		.title("Login successful")
			 	    		.text("Welcome to GS2I Database Management System")
			 	    		.graphic(null)
			 	    		.position(Pos.TOP_CENTER);
			 	            nb.darkStyle();
			 	    		nb.showConfirm();
				rootPane.getScene().getWindow().hide();
			}
				});
			}
			catch (InterruptedException ex) {
				Logger.getLogger(splashController.class.getName()).log(Level.SEVERE,null,ex);
		}
}
	}
}