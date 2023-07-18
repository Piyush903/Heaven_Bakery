package com.bakery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bakery.entities.Category;
import com.bakery.entities.Product;
import com.bakery.entities.UserQuery;
import com.bakery.global.GlobalData;
import com.bakery.repository.UserQueryRepository;
import com.bakery.service.CategoryService;
import com.bakery.service.ProductService;

@Controller
public class HomeController {

	// Injecting our ProductService and CategoryService via @Autowired...
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserQueryRepository userQueryRepository;
	
	// Handler for home page... 
	
	@GetMapping({"/","/home"})
	public String home(Model model) {
	
		// Copied from CartController...
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		return "index";
	
	}
	
	// Handler for shop page...
	
	@GetMapping("/shop")
	public String shop(Model model) {
		
		// Sending all the products and categories to our view via model
		
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("products", productService.getAllProduct());
		
		// Copied from CartController...
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		return "shop";
		
	}
	
	// Handler for shop by Category...
	
	@GetMapping("/shop/category/{id}")
	public String getProductsByCategory(Model model, @PathVariable int id) {
		
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		
		// In previous handler we were sending all the products without any category id section...
		// Here, we are sending the products which are associated with only a particular id of category...
		
		// Copied from CartController...
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		// Returning the same view as it handles all the cases...
		
		return "shop";
		
	}
	
	// Handler for viewing a specific product...
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable int id) {
		
		// Passing the data to our view via model...
		
		model.addAttribute("product", productService.getProductById(id).get());
		
		// Used get() because we are fetching from the optional...
		
		// Copied from CartController...
		model.addAttribute("cartCount", GlobalData.cart.size());
				
		return "viewProduct";
		
	}
	
	// Handler for About view...
	
	@GetMapping("/about")
	public String viewAbout() {
		return "about";
	}
	
	// Handler for Contact view...
	
	@GetMapping("/contact")
	public String viewContact(Model model) {
		
		model.addAttribute("userQueryModel", new UserQuery());
		
		return "contact";
		
	}
	
	// Handler for getting query data from Contact view...
	
	@PostMapping("/processContactQuery")
	public String processContactQuery(@ModelAttribute UserQuery userQueryModel) {
		
		UserQuery userQuery = new UserQuery();
		
		userQuery.setName(userQueryModel.getName());
		userQuery.setEmail(userQueryModel.getEmail());
		userQuery.setNumber(userQueryModel.getNumber());
		userQuery.setQuery(userQueryModel.getNumber());
		
		this.userQueryRepository.save(userQuery);
		
		// To check whether User Query Repository is working...
		System.out.println("User Query Repository Working...");
		
		return "redirect:/contact";
	
	}
	
	@SuppressWarnings({ "unused", "null" })
	@RequestMapping(value = "/shop", method = RequestMethod.POST)
	public String searchQuery(@RequestParam("searchQuery") String query, Model model) {
		
		// Sending all the products and categories to our view via model
		
		/*List<Category> allCategories = categoryService.getAllCategories();*/
		
		System.out.println(query);
		
		List<Product> allProducts = productService.getAllProduct();
		List<Product> resultantProduct = new ArrayList<>();
		
		for (Product product : allProducts) {
			
			if(product.getName().contains(query) || product.getDescription().contains(query)) 
			{
				resultantProduct.add(product);
			}
			
		}
		
		
		
		/*List<Category> resultantCategory;*/
		
		model.addAttribute("categories", categoryService.getAllCategories());
		
		if(resultantProduct!=null) {
			model.addAttribute("products", resultantProduct);
		}
		else {
			model.addAttribute("products", productService.getAllProduct());
		}
			
		
		
		/* model.addAttribute("products", productService.getAllProduct()); */
		
		// Copied from CartController...
		model.addAttribute("cartCount", GlobalData.cart.size());
				
		return "shop";
		
	}
	
	
	
}
