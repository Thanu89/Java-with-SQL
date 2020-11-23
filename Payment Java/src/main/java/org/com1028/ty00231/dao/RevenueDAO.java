package org.com1028.ty00231.dao;


import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.com1028.ty00231.BaseQuery;
import org.com1028.ty00231.BuyPrice;
import org.com1028.ty00231.MSRP;
import org.com1028.ty00231.ProductName;

public class RevenueDAO extends BaseQuery{
	
	public RevenueDAO() {
		openConnection();
	}
	
	public Map<String, String> getData() {
		List<ProductName> pName = new ArrayList<ProductName>();
		List<MSRP> msRP = new ArrayList<MSRP>();
		List<BuyPrice> bPrice = new ArrayList<BuyPrice>();
		List<String> revenue = new ArrayList<String>();
		Map<String, String> List = new HashMap<String, String>();
		Map<String, String> orderedList = new TreeMap<String, String>(List);
		DecimalFormat df = new DecimalFormat("#.00");
		
		try {
			String query = "select * from products"; 
			Statement s = con.createStatement();
			ResultSet results = s.executeQuery(query);
			
			while (results.next()) {
				String productName = results.getString("productName").toString();
				pName.add(new ProductName(productName));
				double msrp = results.getDouble("MSRP");
				msRP.add(new MSRP(msrp));
				double buyPrice = results.getDouble("buyPrice");
				bPrice.add(new BuyPrice(buyPrice));
			}			
			double rev = 0.0;
			int count1 = 0;
			int count2 = 0;
			
			while (count1 < 110) {
				MSRP msrp = msRP.get(count1);
				BuyPrice buyprice = bPrice.get(count1);
				rev = msrp.getMSRP() - buyprice.getBuyPrice();
				String revF = df.format(rev);
				revenue.add(revF);
				count1 = count1 + 1;
			}
			
			while (count2 < 110) {
				ProductName product = pName.get(count2);
				String Revenue = revenue.get(count2);
				orderedList.put(product.getProductName(), Revenue);
				count2 = count2 + 1;
			}

		} catch (Exception e) {
			System.out.println("There was an error getting the data from the database");
			throw new RuntimeException(e);
		}
		return orderedList;
	}
}
