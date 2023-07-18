package com.bakery.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class MyOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long myOrderId;
	
	// Taking same field which are provided by order of payment gateway
	// Taking only few fields from the given fields by payment gateway
	
	private String orderId;	
	private String amount;	
	private String receipt;	
	private String status;
	
	// Now...
	// To get the information which user has done the payment...
	// We will be implementing bidirectional mapping @ManyToOne because many times user can do payment...
	// By this we are making  it as foreign key...
	
	@ManyToOne
	private User user; 
	
	private String paymentId;	
	
	// Now generating getters and setters for all the above fields...

	public Long getMyOrderId() {
		return myOrderId;
	}

	public void setMyOrderId(Long myOrderId) {
		this.myOrderId = myOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	// We did not create any constructor, so java will create a default constructor which 
	// we will be using whenever we need to create its object...
	
	// Now creating interface MyOrderRepository...
	
	// Now setting up the variables of Billing Details Form which will be placed in orders table only...
	// We will use @RequestBody, to fetch these variables values via our category form... 
			
	private String billingDetailsFirstName;
	private String billingDetailsLastName;
	private String billingDetailsAddress1;
	private String billingDetailsAddress2;
	private String billingDetailsPinCode;
	private String billingDetailsCity;
	private String billingDetailsPhone;
	private String billingDetailsEmail;
	private String billingDetailsAdditionalInfo;
	
	private String isValid;
	
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
	/*
	 * private String documentName;
	 * 
	 * @Lob private byte[] content;
	 * 
	 * public String getDocumentName() { return documentName; }
	 * 
	 * public void setDocumentName(String documentName) { this.documentName =
	 * documentName; }
	 * 
	 * public byte[] getContent() { return content; }
	 * 
	 * public void setContent(byte[] content) { this.content = content; }
	 */
	
	
	public String getBillingDetailsFirstName() {
		return billingDetailsFirstName;
	}

	public void setBillingDetailsFirstName(String billingDetailsFirstName) {
		this.billingDetailsFirstName = billingDetailsFirstName;
	}

	public String getBillingDetailsLastName() {
		return billingDetailsLastName;
	}

	public void setBillingDetailsLastName(String billingDetailsLastName) {
		this.billingDetailsLastName = billingDetailsLastName;
	}

	public String getBillingDetailsAddress1() {
		return billingDetailsAddress1;
	}

	public void setBillingDetailsAddress1(String billingDetailsAddress1) {
		this.billingDetailsAddress1 = billingDetailsAddress1;
	}

	public String getBillingDetailsAddress2() {
		return billingDetailsAddress2;
	}

	public void setBillingDetailsAddress2(String billingDetailsAddress2) {
		this.billingDetailsAddress2 = billingDetailsAddress2;
	}

	public String getBillingDetailsPinCode() {
		return billingDetailsPinCode;
	}

	public void setBillingDetailsPinCode(String billingDetailsPinCode) {
		this.billingDetailsPinCode = billingDetailsPinCode;
	}

	public String getBillingDetailsCity() {
		return billingDetailsCity;
	}

	public void setBillingDetailsCity(String billingDetailsCity) {
		this.billingDetailsCity = billingDetailsCity;
	}

	public String getBillingDetailsPhone() {
		return billingDetailsPhone;
	}

	public void setBillingDetailsPhone(String billingDetailsPhone) {
		this.billingDetailsPhone = billingDetailsPhone;
	}

	public String getBillingDetailsEmail() {
		return billingDetailsEmail;
	}

	public void setBillingDetailsEmail(String billingDetailsEmail) {
		this.billingDetailsEmail = billingDetailsEmail;
	}

	public String getBillingDetailsAdditionalInfo() {
		return billingDetailsAdditionalInfo;
	}

	public void setBillingDetailsAdditionalInfo(String billingDetailsAdditionalInfo) {
		this.billingDetailsAdditionalInfo = billingDetailsAdditionalInfo;
	}		

}
