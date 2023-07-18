package com.bakery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bakery.entities.MyOrder;
import com.bakery.repository.MyOrderRepository;

@Service
public class OrderService {

	@Autowired
	MyOrderRepository myOrderRepository;
	
	public List<MyOrder> getAllOrders(){
		return myOrderRepository.findAll();
	}	
	
	public Optional<MyOrder> getOrderById(long id){
		return myOrderRepository.findById(id);
	}
	
}
