package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class PresentationLayer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel customerNumLabel;
	private JList<String> companiesList;
	private BusinessLogicLayer logic;
	private SessionTimeout sessionTimeout;


	/**
	 * Create the frame.
	 */
	public PresentationLayer(BusinessLogicLayer logic, Selection parent, SessionTimeout sessionTimeout) {
		this.logic = logic;
		this.sessionTimeout = sessionTimeout;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		companiesList = new JList();
		companiesList.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(companiesList);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JLabel customerNamesLabel = new JLabel("Customer Names: ");
		customerNamesLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(customerNamesLabel, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel customerAmountLabel = new JLabel("Number of Customers: ");
		customerAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(customerAmountLabel);
		
		customerNumLabel = new JLabel("");
		customerNumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(customerNumLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[][grow]", "[25px]"));
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 10));
		panel_1.add(logoutButton, "cell 0 0, alignx left, aligny top");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JFrame child : parent.getOpenFrames()) {
					child.dispose();
				}
				parent.dispose();
				dispose();
				new LoginLayer().setVisible(true);
			}
		});
		
		JLabel customerDataLabel = new JLabel("Customer Data");
		customerDataLabel.setVerticalAlignment(SwingConstants.TOP);
		customerDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customerDataLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_1.add(customerDataLabel, "cell 1 0,growx");
		
		sessionTimeout.detectUserActivity(this);
		sessionTimeout.startTimer();
		
		dataFiller();
		
		pack();
		setLocationRelativeTo(null);

	}

	private void dataFiller() {
		try {
			//Number of customers
			int amount = logic.getCustomerAmount();
			String strAmount = String.valueOf(amount);
			customerNumLabel.setText(strAmount);
			//Customer names
			List<String> companies = logic.getCustomerNames();
			companiesList.setListData(companies.toArray(new String[0]));
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
