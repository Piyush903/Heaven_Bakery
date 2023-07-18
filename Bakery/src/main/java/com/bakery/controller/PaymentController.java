package com.bakery.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.Multipart;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bakery.entities.MyOrder;
import com.bakery.entities.Product;
import com.bakery.entities.User;
import com.bakery.global.GlobalData;
import com.bakery.repository.MyOrderRepository;
import com.bakery.repository.UserRepository;
import com.bakery.service.EmailService;
import com.razorpay.*;

import org.json.JSONObject;

@Controller
public class PaymentController {

/***************************************************/
	
	@Autowired
	private MyOrderRepository myOrderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;	
	
	// Working now on payment integration using Razorpay...
	
	// Creating order for payment handler
		
		@PostMapping("/create_order")
		@ResponseBody
		public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws Exception {
			
			// Used Response Body to directly send the String...
			// We used @RequestBody to fetch JSON data comming via script.js 
			// We stored that JSON data in map class variable data...
			
			// To check whether the handler is working...
			System.out.println("create order function executed...");
			
			// To check whether the data is coming or not...
			System.out.println("Data comming to createOrder function is "+data);
			
			// Just for testing purposes...
			// System.out.println("****************** "+data.get("billingDetailsFirstName").toString());
												
			// Fetching the amount from JSON here and converting it to int...
			int amt = (int)Double.parseDouble(data.get("amount").toString()); 
			
			System.out.println("***Amount is "+amt+" ***");
			
			// Now implementing Razor-Pay API...
			// First importing com.razorpay.*;
			
			// Notice we can now use var for client from JAVA 11...
			var client = new RazorpayClient("rzp_test_vcCYga8goFLnmu","IgFNebIOV1mXRefbg8fBYfBE");
			
			JSONObject ob = new JSONObject();
			ob.put("amount", amt*100); // Done *100 to convert ruppees to paise
			ob.put("currency", "INR"); // As we are taking indian currency so we did amt*100
			ob.put("receipt", "txn_12345600");
					
			// Setting up extra billing data in JSONObject...
			// Here, the first argument is key by which we refer and use while getting from order when we pass this JSON object in that order...
			// By the second argument we are fetching the values of data property from the Json from our script.js and converting it to String... 
								
			// Creating new order...
			// From here sending request from server to RazorPay Server
			
			Order order = client.Orders.create(ob);
			
			// To check whether the order is created...
			System.out.println("[createOrder handler] Order is: "+order);
			
			// If we want we can then save this order in our database also...
			// It is much  efficient approach...
			
			// Also, the id of order we are receiving in data from RazorPay is very important...
			
			// Payment Integration Part - II
			// Saving the order in our database...
			
			// Automatic Table Creation via HIBERNATE...
			// Created MyOrder entity...		
			// Then MyOrderRepository Dao...
			// Then injecting it in this class to use it...
			
			
			// Now
			// Creating MyOrder and MyOrderRepository
			
			MyOrder myOrder = new MyOrder(); 
			
			// Setting the details in myOrder via order variable...
			
			// Passing the key to obtain the values...
			// Taking reference from our script.js
			
			myOrder.setAmount(order.get("amount")+""); // Here deliberately done "" to convert the amount from int to String which prevents from error...
			myOrder.setOrderId(order.get("id"));
			myOrder.setPaymentId(null);
			myOrder.setStatus("created"); 
			myOrder.setReceipt(order.get("receipt"));
									
			// Also, setting up Billing Details information...
			
			myOrder.setBillingDetailsFirstName(data.get("billingDetailsFirstName").toString());
			myOrder.setBillingDetailsLastName(data.get("billingDetailsLastName").toString());
			myOrder.setBillingDetailsAddress1(data.get("billingDetailsAddress1").toString());
			myOrder.setBillingDetailsAddress2(data.get("billingDetailsAddress2").toString());
			myOrder.setBillingDetailsPinCode(data.get("billingDetailsPinCode").toString());
			myOrder.setBillingDetailsCity(data.get("billingDetailsCity").toString());
			myOrder.setBillingDetailsPhone(data.get("billingDetailsPhone").toString());
			myOrder.setBillingDetailsEmail(data.get("billingDetailsEmail").toString());
			myOrder.setBillingDetailsAdditionalInfo(data.get("billingDetailsAdditionalInfo").toString());
			
			myOrder.setIsValid(data.get("isValid").toString());
			
			// Using principal for finding current user...
			
			// myOrder.setUser(this.userRepository.findUserByEmail(principal.getName()));
						
			myOrder.setUser(this.userRepository.findUserByEmail(principal.getName()).get());
						
			//Saving the order to database finally...
			
			this.myOrderRepository.save(myOrder);
			
			// Note that here even though the details are sent to database but it is not 
			// complete as here the payment is not complete as order is just created here...
			
			// For this we go back to our scipt.js and where we created options in that 
			// handler just before the sweetalert of successfull payment -> before prefill create a function 
			// from where we pass rest of details which will be sent to database to keep
			// record of successfull payment -> updatePaymentOnServer(paymentid,orderid,status)...
			
			
			
			// Returning order details as string...
			
			return order.toString();
		
		}
		
		
		// Handler for 
		// Also, we need to ensure that mapping should be same as we mentioned in our script.js
		
		@PostMapping("/update_order")
		public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data, Principal principal, HttpSession httpSession){
			
			// Here we need to have HttpSession for sending Email in the argument...
			
			// @RequestBody helps in fetching the data passed by the updatePaymentOnServer()
			// We store it in our data variable of map class...
			
			// To check whether our data is comming or not...
			System.out.println("[updateOrder handler] data is: "+data);
			
			// To update the order we need to find order by order id, so defining function in MyOrderRepository
			
			// So, getting that particular order...
			MyOrder myOrder = this.myOrderRepository.findByOrderId(data.get("order_id").toString());
			
			// Updating the required fields in that order...
			
			myOrder.setPaymentId(data.get("payment_id").toString());
			myOrder.setStatus(data.get("status").toString());
			
			// Then finally saving the order details in our database...
			
			this.myOrderRepository.save(myOrder);
			
			// ******************************************************************				
				
				// Code for Sending Order details to User via Email...
				
				// Using emailService 
				// Here we need not to create emailRequest entity as we already have required data needed...
				// Preparing the arguments to be passed...
			
				// User Details:-
			
			
			
				String name = myOrder.getBillingDetailsFirstName()+" "+myOrder.getBillingDetailsLastName();
				String phone = myOrder.getBillingDetailsPhone();
				String emailAddress = myOrder.getBillingDetailsEmail();
				String address = myOrder.getBillingDetailsAddress1()+" "+myOrder.getBillingDetailsAddress2();
				String city = myOrder.getBillingDetailsCity();
				String pincode = myOrder.getBillingDetailsPinCode();
				String additionalInformation = myOrder.getBillingDetailsAdditionalInfo();
							
				List<Product> userProducts = GlobalData.cart; 
				
				// Order Details:-
				
				String amount = myOrder.getAmount();
				String paymentStatus = myOrder.getStatus();
				String orderId = myOrder.getOrderId();
				String paymentId = myOrder.getPaymentId();
				String receipt = myOrder.getReceipt();
				
				
				String subject = "Your Order Details From MedWeb";
				String message = 
						"<div style='border:5px solid black; padding:20px; background: #10ff91; font-weight:600'>"					
						+"<h1>"+"<b>"+"<u>"+"<center>"+"Mail From The MedWeb"+"</center>"+"</u>"+"</b>"+"</h1>";
				
				message += 	"<b><h3> Dear User, </h3><b>"
							+"<b><h3> Thank you for placing your order on our medical application. We appreciate your trust in our platform and we are committed to providing you with the best possible service. </h3><b>" 
							+"<b><h3> We would like to inform you that your order has been received and is currently being reviewed by our admin team for validation. This process is in place to ensure that all orders are processed accurately and in a timely manner. </h3><b>" 
							+"<b><h3> Rest assured, we will keep you updated on the status of your order and will notify you as soon as it has been validated and processed. If you have any questions or concerns regarding your order, please don't hesitate to reach out to our customer support team. </h3><b>"
							+"<b><h3> Thank you for your patience and understanding. </h3><b>" 
							+"<b><h3> Best regards, </h3><b>" 
							+"<b><h3> MedWeb </h3><b>"; 	
							
				message +=  " <hr> <h4>"+"<b>"+"Your Contact Details"+"</b>"+"</h4>" 
							+"<h5>"+"Name: "+"<b>"+name+"</b>"+"</h5>"
							+"<h5>"+"Phone Number: "+"<b>"+phone+"</b>"+"</h5>"
							+"<h5>"+"Email Address: "+"<b>"+emailAddress+"</b>"+"</h5>"
							+"<h5>"+"Address: "+"<b>"+address+"</b>"+"</h5>"
							+"<h5>"+"City: "+"<b>"+city+"</b>"+"</h5>"
							+"<h5>"+"PinCode: "+"<b>"+pincode+"</b>"+"</h5>"
							+"<h5>"+"Additional Information: "+"<b>"+additionalInformation+"</b>"+"</h5><hr>"
							+"<h5>"+"User Products: "+"<b>"+"</b>"+"</h5>";
				
				for(int i=0; i<userProducts.size();i++) {
					message += "<h5>"+ (i+1) + ". Product Name: " + userProducts.get(i).getName() + "<br>";
										  	
				}				
						
				message +=  "</h5> <hr>"
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
				
				// Removing the products from the cart as we have done the payment now !!
				
				GlobalData.cart.removeAll(userProducts);
							
			// ******************************************************************
			
				return ResponseEntity.ok(Map.of("msg","updated"));
			
		}
	
		
}
