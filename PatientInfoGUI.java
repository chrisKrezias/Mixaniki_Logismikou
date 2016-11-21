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
	
	//function that uses "show info" code to refresh the table when an action is triggered
	public void refreshTable(){
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
	/**
	 * Create the frame.
	 */
	public PatientInfoGUI() {
		connection=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 457);
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
		btnLogOut.setBounds(10, 384, 89, 23);
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
		scrollPane.setBounds(170, 43, 439, 290);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 44, 46, 14);
		contentPane.add(lblId);
		
		IDtext = new JTextField();
		IDtext.setBounds(66, 41, 86, 20);
		contentPane.add(IDtext);
		IDtext.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 75, 46, 14);
		contentPane.add(lblName);
		
		Nametext = new JTextField();
		Nametext.setBounds(66, 72, 86, 20);
		contentPane.add(Nametext);
		Nametext.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 106, 46, 14);
		contentPane.add(lblSurname);
		
		Surnametext = new JTextField();
		Surnametext.setBounds(66, 103, 86, 20);
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
					refreshTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 350, 89, 23);
		contentPane.add(btnSave);
		
		//update patient info to the database button by executing query
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String piquery="Update PatientsInfo set Name='"+Nametext.getText()+"',Surname='"+Surnametext.getText()+"' where ID='"+IDtext.getText()+"'";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					pipst.execute();
					//show confirmation message
					JOptionPane.showMessageDialog(null,"Patient Info Updated");
					pipst.close();
					refreshTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(109, 350, 89, 23);
		contentPane.add(btnUpdate);
		
		//delete patient info to the database button by executing query
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String piquery="Delete from PatientsInfo where ID='"+IDtext.getText()+"'";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					pipst.execute();
					//show confirmation message
					JOptionPane.showMessageDialog(null,"Patient Info Deleted");
					pipst.close();
					refreshTable();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(208, 350, 89, 23);
		contentPane.add(btnDelete);
	}
}
