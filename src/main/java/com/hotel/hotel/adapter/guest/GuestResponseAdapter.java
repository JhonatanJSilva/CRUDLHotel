package com.hotel.hotel.adapter.guest;

import com.hotel.hotel.entity.Guest;
import lombok.Getter;

@Getter
public class GuestResponseAdapter {

    private Long id;

    private String name;

    private String document;

    private String telephone;

    public GuestResponseAdapter(Guest guest) {
        this.id = guest.getId();
        this.name = guest.getName();
        this.document = guest.getDocument();
        this.telephone = guest.getTelephone();
    }
}
