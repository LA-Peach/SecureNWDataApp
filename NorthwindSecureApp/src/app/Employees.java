package app;

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
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class Employees extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel employeeNumLabel;
	private JList<String> employeeList;
	private BusinessLogicLayer logic;

	/**
	 * Create the frame.
	 */
	public Employees(BusinessLogicLayer logic, Selection parent, SessionTimeout sessionTimeout, int amountFromAccessCheck) {
		this.logic = logic;
		setTitle("Northwind DataApp");
		
		setName("Employees");
		
		//Give onClose direction to dispose of sensitive information when the window is closed
		addWindowListener(new WindowHandler(() -> {
			dispose();
		}));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		employeeList = new JList<>();
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
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new MigLayout("", "[][grow]", "[25px]"));
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.setFont(new Font("Arial", Font.PLAIN, 10));
		panel_1.add(logoutButton, "cell 0 0,alignx left,aligny top");
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
		
		
		JLabel employeeDataLabel = new JLabel("Employee Data");
		employeeDataLabel.setVerticalAlignment(SwingConstants.TOP);
		employeeDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		employeeDataLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_1.add(employeeDataLabel, "cell 1 0, growx");
		
		sessionTimeout.detectUserActivity(this);
		
		dataFiller(amountFromAccessCheck);
		
		pack();
		setLocationRelativeTo(null);

	}

	private void dataFiller(int amountEmployee) {
		try {
			String strAmount = String.valueOf(amountEmployee);
			employeeNumLabel.setText(strAmount);
			//Employee names
			List<String> employees = logic.getEmployeeNames();
			employeeList.setListData(employees.toArray(new String[0]));
			EventLog.writeLog(Events.DISPLAY_EMPLOYEES_SUCCESS);
		}catch (SQLException e) {
			EventLog.writeLog(Events.DISPLAY_EMPLOYEES_FAILURE);
			e.printStackTrace();
		}
		
	}

}
