package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;


public class Orders extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel ordersNumLabel;
	private BusinessLogicLayer logic;
	private JList<String> ordersDataList;


	/**
	 * Create the frame.
	 * @param amountOrders 
	 */
	public Orders(BusinessLogicLayer logic, Selection parent, SessionTimeout sessionTimeout, int amountOrders) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		
		setName("Orders");
		
		//Give onClose direction to dispose of sensitive information when the window is closed
		addWindowListener(new WindowHandler(() -> {
			dispose();
		}));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel ordersAmountLabel = new JLabel("Number of Orders: ");
		ordersAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(ordersAmountLabel);
		
		ordersNumLabel = new JLabel("");
		ordersNumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(ordersNumLabel);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new MigLayout("", "[][grow]", "[25px]"));
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 10));
		panel_2.add(logoutButton, "cell 0 0, alignx left, aligny top");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JFrame child : parent.getOpenFrames()) {
					child.dispose();
				}
				parent.dispose();
				dispose();
				
				EventLog.writeLog(Events.USER_LOGOUT);
				
				new LoginLayer().setVisible(true);
			}
		});
		
		JLabel ordersLabel = new JLabel("Orders");
		ordersLabel.setVerticalAlignment(SwingConstants.TOP);
		ordersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ordersLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_2.add(ordersLabel, "cell 1 0,growx");
		
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		
		ordersDataList = new JList<String>();
		ordersDataList.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane ordersScrollPane = new JScrollPane(ordersDataList);
		panel_4.add(ordersScrollPane);
		
		JLabel guideLabel = new JLabel("Ship to the following: Customer Name   @   Address");
		ordersScrollPane.setColumnHeaderView(guideLabel);
		guideLabel.setHorizontalAlignment(SwingConstants.LEFT);
		guideLabel.setFont(new Font("Arial", Font.BOLD, 14));
		
		sessionTimeout.detectUserActivity(this);
		
		dataFiller(amountOrders);
		
		pack();
		setLocationRelativeTo(null);

	}

	private void dataFiller(int amountOrders) {
		try {
			String strAmount = String.valueOf(amountOrders);
			ordersNumLabel.setText(strAmount);
			//Employee names
			List<String> orders = logic.getOrders();
			ordersDataList.setListData(orders.toArray(new String[0]));
			
			EventLog.writeLog(Events.DISPLAY_ORDERS_SUCCESS);
		}catch (SQLException e) {
			EventLog.writeLog(Events.DISPLAY_ORDERS_FAILURE);
			e.printStackTrace();
		}
		
	}

}