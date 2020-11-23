package org.com1028.ty00231;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.com1028.ty00231.dao.PaymentsDAO;
import org.junit.Test;

public class PaymentsDAOTest {
	protected Connection con;
	protected Statement statement;
	private final String db = "jdbc:mysql://localhost:3306/classicmodels?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT";

	@Test
	public void testShowAboveAvgPayments() throws SQLException {
		ArrayList<Double> paymentsTest = new ArrayList<Double>();
		openConnectionTest();
		String query = "select amount from payments where amount > 64863.291062";
		Statement s = con.createStatement();
		ResultSet results = s.executeQuery(query);
			
		while (results.next()) {
			Double amountTest = results.getDouble("amount");
			paymentsTest.add(amountTest);
		}
		PaymentsDAO payment = new PaymentsDAO();
		assertEquals(paymentsTest, payment.averageCalc());
		payment.closeConnection();
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

