package com.hotel.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.service.Entity.Hotel;
import com.hotel.service.exceptions.ResourceNotFoundException;
import com.hotel.service.repositories.HotelReposiory;
import com.hotel.service.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService {
	@Autowired
	private HotelReposiory hotelReposiory;

	@Override
	public Hotel create(Hotel hotel) {
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelReposiory.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		// TODO Auto-generated method stub
		return hotelReposiory.findAll();
	}

	@Override
	public Hotel get(String id) {
		// TODO Auto-generated method stub
		return hotelReposiory.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Hotel not found with given name"));
	}

}
