package org.com1028.ty00231.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.com1028.ty00231.BaseQuery;
import org.com1028.ty00231.CustomerNumbers;
import org.com1028.ty00231.Status;

public class OnHoldDAO extends BaseQuery{
	
	public OnHoldDAO() {
		openConnection();
	}
	
	public Map<Integer, String> getStatus() {
		Map<CustomerNumbers, Status> shipStatus = new HashMap<CustomerNumbers, Status>();
		Map<Integer, String> onHoldStatus = new HashMap<Integer, String>();
		try {
			String query = "select * from orders"; 
			Statement s = con.createStatement();
			ResultSet results = s.executeQuery(query);
			
			while (results.next()) {
				if (results.getString("status").compareTo("On Hold") == 0) {
					String ship = results.getString("status").toString();
					int customerNumber = results.getInt("customerNumber");
					shipStatus.put(new CustomerNumbers(customerNumber), new Status(ship));
					//System.out.println(customerNumber + ", " + ship);
				}
			}
			for (CustomerNumbers cNumber : shipStatus.keySet()) {
				for (Status status : shipStatus.values()) {
					if (status.getShipStatus().equals("On Hold")) {
						onHoldStatus.put(cNumber.getCustomerNumber(), status.getShipStatus());
						//System.out.println(cNumber.getCustomerNumber() + ", " + status.getShipStatus());
					}
				}
			}

		} catch (Exception e) {
			System.out.println("There was an error getting the data from the database");
			throw new RuntimeException(e);
		}
		return onHoldStatus;	
	}
	
}
