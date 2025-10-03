/*
 * MOSTLY FROM ORACLE DOCUMENTATION
 * https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html#windowevent
 */

package app;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class WindowHandler extends JFrame implements WindowListener{
	
	private static final long serialVersionUID = 1L;
	
	//Each version has it's own closing settings
	private Runnable onClose;
	
	//Constructor
	public WindowHandler(Runnable onClose) {
		this.onClose = onClose;
	}

	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if (e.getSource() instanceof JFrame page) {
		
			//No matter what, a window was closed
			EventLog.writeLog(Events.WINDOW_EXIT);
			
			//Check if onClose was given instruction, if so use it
			if(onClose != null) {
				onClose.run();
			} 
			//Otherwise just get rid of the page
			else {
				page.dispose();
			}
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
