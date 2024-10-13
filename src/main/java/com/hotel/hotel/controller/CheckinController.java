package com.hotel.hotel.controller;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.dto.checkin.CheckinRequestDTO;
import com.hotel.hotel.dto.checkin.CheckinResponseDTO;
import com.hotel.hotel.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/checkin")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckInService checkinService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CheckinRequestDTO checkinRequestDTO, UriComponentsBuilder uriBuilder) {
        try {
            CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);
            CheckInResponseAdapter checkInResponseAdapter = checkinService.create(checkInRequestAdapter);
            CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);

            URI uri = uriBuilder.path("/checkin/{id}").buildAndExpand(checkinResponseDTO.getId()).toUri();

            return ResponseEntity.created(uri).body(checkinResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBiId(@PathVariable(name = "id") Long id) {
        try {
            CheckInResponseAdapter checkInResponseAdapter = checkinService.findById(id);
            CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);

            return ResponseEntity.ok().body(checkinResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody CheckinRequestDTO checkinRequestDTO, @PathVariable(name = "id") long id) {
        try {
            CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);
            CheckInResponseAdapter checkInResponseAdapter = checkinService.update(id, checkInRequestAdapter);
            CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);

            return ResponseEntity.ok().body(checkinResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(checkinService.delete(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        try {
            List<CheckInResponseAdapter> checkInResponseAdapters = checkinService.list();
            List<CheckinResponseDTO> checkinResponseDTO = checkInResponseAdapters.stream().map(CheckinResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(checkinResponseDTO);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/valueTotalAllGuests")
    public ResponseEntity<?> valueTotalAllGuests() {
        try {
            List<Map<Integer, BigDecimal>> checkInResponseAdapters = checkinService.valueTotalAllGuests();

            return ResponseEntity.ok().body(checkInResponseAdapters);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/guestsAtTheHotel/{guestsAtTheHotel}")
    public ResponseEntity<?> guestsAtTheHotel(@PathVariable(value = "guestsAtTheHotel") Boolean guestsAtTheHotel) {
        try {
            List<CheckinResponseDTO> checkinResponseDTO = checkinService.guestsAtTheHotel(guestsAtTheHotel).stream().map(CheckinResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(checkinResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }
}
