package org.casadocodigo.store.controllers;

import org.casadocodigo.store.daos.ProductDAO;
import org.casadocodigo.store.models.CartItem;
import org.casadocodigo.store.models.PriceType;
import org.casadocodigo.store.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class ShoppingCartController {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShoppingCart cart;

	@RequestMapping("/add")
	public ModelAndView add(Integer productId, PriceType priceType) {
		ModelAndView modelAndView = new ModelAndView("redirect:/cart");

		cart.add(createItem(productId, priceType));
		
		return modelAndView;
	}

	private CartItem createItem(Integer productId, PriceType priceType) {
		CartItem cartItem = new CartItem(productDAO.find(productId), priceType);

		return cartItem;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView items() {
		return new ModelAndView("/cart/items");
	}
}
