package com.hotel.hotel.dto.checkin;

import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.Guest;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class CheckInResponseDTO {

    private Long id;

    private Guest hospede;

    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida;

    private Boolean adicionalVeiculo;

    private BigDecimal valorHospedagem;

    public CheckInResponseDTO(CheckInResponseAdapter responseAdapter) {
        this.id = responseAdapter.getId();
        this.hospede = responseAdapter.getGuest();
        this.dataEntrada = responseAdapter.getDateCheckin();
        this.dataSaida = responseAdapter.getDateCheckout();
        this.adicionalVeiculo = responseAdapter.getAdditionalVehicle();
        this.valorHospedagem = responseAdapter.getValueHosting();
    }
}
