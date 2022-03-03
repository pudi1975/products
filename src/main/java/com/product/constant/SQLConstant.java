package com.product.constant;

public class SQLConstant {
	// SQL Constants
		public static final String SQL_INSERT = "INSERT INTO PRODUCTS(clientNumber, clientInformation, productGroupCode, productInformation, totalTransactionAmount,requestContents) values(?, ?, ?, ?, ?, ?)";
		public static final String SQL_SELECT_CLIENTNUMBER= "SELECT PRODUCTGROUPCODE,CLIENTINFORMATION,  PRODUCTINFORMATION, TOTALTRANSACTIONAMOUNT FROM  PRODUCTS WHERE clientNumber =?";
		public static final String SQL_SELECT_PRODUCTS = "SELECT CLIENTNUMBER,CLIENTINFORMATION,  PRODUCTINFORMATION, TOTALTRANSACTIONAMOUNT FROM  PRODUCTS   WHERE productGroupCode = ?";
		

}
