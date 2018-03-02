package org.casadocodigo.store.controllers;

import javax.validation.Valid;

import org.casadocodigo.store.daos.ProductDAO;
import org.casadocodigo.store.models.PriceType;
import org.casadocodigo.store.models.Product;
import org.casadocodigo.store.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProductValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("types", PriceType.values());
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(@Valid Product product, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors())
			return form();
		
		productDAO.save(product);
		
		redirectAttributes.addFlashAttribute("success", "Book saved!");
		
		return new ModelAndView("redirect:products");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
}
