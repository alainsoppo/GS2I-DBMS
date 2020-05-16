package GS2I;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class queryController implements Initializable {
	Connection conn;
   	PreparedStatement pst;
   	connection conobj=new connection();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<property> table;
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
    private TabPane tab;

    @FXML
    private Button queryYear;

    @FXML
    private Button queryMonth;


    @FXML
    private Button queryMonthYear;
    @FXML
    private ComboBox<String> selectMonth;

    @FXML
    private ComboBox<String> selectMonth1;
    @FXML
    private JFXTextField yearName;

    @FXML
    private JFXTextField yearMonthname;
    @FXML
    void backHome(ActionEvent event) {
    	makeFadeOt();
    }
  @FXML
  public void searchYearly() throws ClassNotFoundException, SQLException
  {
	  ObservableList <property> data=searchYear(yearName.getText());
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
	  	else if(yearName.getText().isEmpty())
	  	{
	  		Notifications nb=Notifications.create()
	  		  		.title("Error")
	  		  		.text("Please enter the year")
	  		  		.graphic(null)
	  		  		.position(Pos.TOP_CENTER);
	  		  		nb.showError();
	  	}
	  	else
	  	{
	  		populateTable(data);
	  		Notifications nb=Notifications.create()
	  		.title("Search result")
	  		.text("No device in the database was recorded in the year " + yearName.getText())
	  		.graphic(null)
	  		.position(Pos.TOP_CENTER);
	  		nb.showError();
	  		
	  	}
	  
  }
  
  @FXML
  public void searchMonthly() throws ClassNotFoundException, SQLException
  {
	  ObservableList <property> data=searchRecordMonthly(selectMonth.getValue());
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
  	else if(selectMonth.getValue()==null)
  	{
  		Notifications nb=Notifications.create()
  		  		.title("Error")
  		  		.text("Please enter the month")
  		  		.graphic(null)
  		  		.position(Pos.TOP_CENTER);
  		  		nb.showError();
  	}
  	else
  	{
  		populateTable(data);
  		Notifications nb=Notifications.create()
  		.title("Search result")
  		.text("No device in database was recorded in "+ selectMonth.getValue())
  		.graphic(null)
  		.position(Pos.TOP_CENTER);
  		nb.showError();
  		
  	}
	  
  }
  
  @FXML
  public void searchMonthYearly() throws ClassNotFoundException, SQLException
  {
	  ObservableList <property> data=searchMonthYear(yearMonthname.getText(),selectMonth1.getValue());
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
  	else if(selectMonth1.getValue()==null||yearMonthname.getText().isEmpty())
  	{
  		Notifications nb=Notifications.create()
  		  		.title("Error")
  		  		.text("Please enter month and year")
  		  		.graphic(null)
  		  		.position(Pos.TOP_CENTER);
  		  		nb.showError();
  	}
  	else
  	{
  		populateTable(data);
  		Notifications nb=Notifications.create()
  		.title("Search result")
  		.text("No device was recorded in "+ selectMonth1.getValue()+" "+yearMonthname.getText())
  		.graphic(null)
  		.position(Pos.TOP_CENTER);
  		nb.showError();  
	  
  }
  }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		selectMonth.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");
		selectMonth1.getItems().addAll("January","February","March","April","May","June","July","August","September","October","November","December");
		
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
    private void populateTable(ObservableList<property> data) {
		table.setItems(data);
		
	}
	public ObservableList<property> searchRecordMonthly(String c) throws ClassNotFoundException, SQLException
    {
		try {
  	Connection conn;
  	PreparedStatement pst;
   	connection conobj=new connection();
   	conn=conobj.getConnection();
          String sel="SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM DATA WHERE MONTHNAME(ENTRY_DATE)=?";
          pst=conn.prepareStatement(sel);
          pst.setString(1, c);
          ResultSet rs=pst.executeQuery();
          ObservableList<property> data=getPropertyObjects(rs);
          return data;
    }
	catch(SQLException e)
	{
		throw e;
	}
          
    }
	
	public ObservableList<property> searchYear(String y) throws ClassNotFoundException, SQLException
    {
		try {
  	Connection conn;
	       	PreparedStatement pst;
	       	connection conobj=new connection();
	       	conn=conobj.getConnection();
          String sel="SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM DATA WHERE YEAR(ENTRY_DATE)=?";
          pst=conn.prepareStatement(sel);
          pst.setString(1, y);
          ResultSet rs=pst.executeQuery();
          ObservableList<property> data=getPropertyObjects(rs);
          return data;
    }
	catch(SQLException e)
	{
		throw e;
	}
      
    }
	public ObservableList<property> searchMonthYear(String y,String m) throws ClassNotFoundException, SQLException
    {
		try {
  	Connection conn;
	       	PreparedStatement pst;
	       	connection conobj=new connection();
	       	conn=conobj.getConnection();
          String sel="SELECT ID,ENTRY_DATE,COMPANY,BRAND,SERIAL_NUMBER,PROBLEM,RETURN_DATE,DIAGNOTICS FROM DATA WHERE YEAR(ENTRY_DATE)=? AND MONTHNAME(ENTRY_DATE)=?";
          pst=conn.prepareStatement(sel);
          pst.setString(1, y);
          pst.setString(2, m);
          ResultSet rs=pst.executeQuery();
          ObservableList<property> data=getPropertyObjects(rs);
          return data;
		}
		catch(SQLException e)
		{
			throw e;
		}
          
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

}
