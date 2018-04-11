package org.casadocodigo.store.controllers;

import javax.validation.Valid;

import org.casadocodigo.store.daos.ProductDAO;
import org.casadocodigo.store.infra.FileProcessor;
import org.casadocodigo.store.models.PriceType;
import org.casadocodigo.store.models.Product;
import org.casadocodigo.store.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private FileProcessor fileProcessor;

	@Autowired
	private ProductDAO productDAO;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ProductValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Product product) {
		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("types", PriceType.values());

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	@CacheEvict(value="productsHome", allEntries=true)
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors())
			return form(product);

		product.setSummaryPath(fileProcessor.write("summary-files", summary));

		productDAO.save(product);

		redirectAttributes.addFlashAttribute("success", "Book saved!");

		return new ModelAndView("redirect:products");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}

	@RequestMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("/products/detail");
		modelAndView.addObject("product", productDAO.find(id));
		return modelAndView;
	}
}
