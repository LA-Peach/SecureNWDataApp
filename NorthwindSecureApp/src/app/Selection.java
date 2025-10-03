package app;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;

public class Selection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private List<JFrame> openFrames = new ArrayList<>();
	private SessionTimeout sessionTimeout;

	/**
	 * Create the frame.
	 */
	public Selection(BusinessLogicLayer logic) {
		setTitle("Northwind DataApp");
		
		setName("Selection");
		
		//Give onClose instruction to go back to the login page
		addWindowListener(new WindowHandler(() -> {
			EventLog.writeLog(Events.USER_LOGOUT);
			dispose();
			new LoginLayer().setVisible(true);
		}));
		
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
					//Test for access first, pass this value to PresentationLayer
					int amountCustomer = logic.getCustomerAmount();
					//Reset the timer on activity
					sessionTimeout.resetTimer();
					//If no exception, open the window
					PresentationLayer customers = new PresentationLayer(logic, Selection.this, sessionTimeout, amountCustomer);
					openFrames.add(customers);
					
					EventLog.writeLog(Events.ACCESS_CUSTOMERS_SUCCESS);
					
					customers.setVisible(true);
				} catch(SQLException ee) {
					
					EventLog.writeLog(Events.ACCESS_CUSTOMERS_FAILURE);
					
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
					int amountEmployee = logic.getEmployeeAmount();
					//Reset the timer on activity
					sessionTimeout.resetTimer();
					Employees employeeData = new Employees(logic, Selection.this, sessionTimeout, amountEmployee);
					openFrames.add(employeeData);
					
					EventLog.writeLog(Events.ACCESS_EMPLOYEES_SUCCESS);
					
					employeeData.setVisible(true);
				} catch(SQLException ee) {
					
					EventLog.writeLog(Events.ACCESS_EMPLOYEES_FAILURE);
					
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
					int amountOrders = logic.getOrdersAmount();
					sessionTimeout.resetTimer();
					Orders ordersData = new Orders(logic, Selection.this, sessionTimeout, amountOrders);
					openFrames.add(ordersData);
					
					EventLog.writeLog(Events.ACCESS_ORDERS_SUCCESS);
					
					ordersData.setVisible(true);
				} catch(SQLException ee) {
					
					EventLog.writeLog(Events.ACCESS_ORDERS_FAILURE);
					
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
				
				EventLog.writeLog(Events.USER_LOGOUT);
				
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
