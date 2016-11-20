import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import javax.swing.*;

public class PatientInfoGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientInfoGUI frame = new PatientInfoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection=null;
	private JTextField IDtext;
	private JTextField Nametext;
	private JTextField Surnametext;
	/**
	 * Create the frame.
	 */
	public PatientInfoGUI() {
		connection=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel PatientInfo = new JLabel("Patient Info");
		PatientInfo.setBounds(10, 11, 66, 14);
		contentPane.add(PatientInfo);
		
		//log out from patient info window button
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//close the patient info window
				contentPane.setVisible(false);
				dispose();
				//load the main application again
				PatientsGUI.main(null);
			}
		});
		btnLogOut.setBounds(10, 302, 89, 23);
		contentPane.add(btnLogOut);
		
		//show patient info from the database button by executing query
		JButton btnShowInfo = new JButton("Show Info");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String piquery="select * from PatientsInfo";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					//show data on JTable
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					pipst.close();
					pirs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnShowInfo.setBounds(385, 7, 89, 23);
		contentPane.add(btnShowInfo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(113, 43, 361, 282);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 36, 46, 14);
		contentPane.add(lblId);
		
		IDtext = new JTextField();
		IDtext.setBounds(10, 61, 86, 20);
		contentPane.add(IDtext);
		IDtext.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 92, 46, 14);
		contentPane.add(lblName);
		
		Nametext = new JTextField();
		Nametext.setBounds(10, 117, 86, 20);
		contentPane.add(Nametext);
		Nametext.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 148, 46, 14);
		contentPane.add(lblSurname);
		
		Surnametext = new JTextField();
		Surnametext.setBounds(10, 173, 86, 20);
		contentPane.add(Surnametext);
		Surnametext.setColumns(10);
		
		//save patient info to the database button by executing query
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String piquery="insert into PatientsInfo (ID,Name,Surname) values (?,?,?)";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					//read data from text fields
					pipst.setString(1,IDtext.getText());
					pipst.setString(2,Nametext.getText());
					pipst.setString(3,Surnametext.getText());
					pipst.execute();
					//show confirmation message
					JOptionPane.showMessageDialog(null,"Patient Info Saved");
					pipst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 268, 89, 23);
		contentPane.add(btnSave);
	}
}
