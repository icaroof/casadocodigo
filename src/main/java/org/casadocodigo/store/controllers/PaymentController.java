package org.casadocodigo.store.controllers;

import java.util.concurrent.Callable;

import org.casadocodigo.store.models.PaymentData;
import org.casadocodigo.store.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/payment")
@Controller
public class PaymentController {
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/finish", method=RequestMethod.POST)
	public Callable<ModelAndView> finish(RedirectAttributes model) {
		return () -> {
			String url = "http://book-payment.herokuapp.com/payment";
			
			String response = "";
			
			try {
				response = restTemplate.postForObject(url, new PaymentData(shoppingCart.getTotal()), String.class);
				
				shoppingCart.clear();
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				response = "Valor maior que o permitido";
			}
			
			model.addFlashAttribute("success", response);
			
			return new ModelAndView("redirect:/products");
		};
	}
}
