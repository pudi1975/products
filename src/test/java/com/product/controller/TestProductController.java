package com.product.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.product.dto.ClientNumberObject;
import com.product.dto.ProductObject;
import com.product.repository.ProductRepository;


public class TestProductController {
	@InjectMocks
	ProductController productController;
     
    @Mock
    ProductRepository productRepository;
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     
    @Test
    public void getProductListTest()   {
    	
    	List<ProductObject> list = new ArrayList<ProductObject>();
    	ProductObject productObject = new ProductObject();
    	productObject.setClient_information("CL  432100020001");
    	productObject.setClient_number("4321");
    	productObject.setProduct_information("SGX FUNK    20100910");
    	productObject.setTotal_transaction_amount(1.0);
    	
    	ProductObject productObject1 = new ProductObject();
    	productObject.setClient_information("CL  432100020001");
    	productObject.setClient_number("4321");    	
    	productObject.setProduct_information("SGX FUNK    20100910");
    	productObject.setTotal_transaction_amount(1.0);
    	
    	ProductObject productObject2 = new ProductObject();
    	productObject.setClient_information("CL  432100020001");
    	productObject.setClient_number("4321");
    	productObject.setProduct_information("SGX FUNK    20100910");
    	productObject.setTotal_transaction_amount(1.0);
    	
        list.add(productObject);
        list.add(productObject1);
        list.add(productObject2);
       
         
        when(productRepository.getProductList("")).thenReturn(list);
         
        //test
        List<ProductObject> empList = productController.getProductList("");
         
        assertEquals(3, empList.size());
        verify(productRepository, times(1)).getProductList("");
    }
     
    @Test
    public void getProductListByClientTest()   {
    	
    	List<ClientNumberObject> list = new ArrayList<ClientNumberObject>();
    	
    	ClientNumberObject clientNumberObject = new ClientNumberObject();
    	clientNumberObject.setClient_information("CL  432100020001");
    	clientNumberObject.setProduct_group_code("FU");
    	clientNumberObject.setProduct_information("SGX FUNK    20100910");
    	clientNumberObject.setTotal_transaction_amount(1.0);
    	
    	ClientNumberObject clientNumberObject1 = new ClientNumberObject();
    	clientNumberObject1.setClient_information("CL  432100020001");
    	clientNumberObject1.setProduct_group_code("FU");    	
    	clientNumberObject1.setProduct_information("SGX FUNK    20100910");
    	clientNumberObject1.setTotal_transaction_amount(1.0);
    	
    	ClientNumberObject clientNumberObject2 = new ClientNumberObject();
    	clientNumberObject2.setClient_information("CL  432100020001");
    	clientNumberObject2.setProduct_group_code("FU");
    	clientNumberObject2.setProduct_information("SGX FUNK    20100910");
    	clientNumberObject2.setTotal_transaction_amount(1.0);
    	
        list.add(clientNumberObject);
        list.add(clientNumberObject1);
        list.add(clientNumberObject2);
       
         
        when(productRepository.getProductListByClient("")).thenReturn(list);
         
        //test
        List<ClientNumberObject> empList = productController.getProductListByClient("");
         
        assertEquals(3, empList.size());
        verify(productRepository, times(1)).getProductListByClient("");
    }
}
