package app;

public class InputValidation {
	
	//BASIC VALIDATION = Non-null, non-empty, no spaces, whitelist basic characters [a-zA-Z0-9-], length equal or under 128 length
	
	/*SERVER VALIDATION
	 * Basic Validation
	 * + Allow dots (.) and colons (:) for IP addresses and port numbers
	 * + Limit length to 255 characters instead
	 */
	public static boolean validateServer(String server) {
		return server != null 
				&& !server.trim().isEmpty() 
				&& !server.contains(" ") 
				&& server.matches("[a-zA-Z0-9-.:]+")
				&& server.length() <= 255;
	}
	
	/*DATABASE VALIDATION
	 * Basic Validation
	 * + Allow underscores (_) for common database naming conventions
	 */
	public static boolean validateDatabase(String database) {
		return database != null 
				&& !database.trim().isEmpty() 
				&& !database.contains(" ") 
				&& database.matches("[a-zA-Z0-9-_]+")
				&& database.length() <= 128;
	}
	
	/*USERNAME VALIDATION
	 * Basic Validation
	 * + Allow underscores (_) and periods (.) for common username conventions
	 */
	public static boolean validateUsername(String username) {
		return username != null 
				&& !username.trim().isEmpty() 
				&& !username.contains(" ") 
				&& username.matches("[a-zA-Z0-9-_.]+")
				&& username.length() <= 128;
	}
	
	/*PASSWORD VALIDATION
	 * Basic Validation
	 * + Allow special characters for stronger passwords
	 * + Limit length to 128 characters
	 */
	public static boolean validatePassword(String password) {
		return password != null 
				&& !password.trim().isEmpty() 
				&& !password.contains(" ") 
				&& password.length() <= 128;
		
	}

}
