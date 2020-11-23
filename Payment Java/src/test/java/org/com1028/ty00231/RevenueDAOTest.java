package org.com1028.ty00231;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import org.com1028.ty00231.dao.RevenueDAO;
import org.junit.Test;

public class RevenueDAOTest {
	
	protected Connection con;
	protected Statement statement;
	private final String db = "jdbc:mysql://localhost:3306/classicmodels?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";
	
	@Test
	public void test() throws SQLException{
		Map<String, String> orderedListTest = new TreeMap<String, String>();
		openConnectionTest();
		String query = "select productName, MSRP - buyPrice as revenue from products";
		Statement s = con.createStatement();
		ResultSet results = s.executeQuery(query);
		
		while (results.next()) {
			String product = results.getString("productName");
			String revenue = results.getString("revenue");
			orderedListTest.put(product, revenue);
		}	
		RevenueDAO revenue = new RevenueDAO();
		assertEquals(orderedListTest, revenue.getData());	
		revenue.closeConnection();
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
