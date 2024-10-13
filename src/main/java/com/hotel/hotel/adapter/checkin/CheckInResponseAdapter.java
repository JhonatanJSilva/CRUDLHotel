package com.hotel.hotel.adapter.checkin;

import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.entity.Guest;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CheckInResponseAdapter {

    private Long id;

    private Guest guest;

    private LocalDateTime dateCheckin;

    private LocalDateTime dateCheckout;

    private Boolean additionalVehicle;

    private BigDecimal valueHosting;

    public CheckInResponseAdapter(CheckIn checkIn) {
        this.id = checkIn.getId();
        this.guest = checkIn.getGuest();
        this.dateCheckin = checkIn.getCheckInDate();
        this.dateCheckout = checkIn.getCheckoutDate();
        this.additionalVehicle = checkIn.getAdditionalVehicle();
        this.valueHosting = checkIn.getHostingValue();
    }
}
