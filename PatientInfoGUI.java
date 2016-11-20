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
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				PatientsGUI.main(null);
			}
		});
		btnLogOut.setBounds(10, 302, 89, 23);
		contentPane.add(btnLogOut);
		
		JButton btnShowInfo = new JButton("Show Info");
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String piquery="select * from PatientsInfo";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(pirs));
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
	}
}
