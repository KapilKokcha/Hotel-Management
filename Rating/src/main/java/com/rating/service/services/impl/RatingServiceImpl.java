package com.rating.service.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rating.service.entities.Rating;
import com.rating.service.repositories.RatingRepository;
import com.rating.service.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public Rating create(Rating rating) {
		// TODO Auto-generated method stub
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getAllRatings() {
		// TODO Auto-generated method stub
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> getAllRatingByUserId(String userId) {
		// TODO Auto-generated method stub
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getAllRatingByHotelId(String hotelRating) {
		// TODO Auto-generated method stub
		return ratingRepository.findByHotelId(hotelRating);
	}

}
