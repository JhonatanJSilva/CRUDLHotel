package com.hotel.hotel.util;

import com.hotel.hotel.adapter.guest.GuestRequestAdapter;
import com.hotel.hotel.adapter.guest.GuestResponseAdapter;
import com.hotel.hotel.entity.Guest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GuestMapper {

    public Guest toGuest(GuestRequestAdapter guestAdapter) {
        return Guest.builder()
                .name(guestAdapter.getName())
                .document(guestAdapter.getDocument())
                .telephone(guestAdapter.getTelephone())
                .build();
    }

    public GuestResponseAdapter toGuestResponse(Guest guest) {
        return new GuestResponseAdapter(guest);
    }

    public List<GuestResponseAdapter> toGuestsDTO(List<Guest> guestsList) {
        return guestsList.stream().map(GuestResponseAdapter::new).collect(Collectors.toList());
    }

    public void updateGuestData(Guest guest, GuestRequestAdapter guestRequestAdapter) {
        guest.setName(guestRequestAdapter.getName());
        guest.setDocument(guestRequestAdapter.getDocument());
        guest.setTelephone(guestRequestAdapter.getTelephone());
    }
}
