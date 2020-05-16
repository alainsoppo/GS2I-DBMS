package GS2I;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import org.controlsfx.control.Notifications;
import DBConnection.connection;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class reportController {
	connection conObj=new connection();
	Connection conn;
	PreparedStatement pst;
	 @FXML
	    private AnchorPane rootPane;
    @FXML
    private StackPane stackPane;
	 @FXML
	    private TextField reportID;

	    @FXML
	    private TextArea reportTest;

	    @FXML
	    private TextField repairerName;

	    @FXML
	    private Button printfReport;

	    @FXML
	    public void generateReport() throws JRException, ClassNotFoundException, SQLException
	    {
	    if(reportID.getText().isEmpty())
		    	{
		    		 Notifications nb=Notifications.create()
			    	    		.title("Please enter all fields")
			    	    		.text("Device ID can not be empty")
			    	    		.graphic(null)
			    	    		.position(Pos.TOP_CENTER);
			    	    		nb.showWarning();
		    	}
		    	
		     else if(repairerName.getText().isEmpty())
		    	{
		    		 Notifications nb=Notifications.create()
			    	    		.title("Repairer name can not be empty")
			    	    		.text("Please enter your name")
			    	    		.graphic(null)
			    	    		.position(Pos.TOP_CENTER);
			    	    		nb.showWarning();
		    	}
		    	else if (reportTest.getText().isEmpty())
		    	{
		    		 Notifications nb=Notifications.create()
			    	    		.title("Test field can not be empty")
			    	    		.text("Please enter the test carriedout")
			    	    		.graphic(null)
			    	    		.position(Pos.TOP_CENTER);
			    	    		nb.showWarning();
		    	}
		    	else {
		    		try {
		    			conn=conObj.getConnection();
						JasperDesign jd=JRXmlLoader.load(this.getClass().getResourceAsStream("/GS2I/Invoice.jrxml"));
						JRDesignQuery query=new JRDesignQuery();
						query.setText("SELECT * FROM `data` WHERE `ID`='"+reportID.getText()+"'");
						jd.setQuery(query);
						HashMap<String, Object> parameters=new HashMap<>();
						parameters.put("test", reportTest.getText());
						parameters.put("name", repairerName.getText());
					    JasperReport js=JasperCompileManager.compileReport(jd);
					    JasperPrint jp=JasperFillManager.fillReport(js, parameters,conn);
					     if(isIDValid()==0)
					     {
								Notifications nb=Notifications.create()
					    	    		.title("An error occured while creating report")
					    	    		.text("ID does not exist")
					    	    		.graphic(null)
					    	    		.position(Pos.TOP_CENTER);
					    	    		nb.showError();
					     }
					     else
					     {
					    	 JasperViewer.viewReport(jp,false);
					    	 Stage curStage= (Stage) stackPane.getScene().getWindow();
					    	 curStage.hide();
					}
		    		}
					     catch (JRException e) {
						// TODO Auto-generated catch block
					throw e;
					}
		    	}
	    	
	    }
	    public int isIDValid() throws SQLException
	    {
	    	String selectID="SELECT *FROM DATA WHERE ID=?";
			   pst=conn.prepareStatement(selectID);
			   pst.setString(1, reportID.getText());
			     ResultSet result = pst.executeQuery();
			     int flag=0;
			     while(result.next())
			     {
			    	 flag=flag+1;
			     }
			return flag;
	    	
	    }
	    
	    
	    
	    
	    
	    
}
