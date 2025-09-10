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

public class Employees extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel employeeNumLabel;
	private JList<String> employeeList;
	private BusinessLogicLayer logic;


	/**
	 * Create the frame.
	 */
	public Employees(BusinessLogicLayer logic) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel employeeDataLabel = new JLabel("Employee Data");
		employeeDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeDataLabel.setVerticalAlignment(SwingConstants.TOP);
		employeeDataLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(employeeDataLabel, BorderLayout.NORTH);
		
		employeeList = new JList();
		employeeList.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane scrollPane = new JScrollPane(employeeList);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JLabel employeeNamesLabel = new JLabel("Employee Names: ");
		employeeNamesLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contentPane.add(employeeNamesLabel, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JLabel employeeAmountLabel = new JLabel("Number of Employees: ");
		employeeAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		panel.add(employeeAmountLabel);
		
		employeeNumLabel = new JLabel("");
		employeeNumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(employeeNumLabel);
		
		dataFiller();

	}

	private void dataFiller() {
		try {
			//Number of employees
			int amountEmployee = logic.getEmployeeAmount();
			String strAmount = String.valueOf(amountEmployee);
			employeeNumLabel.setText(strAmount);
			//Employee names
			List<String> employees = logic.getEmployeeNames();
			employeeList.setListData(employees.toArray(new String[0]));
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
