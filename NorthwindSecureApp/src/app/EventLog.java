package app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class EventLog {
	
	//Logger variables
	private static Logger logger;
	private static String logDirectory = "logs";
	private static String filename = logDirectory +"/NWDataApp.log";
	
	//Header constants
	static String cefVersion = "CEF:1";
	static String deviceVendor = "LA-Peach";
	static String deviceProduct = "Northwind DataApp";
	static String deviceVersion = "0.6";
	
	//Constructor
	static {
		try {
			java.nio.file.Files.createDirectories(java.nio.file.Paths.get(logDirectory));
			logger = Logger.getLogger("NWDataApp");
			FileHandler fileHandler = new FileHandler(filename, true);
			
			//Custom FileHandler format
			fileHandler.setFormatter(new Formatter() {
				public String format(LogRecord record) {
					return record.getMessage() + System.lineSeparator();
				}
			});
			
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Sanitize user input
	public static String sanitize(String input) {
		return input.replaceAll("[\\n\\r|]", "_");
	}
	
	//Header method
	public static String headerStringHelper(Events event) {
		//Variable header pieces
		String eventClassID = String.valueOf(event.getEventClassID());
		String name = event.getName();
		String severity = String.valueOf(event.getSeverity());
		
		//Set header string
		String header = cefVersion + "|" + deviceVendor + "|" + deviceProduct + "|" + deviceVersion + "|" + eventClassID + "|" + name + "|" + severity + "|";
		return header;
	}
	
	//Extension method
	public static String extensionStringHelper(Events event, String username) {
		String src;
		//Get src (IP), when unable to resolve use N/A
		try {
			src = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			
			EventLog.writeLog(Events.FIND_HOST_FAILURE);
			
			src = "RETREVAL ERROR";
		}
		
		String currentUser = Session.getUser();
		String suser;
		if (currentUser == null || currentUser.isEmpty()) {
			suser = "N/A";
		} else {
			suser = sanitize(currentUser);
		}
		
		//Get outcome by examining the last character of the eventClassID string
		String eventClassID = String.valueOf(event.getEventClassID());
		int idLength = eventClassID.length();
		int lastCharacterIndex = idLength - 1;
		char lastCharacter = eventClassID.charAt(lastCharacterIndex);
		char success = '0';
		
		String outcome;
		if(lastCharacter == success) {
			outcome = "Success";
		}else {
			outcome = "Failure";
		}
		
		String msg = event.getMsg();
		
		String rt = event.getTimestamp();
		
		//Get security tag (st) by examining the value of the severity in the context of RFC 5424
		int severity = event.getSeverity();
		String st = null;
		switch (severity) {
			case 0:
				st = "[EMERGENCY]";
				break;
			case 1:
				st = "[ALERT]";
				break;
			case 2:
				st = "[CRITICAL]";
				break;
			case 3:
				st = "[ERROR]";
				break;
			case 4:
				st = "[WARNING]";
				break;
			case 5:
				st = "[NOTICE]";
				break;
			case 6:
				st = "[INFO]";
				break;
			case 7:
				st = "[DEBUG]";
				break;
		}
		
		String extension = "src=" + src + " suser=" + suser + " outcome=" + outcome + " msg=" + msg + " rt=" + rt + " st=" + st;
		return extension;
	}
	
	public static String CEFStringHelper(Events event, String username){
		return headerStringHelper(event) + extensionStringHelper(event, username);
	}
	
	public static void writeLog(Events event) {
		String username = Session.getUser();
		
		String logString = CEFStringHelper(event, username);
		
		Level logLevel = null;
		int severity = event.getSeverity();
		
		switch (severity) {
			case 0:
				logLevel = Level.SEVERE;
				break;
			case 1:
				logLevel = Level.SEVERE;
				break;
			case 2:
				logLevel = Level.SEVERE;
				break;
			case 3:
				logLevel = Level.SEVERE;
				break;
			case 4:
				logLevel = Level.WARNING;
				break;
			case 5:
				logLevel = Level.INFO;
				break;
			case 6:
				logLevel = Level.INFO;
				break;
			case 7:
				logLevel = Level.FINE;
				break;
		}
		logger.log(logLevel, logString);
		
	}
	
}
	


