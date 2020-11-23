package org.com1028.ty00231;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseQuery {
	
	private final String db = "jdbc:mysql://localhost:3306/classicmodels?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";
	protected Connection con;
	protected Statement statement;

	public BaseQuery(){
		try {
		    
			this.openConnection();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void openConnection() {
		try {
			if (this.con == null || this.con.isClosed()) {
				this.con = DriverManager.getConnection(db, "root", "");
			}
			if (this.statement ==null || this.statement.isClosed()) {
				this.statement = this.con.createStatement();
			}
			System.out.println("You have successfully connected to the database");
			} catch (SQLException e){
			System.out.println("There was an error connecting to the database.");
			throw new RuntimeException(e);
		}
	}
	
	public void closeConnection() {
		try {
			if (this.con != null) {
				this.con.close();
			}
			if (this.statement != null) {
				this.statement.close();
			}
			System.out.println("The connection to the database has been closed successfully");
		} catch (Exception e) {
			System.out.println("There was an error closing the connection to the database");
			throw new RuntimeException(e);
		}
	}
	

}