package com.hotel.hotel.dto.guest;

import com.hotel.hotel.adapter.guest.GuestResponseAdapter;
import lombok.Getter;

@Getter
public class GuestResponseDTO {

    private Long id;

    private String nome;

    private String documento;

    private String telefone;

    public GuestResponseDTO(GuestResponseAdapter responseAdapter) {
        this.id = responseAdapter.getId();
        this.nome = responseAdapter.getName();
        this.documento = responseAdapter.getDocument();
        this.telefone = responseAdapter.getTelephone();
    }
}
