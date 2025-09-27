package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTextField;

public class PresentationLayer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel customerNumLabel;
	private JList<String> companiesList;
	private BusinessLogicLayer logic;


	/**
	 * Create the frame.
	 */
	public PresentationLayer(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel customerDataLabel = new JLabel("Customer Data");
		customerDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customerDataLabel.setVerticalAlignment(SwingConstants.TOP);
		customerDataLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(customerDataLabel, BorderLayout.NORTH);
		
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
		
		dataFiller();

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
