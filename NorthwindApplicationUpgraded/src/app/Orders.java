package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Orders extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel ordersNumLabel;
	private JList<String> ordersDataList;
	private BusinessLogicLayer logic;


	/**
	 * Create the frame.
	 */
	public Orders(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel ordersLabel = new JLabel("Orders");
		ordersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ordersLabel.setVerticalAlignment(SwingConstants.TOP);
		ordersLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(ordersLabel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel guideLabel = new JLabel("Ship to the following: Customer Name   @   Address");
		guideLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		guideLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(guideLabel, BorderLayout.NORTH);
		
		ordersDataList = new JList<>();
		panel_1.add(ordersDataList, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel ordersAmountLabel = new JLabel("Number of Orders: ");
		ordersAmountLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		panel.add(ordersAmountLabel);
		
		ordersNumLabel = new JLabel("");
		ordersNumLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		panel.add(ordersNumLabel);
		
		dataFiller();

	}

	private void dataFiller() {
		try {
			//Number of employees
			int amountOrders = logic.getOrdersAmount();
			String strAmount = String.valueOf(amountOrders);
			ordersNumLabel.setText(strAmount);
			//Employee names
			List<String> orders = logic.getOrders();
			ordersDataList.setListData(orders.toArray(new String[0]));
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}