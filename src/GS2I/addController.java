package GS2I;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class addController implements Initializable {
	Connection conn;
	PreparedStatement pst;
	connection conobj=new connection();
	  @FXML
	    private StackPane rootPane;

	    @FXML
	    private AnchorPane mainContainer;

	    @FXML
	    private Button saveButton;

	    @FXML
	    private TextField id;

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


	    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
		

		}
    @FXML
    void addMember(ActionEvent event) throws ClassNotFoundException, SQLException {
    	conn=conobj.getConnection();
    	LocalDate entry=ed.getValue();
    	LocalDate retn=rd.getValue();
 	   String insert = "INSERT INTO data(ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS) VALUES(?,?,?,?,?,?,?,?)";
 	     pst=conn.prepareStatement(insert);	
 	
 	    if(id.getText().isEmpty() || cn.getText().isEmpty() ||b.getText().isEmpty()||diag.getText().isEmpty()||pb.getText().isEmpty()||ed.getValue()==null||rd.getValue()==null)
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
 		else if(checkID()==true)
    	{
    		Notifications nb=Notifications.create()
    	    		.title("Error")
    	    		.text("ID already belongs to a record")
    	    		.graphic(null)
    	    		.position(Pos.TOP_CENTER);
    	    		nb.showWarning();
    	}
 	    else {
 	     pst.setString(1, id.getText());
 	      pst.setDate(2, Date.valueOf(entry));
 	     pst.setString(3, cn.getText());
 	     pst.setString(4, b.getText());
	     pst.setString(5, sn.getText());
	     pst.setString(6, pb.getText());
	       pst.setDate(7, Date.valueOf(retn));
	     pst.setString(8, diag.getText());
 	    pst.executeUpdate();
 	   creatDiag("Success","Your data has been added successfully!");
 	   clear();
    }
 	    }
    
    public boolean checkID() throws SQLException, ClassNotFoundException
    {
    	conn=conobj.getConnection();
    	String check="SELECT *FROM data";
	    PreparedStatement pst1 = conn.prepareStatement(check);
	   ResultSet rs=pst1.executeQuery();
    	boolean userID=false;
	    while(rs.next()) {
	    	String ID=rs.getString("ID");
	    	if(id.getText().equals(ID))
	    	{
	    		userID=true;
	    	}
	    }	
	   
    	
    	return userID;
    }
    @FXML
    void back(ActionEvent event) {
    	
    	makeFadeOt();
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
    public void clear()
    {
    	id.setText("");
    	cn.setText("");
    	b.setText("");
    	diag.setText("");
    	pb.setText("");
    	ed.setValue(null);
    	rd.setValue(null);
    	sn.setText("");
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



}
