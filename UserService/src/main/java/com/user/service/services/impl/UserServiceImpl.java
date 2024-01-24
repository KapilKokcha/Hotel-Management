package com.user.service.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.external.service.HotelService;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {

		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
		
		// Make the request using exchange method to capture the generic type information
		ResponseEntity<List<Rating>> responseEntity = restTemplate.exchange(
		        "http://RATING-SERVICE/ratings/users/" + user.getUserId(),
		        HttpMethod.GET,
		        null,
		        new ParameterizedTypeReference<List<Rating>>() {
				});

		// Retrieve the body from the ResponseEntity
		List<Rating>ratingsOfUser = responseEntity.getBody();

		// Log the ratings
		logger.info("{}", ratingsOfUser);

		// Set the ratings for the user
		user.setRating(ratingsOfUser);

		// Fetch hotel information for each rating
		for (Rating rating : ratingsOfUser) {
			// The below is using restTemplate
//		    Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
		    // The below is using FeignClient
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
		}
		
		return user;
	}

}
