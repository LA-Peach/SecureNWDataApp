package app;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class SessionTimeout {
	
	private Timer sessionInactivityTimer;
	// Timeout duration in milliseconds, currently set to 3 minutes
	private int timeoutDuration = 3 * 60 * 1000;
	
	public SessionTimeout(Runnable onTimeout) {
		sessionInactivityTimer = new Timer(timeoutDuration, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sessionInactivityTimer.stop();
				onTimeout.run();
			}
		});
		sessionInactivityTimer.setRepeats(false);
	}
	
	public void resetTimer() {
		if (sessionInactivityTimer.isRunning()) {
			sessionInactivityTimer.restart();
		} else {
			sessionInactivityTimer.start();
		}
	}
	
	public void stopTimer() {
		sessionInactivityTimer.stop();
	}
	
	public void startTimer() {
		sessionInactivityTimer.start();
	}
	
	//Listens for user activity to reset the timer
	public void detectUserActivity(Component component) {
		// Mouse clicks
		component.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				resetTimer();
			}
		});
		// Key presses
		component.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				resetTimer();
			}
		});
		// Mouse movements
		component.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			@Override
			public void mouseMoved(java.awt.event.MouseEvent e) {
				resetTimer();
			}
		});
	}
	

}
