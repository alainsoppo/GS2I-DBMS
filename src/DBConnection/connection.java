package DBConnection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;

public class connection extends Config {
	
Connection conn;

public Connection getConnection() throws ClassNotFoundException, SQLException
{
	try 
{
	Class.forName("com.mysql.jdbc.Driver");
	String str ="jdbc:mysql://"+Config.dbhost+":"+Config.dbport+"/"+Config.dbname;
	conn = DriverManager.getConnection(str,Config.dbuser,Config.dbpass);
	
}
catch(SQLException ex)
{
	Notifications nb=Notifications.create()
    		.title("Server not found")
    		.text("Please check if your computer is connected to the server\n\n"+"Contact the developer if this problem persist")
    		.graphic(null)
    		.position(Pos.TOP_CENTER);
    		nb.showWarning();
}
	return conn;
}

}
