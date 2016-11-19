import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientsGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientsGUI window = new PatientsGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection=null;
	private JTextField NameText;
	private JPasswordField PasswordText;
	/**
	 * Create the application.
	 */
	public PatientsGUI() {
		initialize();
		connection=sqliteConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 375);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel NameLabel = new JLabel("Name");
		NameLabel.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(NameLabel);
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setBounds(10, 77, 46, 14);
		frame.getContentPane().add(PasswordLabel);
		
		NameText = new JTextField();
		NameText.setBounds(10, 36, 86, 20);
		frame.getContentPane().add(NameText);
		NameText.setColumns(10);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String loginquery="select * from StaffInfo where Username=? and Password=? ";
					PreparedStatement loginpst=connection.prepareStatement(loginquery);
					loginpst.setString(1, NameText.getText());
					loginpst.setString(2, PasswordText.getText());
					ResultSet loginrs=loginpst.executeQuery();
					int count=0;
					while(loginrs.next()){
						count++;
					}
					if (count==1){
						JOptionPane.showMessageDialog(null,"Username and password is correct");
					}else if(count>1){
						JOptionPane.showMessageDialog(null,"Duplicate username and password");
					}else{
						JOptionPane.showMessageDialog(null,"Username and password is not correct");
					}
					loginrs.close();
					loginpst.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}
			}
		});
		LoginButton.setBounds(10, 133, 89, 23);
		frame.getContentPane().add(LoginButton);
		
		PasswordText = new JPasswordField();
		PasswordText.setBounds(10, 102, 86, 20);
		frame.getContentPane().add(PasswordText);
	}
}
