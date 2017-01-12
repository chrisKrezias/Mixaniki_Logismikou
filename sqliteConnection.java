import java.sql.*;
import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class sqliteConnection { 	//connect to database script
	Connection conn;		//define connection
	public static Connection dbConnector(){ 	//connect to database script
		try{
			Class.forName("org.sqlite.JDBC");
			final Connection conn=DriverManager.getConnection("jdbc:sqlite:src/PatientsDatabase.sqlite");
			JOptionPane.showMessageDialog(null,"Connection Successful");
			return conn;
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
			return null;
		}
	}
	

	public static int save(Connection connections, String IDtext, String Nametext, String Surnametext, String Addresstext, String Teltext, String Birthtext, String mailtext) {
		// TODO Auto-generated method stub
		try{
			JOptionPane.showMessageDialog(null,"Patient Info Saved");
			final String piquery="insert into PatientsInfo (ID,Name,Surname,Address,Tel,Date,mail) values (?,?,?,?,?,?,?)";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			//read data from text fields
			pipst.setString(1,IDtext);
			pipst.setString(2,Nametext);
			pipst.setString(3,Surnametext);
			pipst.setString(4,Addresstext);
			pipst.setString(5,Teltext);
			pipst.setString(6,Birthtext);
			pipst.setString(7,mailtext);
			pipst.execute();
			//show confirmation message
			pipst.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		return 1;
	}


	public static int update(Connection connections, String IDtext, String Nametext, String Surnametext, String Addresstext, String Teltext, String Birthtext, String mailtext) {
		try {
			final String piquery="Update PatientsInfo set Name='"+Nametext+"',Surname='"+Surnametext+"',Address='"+Addresstext+"',Tel='"+Teltext+"',Date='"+Birthtext+"',mail='"+mailtext+"' where ID='"+IDtext+"'";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pipst.execute();
			//show confirmation message
			JOptionPane.showMessageDialog(null,"Patient Info Updated");
			pipst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}


	public static int delete(Connection connections, String IDtext) {
		try {
			final String piquery="Delete from PatientsInfo where ID='"+IDtext+"'";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pipst.execute();
			//show confirmation message
			JOptionPane.showMessageDialog(null,"Patient Info Deleted");
			pipst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 1;
	}


	public static int folderSave(Connection connections, String Entrytext, String IDtext, String Datetext, String Commenttext) {
		// TODO Auto-generated method stub
		try {
			final String piquery="insert into PatientFolder (Num,ID,Date,Comment) values (?,?,?,?)";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			//read data from text fields
			pipst.setString(1,Entrytext);
			pipst.setString(2,IDtext);
			pipst.setString(3,Datetext);
			pipst.setString(4,Commenttext);
			pipst.execute();
			//show confirmation message
			JOptionPane.showMessageDialog(null,"Patient Folder Info Saved");
			pipst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}


	public static int folderUpdate(Connection connections, String Entrytext, String IDtext, String Datetext, String Commenttext) {
		// TODO Auto-generated method stub
		try {
			final String piquery="Update PatientFolder set ID='"+IDtext+"',Date='"+Datetext+"',Comment='"+Commenttext+"' where Num='"+Entrytext+"'";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pipst.execute();
			//show confirmation message
			JOptionPane.showMessageDialog(null,"Patient Fodler Entry Updated");
			pipst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 1;
	}


	public static int folderDelete(Connection connections, String Entrytext) {
		// TODO Auto-generated method stub
		try {
			final String piquery="Delete from PatientFolder where Num='"+Entrytext+"'";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pipst.execute();
			//show confirmation message
			JOptionPane.showMessageDialog(null,"Patient Folder Entry Deleted");
			pipst.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 1;
	}


	public static int[] login(Connection connections, String NameText, String PasswordText) {
		// TODO Auto-generated method stub
		int array[]=new int[2];
		int prof=0;
		int count=0; //checking the database for unique, double or no info
		try{
			String loginquery="select * from StaffInfo where Username=? and Password=? ";
			PreparedStatement loginpst=connections.prepareStatement(loginquery);
			//read info from text fields
			loginpst.setString(1, NameText);
			loginpst.setString(2, PasswordText);
			ResultSet loginrs=loginpst.executeQuery(); 
			while(loginrs.next()){
				count++;
			}
			//actions for unique
			if (count==1){
				JOptionPane.showMessageDialog(null,"Username and password is correct");
				loginpst.close();
				loginquery="select Prof from StaffInfo where Username='"+NameText+"' and Password='"+PasswordText+"' ";
				loginpst=connections.prepareStatement(loginquery);
				loginrs=loginpst.executeQuery();
				prof=loginrs.getInt("Prof");
			}
			//actions for double
			else if(count>1){
				JOptionPane.showMessageDialog(null,"Duplicate username and password");
			}
			//action for non existent
			else{
				JOptionPane.showMessageDialog(null,"Username and password is not correct");
			}
			loginrs.close();
			loginpst.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}
		array[0]=prof;
		array[1]=count;
		return array;
	}


	public static ResultSet showFolder(Connection connections) {
		// TODO Auto-generated method stub
		ResultSet pirs = null;
		try {
			final String piquery="select * from PatientFolder";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pirs=pipst.executeQuery();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pirs;
	}


	public static ResultSet searchFolder(Connection connections, String IDtext) {
		// TODO Auto-generated method stub
		ResultSet pirs=null;
		try {
			final String piquery="select * from PatientFolder where ID='"+IDtext+"'";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pirs=pipst.executeQuery(); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pirs;
	}


	public static ResultSet showInfo(Connection connections) {
		// TODO Auto-generated method stub
		ResultSet pirs=null;
		try {
			final String piquery="select * from PatientsInfo";
			final PreparedStatement pipst=connections.prepareStatement(piquery);
			pirs=pipst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pirs;
	}


	public static ResultSet searchInfo(Connection connection, String IDtext, String Nametext, String Surnametext) {
		// TODO Auto-generated method stub
		ResultSet pirs=null;
		try {
			String piquery="select * from PatientsInfo where ID='"+IDtext+"' OR (Name='"+Nametext+"' AND Surname='"+Surnametext+"')";
			if (IDtext.isEmpty()){
				if (Nametext.isEmpty()){
					piquery="select * from PatientsInfo where Surname='"+Surnametext+"'";
				} else if(Surnametext.isEmpty()){
					piquery="select * from PatientsInfo where Name='"+Nametext+"'";
				}
			}
			final PreparedStatement pipst=connection.prepareStatement(piquery);
			pirs=pipst.executeQuery();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pirs;
	}


	public static int fillEntries(int indicator, Connection connections, int row, String AMKA, String Entry, String IDtext) {
		// TODO Auto-generated method stub
		if(indicator==0){
			try{
				final String piquery="select * from PatientsInfo where ID='"+AMKA+"'";
				final PreparedStatement pipst=connections.prepareStatement(piquery);
				final ResultSet pirs=pipst.executeQuery();
				while(pirs.next()){
					PatientInfoGUI.IDtext.setText(pirs.getString("ID"));
					PatientInfoGUI.Nametext.setText(pirs.getString("Name"));
					PatientInfoGUI.Surnametext.setText(pirs.getString("Surname"));
					PatientInfoGUI.Addresstext.setText(pirs.getString("Address"));
					PatientInfoGUI.Teltext.setText(pirs.getString("Tel"));
					PatientInfoGUI.Birthtext.setText(pirs.getString("Date"));
					PatientInfoGUI.mailtext.setText(pirs.getString("mail"));
				}
				pipst.close();
				PatientInfoGUI.Entrytext.setText("");
				PatientInfoGUI.Datetext.setText("dd/mm/yyyy");
				PatientInfoGUI.Commenttext.setText("");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else if(indicator==1){
			try{
				String piquery="select * from PatientFolder where Num='"+Entry+"'";
				PreparedStatement pipst=connections.prepareStatement(piquery);
				ResultSet pirs=pipst.executeQuery();
				while(pirs.next()){
					PatientInfoGUI.Entrytext.setText(pirs.getString("Num"));
					PatientInfoGUI.IDtext.setText(pirs.getString("ID"));
					PatientInfoGUI.Datetext.setText(pirs.getString("Date"));
					PatientInfoGUI.Commenttext.setText(pirs.getString("Comment"));
				}
				pipst.close();
				piquery="select * from PatientsInfo where ID='"+IDtext+"'";
				pipst=connections.prepareStatement(piquery);
				pirs=pipst.executeQuery();
				while(pirs.next()){
					PatientInfoGUI.Nametext.setText(pirs.getString("Name"));
					PatientInfoGUI.Surnametext.setText(pirs.getString("Surname"));
					PatientInfoGUI.Addresstext.setText(pirs.getString("Address"));
					PatientInfoGUI.Teltext.setText(pirs.getString("Tel"));
					PatientInfoGUI.Birthtext.setText(pirs.getString("Date"));
					PatientInfoGUI.mailtext.setText(pirs.getString("mail"));
				}
				pipst.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 1;
	}
}
