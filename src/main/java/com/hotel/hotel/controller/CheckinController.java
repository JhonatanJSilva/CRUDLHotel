package com.hotel.hotel.controller;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.dto.checkin.CheckinRequestDTO;
import com.hotel.hotel.dto.checkin.CheckinResponseDTO;
import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.service.CheckinService;
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

    private final CheckinService checkinService;

    @PostMapping("/create")
    public ResponseEntity<CheckinResponseDTO> create(@RequestBody CheckinRequestDTO checkinRequestDTO, UriComponentsBuilder uriBuilder) {
        CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);

        CheckInResponseAdapter checkInResponseAdapter = checkinService.create(checkInRequestAdapter);

        CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);

        URI uri = uriBuilder.path("/checkin/{id}").buildAndExpand(checkinResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(checkinResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckinResponseDTO> findBiId(@PathVariable(name = "id") Long id) {
        CheckInResponseAdapter checkInResponseAdapter = checkinService.findById(id);

        CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);
        
        return ResponseEntity.ok().body(checkinResponseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CheckinResponseDTO> update(@RequestBody CheckinRequestDTO checkinRequestDTO, @PathVariable(name = "id") long id) {
        CheckInRequestAdapter checkInRequestAdapter = new CheckInRequestAdapter(checkinRequestDTO);

        CheckInResponseAdapter checkInResponseAdapter = checkinService.update(id, checkInRequestAdapter);

        CheckinResponseDTO checkinResponseDTO = new CheckinResponseDTO(checkInResponseAdapter);

        return ResponseEntity.ok().body(checkinResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(checkinService.delete(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<CheckinResponseDTO>> list() {
        List<CheckInResponseAdapter> checkInResponseAdapters = checkinService.list();

        List<CheckinResponseDTO> checkinResponseDTO = checkInResponseAdapters.stream().map(CheckinResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(checkinResponseDTO);
    }

    @GetMapping("/valueTotalAllGuests")
    public ResponseEntity<List<Map<Integer, BigDecimal>>> valueTotalAllGuests() {
        List<Map<Integer, BigDecimal>> checkInResponseAdapters = checkinService.valueTotalAllGuests();

        return ResponseEntity.ok().body(checkInResponseAdapters);
    }

    @GetMapping("/guestsAtTheHotel/{guestsAtTheHotel}")
    public ResponseEntity<List<CheckinResponseDTO>> guestsAtTheHotel(@PathVariable(value = "guestsAtTheHotel") Boolean guestsAtTheHotel) {
        List<CheckinResponseDTO> checkinResponseDTO = checkinService.guestsAtTheHotel(guestsAtTheHotel).stream().map(CheckinResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(checkinResponseDTO);
    }
}

