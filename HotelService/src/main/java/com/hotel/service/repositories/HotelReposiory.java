package com.hotel.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.service.Entity.Hotel;

@Repository
public interface HotelReposiory extends JpaRepository<Hotel, String> {

}
