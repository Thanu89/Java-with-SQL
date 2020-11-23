package org.com1028.ty00231;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.com1028.ty00231.dao.OnHoldDAO;
import org.junit.Test;

public class OnHoldDAOTest {
	
	protected Connection con;
	protected Statement statement;
	private final String db = "jdbc:mysql://localhost:3306/classicmodels?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";
	
	@Test
	public void test() throws SQLException {
		Map<Integer, String> shipStatusTest = new HashMap<Integer, String>();
		openConnectionTest();
		String query = "select customerNumber, status from orders where status like '%On Hold%'";
		Statement s = con.createStatement();
		ResultSet results = s.executeQuery(query);
		
		while (results.next()) {
			String shipTest = results.getString("status");
			int customerNumberTest = results.getInt("customerNumber");
			shipStatusTest.put(customerNumberTest, shipTest);
		}
		
		OnHoldDAO onHold = new OnHoldDAO();
		assertEquals(shipStatusTest, onHold.getStatus());
		onHold.closeConnection();
		closeConnectionTest();
	}
	
	public void openConnectionTest() {
		try {
			if (this.con == null || this.con.isClosed()) {
				this.con = DriverManager.getConnection(db, "root", "");
			}
			if (this.statement ==null || this.statement.isClosed()) {
				this.statement = this.con.createStatement();
			}
			} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void closeConnectionTest() {
		try {
			if (this.con != null) {
				this.con.close();
			}
			if (this.statement != null) {
				this.statement.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
