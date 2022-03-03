package com.product.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import com.product.constant.ApplicationConstant;
import com.product.dto.RequestResponseObject;
import com.product.dto.ResponseObject;

/**
 * 
 * @author madhu.pudi 
 * 
 * Description : this class is used for parsing the given
 *         input file and calculate the client information , Product information and total Transaction amount
 */
@Component
public class TextUtils {
	private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);
	@Autowired
	private ConcurrentKafkaListenerContainerFactory<String, ResponseObject> factory;

	/**
	 * 
	 * @return
	 */
	public List<RequestResponseObject> inputTextParse() throws FileNotFoundException {
		logger.info("Started inputTextParse method() ::" );
		Scanner scanner = null;
		List<RequestResponseObject> listRequestObject = null;
		RequestResponseObject requestResponseObject = null;
		try {
			listRequestObject = new ArrayList<>();
			scanner = new Scanner(new File("Input.txt"));
			while (scanner.hasNext()) {
				requestResponseObject = new RequestResponseObject();
				String nextLine = scanner.nextLine();
				if (null != nextLine) {
					requestResponseObject.setRequest_Contents(nextLine);
					requestResponseObject.setClient_information(client_information(nextLine));
					requestResponseObject.setProduct_information(product_information(nextLine));
					requestResponseObject.setTotal_transaction_amount(total_transaction_amount(nextLine));
					requestResponseObject.setClient_number(client_number(nextLine));
					requestResponseObject.setProduct_group_code(product_group_code(nextLine));
					listRequestObject.add(requestResponseObject);
					
				}
			}

		} catch (FileNotFoundException fnfe) {
			logger.debug("FileNotFoundException in inputTextParse method() ::" + fnfe.getMessage());
		} catch (Exception e) {
			logger.debug("Exception in inputTextParse method()::" + e.getMessage());
		} finally {
			if (null != scanner) {
				scanner.close();
			}			
			if (null != requestResponseObject) {
				requestResponseObject = null;
			}
		}

		return listRequestObject;
	}

	/**
	 * 
	 * @param nextLine
	 * @return as String  of client_information
	 * Description : This method take the input as String and
	 *         calculate the client information from input string
	 *calculation rules : Client_Information should be a combination of the CLIENT TYPE, CLIENT NUMBER, ACCOUNT NUMBER, SUBACCOUNT NUMBER
	 */
	private String client_information(final String nextLine) {
		String client_information = "";
		if (null != nextLine) {
			client_information = nextLine.substring(3, 19).trim();
			logger.info("Client information  ::" + client_information);
		}
		return client_information;

	}

	/**
	 * 
	 * @param nextLine
	 * @return as String of  product_group_code
	 * 
	 * Description : This method take the input as String and
	 *         calculate the product information from given input string
	 * calculation rules : Product_Information should be a PRODUCT GROUP CODE
	 */
	private String product_group_code(final String nextLine) {
		String product_group_code = "";
		if (null != nextLine) {
			product_group_code = nextLine.substring(24,27).trim();
			logger.info("product_group_code  ::" + product_group_code);
		}
		return product_group_code;

	}

	/**
	 * 
	 * @param nextLine
	 * @return as double of  total_transaction_amount
	 * Description : This method take the input as String and
	 *         calculate the client information from input string
	 * calculation rules : Total_Transaction_Amount should be a Net Total of the (QUANTITY LONG - QUANTITY SHORT)
	 */
	private double total_transaction_amount(final String nextLine) {
		double total_transaction_amount = 0.00;
		if (null != nextLine) {
			
			total_transaction_amount = (Double.parseDouble(nextLine.substring(52, 62))
					- Double.parseDouble(nextLine.substring(63, 73)));
			logger.info("total_transaction_amount  ::" + total_transaction_amount);
		}
		return total_transaction_amount;

	}
	
	/**
	 * 
	 * @param nextLine
	 * @return as String  of client_number
	 * Description : This method take the input as String and
	 *         calculate the client information from input string
	 *calculation rules :client_number should be a CLIENT NUMBER
	 */
	private String client_number(final String nextLine) {
		String client_number = "";
		if (null != nextLine) {
			client_number = nextLine.substring(7, 11).trim();
			logger.info("Client Number  ::" + client_number);
		}
		return client_number;

	}

	/**
	 * 
	 * @param nextLine
	 * @return as String of  product_information
	 * 
	 * Description : This method take the input as String and
	 *         calculate the product information from given input string
	 * calculation rules : Product_Information should be a combination of the EXCHANGE CODE, PRODUCT GROUP CODE, SYMBOL, EXPIRATION DATE
	 */
	private String product_information(final String nextLine) {
		String product_information = "";
		if (null != nextLine) {
			product_information = nextLine.substring(27, 31) + nextLine.substring(25, 27)
					+ nextLine.substring(31, 45).trim();
			logger.info("product_information  ::" + product_information);
		}
		return product_information;

	}	
	
/**
 * 
 * @return list of  ResponseObject
 * Descriptions : This get the list of  product details from the TOPIC of Kafka 
 */
	public List<ResponseObject> getProductDetails() {
		List<ResponseObject>  listResponseObject = null ;
		ConsumerFactory<String, ResponseObject> consumerFactory = null;
		Consumer<String, ResponseObject> consumer = null;
		logger.info("Topic Name  in  TextUtils ::" + ApplicationConstant.PRODUCT_TOPIC_NAME);
		try { // retrieving the data from Topic
			listResponseObject = new ArrayList<>();
			consumerFactory = factory.getConsumerFactory();
			consumer = consumerFactory.createConsumer();
			consumer.subscribe(Arrays.asList(ApplicationConstant.PRODUCT_TOPIC_NAME));
			ConsumerRecords<String, ResponseObject> consumerRecords = consumer.poll(10000);
			Iterable<ConsumerRecord<String, ResponseObject>> records = consumerRecords
					.records(ApplicationConstant.PRODUCT_TOPIC_NAME);
			Iterator<ConsumerRecord<String, ResponseObject>> iterator = records.iterator();
			while (iterator.hasNext()) {
				listResponseObject.add(iterator.next().value());
			}
		}catch(Exception e) {
	    	logger.debug("Excpetion in KafkaConsumerCSV class ::" + e.getMessage());
	    	}finally {	
				if (null != consumerFactory) {
					consumerFactory = null;				
				}
				if (null != consumer) {
					consumer.close();				
				}
	    	} 
		return listResponseObject;
	}
	

}
