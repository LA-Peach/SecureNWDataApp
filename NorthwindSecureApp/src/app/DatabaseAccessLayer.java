package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessLayer {
	//First layer
	
	//Open connection
	Connection connection;
	public DatabaseAccessLayer(String connectionString) throws SQLException{	
		try {
			connection = DriverManager.getConnection(connectionString);
			
			EventLog.writeLog(Events.DATABASE_CONNECTION_SUCCESSFUL);
			
		} catch (SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_CONNECTION_FAILURE);
			
			throw e;
		}
	}
	
	//Method to find the amount of customers in the database
	public int getCustomerAmount() throws SQLException{
		try {
			//Code to query the database
			String sqlInput = "SELECT COUNT(*) AS amount FROM Customers";
			Statement statement = connection.createStatement();
			//Receive the results for the SQL input
			ResultSet result = statement.executeQuery(sqlInput);
			
			EventLog.writeLog(Events.DATABASE_CUSTOMER_AMOUNT_SUCCESS);
			
			if (result.next()) {
				return result.getInt("amount");
			}
			else {
				return 0;
			}
			
			
		} catch(SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_CUSTOMER_AMOUNT_FAILURE);
			
			throw e;
		}
	}
	
	//Method to store a list the names of the customers. Customers in this case are businesses stored under "CompanyName" 
	public List<String> getCustomerNames() throws SQLException{
		try {
			List<String> companies = new ArrayList<>();
			String sqlInput = "SELECT CompanyName FROM Customers";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlInput);
		
			EventLog.writeLog(Events.DATABASE_CUSTOMER_NAMES_SUCCESS);
		
			//Add each submission in the results to the list
			while (result.next()) {
				companies.add(result.getString("CompanyName"));
			}
			return companies;
		} catch (SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_CUSTOMER_NAMES_FAILURE);
			
			throw e;
		}
	}
	
	//Method to find the amount of employees in the database
	public int getEmployeeAmount() throws SQLException{
		try {
			//Code to query the database
			String sqlInput = "SELECT COUNT(*) AS amountEmployee FROM Employees";
			Statement statement = connection.createStatement();
			//Receive the results for the SQL input
			ResultSet result = statement.executeQuery(sqlInput);
			
			EventLog.writeLog(Events.DATABASE_EMPLOYEE_AMOUNT_SUCCESS);
			
			if (result.next()) {
				return result.getInt("amountEmployee");
			}
			else {
				return 0;
			}
		} catch(SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_EMPLOYEE_AMOUNT_FAILURE);
			
			throw e;
		}
	}
	
	//Method to store a list of the names of the employees. Employee names are stored as First and Last.
	public List<String> getEmployeeNames() throws SQLException{
		try {
			List<String> employees = new ArrayList<>();
			String sqlInput = "SELECT FirstName + ' ' + LastName AS FullName FROM Employees";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlInput);
			
			EventLog.writeLog(Events.DATABASE_EMPLOYEE_NAMES_SUCCESS);
			
			//Add each submission in the results to the list
			while (result.next()) {
				employees.add(result.getString("FullName"));
			}
			return employees;
		} catch (SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_EMPLOYEE_NAMES_FAILURE);
			
			throw e;
		}
	}
	
	//Method to find the amount of products in the database
	public int getOrdersAmount() throws SQLException{
		try {
			//Code to query the database
			String sqlInput = "SELECT COUNT(*) AS amountOrders FROM Orders";
			Statement statement = connection.createStatement();
			//Receive the results for the SQL input
			ResultSet result = statement.executeQuery(sqlInput);
			
			EventLog.writeLog(Events.DATABASE_ORDERS_AMOUNT_SUCCESS);
			
			if (result.next()) {
				return result.getInt("amountOrders");
			}
			else {
				return 0;
			}
		} catch (SQLException e) {
			
			EventLog.writeLog(Events.DATABASE_ORDERS_AMOUNT_FAILURE);
			
			throw e;
		}
	}
	
	public List<String> getOrders() throws SQLException{
		try {
			List<String> orders = new ArrayList<>();
			//Extra spaces added for readability
			String sqlInput = "SELECT ShipName + '   @   ' + ShipAddress + '  ' + ShipCity + ', ' + ShipCountry + '  ' + ShipPostalCode AS ordersData FROM Orders";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sqlInput);
			
			EventLog.writeLog(Events.DATABASE_ORDERS_DATA_SUCCESS);
			
			//Add each submission in the results to the list
			while (result.next()) {
				orders.add(result.getString("ordersData"));
			}
			return orders;
		} catch (SQLException e) {

			EventLog.writeLog(Events.DATABASE_ORDERS_DATA_FAILURE);
			
			throw e;
		}
	}
}
