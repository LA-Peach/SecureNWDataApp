package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;
import app.Orders;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;

public class Selection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BusinessLogicLayer logic;
	private List<JFrame> openFrames = new ArrayList<>();
	private SessionTimeout sessionTimeout;

	/**
	 * Create the frame.
	 */
	public Selection(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton customerButton = new JButton("Customers");
		customerButton.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(customerButton);
		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Test for access first
					logic.getCustomerAmount();
					//Reset the timer on activity
					sessionTimeout.resetTimer();
					//If no exception, open the window
					PresentationLayer customers = new PresentationLayer(logic, Selection.this, sessionTimeout);
					openFrames.add(customers);
					customers.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton employeeButton = new JButton("Employees");
		employeeButton.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(employeeButton);
		employeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logic.getEmployeeAmount();
					//Reset the timer on activity
					sessionTimeout.resetTimer();
					Employees employeeData = new Employees(logic, Selection.this, sessionTimeout);
					openFrames.add(employeeData);
					employeeData.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton ordersButton = new JButton("Orders");
		ordersButton.setFont(new Font("Arial", Font.PLAIN, 12));
		panel.add(ordersButton);
		ordersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logic.getOrdersAmount();
					sessionTimeout.resetTimer();
					Orders ordersData = new Orders(logic, Selection.this, sessionTimeout);
					openFrames.add(ordersData);
					ordersData.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[][grow]", "[25px]"));
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 10));
		panel_1.add(logoutButton, "cell 0 0, alignx left, aligny top");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JFrame child : getOpenFrames()) {
					child.dispose();
				}
				openFrames.clear();
				dispose();
				new LoginLayer().setVisible(true);
			}
		});
		
		
		JLabel selectionsLabel = new JLabel("Selections");
		selectionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectionsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel_1.add(selectionsLabel, "cell 1 0,growx");
		
		pack();
		setLocationRelativeTo(null);
		

		sessionTimeout = new SessionTimeout(() -> {
			logout();
			JOptionPane.showMessageDialog(null, "Session timed out due to inactivity.", 
					"Session Timeout", JOptionPane.INFORMATION_MESSAGE);
		});
		sessionTimeout.detectUserActivity(this);
		sessionTimeout.startTimer();
		
		
	}
	
	public void logout() {
		for (JFrame child : getOpenFrames()) {
			child.dispose();
		}
		openFrames.clear();
		dispose();
		new LoginLayer().setVisible(true);
	}
	
	public List<JFrame> getOpenFrames() {
		return openFrames;
	}

}
