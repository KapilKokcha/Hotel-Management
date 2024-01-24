package com.rating.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.service.entities.Rating;
import com.rating.service.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	@Autowired
	private RatingService ratingService;

	@PostMapping
	public ResponseEntity<Rating> create(@RequestBody Rating rating) {
		ratingService.create(rating);
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
	}
	
	@GetMapping
	public ResponseEntity<List<Rating>> getAllRatings(){
		return ResponseEntity.ok(ratingService.getAllRatings());
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getAllRatingsByUserId(@PathVariable String userId){
		return ResponseEntity.ok(ratingService.getAllRatingByUserId(userId));
	}
	
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getAllRatingsByHotelId(@PathVariable String hotelId){
		return ResponseEntity.ok(ratingService.getAllRatingByHotelId(hotelId));
	}
	
	
}
