package com.hotel.hotel.controller;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.dto.checkin.CheckInRequestDTO;
import com.hotel.hotel.dto.checkin.CheckInResponseDTO;
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
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CheckInRequestDTO checkinRequestDTO, UriComponentsBuilder uriBuilder) {
        try {
            CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);
            CheckInResponseAdapter checkInResponseAdapter = checkInService.create(checkInRequestAdapter);
            CheckInResponseDTO checkinResponseDTO = new CheckInResponseDTO(checkInResponseAdapter);

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
            CheckInResponseAdapter checkInResponseAdapter = checkInService.findById(id);
            CheckInResponseDTO checkInResponseDTO = new CheckInResponseDTO(checkInResponseAdapter);

            return ResponseEntity.ok().body(checkInResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody CheckInRequestDTO checkinRequestDTO, @PathVariable(name = "id") long id) {
        try {
            CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);
            CheckInResponseAdapter checkInResponseAdapter = checkInService.update(id, checkInRequestAdapter);
            CheckInResponseDTO checkInResponseDTO = new CheckInResponseDTO(checkInResponseAdapter);

            return ResponseEntity.ok().body(checkInResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(checkInService.delete(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        try {
            List<CheckInResponseAdapter> checkInResponseAdapters = checkInService.list();
            List<CheckInResponseDTO> checkInResponseDTO = checkInResponseAdapters.stream().map(CheckInResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(checkInResponseDTO);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/valueTotalAllGuests")
    public ResponseEntity<?> valueTotalAllGuests() {
        try {
            List<Map<Integer, BigDecimal>> checkInResponseAdapters = checkInService.valueTotalAllGuests();

            return ResponseEntity.ok().body(checkInResponseAdapters);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/guestsAtTheHotel/{guestsAtTheHotel}")
    public ResponseEntity<?> guestsAtTheHotel(@PathVariable(value = "guestsAtTheHotel") Boolean guestsAtTheHotel) {
        try {
            List<CheckInResponseDTO> checkInResponseDTO = checkInService.guestsAtTheHotel(guestsAtTheHotel).stream().map(CheckInResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(checkInResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }
}
