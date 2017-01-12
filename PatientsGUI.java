import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PatientsGUI {

	private JFrame frame; //create JFrame
	private static int prof; //mark profession
	Connection connection=null;
	private JTextField NameText; //Text field
	private JPasswordField PasswordText;

	public PatientsGUI() {
		initialize();
		connection=sqliteConnection.dbConnector();//make connection
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final PatientsGUI window = new PatientsGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 141, 249);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel NameLabel = new JLabel("Username");
		NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NameLabel.setBounds(20, 11, 86, 14);
		frame.getContentPane().add(NameLabel);
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordLabel.setBounds(20, 77, 86, 14);
		frame.getContentPane().add(PasswordLabel);
		NameText = new JTextField();
		NameText.setBounds(20, 36, 86, 20);
		frame.getContentPane().add(NameText);
		NameText.setColumns(10);
		
		final JButton LoginButton = new JButton("Login"); //log in to the patient info window button by executing query that checks info to the database
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int array[]=new int[2];
				int count=0; //checking the database for unique, double or no info
				array=sqliteConnection.login(connection, NameText.getText(), PasswordText.getText());
				prof=array[0];
				count=array[1];
				if (count==1){
					frame.dispose();
					final PatientInfoGUI patientInfo=new PatientInfoGUI();
					patientInfo.setVisible(true);
				}
			}
		});
		LoginButton.setBounds(20, 143, 89, 23);
		frame.getContentPane().add(LoginButton);
		
		PasswordText = new JPasswordField();
		PasswordText.setBounds(20, 102, 86, 20);
		frame.getContentPane().add(PasswordText);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(20, 177, 89, 23);
		frame.getContentPane().add(btnExit);
	}

	public static int profint() {
		return prof;
	}
}
