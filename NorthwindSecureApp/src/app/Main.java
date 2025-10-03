package app;

//Starts the application at the login page
public class Main {
	public static void main (String[] args){
		
		//Log event
		EventLog.writeLog(Events.APP_START);
		
		//Launch login page
		LoginLayer login = new LoginLayer();
		login.setVisible(true);
	}
}
