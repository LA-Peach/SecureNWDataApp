package app;
import java.sql.SQLException;


//Starts the application at the login page
public class Main {
	public static void main (String[] args) throws SQLException {
		//Launch login page
		LoginLayer login = new LoginLayer();
		login.setVisible(true);
	}
}
