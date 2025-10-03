package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum Events {
	
	/*
	 * Most event IDs are grouped by the class in which they occur in ranges of 1000s
	 * Successful events will end with 0
	 * Failure events will end with 1-9
	 * 
	 * Severity levels are based on RFC 5424
	 * 0 - Emergency: system is unusable
	 * 1 - Alert: action must be taken immediately
	 * 2 - Critical: critical conditions
	 * 3 - Error: error conditions
	 * 4 - Warning: warning conditions
	 * 5 - Notice: normal but significant condition
	 * 6 - Informational: informational messages
	 * 7 - Debug: debug-level messages
	 */
	
	//Various Events 0010-0099
	APP_START(10, "Application Started", 6, "Application started via the Main class"),
	USER_LOGOUT(20, "User Logged Out", 6,  "User pressed logout button"),
	WINDOW_EXIT(30, "Window Exited", 6,  "User pressed the exit button"),
	SESSION_TIMEOUT(40, "Session Timed Out", 5,  "User idle for 3 minutes, logged out for security"),
	FIND_HOST_FAILURE(91, "Host Not Found", 3,  "Unable to find host IP"),
	APP_EXIT(50, "Application Exited", 6, "The application has stopped running."),
	
	//LoginLayer.java 1000-1999
		//Login 1000-1001
	SUCCESSFUL_LOGIN(1000, "Login Success", 6,  "User has logged in successfully"),
	FAILED_LOGIN(1001, "Login Failed", 4, "Information is valid, but incorrect"),
		//Login input validation 1100-1199
	LOGIN_VALIDATED(1100, "Login Inputs Validated", 6,  "User input valid, not necessarily correct"),
	INVALID_SERVER(1101, "Invalid Server", 4,  "User input does not match what a server input should include"),
	INVALID_DATABASE(1102, "Invalid Database", 4,  "User input does not match what a database input should include"),
	INVALID_USERNAME(1103, "Invalid Username", 4,  "User input does not match what a username input should include"),
	INVALID_PASSWORD(1104, "Invalid Password", 4,  "User input does not match what a password input should include"),
	
	//Selection.java 2000-2999
		//Customers 2100-2101
	ACCESS_CUSTOMERS_SUCCESS(2100, "Accessed Customer Data", 6,  "Access allowed to user for customer data display"),
	ACCESS_CUSTOMERS_FAILURE(2101, "Denied Access to Customer Data", 4,  "Access denied to user for customer data display"),
		//Employees 2200-2201
	ACCESS_EMPLOYEES_SUCCESS(2200, "Accessed Employees Data", 6,  "Access allowed to user for employee data display"),
	ACCESS_EMPLOYEES_FAILURE(2201, "Denied Access to Employees Data", 4,  "Access denied to user for employee data display"),
		//Orders 2300-2301
	ACCESS_ORDERS_SUCCESS(2300, "Accessed Orders Data", 6,  "Access allowed to user for orders data display"),
	ACCESS_ORDERS_FAILURE(2301, "Denied Access to Orders Data", 4,  "Access denied to user for orders data display"),
	
	//PresentationLayer.java 3000-3999
	DISPLAY_CUSTOMERS_SUCCESS(3000, "Displayed Customer Data", 6,  "Customer data retrieved and displayed successfully"),
	DISPLAY_CUSTOMERS_FAILURE(3001, "Failed Displaying Customer Data", 3,  "Unable to retrieve or display customer data"),
	
	//Employees.java 4000-4999
	DISPLAY_EMPLOYEES_SUCCESS(4000, "Displayed Employees Data", 6, "Employees data retrieved and displayed successfully"),
	DISPLAY_EMPLOYEES_FAILURE(4001, "Failed Displaying Employees Data", 3,  "Unable to retrieve or display employees data"),
	
	//Orders.java 5000-5999
	DISPLAY_ORDERS_SUCCESS(5000, "Displayed Orders Data", 6,  "Orders data retrieved and displayed successfully"),
	DISPLAY_ORDERS_FAILURE(5001, "Failed Displaying Orders Data", 3,  "Unable to retrieve or display orders data"),
	
	//BusinessLogicLayer.java 6000-6999
		//Customers 6100-6199
	GET_CUSTOMER_AMOUNT_SUCCESS(6110, "Recieved Customer Amount", 6, "Logic layer recieved amount from the database"),
	GET_CUSTOMER_AMOUNT_FAILURE(6111, "Failed to Recieve Customer Amount", 3, "Logic layer could not recieve the amount from the database"),
	GET_CUSTOMER_NAMES_SUCCESS(6120, "Recieved Customer Names", 6, "Logic layer recieved the names from the database"),
	GET_CUSTOMER_NAMES_FAILURE(6121, "Failed to Recieve Customer Names", 3, "Logic layer could not recieve the names from the database"),
		//Employees 6200-6299
	GET_EMPLOYEE_AMOUNT_SUCCESS(6210, "Recieved Employee Amount", 6, "Logic layer recieved amount from the database"),
	GET_EMPLOYEE_AMOUNT_FAILURE(6211, "Failed to Recieve Employee Amount", 3, "Logic layer could not recieve the amount from the database"),
	GET_EMPLOYEE_NAMES_SUCCESS(6220, "Recieved Employee Names", 6, "Logic layer recieved the names from the database"),
	GET_EMPLOYEE_NAMES_FAILURE(6221, "Failed to Recieve Employee Names", 3, "Logic layer could not recieve the names from the database"),
		//Orders 6300-6399
	GET_ORDERS_AMOUNT_SUCCESS(6310, "Recieved Orders Amount", 6, "Logic layer recieved amount from the database"),
	GET_ORDERS_AMOUNT_FAILURE(6311, "Failed to Recieve Orders Amount", 3, "Logic layer could not recieve the amount from the database"),
	GET_ORDERS_DATA_SUCCESS(6320, "Recieved Orders Data", 6, "Logic layer recieved the orders data from the database"),
	GET_ORDERS_DATA_FAILURE(6321, "Failed to Recieve Orders Data", 6, "Logic layer could not recieve the orders data from the database"),
	
	//DatabaseAccessLayer.java 7000-7999
	DATABASE_CONNECTION_SUCCESSFUL(7000, "Connected to Database", 6, "Database connection has been established"),
	DATABASE_CONNECTION_FAILURE(7001, "Failed to Connect to Database", 3, "Unable to connect to the databse"),
		//Customers 7100-7199
	DATABASE_CUSTOMER_AMOUNT_SUCCESS(7110, "Queried Customer Amount", 6, "Access layer was able to query the amount from the Customers table"),
	DATABASE_CUSTOMER_AMOUNT_FAILURE(7111, "Failed to Query Customer Amount", 3, "Unable to query the amount from the Customers table"),
	DATABASE_CUSTOMER_NAMES_SUCCESS(7120, "Queried Customer Names", 6, "Access layer was able to query the names from the Customers table"),
	DATABASE_CUSTOMER_NAMES_FAILURE(7121, "Failed to Query Customer Names", 3, "Unable to query the names from the Customers table"),
		//Employees 7200-7299
	DATABASE_EMPLOYEE_AMOUNT_SUCCESS(7210, "Queried Employee Amount", 6, "Access layer was able to query the amount from the Employees table"),
	DATABASE_EMPLOYEE_AMOUNT_FAILURE(7211, "Failed to Query Employee Amount", 3, "Unable to query the amount from the Employees table"),
	DATABASE_EMPLOYEE_NAMES_SUCCESS(7220, "Queried Employee Names", 6, "Access layer was able to query the names from the Employees table"),
	DATABASE_EMPLOYEE_NAMES_FAILURE(7221, "Failed to Query Employee Names", 3, "Unable to query the names from the Employees table"),
		//Orders 7300-7399
	DATABASE_ORDERS_AMOUNT_SUCCESS(7310, "Queried Orders Amount", 6, "Access layer was able to query the amount from the Orders table"),
	DATABASE_ORDERS_AMOUNT_FAILURE(7311, "Failed to Query Orders Amount", 3, "Unable to query the amount from the Orders table"),
	DATABASE_ORDERS_DATA_SUCCESS(7320, "Queried Orders Data", 6, "Access layer was able to query the orders data from the Orders table"),
	DATABASE_ORDERS_DATA_FAILURE(7321, "Failed to Query Orders Data", 6, "Unable to query the orders data from the Orders table");
	
	//Variables
	private final int eventClassID;
	private final String name;
	private final int severity;
	private String msg;
	
	//Constructor
	Events(int eventClassID, String name, int severity, String msg){
		this.eventClassID = eventClassID;
		this.name = name;
		this.severity = severity;
		this.msg = msg;
	}
	
	//Variable Getters
	public int getEventClassID() {
		return eventClassID;
	}
	public String getName() {
		return name;
	}
	public int getSeverity() {
		return severity;
	}
	public String getMsg() {
		return msg;
	}
	
	public String getTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");
		return LocalDateTime.now().format(formatter);
	}


}
