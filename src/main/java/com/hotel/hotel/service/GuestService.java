package com.hotel.hotel.service;

import com.hotel.hotel.adapter.guest.GuestRequestAdapter;
import com.hotel.hotel.adapter.guest.GuestResponseAdapter;
import com.hotel.hotel.dto.guest.GuestRequestDTO;
import com.hotel.hotel.dto.guest.GuestResponseDTO;
import com.hotel.hotel.entity.Guest;
import com.hotel.hotel.repository.GuestRepository;
import com.hotel.hotel.util.GuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestResponseAdapter create(GuestRequestAdapter guestAdapter) {
        Guest guest = guestMapper.toGuest(guestAdapter);

        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public GuestResponseAdapter findById(Long id) {
        return guestMapper.toGuestResponse(returnGuest(id));
    }

    public GuestResponseAdapter update(Long id, GuestRequestAdapter guestRequest) {
        Guest guest = returnGuest(id);

        guestMapper.updateGuestData(guest, guestRequest);

        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public String delete(Long id) {
        guestRepository.deleteById(id);
        return "Guest id: "+ id +" deleted.";
    }

    public List<GuestResponseAdapter> list() {
        return guestMapper.toGuestsDTO(guestRepository.findAll());
    }

    public GuestResponseAdapter find(GuestRequestAdapter requestAdapter) {
        String name = requestAdapter.getName();
        String document = requestAdapter.getDocument();
        String telephone = requestAdapter.getTelephone();

        if (name != null) {
             Guest guestByName = guestRepository.findByName(name);
             if (guestByName != null ) {
                 return guestMapper.toGuestResponse(guestByName);
             }
        }

        if (document != null) {
            Guest guestByDocument = guestRepository.findByDocument(document);
            if (guestByDocument != null) {
                return guestMapper.toGuestResponse(guestByDocument);
            }
        }

        if (telephone != null) {
            Guest guestByTelephone = guestRepository.findByTelephone(telephone);
            if (guestByTelephone != null) {
                return guestMapper.toGuestResponse(guestByTelephone);
            }
        }

        throw new RuntimeException("Guest not found.");
    }

    private Guest returnGuest(Long id) {
        return  guestRepository.findById(id).orElseThrow(() -> new RuntimeException("Guest not found."));
    }
}
