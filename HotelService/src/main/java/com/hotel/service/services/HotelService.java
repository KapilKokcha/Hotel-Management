package com.hotel.service.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.service.Entity.Hotel;


public interface HotelService {
	Hotel create (Hotel hotel);
	List<Hotel>getAll();
	Hotel get(String id);
}
