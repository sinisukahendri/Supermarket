package com.rigor.controller;

/** 

* Controller Class for Product. 
* 
* Copyright (c) Virtusa Corporation 2016, All Rights Reserved.
* 
* This class is the controller class for Product.It prepares data,create a java object to 
* hold the data
* 
* @autho Nadeesha 
* 
* @version 1.0
* 
* @see see also Product.java, 
* ProductDAO.java, ProductDAOImpl.java, 
* ProductDTO.java, ProductService.java, 
* ProductServiceImpl.java classes

*/ 

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.rigor.entity.Product;
import com.rigor.entity.Supplier;
import com.rigor.service.ProductService;

@Controller
@RequestMapping("/")
public class ProductController {

	/*private List<Product> productList = new ArrayList();*/
	private List<Product> productList = new ArrayList<Product>();


	@Autowired
	private ProductService productService;
	
	 @Autowired
	    MessageSource messageSource;
	

	@RequestMapping(value = "/listProduct", method = RequestMethod.GET)
	public ModelAndView listProduct() {
		return new ModelAndView("list-product", "products",
				productService.getAllProducts());

	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String addProduct(ModelMap modelMap) {
		modelMap.addAttribute("product", new Product());
		modelMap.addAttribute("update", false);
		return "create-product";

	}

	@RequestMapping(value = "modifyProduct")
	public ModelAndView modifyProduct(Product product,
			BindingResult result) {

		return new ModelAndView("list-product");

	}

	@RequestMapping(value = "/editProduct", method = RequestMethod.GET)
	public String editPage(ModelMap modelMap, HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("id"));
		modelMap.addAttribute("product", productService.getProduct(productId));
		modelMap.addAttribute("update", true);
		return "create-product";
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("id"));
		productService.deleteProduct(productId);
		return new ModelAndView("list-product", "products",
				productService.getAllProducts());

	}

	@RequestMapping(value = "/addProduct")
	public ModelAndView addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("list-product");
		
		if(result.hasErrors()){
			return new ModelAndView("create-product");
		}
		else {
		if (product.getProductId() > 0) {
			// update
			productService.update(product);
		} else {
			// add product
			productService.saveProduct(product);
		}
		modelAndView.addObject("products", productService.getAllProducts());
		System.out.println(product.getProductId());
		return modelAndView;
	}
	}

}
