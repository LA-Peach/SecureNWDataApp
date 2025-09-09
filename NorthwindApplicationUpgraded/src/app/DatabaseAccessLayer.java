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
		connection = DriverManager.getConnection(connectionString);
	}
	//Method to find the amount of customers in the database
	public int getCustomerAmount() throws SQLException{
		//Code to query the database
		String sqlInput = "SELECT COUNT(*) AS amount FROM Customers";
		Statement statement = connection.createStatement();
		//Receive the results for the SQL input
		ResultSet result = statement.executeQuery(sqlInput);
		if (result.next()) {
			return result.getInt("amount");
		}
		else {
			return 0;
		}
	}
	//Method to store a list the names of the customers. Customers in this case are businesses stored under "CompanyName" 
	public List<String> getCustomerNames() throws SQLException{
		List<String> companies = new ArrayList<>();
		String sqlInput = "SELECT CompanyName FROM Customers";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlInput);
		//Add each submission in the results to the list
		while (result.next()) {
			companies.add(result.getString("CompanyName"));
		}
		return companies;
	}
	//Method to find the amount of employees in the database
	public int getEmployeeAmount() throws SQLException{
		//Code to query the database
		String sqlInput = "SELECT COUNT(*) AS amountEmployee FROM Employees";
		Statement statement = connection.createStatement();
		//Receive the results for the SQL input
		ResultSet result = statement.executeQuery(sqlInput);
		if (result.next()) {
			return result.getInt("amountEmployee");
		}
		else {
			return 0;
		}
	}
	//Method to store a list of the names of the employees. Employee names are stored as First and Last.
	public List<String> getEmployeeNames() throws SQLException{
		List<String> employees = new ArrayList<>();
		String sqlInput = "SELECT FirstName + ' ' + LastName AS FullName FROM Employees";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlInput);
		//Add each submission in the results to the list
		while (result.next()) {
			employees.add(result.getString("FullName"));
		}
		return employees;
	}
	//Method to find the amount of products in the database
	public int getOrdersAmount() throws SQLException{
		//Code to query the database
		String sqlInput = "SELECT COUNT(*) AS amountOrders FROM Orders";
		Statement statement = connection.createStatement();
		//Receive the results for the SQL input
		ResultSet result = statement.executeQuery(sqlInput);
		if (result.next()) {
			return result.getInt("amountOrders");
		}
		else {
			return 0;
	}
}
	public List<String> getOrders() throws SQLException{
		List<String> orders = new ArrayList<>();
		//Extra spaces added for readability
		String sqlInput = "SELECT 'Ship to:   ' + ShipName + '   @   ' + ShipAddress + '   ' + ShipCity + '   ' + ShipCountry + '   ' + ShipPostalCode AS ordersData FROM Orders";
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlInput);
		//Add each submission in the results to the list
		while (result.next()) {
			orders.add(result.getString("ordersData"));
		}
		return orders;
	}
}
