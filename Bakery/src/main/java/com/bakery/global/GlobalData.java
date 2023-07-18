package com.bakery.global;

import java.util.ArrayList;
import java.util.List;

import com.bakery.entities.Product;

public class GlobalData {

	// Whatever the things we gonna include must be static in nature here...
	
	// We will be using this specifically for our cart...
	
	// This will be accessed anywhere by the whole application...
	
	
	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
	
}
