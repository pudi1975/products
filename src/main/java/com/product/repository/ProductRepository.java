package com.product.repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.product.constant.SQLConstant;
import com.product.dto.ClientNumberObject;
import com.product.dto.ProductObject;
import com.product.dto.RequestResponseObject;
@Repository
public class ProductRepository {
	private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);
	@Autowired
    JdbcTemplate jdbcTemplate;   
/**
 * 
 * @param requestResponseObject
 * @throws SQLException
 * Descriptions this method is used to add the products details for transactions  
 */    
    public void addListofProducts(List<RequestResponseObject> requestResponseObject)  throws SQLException {
        String insertSql = SQLConstant.SQL_INSERT;
        logger.info("Insert SQL ::"+ insertSql);
        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
            	RequestResponseObject requestObject = requestResponseObject.get(i);
                ps.setString(1, requestObject.getClient_number());
                ps.setString(2, requestObject.getClient_information());
                ps.setString(3, requestObject.getProduct_group_code());                
                ps.setString(4, requestObject.getProduct_information());
                ps.setDouble(5,requestObject.getTotal_transaction_amount());
                ps.setString(6,requestObject.getRequest_Contents());
            }

            @Override
            public int getBatchSize() {
            	int objectSize = requestResponseObject.size();
            	logger.info("objectSize in addListofProducts ::"+ objectSize);
                return objectSize;
            }

        });
    }
    
    /**
     * 
     * @param productCode
     * @return type is  ProductObject 
     * Descriptions : this method will return the list of products for given productCode
     */
    public List<ProductObject> getProductList(final String productCode) {
    	String sqlProductSelect = SQLConstant.SQL_SELECT_PRODUCTS;
        logger.info("SQL ProductSelect  ::"+ sqlProductSelect);
        logger.info("productCode ::"+ productCode);
        List<ProductObject> listProductObject = null;
        ProductObject product = null;
        try {
			 listProductObject = new ArrayList<ProductObject>();
			 List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlProductSelect, new Object[]{productCode});
        for (Map<String, Object> row : rows) {
        	product = new ProductObject();	
			product.setClient_number(String.valueOf(row.get("CLIENTNUMBER")));
			product.setClient_information(String.valueOf(row.get("CLIENTINFORMATION")));
			product.setProduct_information(String.valueOf(row.get("PRODUCTINFORMATION")));			
			product.setTotal_transaction_amount(Double.parseDouble(String.valueOf(row.get("TOTALTRANSACTIONAMOUNT"))));
			listProductObject.add(product);		
        }
	    } catch (Exception ex) {
	    	logger.debug("SQL Exception in getProductList" + ex.getMessage());
	    	}finally {	    		
	    			if (null != product) {
	    				product = null;
	    			}			
	    			
	    		}
        return listProductObject;
	    	}
   
/**
 *     
 * @param clientNumber
 * @return type is  ClientNumberObject 
 * Descriptions : this method will return the list of products for given client number 
 */
    public List<ClientNumberObject> getProductListByClient(final String clientNumber) {
    	String sqlClientSelect = SQLConstant.SQL_SELECT_CLIENTNUMBER;
        logger.info("SQL Client Number Select  ::"+ sqlClientSelect);
        logger.info("clientNumber ::"+ clientNumber);
    	 List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlClientSelect, new Object[]{clientNumber});
         List<ClientNumberObject> listClientNumberObject = null;
         ClientNumberObject clientNumberObject = null;
         try {
        	 listClientNumberObject = new ArrayList<ClientNumberObject>();

         for (Map<String, Object> row : rows) {
        	 clientNumberObject = new ClientNumberObject();	
        	 clientNumberObject.setProduct_group_code(String.valueOf(row.get("PRODUCTGROUPCODE")));
        	 clientNumberObject.setClient_information(String.valueOf(row.get("CLIENTINFORMATION")));
        	 clientNumberObject.setProduct_information(String.valueOf(row.get("PRODUCTINFORMATION")));			
        	 clientNumberObject.setTotal_transaction_amount(Double.parseDouble(String.valueOf(row.get("TOTALTRANSACTIONAMOUNT"))));
        	 listClientNumberObject.add(clientNumberObject);		
         }
 	    } catch (Exception ex) {
 	    	logger.debug("SQL Exception in getProductListByClient" + ex.getMessage());
 	    	}finally {	    		
 	    			if (null != clientNumberObject) {
 	    				clientNumberObject = null;
 	    			}			
 	    			
 	    		}
         return listClientNumberObject;
 	    	}
	

}
