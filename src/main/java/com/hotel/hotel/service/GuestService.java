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
//        if (guestAdapter.getName()){
//            throw new RuntimeException("Campo nome invalido")
//        }

        Guest guest = guestMapper.toGuest(guestAdapter);

        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public GuestResponseAdapter findById(Long id) {
        return guestMapper.toGuestResponse(returnGuest(id));
    }

    public GuestResponseAdapter findByName(String name) {
        return guestMapper.toGuestResponse(guestRepository.findByName(name));
    }

    public GuestResponseAdapter findByDocument(String document) {
        return guestMapper.toGuestResponse(guestRepository.findByDocument(document));
    }

    public GuestResponseAdapter findByTelephone(String telephone) {
        return guestMapper.toGuestResponse(guestRepository.findByTelephone(telephone));
    }

    public List<GuestResponseAdapter> list(GuestRequestAdapter guestRequestAdapter) {
        if (guestRequestAdapter.getName() != null) {
            return (List<GuestResponseAdapter>) guestMapper.toGuestResponse(guestRepository.findByName(guestRequestAdapter.getName()));
        }

        return guestMapper.toGuestsDTO(guestRepository.findAll());
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

    private Guest returnGuest(Long id) {
        return  guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found."));

    }
}
