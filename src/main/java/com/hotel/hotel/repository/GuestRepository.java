package com.hotel.hotel.repository;

import com.hotel.hotel.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Guest findByName(String name);

    Guest findByDocument(String document);

    Guest findByTelephone(String telephone);
}
