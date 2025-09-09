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
import java.awt.FlowLayout;
import app.Inventory;

public class Selection extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BusinessLogicLayer logic;


	/**
	 * Create the frame.
	 */
	public Selection(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel selectionsLabel = new JLabel("Selections");
		selectionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectionsLabel.setFont(new Font("Cambria", Font.BOLD, 16));
		contentPane.add(selectionsLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton customerButton = new JButton("Customers");
		customerButton.setFont(new Font("Cambria", Font.PLAIN, 12));
		panel.add(customerButton);
		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Test for access first
					logic.getCustomerAmount();
					
					PresentationLayer customers = new PresentationLayer(logic);
					customers.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton employeeButton = new JButton("Employees");
		employeeButton.setFont(new Font("Cambria", Font.PLAIN, 12));
		panel.add(employeeButton);
		employeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logic.getEmployeeAmount();
					Employees employeeData = new Employees(logic);
					employeeData.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton inventoryButton = new JButton("Inventory");
		inventoryButton.setFont(new Font("Cambria", Font.PLAIN, 12));
		panel.add(inventoryButton);
		inventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logic.getItemAmount();
					Inventory inventoryData = new Inventory(logic);
					inventoryData.setVisible(true);
				} catch(SQLException ee) {
					JOptionPane.showMessageDialog(null, "Insufficient permissions.", "Access Denied", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

	}

}
