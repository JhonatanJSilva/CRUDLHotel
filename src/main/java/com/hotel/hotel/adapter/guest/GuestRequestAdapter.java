package com.hotel.hotel.adapter.guest;

import com.hotel.hotel.dto.guest.GuestRequestDTO;
import lombok.Getter;

@Getter
public class GuestRequestAdapter {

    private String name;

    private String document;

    private String telephone;

    public GuestRequestAdapter(GuestRequestDTO requestDTO) {
        this.name = requestDTO.getNome();
        this.document = requestDTO.getDocumento();
        this.telephone = requestDTO.getTelefone();
    }
}
