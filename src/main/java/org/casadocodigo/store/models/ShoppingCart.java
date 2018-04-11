package org.casadocodigo.store.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<CartItem, Integer> items = new LinkedHashMap<>();

	public void add(CartItem item) {
		items.put(item, getAmount(item) + 1);
	}

	public int getAmount(CartItem item) {
		if (!items.containsKey(item))
			items.put(item, 0);

		return items.get(item);
	}

	public int getAmount() {
		return items.values().stream().reduce(0, (next, accumulator) -> (next + accumulator));
	}
	
	public Collection<CartItem> getItems() {
		return items.keySet();
	}
	
	public BigDecimal getTotal(CartItem item) {
		return item.getTotal(getAmount(item));
	}

	public BigDecimal getTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for(CartItem item : items.keySet())
			total = total.add(getTotal(item));
		
		return total;
	}
}
