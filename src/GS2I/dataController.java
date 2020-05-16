package GS2I;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import DBConnection.connection;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class dataController implements Initializable {
	Connection conn;
   	PreparedStatement pst;
   	connection conobj=new connection();
	
	  @FXML
	  private AnchorPane rootPane;

    @FXML
    private TableColumn<property, String> id;

    @FXML
    private TableColumn<property, LocalDate> Entry_Date;

    @FXML
    private TableColumn<property, String> Company;

    @FXML
    private TableColumn<property, String> Brand;

    @FXML
    private TableColumn<property, String> Serial_Number;

    @FXML
    private TableColumn<property, String> Problem;

    @FXML
    private TableColumn<property, LocalDate> Return_Date;

    @FXML
    private TableColumn<property, String> Diagnostics;
    @FXML
    private Button search;
    @FXML
    private TableView<property> table;
    @FXML
    private Button delete;

    @FXML
    private Button reload;
    
    @FXML
    private TextField seachbar;
    
    @FXML
    void reloadData(ActionEvent event) throws ClassNotFoundException, SQLException {
    	ObservableList <property> data=getAllRecords();
    	populateTable(data);
    	seachbar.clear();
    }

    
    @FXML
    void searchData(ActionEvent event) throws ClassNotFoundException, SQLException {
    	
    	ObservableList <property> data=searchRecord(seachbar.getText());
    	if(data.size()>0)
    	{
    		populateTable(data);
    		Notifications nb=Notifications.create()
    		.title("Research")
    		.text("Search completed!!")
    		.graphic(null)
    		.position(Pos.TOP_CENTER);
    		nb.showConfirm();
    		
    	}
    	else
    	{
    		populateTable(data);
    		Notifications nb=Notifications.create()
    		.title("Search result")
    		.text("No device corresponding to the company "+ "<<"+seachbar.getText()+">>" +" in Database")
    		.graphic(null)
    		.position(Pos.TOP_CENTER);
    		nb.showError();
    		
    	}
    	
    	

    }

    private void populateTable(ObservableList<property> data) {
		table.setItems(data);
		
	}


	@FXML
    void deleteData(ActionEvent event) throws Exception {
		property prop=table.getSelectionModel().getSelectedItem();
		AlertType type = AlertType.CONFIRMATION;
    	Alert alert = new Alert(type,"");
    	alert.initModality(Modality.APPLICATION_MODAL);
    	alert.getDialogPane().setContentText("Press OK to confirm");
    	alert.getDialogPane().setHeaderText("Deleting record with ID = "+prop.getId());
    	Optional<ButtonType> opt=alert.showAndWait();
    	if (opt.get()==ButtonType.OK) {
	    	
	    	if (prop!=null)
	    	{
	    		
		       	conn=conobj.getConnection();
	             String sel="DELETE FROM data WHERE ID=?";
	             pst=conn.prepareStatement(sel);
	             pst.setString(1, prop.getId());
	             pst.executeUpdate(); 
	             Notifications nb=Notifications.create()
	  		    		.title("Status")
	  		    		.text("Data deleted successfully!")
	  		    		.graphic(null)
	  		    		.position(Pos.TOP_CENTER);
	  		    		nb.showInformation();
	             ObservableList <property> data=getAllRecords();
	 	    	populateTable(data);
	    	}
    }
	}
	
	
    @FXML
    void backHome(ActionEvent event) {
    	
    	
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
	    		curStage.setScene(sceneHome);
	    		
	    	} catch (IOException ex) {
	    		// TODO Auto-generated catch block
	    		ex.printStackTrace();
	    	}
		});
		ft.play();
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		id.setCellValueFactory(new PropertyValueFactory <property, String>("id"));
		Entry_Date.setCellValueFactory(cellData-> cellData.getValue().entryDateProperty());
		Company.setCellValueFactory(new PropertyValueFactory <property, String>("company"));
		Brand.setCellValueFactory(new PropertyValueFactory <property, String>("brand"));
		Serial_Number.setCellValueFactory(new PropertyValueFactory <property, String>("Serial_Number"));
		Problem.setCellValueFactory(new PropertyValueFactory <property, String>("problem"));
		Return_Date.setCellValueFactory(cellData-> cellData.getValue().returnDateProperty());
		Diagnostics.setCellValueFactory(new PropertyValueFactory <property, String>("Diagnotics"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		Entry_Date.setCellFactory(column -> {
	        return new TableCell<property,LocalDate>() {
	        	@Override
	            protected void updateItem(LocalDate item, boolean empty) {
	                super.updateItem(item, empty);

	                if (item == null || empty) {
	                    setText(null);
	                } else {
						setText(formatter.format(item));

	                }
	            }
	        };
	    });
	
Return_Date.setCellFactory(column -> {
        return new TableCell<property, LocalDate>() {
        	@Override
            protected void updateItem(LocalDate item1, boolean empty1) {
                super.updateItem(item1, empty1);

                if (item1 == null || empty1) {
                    setText(null);
                } else {
					setText(formatter.format(item1));

                }
            }
        };
    });

		try {
			showData();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
	public void showData() throws ClassNotFoundException, SQLException {
        ObservableList <property> data =FXCollections.observableArrayList();
        Connection conn;
    	PreparedStatement pst;
    	connection conobj=new connection();
          conn=conobj.getConnection();
              try {
            	  String sel = "SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM data";
          	     pst=conn.prepareStatement(sel);	
          	   ResultSet rs = pst.executeQuery();
                  while ( rs.next() ) 
                  {
                	  data.add(new property(
                			  rs.getString("id"),
                			  rs.getDate("Entry_Date").toLocalDate(),
                              rs.getString("company"),
                              rs.getString("brand"),
                              rs.getString("Serial_Number"),
                              rs.getString("problem"),
                              rs.getDate("Return_Date").toLocalDate(),
                              rs.getString("Diagnotics")
                              ));
                	  table.setItems(data);
                  }
 
              } catch (SQLException e) {
                  e.printStackTrace();
              }
 
    }
	   public ObservableList<property> searchRecord(String c) throws ClassNotFoundException, SQLException
       {
     	Connection conn;
	       	PreparedStatement pst;
	       	connection conobj=new connection();
	       	conn=conobj.getConnection();
             String sel="SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM DATA WHERE COMPANY=?";
             pst=conn.prepareStatement(sel);
             pst.setString(1, c);
             ResultSet rs=pst.executeQuery();
             ObservableList<property> data=getPropertyObjects(rs);
             return data;
             
       }
	   private ObservableList<property>getPropertyObjects(ResultSet rs) throws ClassNotFoundException,SQLException
	   {
		   try {
			   ObservableList <property> data =FXCollections.observableArrayList();
			   while ( rs.next() ) 
	             {
	           	  data.add(new property(
	           			  rs.getString("id"),
	           			  rs.getDate("Entry_Date").toLocalDate(),
	                         rs.getString("company"),
	                         rs.getString("brand"),
	                         rs.getString("Serial_Number"),
	                         rs.getString("problem"),
	                         rs.getDate("Return_Date").toLocalDate(),
	                         rs.getString("Diagnotics")
	                         ));
	           	  table.setItems(data);
	             }
			   return data;
		   }
		   catch(SQLException e)
		   {
			   e.printStackTrace();
			   throw e;
		   }
	   }
	   private ObservableList<property>getAllRecords() throws ClassNotFoundException,SQLException
	   {
		   Connection conn1;
	       	PreparedStatement pst;
	       	connection conobj=new connection();
	             conn1=conobj.getConnection();
		   String sel = "SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM data";
		   try
		   {
    	     pst=conn1.prepareStatement(sel);	
    	   ResultSet rs = pst.executeQuery();
    	   ObservableList <property> data =getPropertyObjects(rs);
    	   return data;
	   }
		   catch(SQLException e)
		   {
			   e.printStackTrace();
			   throw e;
		   }
	   }
	 
}
