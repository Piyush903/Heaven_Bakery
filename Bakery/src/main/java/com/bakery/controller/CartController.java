package com.bakery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bakery.entities.Product;
import com.bakery.global.GlobalData;
import com.bakery.service.ProductService;

@Controller
public class CartController {

	@Autowired
	ProductService productService;
	
	// Handler for adding products to cart... 
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		
		// Fetching the dynamic id of product via @PathVariable..		
		// Now adding the product to our Cart as GlobalData via productService..	
		
		// As our cart is the ArrayList...		
		// So adding product items in our ArrayList...
		
		GlobalData.cart.add(productService.getProductById(id).get());
		
		// Redirecting the user to shop once the item is added...
		
		return "redirect:/shop";		
		
	}
	
	// Handler for viewing our cart...
	
	@GetMapping("/cart")
	public String viewCart(Model model) {
		
		// Using model to send data to view...
		
		// Sending the cart count of items by the GlobalData via cart list -> size() function...
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		// Sending the above same function of cartCount to each and every handler of HomeController now...
		
		// Sending the total cost of items of the user in the cart...
		// We are using GlobalData -> cart -> stream() -> mapToDouble() -> sum()...
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		
		// Then Finally Sending the data of cart to our cart view...
		model.addAttribute("cart", GlobalData.cart);
		
		return "cart";
				
	}	
	
	// Handler for removing the items in the cart...
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		
		// As our cart is a simply an ArrayList...
		
		GlobalData.cart.remove(index);
		
		// Redirecting the user to cart page...
		
		return "redirect:/cart";
		
	}
	
	// Handler for checkout view...
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		
		// Model used to send the data to our view...
		
		// Sending the total cost of items of the user in the cart...
		// We are using GlobalData -> cart -> stream() -> mapToDouble() -> sum()...
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		
		return "checkout";
		
	}
	
	
}
