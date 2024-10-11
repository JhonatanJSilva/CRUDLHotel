package com.hotel.hotel.adapter.checkin;

import com.hotel.hotel.dto.checkin.CheckinRequestDTO;
import com.hotel.hotel.entity.Guest;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CheckInRequestAdapter {

    private Guest guest;

    private LocalDateTime dateCheckin;

    private LocalDateTime dateCheckout;

    private Boolean additionalVehicle;

    private BigDecimal valueHosting;

    public CheckInRequestAdapter(CheckinRequestDTO checkinRequestDTO) {
        this.guest = checkinRequestDTO.getHospede();
        this.dateCheckin = checkinRequestDTO.getDataEntrada();
        this.dateCheckout = checkinRequestDTO.getDataSaida();
        this.additionalVehicle = checkinRequestDTO.getAdicionalVeiculo();
        this.valueHosting = checkinRequestDTO.getValorHospedagem();
    }
}
