package com.bakery.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.dto.ProductDTO;
import com.bakery.entities.Category;
import com.bakery.entities.MyOrder;
import com.bakery.entities.Product;
import com.bakery.global.GlobalData;
import com.bakery.repository.CategoryRepository;
import com.bakery.service.CategoryService;
import com.bakery.service.EmailService;
import com.bakery.service.OrderService;
import com.bakery.service.ProductService;

@Controller
public class AdminController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	// public static String uploadDir = "/root/productImages";

	// user.dir gives us the path to our root folder...
	
	// Injecting our category service...
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;
	
	// Injecting our productService...
	
	@Autowired
	ProductService productService; 
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private EmailService emailService;	
	
	// Handler for viewing admin home 
	
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}
	
	@GetMapping("/admin/orders")
	public String getOrders(Model model) {
				
		model.addAttribute("orders", orderService.getAllOrders());
		
		return "orders";
		
	}	
	
	
	@GetMapping("/admin/orders/confirm/{id}")
	public String confirmOrder(@PathVariable long id, Model model) {
		
		// Fetching that product from the database...
		
		MyOrder myOrder = orderService.getOrderById(id).get();
		
		String name = myOrder.getBillingDetailsFirstName()+" "+myOrder.getBillingDetailsLastName();
		String phone = myOrder.getBillingDetailsPhone();
		String emailAddress = myOrder.getBillingDetailsEmail();
		String address = myOrder.getBillingDetailsAddress1()+" "+myOrder.getBillingDetailsAddress2();
		String city = myOrder.getBillingDetailsCity();
		String pincode = myOrder.getBillingDetailsPinCode();
		String additionalInformation = myOrder.getBillingDetailsAdditionalInfo();
				
		// Order Details:-
		
		String amount = myOrder.getAmount();
		String paymentStatus = myOrder.getStatus();
		String orderId = myOrder.getOrderId();
		String paymentId = myOrder.getPaymentId();
		String receipt = myOrder.getReceipt();
		
		
		String subject = "Confirmation: Your Order from MedWeb Has Been Processed";
		String message = 
				"<div style='border:5px solid black; padding:20px; background: #10ff91; font-weight:600'>"					
				+"<h1>"+"<b>"+"<u>"+"<center>"+"Mail From The MedWeb"+"</center>"+"</u>"+"</b>"+"</h1>";
		
		message += 	"<b><h3> Dear "+name+", </h3><b>"
					+"<b><h3> We are pleased to inform you that your recent order on our medical application has been successfully validated with the prescription you uploaded. We have processed your order and it has been dispatched from our warehouse. </h3><b>" 
					+"<b><h3> You should expect to receive your order soon, based on your chosen shipping method. If you have any questions or concerns about your order, please don't hesitate to contact us. </h3><b>"
					+"<b><h3> Thank you for choosing our medical application for your healthcare needs. </h3><b>"
					+"<b><h3> Best regards, </h3><b>"					 
					+"<b><h3> MedWeb </h3><b>"; 	
					
		message +=  " <hr> <h4>"+"<b>"+"Your Contact Details"+"</b>"+"</h4>" 
					+"<h5>"+"Name: "+"<b>"+name+"</b>"+"</h5>"
					+"<h5>"+"Phone Number: "+"<b>"+phone+"</b>"+"</h5>"
					+"<h5>"+"Email Address: "+"<b>"+emailAddress+"</b>"+"</h5>"
					+"<h5>"+"Address: "+"<b>"+address+"</b>"+"</h5>"
					+"<h5>"+"City: "+"<b>"+city+"</b>"+"</h5>"
					+"<h5>"+"PinCode: "+"<b>"+pincode+"</b>"+"</h5>"
					+"<h5>"+"Additional Information: "+"<b>"+additionalInformation+"</b>"+"</h5><hr>";
				
		message +=  "<hr>"
					+"<h4>"+"<b>"+"Your Order Details"+"</b>"+"</h4>" 
					+"<h5>"+"Order Amount: "+"<b> Rs."+ amount.substring(0, amount.length() - 2) +"</b>"+"</h5>"
					+"<h5>"+"Payment Status: "+"<b>"+paymentStatus+"</b>"+"</h5>"
					+"<h5>"+"Order Id: "+"<b>"+orderId+"</b>"+"</h5>"
					+"<h5>"+"Payment Id: "+"<b>"+paymentId+"</b>"+"</h5>"
					+"<h5>"+"Payment Receipt Number: "+"<b>"+receipt+"</b>"+"</h5>"
					+"<hr>"							
					+"<h5>"+"<b>"+"Thanks for Shopping With Us !"+"</b>"+"</h5>" 
					+"</div>";
		
		String to = emailAddress;
		System.out.println(emailAddress);
		  
		this.emailService.sendEmail(subject, message, to);
		
		// Now returning to products add page...
		
		return "redirect:/admin/orders";
		
	}
	
	@GetMapping("/admin/orders/failure/{id}")
	public String failureOrder(@PathVariable long id, Model model) {
		
		// Fetching that product from the database...
		
		MyOrder myOrder = orderService.getOrderById(id).get();
		
		String name = myOrder.getBillingDetailsFirstName()+" "+myOrder.getBillingDetailsLastName();
		String phone = myOrder.getBillingDetailsPhone();
		String emailAddress = myOrder.getBillingDetailsEmail();
		String address = myOrder.getBillingDetailsAddress1()+" "+myOrder.getBillingDetailsAddress2();
		String city = myOrder.getBillingDetailsCity();
		String pincode = myOrder.getBillingDetailsPinCode();
		String additionalInformation = myOrder.getBillingDetailsAdditionalInfo();
				
		// Order Details:-
		
		String amount = myOrder.getAmount();
		String paymentStatus = myOrder.getStatus();
		String orderId = myOrder.getOrderId();
		String paymentId = myOrder.getPaymentId();
		String receipt = myOrder.getReceipt();
		
		
		String subject = "Order Cancellation and Refund from MedWeb Due to Prescription Validation Failure";
		String message = 
				"<div style='border:5px solid black; padding:20px; background: #10ff91; font-weight:600'>"					
				+"<h1>"+"<b>"+"<u>"+"<center>"+"Mail From The MedWeb"+"</center>"+"</u>"+"</b>"+"</h1>";
		
		message += 	"<b><h3> Dear "+name+", </h3><b>"
					+"<b><h3> We regret to inform you that we were unable to process your recent order on our medical application as the prescription you uploaded did not pass our validation process. </h3><b>" 
					+"<b><h3> As a result, we have canceled your order, and your payment will be refunded within 3 business days. The refunded amount will be credited back to the original payment method you used to make the purchase. </h3><b>" 
					+"<b><h3> We apologize for any inconvenience this may have caused. If you have any questions or concerns, please don't hesitate to contact us. </h3><b>" 
					+"<b><h3> Thank you for your understanding. </h3><b>"
					+"<b><h3> Best regards, </h3><b>"					 
					+"<b><h3> MedWeb </h3><b>"; 	
					
		message +=  " <hr> <h4>"+"<b>"+"Your Contact Details"+"</b>"+"</h4>" 
					+"<h5>"+"Name: "+"<b>"+name+"</b>"+"</h5>"
					+"<h5>"+"Phone Number: "+"<b>"+phone+"</b>"+"</h5>"
					+"<h5>"+"Email Address: "+"<b>"+emailAddress+"</b>"+"</h5>"
					+"<h5>"+"Address: "+"<b>"+address+"</b>"+"</h5>"
					+"<h5>"+"City: "+"<b>"+city+"</b>"+"</h5>"
					+"<h5>"+"PinCode: "+"<b>"+pincode+"</b>"+"</h5>"
					+"<h5>"+"Additional Information: "+"<b>"+additionalInformation+"</b>"+"</h5><hr>";
				
		message +=  "<hr>"
					+"<h4>"+"<b>"+"Your Order Details"+"</b>"+"</h4>" 
					+"<h5>"+"Order Amount: "+"<b> Rs."+ amount.substring(0, amount.length() - 2) +"</b>"+"</h5>"
					+"<h5>"+"Payment Status: "+"<b>"+paymentStatus+"</b>"+"</h5>"
					+"<h5>"+"Order Id: "+"<b>"+orderId+"</b>"+"</h5>"
					+"<h5>"+"Payment Id: "+"<b>"+paymentId+"</b>"+"</h5>"
					+"<h5>"+"Payment Receipt Number: "+"<b>"+receipt+"</b>"+"</h5>"
					+"<hr>"							
					+"<h5>"+"<b>"+"Thanks for Shopping With Us !"+"</b>"+"</h5>" 
					+"</div>";
		
		String to = emailAddress;
		System.out.println(emailAddress);
		  
		this.emailService.sendEmail(subject, message, to);
		
		// Now returning to products add page...
		
		return "redirect:/admin/orders";
		
	}
	
	// Handler for viewing admin categories

	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		
		/* 
			Notice earlier when we do not implement Services we need to obtain first our 
			data from Dao and then we put in methods below...
		*/ 
		
		model.addAttribute("categories", categoryService.getAllCategories());
		
		return "categories";
		
	}
	
	// Handler for adding the categories -> Filling the category 
	
	@GetMapping("/admin/categories/add")
	public String addCategory(Model model) {
		
		model.addAttribute("category", new Category());
		
		return "categoriesAdd";
		
	}
	
	// Handler for processing the adding of category -> Fetching data of category from view and processing...
	
	
	/*
	 You might observe same url in above and below handlers, The fact is that above handler is
	 taking input from the user and the below handler is recieving that input and processing
	 it...
	*/
	
	
	// Post Mapping used because specified in form tag of categoriesAdd view
		
	@PostMapping("/admin/categories/add")	
	public String processingaddCategory(@ModelAttribute("category") Category category) {
		// Fetching data from user via @ModelAttribute and storing it in category variable of Category class
		
		// Now, saving that data in our database...
		// Using service layer...
		// So, injecting CategoryService in our class...
		
		System.out.println(category);
		// However receiving id as 0 always from model but no issues as we did not set anything in our page for that id 
		// However in database proper id is store...
		// Verified also, from categories.html

		// Finally saving our category...
		
		this.categoryService.addCategory(category);	
		
		
		// Now redirecting the user to categories page...
		
		return "redirect:/admin/categories";
		
	}
	
	// Handler for deleting the categories...
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		
		// Fetching the dynamic id from @Pathvariable
		
		// Now, creating a delete method in Category Service...
		
		categoryService.removeCategoryById(id);
		
		// Also notice that we need not to save the entity while using here...
				
		// Now redirecting the user to categories page...
		
		return "redirect:/admin/categories";
		
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		
		// Fetching the dynamic id from @Pathvariable
		// Sending the data via model
		
		// Now creating a method which returns category by id 
		
		Optional<Category> category = categoryService.getCategoryById(id);
		
		if(category.isPresent()) {
			
			// If the category is present...
			
			model.addAttribute("category", category.get());
			
			// Returning our categoriesAdd view...
			// Note that we have created the view for normal creating as well as updating the category...
			// For updating just adding a single line there i.e. 
			
			return "categoriesAdd";
		
		}
		else {
		
			// If the category is not present...
			
			return "404";
		
		}
		
		
	}
	
	// Now working on products section
	
	// Handler to view the products...
	
	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		
		// Now to deal with products we will create ProductService...
		// ProductService will require ProductRepository, so creating ProductRepository...
		
		// Now, injecting our ProductService here...
		
		// Sending data of all the products to our products view...
		
		model.addAttribute("products",productService.getAllProduct());
						
		return "products";
		
	}
	
	// Handler to add the products -> Giving the add products form...
	
	@GetMapping("/admin/products/add")
	public String addProducts(Model model) {
		
		model.addAttribute("productDTO",new ProductDTO());
		
		// We are sending categories so that we can select only those categories which are available in our database... 
		// We are using this categories so that they can be selected and the product gets associates with that...
		
		model.addAttribute("categories", categoryService.getAllCategories());
		
		return "productsAdd";

	}
	
	// Handler for processing add products -> Processing add products form...
	
	@PostMapping("/admin/products/add")
	public String processingAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO, 
									   @RequestParam("productImage") MultipartFile file,
									   @RequestParam("imgName") String imgName) throws IOException {
		
		// Used @ModelAttribute to fetch the data entered by the user from the view to our handler...
		// Used @RequestParam to fetch specific fields data entered by user from the view to our handler...
		
		
		// Now, we need to convert our ProductDTO object to Product object... 
		
		// Creating a product object...
		
		Product product= new Product();
		
		// Setting up the data...
		
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		
		// We need to set Category, first we need to fetch category by category id as we only have Category id...
		
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		// Breaking the processing of image in two parts...
		// One image name will be going in database...
		// Second image will be storing in static/images...
		
		String imageUUID;
		
		if(!file.isEmpty()) {
			
			// When the user gives a image for product...
			// When the file is not empty...
			
			// Getting the actual name of our image
			
			imageUUID = file.getOriginalFilename();
			
			// Saving the file in our /static/productImages...
			
			Path fileNameAndPath = Paths.get(uploadDir,imageUUID); 
			
			// Paths.get(Where to save and which file to save)
			
			 Files.write(fileNameAndPath, file.getBytes()); 
			
		} else {
			
			// When the user does not give the image for the product... 
			// When the file is empty...
			
			imageUUID = imgName;
			
		}
		
		product.setImageName(imageUUID);
		
		// Finally saving the product...
		
		productService.addProduct(product);
		
		
		// Observe that even though we included dtos but at final stage we need the product class only...
		
		// Redirecting to our products page...
		
		return "redirect:/admin/products";
		
	}
	
	// Handler for deleting the product...
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		
		// @PathVAriable to fetch the id from the url dynamically... 
		
		productService.removeProductById(id);
		
		// Now redirecting to products page...
		
		return "redirect:/admin/products";
	
	}
	
	// Handler for updating the product...
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		
		// Fetching that product from the database...
		
		Product product = productService.getProductById(id).get();
		
		// Creating a product DTO...
		
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		// Now passing our product categories and product dto to view via our model...
		
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("productDTO", productDTO);
		
		// Now returning to products add page...
		
		return "productsAdd";
		
	}
	
	
}
