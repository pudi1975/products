package com.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.ClientNumberObject;
import com.product.dto.ProductObject;
import com.product.repository.ProductRepository;

@RestController
@RequestMapping("/product")
/**
 * 
 * @author madhu.pudi
 *  Description : this class is used to get products information 
 */
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping({ "/{productCode}", "" })
	/**
	 * 
	 * @param productCode
	 * @return list of products 
	 * Description : this method  is used to get products information for given product group -code 
	 */
	public List<ProductObject> getProductList(@PathVariable(name = "productCode") String productCode) {
		return productRepository.getProductList(productCode);
	}
/**
 * 
 * @param clientNumber
 * @return list of products
 * Description : this method  is used to get products information for given client number  
 */
	@GetMapping({ "/client/{clientNumber}", "" })
	public List<ClientNumberObject> getProductListByClient(@PathVariable(name = "clientNumber") String clientNumber) {
		return productRepository.getProductListByClient(clientNumber);
	}

}
