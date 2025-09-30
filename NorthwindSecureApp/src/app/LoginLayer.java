package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginLayer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTextField serverField;
	private JTextField databaseField;
	
	public LoginLayer() {
		setTitle("NorthwindDataApp");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel loginTitleLabel = new JLabel("Login to Northwind");
		loginTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(loginTitleLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel serverLabel = new JLabel("Server: ");
		serverLabel.setHorizontalAlignment(SwingConstants.CENTER);
		serverLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_1.add(serverLabel, "6, 4, left, default");
		
		serverField = new JTextField();
		serverLabel.setLabelFor(serverField);
		panel_1.add(serverField, "8, 4, 7, 1, fill, default");
		serverField.setColumns(10);
		
		JLabel databaseLabel = new JLabel("Database: ");
		databaseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		databaseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_1.add(databaseLabel, "6, 6, left, default");
		
		databaseField = new JTextField();
		databaseLabel.setLabelFor(databaseField);
		panel_1.add(databaseField, "8, 6, 7, 1, fill, default");
		databaseField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_1.add(usernameLabel, "6, 8, left, default");
		usernameLabel.setLabelFor(usernameField);
		
		usernameField = new JTextField();
		panel_1.add(usernameField, "8, 8, 7, 1, fill, default");
		usernameField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_1.add(passwordLabel, "6, 10, left, default");
		passwordLabel.setLabelFor(passwordField);
		
		passwordField = new JPasswordField();
		panel_1.add(passwordField, "8, 10, 7, 1, fill, default");
		
		JButton loginButton = new JButton("Submit");
		loginButton.setFont(new Font("Arial", Font.PLAIN, 12));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String server = serverField.getText();
				String database = databaseField.getText();
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				
				//Input validation
				if (!InputValidation.validateServer(server)) {
					JOptionPane.showMessageDialog(null, "Invalid server input", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!InputValidation.validateDatabase(database)) {
					JOptionPane.showMessageDialog(null, "Invalid database input", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!InputValidation.validateUsername(username)) {
					JOptionPane.showMessageDialog(null, "Invalid username input", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!InputValidation.validatePassword(password)) {
					JOptionPane.showMessageDialog(null, "Invalid password input", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//Connection string for the database
				String connectionString = "jdbc:sqlserver://" + server + ";databaseName=" + database + ";user=" 
				+ username + ";password=" + password + ";encrypt=true;trustServerCertificate=true;";
				
				//Attempt to connect to the database
				try(Connection connection = DriverManager.getConnection(connectionString)){
					DatabaseAccessLayer data = new DatabaseAccessLayer(connectionString);
					BusinessLogicLayer logic = new BusinessLogicLayer(data);
					Selection selection = new Selection(logic);
					selection.setVisible(true);
					LoginLayer.this.setVisible(false);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Login failed", "Invalid", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(loginButton, "10, 14");
		
		pack();
		setLocationRelativeTo(null);

	}

}
