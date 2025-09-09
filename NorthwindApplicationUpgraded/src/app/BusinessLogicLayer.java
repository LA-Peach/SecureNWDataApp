package app;

import java.sql.SQLException;
import java.util.List;

public class BusinessLogicLayer {
	//Access lower layer
	DatabaseAccessLayer database;
	public BusinessLogicLayer(DatabaseAccessLayer database) {
		this.database = database;
	}
	//Customer amount method
	public int getCustomerAmount() throws SQLException{
		return database.getCustomerAmount();
	}
	//Customer names method
	public List<String> getCustomerNames() throws SQLException{
		return database.getCustomerNames();
	}
	//Employee amount method
	public int getEmployeeAmount() throws SQLException{
		return database.getEmployeeAmount();
	}
	//Employee names method
	public List<String> getEmployeeNames() throws SQLException{
		return database.getEmployeeNames();
	}
	//Product amount method
	public int getOrdersAmount() throws SQLException{
		return database.getOrdersAmount();
	}
	//Inventory method
	public List<String> getOrders() throws SQLException{
		return database.getOrders();
	}

}
