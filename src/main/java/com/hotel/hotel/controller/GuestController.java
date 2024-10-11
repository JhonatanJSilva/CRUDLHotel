package com.hotel.hotel.controller;

import com.hotel.hotel.adapter.guest.GuestRequestAdapter;
import com.hotel.hotel.adapter.guest.GuestResponseAdapter;
import com.hotel.hotel.dto.guest.GuestRequestDTO;
import com.hotel.hotel.dto.guest.GuestResponseDTO;
import com.hotel.hotel.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/create")
    public ResponseEntity<GuestResponseDTO> create(@RequestBody @Valid GuestRequestDTO guestRequestDTO, UriComponentsBuilder uriBuilder) {
        GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestRequestDTO);

        GuestResponseAdapter guestResponseAdapter = guestService.create(guestRequestAdapter);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        URI uri = uriBuilder.path("/guest/{id}").buildAndExpand(guestResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(guestResponseDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<GuestResponseDTO>> list(GuestRequestDTO guestRequestDTO) {
        GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestRequestDTO);

        List<GuestResponseAdapter> guestResponseAdapter = guestService.list(guestRequestAdapter);

        List<GuestResponseDTO> guestResponseDTO = guestResponseAdapter.stream().map(GuestResponseDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(guestResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> findById(@PathVariable Long id) {
        GuestResponseAdapter guestResponseAdapter = guestService.findById(id);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        return ResponseEntity.ok().body(guestResponseDTO);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<GuestResponseDTO> findByName(@PathVariable(name = "name") String name) {
        GuestResponseAdapter guestResponseAdapter = guestService.findByName(name);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        return ResponseEntity.ok().body(guestResponseDTO);
    }

    @GetMapping(value = "/document/{document}")
    public ResponseEntity<GuestResponseDTO> findByDocument(@PathVariable(name = "document") String document) {
        GuestResponseAdapter guestResponseAdapter = guestService.findByDocument(document);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        return ResponseEntity.ok().body(guestResponseDTO);
    }

    @GetMapping(value = "/telephone/{telephone}")
    public ResponseEntity<GuestResponseDTO> findByTelephone(@PathVariable(name = "telephone") String telephone) {
        GuestResponseAdapter guestResponseAdapter = guestService.findByTelephone(telephone);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        return ResponseEntity.ok().body(guestResponseDTO);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GuestResponseDTO> update(@RequestBody @Valid GuestRequestDTO guestDTO, @PathVariable(name = "id") long id) {
        GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestDTO);

        GuestResponseAdapter guestResponseAdapter = guestService.update(id, guestRequestAdapter);

        GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

        return ResponseEntity.ok().body(guestResponseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(guestService.delete(id));
    }
}
