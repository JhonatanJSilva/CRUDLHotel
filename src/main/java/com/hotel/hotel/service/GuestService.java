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

    public GuestResponseAdapter create(GuestRequestAdapter guestRequest) {
        validateFieldsGuestRequest(guestRequest);

        Guest guest = guestMapper.toGuest(guestRequest);
        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public GuestResponseAdapter findById(Long id) {
        validateFieldsId(id);

        return guestMapper.toGuestResponse(returnGuest(id));
    }

    public GuestResponseAdapter update(Long id, GuestRequestAdapter guestRequest) {
        validateFieldsGuestRequest(guestRequest);
        validateFieldsId(id);

        Guest guest = returnGuest(id);
        guestMapper.updateGuestData(guest, guestRequest);
        return guestMapper.toGuestResponse(guestRepository.save(guest));
    }

    public String delete(Long id) {
        validateFieldsId(id);

        guestRepository.deleteById(id);
        return "Hospede id: "+ id +" deletado.";
    }

    public List<GuestResponseAdapter> list() {
        return guestMapper.toGuestsDTO(guestRepository.findAll());
    }

    public GuestResponseAdapter find(GuestRequestAdapter guestRequest) {
        String name = guestRequest.getName();
        String document = guestRequest.getDocument();
        String telephone = guestRequest.getTelephone();

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

    private void validateFieldsGuestRequest(GuestRequestAdapter guestRequest) {
        String name = guestRequest.getName();
        String document = guestRequest.getDocument();
        String telephone = guestRequest.getTelephone();

        if (name == null || name.isEmpty()) throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        if (document == null || document.isEmpty()) throw new IllegalArgumentException("O documento não pode ser nulo ou vazio.");
        if (telephone == null || telephone.isEmpty()) throw new IllegalArgumentException("O telefone não pode ser nulo ou vazio.");
    }

    private void validateFieldsId(Long id) {
        if (id == null) throw new IllegalArgumentException("O ID não pode ser nulo ou vazio.");
    }
}
