package com.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entities.MyOrder;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {
	
	// Used currently by updateOrder() handler...
	// Ensuring that in findByOrderId OrderId should be in camelCase (orderId) in MyOrder entity...
	public MyOrder findByOrderId(String orderId);
	
}
