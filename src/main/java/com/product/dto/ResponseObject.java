package com.product.dto;

import org.springframework.stereotype.Component;

@Component
public class ResponseObject {
	private String client_number;
	private String product_group_code;
	private String client_information;
	private String product_information;
	private double total_transaction_amount;
	
	/**
	 * @return the product_group_code
	 */
	public String getProduct_group_code() {
		return product_group_code;
	}
	/**
	 * @param product_group_code the product_group_code to set
	 */
	public void setProduct_group_code(String product_group_code) {
		this.product_group_code = product_group_code;
	}
	/**
	 * @return the client_information
	 */
	public String getClient_information() {
		return client_information;
	}
	/**
	 * @param client_information the client_information to set
	 */
	public void setClient_information(String client_information) {
		this.client_information = client_information;
	}
	/**
	 * @return the product_information
	 */
	public String getProduct_information() {
		return product_information;
	}
	/**
	 * @param product_information the product_information to set
	 */
	public void setProduct_information(String product_information) {
		this.product_information = product_information;
	}
	/**
	 * @return the total_transaction_amount
	 */
	public double getTotal_transaction_amount() {
		return total_transaction_amount;
	}
	/**
	 * @param total_transaction_amount the total_transaction_amount to set
	 */
	public void setTotal_transaction_amount(double total_transaction_amount) {
		this.total_transaction_amount = total_transaction_amount;
	}
	/**
	 * @return the client_number
	 */
	public String getClient_number() {
		return client_number;
	}
	/**
	 * @param client_number the client_number to set
	 */
	public void setClient_number(String client_number) {
		this.client_number = client_number;
	}
	
	@Override
    public String toString()
    {
        return "ResponseObject [client_information=" + client_information + ", product_information=" + product_information
                + ", total_transaction_amount=" + total_transaction_amount + ", product_group_code=" + product_group_code +",client_number=" + client_number + "]";
       
    }

	

}
