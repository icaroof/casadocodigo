package org.casadocodigo.store.controllers;

import java.util.List;

import org.casadocodigo.store.daos.ProductDAO;
import org.casadocodigo.store.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@Autowired
	ProductDAO productDAO;

	@RequestMapping("/")
	@Cacheable(value="productsHome")
	public ModelAndView index() {
		List<Product> productList = productDAO.list();
		ModelAndView model = new ModelAndView("home");
		model.addObject("products", productList);
		
		return model;
	}
}
