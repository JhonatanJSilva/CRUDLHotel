package com.hotel.hotel.repository;

import com.hotel.hotel.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {

    @Query(value = "SELECT check_in.guest, SUM(hosting_value) AS total_hosting_value FROM check_in WHERE checkout_date < :today GROUP BY check_in.guest", nativeQuery = true)
    List<Map<Integer, BigDecimal>> valueTotalAllGuests(LocalDate today);

    @Query(value = "SELECT * FROM check_in WHERE checkout_date < :today", nativeQuery = true)
    List<CheckIn> guestsAreNotInTheHotel(LocalDate today);

    @Query(value = "SELECT * FROM check_in WHERE check_in_date < :today AND checkout_date >= :today", nativeQuery = true)
    List<CheckIn> guestsAtTheHotel(LocalDate today);
}
