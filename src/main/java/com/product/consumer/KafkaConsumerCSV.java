package com.product.consumer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ResponseObject;
import com.product.utils.TextUtils;

@RestController
@RequestMapping("/consume")
/**
 * 
 * @author madhu.pudi Descriptions : This KafkaConsumer class is used to get
 *         data from topic , the out format is csv file
 */
public class KafkaConsumerCSV {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);	
	/**
	 * 
	 * @return Descriptions : This exportCSV method is used to transfer the data
	 *         from the topic to out put as CSV file.
	 * 
	 */
	@Autowired
	private TextUtils textUtils;
	@GetMapping(value = "/exportcsv", produces = "text/csv")
	public ResponseEntity<Object> exportCSV() {
		// setting the headers
		String[] csvHeader = { "client_information", "product_information", "total_transaction_amount" };
		InputStreamResource fileInputStream = null;
		HttpHeaders headers = null;
		List<ResponseObject> listResponseObject = textUtils.getProductDetails();
		int size = listResponseObject.size();
		logger.info("Size of the product details" +size);
		// to check data is available or not in TOPIC
		if (size > 0) {
			// write the data to CSV file
			ByteArrayInputStream byteArrayInputStream;
			try (ByteArrayOutputStream out = new ByteArrayOutputStream();
					// defining the CSV printer
					CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out),
							// withHeader is optional
							CSVFormat.DEFAULT.withHeader(csvHeader));) {
				// populating the CSV content
				for (ResponseObject record : listResponseObject)
					csvPrinter.printRecord(record.getClient_information(), record.getProduct_information(),
							record.getTotal_transaction_amount());

				// writing the underlying stream
				csvPrinter.flush();

				byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
			fileInputStream = new InputStreamResource(byteArrayInputStream);
			String csvFileName = "Output.csv";
			// setting HTTP headers
			headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
			// defining the custom Content-Type
			headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
			return new ResponseEntity<>(fileInputStream, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Data is not available in the TOPIC ,please call USE CASE  1", headers,
					HttpStatus.OK);
		}
	}

}
