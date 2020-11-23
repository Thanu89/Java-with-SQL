package org.com1028.ty00231.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.com1028.ty00231.Payments;
import org.com1028.ty00231.BaseQuery;

public class PaymentsDAO extends BaseQuery {
	
	public PaymentsDAO() {
		openConnection();
	}
	
	public List<Payments> getPayments() {
		ArrayList<Payments> payments = new ArrayList<Payments>();
		
		try {
			String query = "select * from payments"; 
			Statement s = con.createStatement();
			ResultSet results = s.executeQuery(query);
			
			while (results.next()) {
				Double amount = results.getDouble("amount");
				payments.add(new Payments(amount));
				//System.out.println(amount);
			}
		} catch (Exception e) {
			System.out.println("There was an error getting the data from the database");
			throw new RuntimeException(e);
		}
		return payments;
	}
	
	public List<Double> averageCalc() {
		List<Payments> payments = getPayments();
		List<Double> aboveAvg = new ArrayList<Double>(); 	
		int count1 = 0;
		double total = 0.0;
		double average = 0.0;	
		for (Payments amount : payments) {
			total = total + amount.getAmount();
			count1 = count1 + 1;
		}
		average = total/count1;
		//System.out.println("The average is: " + average);
		for (Payments pay : payments) {
			if (pay.getAmount() > (2 * average)) {
				aboveAvg.add(pay.getAmount());
				//System.out.println(pay.getAmount());
			}
		}
		return aboveAvg;
	}
	
}
