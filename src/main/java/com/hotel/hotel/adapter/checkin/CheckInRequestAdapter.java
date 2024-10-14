package com.hotel.hotel.adapter.checkin;

import com.hotel.hotel.dto.checkin.CheckInRequestDTO;
import com.hotel.hotel.entity.Guest;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class CheckInRequestAdapter {

    private Guest guest;

    private LocalDateTime checkInDate;

    private LocalDateTime checkoutDate;

    private Boolean additionalVehicle;

    private BigDecimal hostingValue;

    public CheckInRequestAdapter(CheckInRequestDTO checkinRequestDTO) {
        this.guest = checkinRequestDTO.getHospede();
        this.checkInDate = checkinRequestDTO.getDataEntrada();
        this.checkoutDate = checkinRequestDTO.getDataSaida();
        this.additionalVehicle = checkinRequestDTO.getAdicionalVeiculo();
    }
}
