package GS2I;
	import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

	public class welcomeController implements Initializable {

	    @FXML
	    private JFXButton getStarted;
	    @FXML
	    private StackPane rootPane;
	    
	    
	    
	    
	    @FXML
	    void fadeAction(ActionEvent event) {
          makeFadeOt();
	    }




		private void makeFadeOt() {
			// TODO Auto-generated method stub
			FadeTransition ft= new FadeTransition();
			ft.setDuration(Duration.millis(750));
			ft.setNode(rootPane);
			ft.setFromValue(1);
			ft.setToValue(0);
			
			ft.setOnFinished((ActionEvent e) ->
			{
				nav_home();
			});
			ft.play();
		}
		
		 public void nav_home()
		    {
		    	try {
		    		Rectangle2D s=Screen.getPrimary().getVisualBounds();
					Parent homeView = FXMLLoader.load(getClass().getResource("home.fxml"));
					Scene sceneHome= new Scene(homeView,s.getWidth(),s.getHeight());
					Stage stage= new Stage();
					stage.setScene(sceneHome);
					stage.setMaximized(true);
					stage.show();
					rootPane.getScene().getWindow().hide();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }




		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}


	}
