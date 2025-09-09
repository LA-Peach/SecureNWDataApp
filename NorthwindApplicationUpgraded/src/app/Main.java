package app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


//Starts the application at the login page
public class Main {
	public static void main (String[] args) {
		//Login as admin in order to create other users
		try {
			String adminString = "jdbc:sqlserver://localhost:1433;databaseName=Northwind;user=userMaker;password=maker;encrypt=true;trustServerCertificate=true;";
			Connection adminConnection = DriverManager.getConnection(adminString);
			//Add logins, users and their roles. Only add the users if they haven't been added already. 
			Statement statement = adminConnection.createStatement();
			//Logins
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'inventory_specialist') "
				+ "CREATE LOGIN inventory_specialist WITH PASSWORD = 'pass123';");
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'human_resources') "
				+ "CREATE LOGIN human_resources WITH PASSWORD = 'pass321';");
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'CEO') "
				+ "CREATE LOGIN CEO WITH PASSWORD = 'CEO123!';");
			//Users
			statement.executeUpdate("USE Northwind;");
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'inventory_specialist') "
				+ "CREATE USER inventory_specialist FOR LOGIN inventory_specialist;");
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'human_resources') "
				+"CREATE USER human_resources FOR LOGIN human_resources;");
			statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'CEO') "
				+"CREATE USER CEO FOR LOGIN CEO;");
			//Roles
			statement.executeUpdate("GRANT SELECT ON Customers TO inventory_specialist;");
			statement.executeUpdate("GRANT SELECT ON Products TO inventory_specialist;");
			statement.executeUpdate("GRANT SELECT ON Employees TO human_resources;");
			statement.executeUpdate("GRANT SELECT ON Customers TO CEO;");
			statement.executeUpdate("GRANT SELECT ON Employees TO CEO;");
			statement.executeUpdate("GRANT SELECT ON Products TO CEO;");
			
			//Close the connection since this is the only thing that should be done without logging in
			adminConnection.close();
			
			//Launch login page
			LoginLayer login = new LoginLayer();
			login.setVisible(true);
		} catch(SQLException e) {
			
		}
		
		
		
	}
}
