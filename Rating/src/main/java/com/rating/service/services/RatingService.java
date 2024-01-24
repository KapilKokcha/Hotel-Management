package com.rating.service.services;

import java.util.List;

import com.rating.service.entities.Rating;

public interface RatingService {
	Rating create (Rating rating);
	List<Rating>getAllRatings();
	List<Rating>getAllRatingByHotelId(String hotelRating);
	List<Rating> getAllRatingByUserId(String userId);
}
