package testGUI_SE;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UserLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private JLabel label;
	private JPanel contentPane;

	/**
	 * Launch the application. */
	/* 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public UserLogin() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 190, 600, 580);
		
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
		lblNewLabel.setBounds(250, 13, 273, 93);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		textField.setBounds(233, 170, 281, 68);
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
		passwordField.setBounds(233, 286, 281, 68);
		contentPane.add(passwordField);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBackground(Color.BLACK);
		lblUsername.setForeground(Color.BLACK);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblUsername.setBounds(20, 166, 193, 52);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.BLACK);
		lblPassword.setBackground(Color.CYAN);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblPassword.setBounds(20, 286, 193, 52);
		contentPane.add(lblPassword);

		btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton.setBounds(250, 392, 162, 73);
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String userName = textField.getText();
				String password = passwordField.getText();
				try {
					Connection connection = (Connection) DriverManager
							.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "Admin_2022");

					PreparedStatement st = (PreparedStatement) connection
							.prepareStatement("Select username, password from user where username=? and password=?");

					st.setString(1, userName);
					st.setString(2, password);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						System.out.println("Data: "+rs.getRow());
						dispose();
					
						JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
						ChatBox chatBox = new ChatBox();
					} else {
						JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
					}
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}	
			}		
		});

		contentPane.add(btnNewButton);
	}
}
