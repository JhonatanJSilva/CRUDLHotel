package com.hotel.hotel.service;

import com.hotel.hotel.adapter.guest.GuestRequestAdapter;
import com.hotel.hotel.adapter.guest.GuestResponseAdapter;
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
        String name = guestAdapter.getName();
        String document = guestAdapter.getDocument();
        String telephone = guestAdapter.getTelephone();

        if (name == null || name.isEmpty()) throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        if (document == null || document.isEmpty()) throw new IllegalArgumentException("O documento não pode ser nulo ou vazio.");
        if (telephone == null || telephone.isEmpty()) throw new IllegalArgumentException("O telefone não pode ser nulo ou vazio.");

        Guest guest = guestMapper.toGuest(guestAdapter);
        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public GuestResponseAdapter findById(Long id) {
        if (id == null) throw new IllegalArgumentException("O ID não pode ser nulo ou vazio.");

        return guestMapper.toGuestResponse(returnGuest(id));
    }

    public GuestResponseAdapter update(Long id, GuestRequestAdapter guestAdapter) {
        String name = guestAdapter.getName();
        String document = guestAdapter.getDocument();
        String telephone = guestAdapter.getTelephone();

        if (name == null || name.isEmpty()) throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        if (document == null || document.isEmpty()) throw new IllegalArgumentException("O documento não pode ser nulo ou vazio.");
        if (telephone == null || telephone.isEmpty()) throw new IllegalArgumentException("O telefone não pode ser nulo ou vazio.");
        if (id == null) throw new IllegalArgumentException("O ID não pode ser nulo ou vazio.");

        Guest guest = returnGuest(id);
        guestMapper.updateGuestData(guest, guestAdapter);
        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public String delete(Long id) {
        if (id == null) throw new IllegalArgumentException("O ID não pode ser nulo ou vazio.");

        guestRepository.deleteById(id);
        return "Hospede id: "+ id +" deletado.";
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

        throw new IllegalArgumentException("Hospede não encontrado.");
    }

    private Guest returnGuest(Long id) {
        return  guestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Hospede não encontrado."));
    }
}
