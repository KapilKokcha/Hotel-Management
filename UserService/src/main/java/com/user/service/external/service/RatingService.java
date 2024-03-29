package com.user.service.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.user.service.entities.Rating;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

	@PostMapping("/ratings")
	public Rating createRating(Rating values);
	
	@PutMapping("/ratings/{ratingId}")
	public Rating updateRating(@PathVariable String ratingId, Rating  rating);
	
}
