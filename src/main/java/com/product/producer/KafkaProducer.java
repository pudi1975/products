package com.product.producer;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.constant.ApplicationConstant;
import com.product.dto.RequestResponseObject;
import com.product.dto.ResponseObject;
import com.product.repository.ProductRepository;
import com.product.utils.TextUtils;

@RestController
@RequestMapping("/produce")
public class KafkaProducer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Autowired
	private TextUtils textUtilsObject;
	@Autowired
	private ResponseObject responseObject;
	@Autowired
	ProductRepository productRepository;
	@PostMapping("/{message}")
	public String sendMessage(@PathVariable String message) {		
		try {
			logger.info("sendMessage method ()  KafkaProducer class started ::");					
			List<RequestResponseObject> listRequestResponseObject = textUtilsObject.inputTextParse();
			int size = listRequestResponseObject.size();
			logger.info("listRequestResponseObject  length ::"+size);			
			 for (RequestResponseObject requestResponseObject : listRequestResponseObject) {				 
		        	responseObject.setClient_information(requestResponseObject.getClient_information());
		        	responseObject.setProduct_information(requestResponseObject.getProduct_information());
		        	responseObject.setTotal_transaction_amount(requestResponseObject.getTotal_transaction_amount());
		        	responseObject.setClient_number(requestResponseObject.getClient_number());
		        	responseObject.setProduct_group_code(requestResponseObject.getProduct_group_code());
		        	kafkaTemplate.send(ApplicationConstant.PRODUCT_TOPIC_NAME, responseObject);
		        }
			// send the products information to postgresSQL data base
	        productRepository.addListofProducts(listRequestResponseObject);
			return " messages are sent succuessfully for kafka topic";		
			
		} catch (FileNotFoundException fnfe) {
			logger.debug("FileNotFoundException in KafkaProducer ::" + fnfe.getMessage());
			return "error ocquired while sending message to   kafka topic ";
		} catch (Exception e) {
			logger.info(" Exception raised in KafkaProducer class::" + e);
			return "error ocquired while sending message to   kafka topic ";
			
		}	
		
		
	}


}