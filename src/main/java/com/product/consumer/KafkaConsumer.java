package com.product.consumer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ResponseObject;
import com.product.utils.TextUtils;

@RestController
@RequestMapping("/consume")
/**
 * 
 * @author madhu.pudi
 * Descriptions : This KafkaConsumer class is used to get data from topic , the out format is Json object
 */
public class KafkaConsumer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);	
/**
 * 
 * @return
 *  Descriptions : This exportCSV method  is used to  transfer the data from the topic  to out put as Json object.
 */
	@Autowired
	private TextUtils textUtils;
	@GetMapping("/message")
	public List<ResponseObject> receiveMessage() {	
		logger.info("receiveMessage method ()  KafkaConsumer class started ::");
		List<ResponseObject> listResponseObject = textUtils.getProductDetails();
		int size = listResponseObject.size();
		logger.info(" Size of the product details  ::"+size);
		logger.info("receiveMessage method ()  KafkaConsumer class ended ::");
		
					
		return listResponseObject;		    

	}

}