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

public class Inventory extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel inventoryNumLabel;
	private JList<String> itemDataList;
	private BusinessLogicLayer logic;


	/**
	 * Create the frame.
	 */
	public Inventory(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabel.setVerticalAlignment(SwingConstants.TOP);
		inventoryLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(inventoryLabel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel guideLabel = new JLabel("Item name        units in stock @ price");
		guideLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		guideLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(guideLabel, BorderLayout.NORTH);
		
		itemDataList = new JList<>();
		panel_1.add(itemDataList, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel inventoryAmountLabel = new JLabel("Number of Products: ");
		inventoryAmountLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		panel.add(inventoryAmountLabel);
		
		inventoryNumLabel = new JLabel("");
		inventoryNumLabel.setFont(new Font("Cambria", Font.PLAIN, 14));
		panel.add(inventoryNumLabel);
		
		dataFiller();

	}

	private void dataFiller() {
		try {
			//Number of employees
			int amountInventory = logic.getItemAmount();
			String strAmount = String.valueOf(amountInventory);
			inventoryNumLabel.setText(strAmount);
			//Employee names
			List<String> inventory = logic.getInventory();
			itemDataList.setListData(inventory.toArray(new String[0]));
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}