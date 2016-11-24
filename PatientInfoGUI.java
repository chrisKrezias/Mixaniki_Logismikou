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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JTextField Commenttext;
	private JTextField Datetext;
	private JTextField Entrytext;
	
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
	
	public void refreshFolderTable(){
		try {
			String piquery="select * from PatientFolder";
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
		setBounds(100, 100, 708, 630);
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
		btnLogOut.setBounds(10, 557, 89, 23);
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
		btnShowInfo.setBounds(170, 7, 105, 23);
		contentPane.add(btnShowInfo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 43, 512, 469);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					int row=table.getSelectedRow();
					String AMKA=(table.getModel().getValueAt(row, 0)).toString();
					String piquery="select * from PatientsInfo where ID='"+AMKA+"'";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					while(pirs.next()){
						IDtext.setText(pirs.getString("ID"));
						Nametext.setText(pirs.getString("Name"));
						Surnametext.setText(pirs.getString("Surname"));
					}
					pipst.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
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
		btnSave.setBounds(10, 523, 89, 23);
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
		btnUpdate.setBounds(109, 523, 89, 23);
		contentPane.add(btnUpdate);
		
		//delete patient info to the database button by executing query
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action=JOptionPane.showConfirmDialog(null,"Delete confirmation","Delete",JOptionPane.YES_NO_OPTION);
				if (action==0){
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
			}
		});
		btnDelete.setBounds(208, 523, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnSearch = new JButton("Search Info");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String piquery="select * from PatientsInfo where ID='"+IDtext.getText()+"' OR (Name='"+Nametext.getText()+"' AND Surname='"+Surnametext.getText()+"')";
					if (IDtext.getText().isEmpty()){
						if (Nametext.getText().isEmpty()){
							piquery="select * from PatientsInfo where Surname='"+Surnametext.getText()+"'";
						} else if(Surnametext.getText().isEmpty()){
							piquery="select * from PatientsInfo where Name='"+Nametext.getText()+"'";
						}
					}
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					//show data on JTable
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					pipst.close();
					pirs.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(285, 7, 113, 23);
		contentPane.add(btnSearch);
		
		JLabel lblComment = new JLabel("Comment");
		lblComment.setBounds(10, 495, 46, 14);
		contentPane.add(lblComment);
		
		Commenttext = new JTextField();
		Commenttext.setBounds(66, 492, 86, 20);
		contentPane.add(Commenttext);
		Commenttext.setColumns(10);
		
		Datetext = new JTextField();
		Datetext.setText("dd/mm/yyyy");
		Datetext.setBounds(66, 461, 86, 20);
		contentPane.add(Datetext);
		Datetext.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(10, 464, 46, 14);
		contentPane.add(lblDate);
		
		JButton btnFolderSave = new JButton("Folder Save");
		btnFolderSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String piquery="insert into PatientFolder (Num,ID,Date,Comment) values (?,?,?,?)";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					//read data from text fields
					pipst.setString(1,Entrytext.getText());
					pipst.setString(2,IDtext.getText());
					pipst.setString(3,Datetext.getText());
					pipst.setString(4,Commenttext.getText());
					pipst.execute();
					//show confirmation message
					JOptionPane.showMessageDialog(null,"Patient Folder Info Saved");
					pipst.close();
					refreshFolderTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnFolderSave.setBounds(323, 523, 113, 23);
		contentPane.add(btnFolderSave);
		
		JButton btnFolderUpdate = new JButton("Folder Update");
		btnFolderUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String piquery="Update PatientFolder set ID='"+IDtext.getText()+"',Date='"+Datetext.getText()+"',Comment='"+Commenttext.getText()+"' where Num='"+Entrytext.getText()+"'";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					pipst.execute();
					//show confirmation message
					JOptionPane.showMessageDialog(null,"Patient Fodler Entry Updated");
					pipst.close();
					refreshFolderTable();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnFolderUpdate.setBounds(446, 523, 113, 23);
		contentPane.add(btnFolderUpdate);
		
		JButton btnShowFolder = new JButton("Show Folder");
		btnShowFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String piquery="select * from PatientFolder";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					//show data on JTable
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					pipst.close();
					pirs.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnShowFolder.setBounds(446, 7, 113, 23);
		contentPane.add(btnShowFolder);
		
		JButton btnSearchFolder = new JButton("Search Folder");
		btnSearchFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String piquery="select * from PatientFolder where ID='"+IDtext.getText()+"'";
					PreparedStatement pipst=connection.prepareStatement(piquery);
					ResultSet pirs=pipst.executeQuery();
					//show data on JTable
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					pipst.close();
					pirs.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSearchFolder.setBounds(569, 7, 113, 23);
		contentPane.add(btnSearchFolder);
		
		JButton btnFolderDelete = new JButton("Folder Delete");
		btnFolderDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action=JOptionPane.showConfirmDialog(null,"Delete confirmation","Delete",JOptionPane.YES_NO_OPTION);
				if (action==0){
					try {
						String piquery="Delete from PatienFolder where Num='"+Entrytext.getText()+"'";
						PreparedStatement pipst=connection.prepareStatement(piquery);
						pipst.execute();
						//show confirmation message
						JOptionPane.showMessageDialog(null,"Patient Folder Entry Deleted");
						pipst.close();
						refreshFolderTable();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnFolderDelete.setBounds(569, 523, 113, 23);
		contentPane.add(btnFolderDelete);
		
		Entrytext = new JTextField();
		Entrytext.setBounds(66, 430, 86, 20);
		contentPane.add(Entrytext);
		Entrytext.setColumns(10);
		
		JLabel lblNum = new JLabel("Num");
		lblNum.setBounds(10, 433, 46, 14);
		contentPane.add(lblNum);
		
		JLabel lblPatientFolder = new JLabel("Patient Folder");
		lblPatientFolder.setBounds(10, 405, 89, 14);
		contentPane.add(lblPatientFolder);
	}
}
