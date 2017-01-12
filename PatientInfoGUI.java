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

final public class PatientInfoGUI extends JFrame {
	
	Connection connection;
	static JTextField IDtext;
	static JTextField Nametext;
	static JTextField Surnametext;
	static JTextField Commenttext;
	static JTextField Datetext;
	static JTextField Entrytext;
	public int indicator;
	static JTextField Addresstext;
	static JTextField Teltext;
	static JTextField Birthtext;
	static JTextField mailtext;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public int prof = PatientsGUI.profint();
	
	final public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final PatientInfoGUI frame = new PatientInfoGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//function that uses "show info" code to refresh the table when an action is triggered
	public void refreshTable(){
		try {
			final String piquery="select * from PatientsInfo";
			final PreparedStatement pipst=connection.prepareStatement(piquery);
			final ResultSet pirs=pipst.executeQuery();
			//show data on JTable
			table.setModel(DbUtils.resultSetToTableModel(pirs));
			pipst.close();
			pirs.close();
			indicator=0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refreshFolderTable(){
		try {
			final String piquery="select * from PatientFolder";
			final PreparedStatement pipst=connection.prepareStatement(piquery);
			final ResultSet pirs=pipst.executeQuery(); 
			//show data on JTable
			table.setModel(DbUtils.resultSetToTableModel(pirs));
			pipst.close();
			pirs.close();
			indicator=1;
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
		
		final JLabel PatientInfo = new JLabel("Patient Info");
		PatientInfo.setBounds(10, 11, 66, 14);
		contentPane.add(PatientInfo);
		
		//log out from patient info window button
		final JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent arg0) {
				//close the patient info window
				contentPane.setVisible(false);
				dispose();
				//load the main application again
				//PatientsGUI.main(null);
			}
		});
		btnLogOut.setBounds(10, 557, 89, 23);
		contentPane.add(btnLogOut);
		
		//show patient info from the database button by executing query
		final JButton btnShowInfo = new JButton("Show Info");
		btnShowInfo.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent arg0) {
				ResultSet pirs=sqliteConnection.showInfo(connection);
				if(pirs!=null){
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					indicator=0;
				}
			}
		});
		btnShowInfo.setBounds(170, 7, 105, 23);
		contentPane.add(btnShowInfo);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(170, 43, 512, 469);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			final public void mouseClicked(MouseEvent arg0) {
				int row=table.getSelectedRow();
				String AMKA=(table.getModel().getValueAt(row, 0)).toString();
				String Entry=(table.getModel().getValueAt(row, 0)).toString();
				sqliteConnection.fillEntries(indicator, connection, row, AMKA, Entry, IDtext.getText());
			}
		});
		scrollPane.setViewportView(table);
		
		final JLabel lblId = new JLabel("\u0391\u039C\u039A\u0391");
		lblId.setBounds(10, 44, 46, 14);
		contentPane.add(lblId);
		
		IDtext = new JTextField();
		IDtext.setBounds(66, 41, 86, 20);
		contentPane.add(IDtext);
		IDtext.setColumns(10);
		
		final JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 75, 46, 14);
		contentPane.add(lblName);
		
		Nametext = new JTextField();
		Nametext.setBounds(66, 72, 86, 20);
		contentPane.add(Nametext);
		Nametext.setColumns(10);
		
		final JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 106, 46, 14);
		contentPane.add(lblSurname);
		
		Surnametext = new JTextField();
		Surnametext.setBounds(66, 103, 86, 20);
		contentPane.add(Surnametext);
		Surnametext.setColumns(10);
		
		//save patient info to the database button by executing query
		final JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent arg0) {
				sqliteConnection.save(connection,IDtext.getText(),Nametext.getText(),Surnametext.getText(),Addresstext.getText(),Teltext.getText(),Birthtext.getText(),mailtext.getText());
				refreshTable();
			}
		});
		btnSave.setBounds(10, 523, 89, 23);
		contentPane.add(btnSave);
		
		//update patient info to the database button by executing query
		final JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent arg0) {
				sqliteConnection.update(connection,IDtext.getText(),Nametext.getText(),Surnametext.getText(),Addresstext.getText(),Teltext.getText(),Birthtext.getText(),mailtext.getText());
				refreshTable();
			}
		});
		btnUpdate.setBounds(109, 523, 89, 23);
		contentPane.add(btnUpdate);
		
		//delete patient info to the database button by executing query
		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				final int action=JOptionPane.showConfirmDialog(null,"Delete confirmation","Delete",JOptionPane.YES_NO_OPTION);
				if (action==0){
					sqliteConnection.delete(connection,IDtext.getText());
					refreshTable();
				}
			}
		});
		btnDelete.setBounds(208, 523, 89, 23);
		contentPane.add(btnDelete);
		
		final JButton btnSearch = new JButton("Search Info");
		btnSearch.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				ResultSet pirs=sqliteConnection.searchInfo(connection, IDtext.getText(), Nametext.getText(), Surnametext.getText());
				if (pirs!=null){
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					indicator=0;
				}
			}
		});
		btnSearch.setBounds(285, 7, 113, 23);
		contentPane.add(btnSearch);
		
		final JLabel lblComment = new JLabel("Comment");
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
		
		final JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(10, 464, 46, 14);
		contentPane.add(lblDate);
		
		final JButton btnFolderSave = new JButton("Folder Save");
		btnFolderSave.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent arg0) {
				sqliteConnection.folderSave(connection,Entrytext.getText(),IDtext.getText(),Datetext.getText(),Commenttext.getText());
				refreshFolderTable();
			}
		});
		btnFolderSave.setBounds(323, 523, 113, 23);
		contentPane.add(btnFolderSave);
		
		final JButton btnFolderUpdate = new JButton("Folder Update");
		btnFolderUpdate.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				sqliteConnection.folderUpdate(connection,Entrytext.getText(),IDtext.getText(),Datetext.getText(),Commenttext.getText());
				refreshFolderTable();
			}
		});
		btnFolderUpdate.setBounds(446, 523, 113, 23);
		contentPane.add(btnFolderUpdate);
		
		final JButton btnShowFolder = new JButton("Show Folder");
		btnShowFolder.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				ResultSet pirs=sqliteConnection.showFolder(connection);
				if (pirs!=null){
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					indicator=1;
				}
			}
		});
		btnShowFolder.setBounds(446, 7, 113, 23);
		contentPane.add(btnShowFolder);
		
		final JButton btnSearchFolder = new JButton("Search Folder");
		btnSearchFolder.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				ResultSet pirs=sqliteConnection.searchFolder(connection, IDtext.getText());
				if (pirs!=null){
					table.setModel(DbUtils.resultSetToTableModel(pirs));
					indicator=1;
				}
			}
		});
		btnSearchFolder.setBounds(569, 7, 113, 23);
		contentPane.add(btnSearchFolder);
		
		final JButton btnFolderDelete = new JButton("Folder Delete");
		btnFolderDelete.addActionListener(new ActionListener() {
			final public void actionPerformed(ActionEvent e) {
				final int action=JOptionPane.showConfirmDialog(null,"Delete confirmation","Delete",JOptionPane.YES_NO_OPTION);
				if (action==0){
					sqliteConnection.folderDelete(connection,Entrytext.getText());
					refreshFolderTable();
				}
			}
		});
		btnFolderDelete.setBounds(569, 523, 113, 23);
		contentPane.add(btnFolderDelete);
		
		Entrytext = new JTextField();
		Entrytext.setBounds(66, 430, 86, 20);
		contentPane.add(Entrytext);
		Entrytext.setColumns(10);
		
		final JLabel lblNum = new JLabel("Num");
		lblNum.setBounds(10, 433, 46, 14);
		contentPane.add(lblNum);
		
		final JLabel lblPatientFolder = new JLabel("Patient Folder");
		lblPatientFolder.setBounds(10, 380, 89, 14);
		contentPane.add(lblPatientFolder);
		
		final JLabel lblMaxNum = new JLabel("Max Num");
		lblMaxNum.setBounds(10, 405, 54, 14);
		contentPane.add(lblMaxNum);
		
		final JLabel lblShownum = new JLabel("ShowNum");
		lblShownum.setBounds(74, 405, 78, 14);
		try {
			final String maxquery="SELECT MAX(Num) AS Max_Num FROM PatientFolder";
			final PreparedStatement maxpst=connection.prepareStatement(maxquery);
			final ResultSet maxrs=maxpst.executeQuery();
			int id2 = -1;
			if (maxrs.next()) {
			   id2 = maxrs.getInt("Max_Num");  
			}
			lblShownum.setText(String.valueOf(id2));
			//show confirmation message
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		contentPane.add(lblShownum);
		
		Addresstext = new JTextField();
		Addresstext.setBounds(66, 134, 86, 20);
		contentPane.add(Addresstext);
		Addresstext.setColumns(10);
		
		Teltext = new JTextField();
		Teltext.setBounds(66, 165, 86, 20);
		contentPane.add(Teltext);
		Teltext.setColumns(10);
		
		Birthtext = new JTextField();
		Birthtext.setText("dd/mm/yyyy");
		Birthtext.setBounds(66, 196, 86, 20);
		contentPane.add(Birthtext);
		Birthtext.setColumns(10);
		
		mailtext = new JTextField();
		mailtext.setBounds(66, 227, 86, 20);
		contentPane.add(mailtext);
		mailtext.setColumns(10);
		
		final JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 137, 46, 14);
		contentPane.add(lblAddress);
		
		final JLabel lblTel = new JLabel("Tel");
		lblTel.setBounds(10, 168, 46, 14);
		contentPane.add(lblTel);
		
		final JLabel lblBirth = new JLabel("Birth");
		lblBirth.setBounds(10, 199, 46, 14);
		contentPane.add(lblBirth);
		
		final JLabel lblMail = new JLabel("mail");
		lblMail.setBounds(10, 230, 46, 14);
		contentPane.add(lblMail);
		
		if(prof!=1){
			btnSave.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
			btnFolderUpdate.setEnabled(false);
			btnFolderDelete.setEnabled(false);
			if (prof!=2){
				btnFolderSave.setEnabled(false);
			}
		}
		
	}
}
