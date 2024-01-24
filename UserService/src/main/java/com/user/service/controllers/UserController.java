package com.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.User;
import com.user.service.services.UserService;
import com.user.service.services.impl.UserServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	int retryCount = 1;
	
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//	@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		retryCount++;
		logger.info("Retry count = " + retryCount);
		System.out.println(retryCount);
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	// Fallback method for circuit breaker
	
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback excuted because service is down : ",ex.getMessage());
		User user = new User(userId,"Dummy name","Dummy@email.com","NA",null);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}
