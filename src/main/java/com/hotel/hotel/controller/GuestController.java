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
    public ResponseEntity<?> create(@RequestBody @Valid GuestRequestDTO guestRequestDTO, UriComponentsBuilder uriBuilder) {
        try {
            GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestRequestDTO);
            GuestResponseAdapter guestResponseAdapter = guestService.create(guestRequestAdapter);
            GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

            URI uri = uriBuilder.path("/guest/create/{id}").buildAndExpand(guestResponseDTO.getId()).toUri();

            return ResponseEntity.created(uri).body(guestResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            GuestResponseAdapter guestResponseAdapter = guestService.findById(id);
            GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

            return ResponseEntity.ok().body(guestResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid GuestRequestDTO guestDTO, @PathVariable(name = "id") long id) {
        try {
            GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestDTO);
            GuestResponseAdapter guestResponseAdapter = guestService.update(id, guestRequestAdapter);
            GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

            return ResponseEntity.ok().body(guestResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(guestService.delete(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        try {
            List<GuestResponseAdapter> guestResponseAdapter = guestService.list();
            List<GuestResponseDTO> guestResponseDTO = guestResponseAdapter.stream().map(GuestResponseDTO::new).collect(Collectors.toList());

            return ResponseEntity.ok().body(guestResponseDTO);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }

    @GetMapping(value = "/find")
    public ResponseEntity<?> find(@RequestBody GuestRequestDTO guestRequestDTO) {
        try {
            GuestRequestAdapter guestRequestAdapter = new GuestRequestAdapter(guestRequestDTO);
            GuestResponseAdapter guestResponseAdapter = guestService.find(guestRequestAdapter);
            GuestResponseDTO guestResponseDTO = new GuestResponseDTO(guestResponseAdapter);

            return ResponseEntity.ok().body(guestResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + exception.getMessage());
        }
    }
}
