package com.hotel.hotel.dto.checkin;

import com.hotel.hotel.entity.Guest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CheckInRequestDTO {

    private Guest hospede;

    private LocalDateTime dataEntrada;

    private LocalDateTime dataSaida;

    private Boolean adicionalVeiculo;
}
