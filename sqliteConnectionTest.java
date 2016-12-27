import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class sqliteConnectionTest {
	
	static Connection connection=null;

	@BeforeClass
	public static void testSetup(){
	System.out.println("Starting...");
	connection=sqliteConnection.dbConnector();//make connection
	}
	
	@AfterClass
	public static void testCleanup(){
	System.out.println("Done!");
	}

	@Test
	public void testSave() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.save(connection, "1", "2", "3", "4", "5", "6", "7"));
	}

	@Test
	public void testUpdate() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.update(connection, "1", "2", "3", "4", "5", "6", "7"));
	}

	@Test
	public void testDelete() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.delete(connection, "1"));
	}

	@Test
	public void testFolderSave() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.folderSave(connection, "1", "2", "3", "4"));
	}

	@Test
	public void testFolderUpdate() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.folderUpdate(connection, "1", "2", "3", "4"));
	}

	@Test
	public void testFolderDelete() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.folderDelete(connection, "1"));
	}

	@Test
	public void testLogin() {
		sqliteConnection tester=new sqliteConnection();
		assertNotEquals("Result", null, tester.login(connection, "1", "1"));
	}

	@Test
	public void testShowFolder() {
		sqliteConnection tester=new sqliteConnection();
		assertNotEquals("Result", null, tester.showFolder(connection));
	}

	@Test
	public void testSearchFolder() {
		sqliteConnection tester=new sqliteConnection();
		assertNotEquals("Result", null, tester.searchFolder(connection,"1"));
	}

	@Test
	public void testShowInfo() {
		sqliteConnection tester=new sqliteConnection();
		assertNotEquals("Result", null, tester.showInfo(connection));
	}

	@Test
	public void testSearchInfo() {
		sqliteConnection tester=new sqliteConnection();
		assertNotEquals("Result", null, tester.searchInfo(connection, "1", "1", "1"));
	}

	@Test
	public void testFillEntries() {
		sqliteConnection tester=new sqliteConnection();
		assertEquals("Result", 1, tester.fillEntries(1, connection, 1, "1", "1", "1"));
	}

}
