package com.hotel.hotel.service;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.dto.checkin.CheckinRequestDTO;
import com.hotel.hotel.dto.checkin.CheckinResponseDTO;
import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.repository.CheckinRepository;
import com.hotel.hotel.util.CheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class CheckinService {

    private final CheckinRepository checkinRepository;

    private final CheckinMapper checkinMapper;

    private final static BigDecimal VALOR_DIA_DE_SEMANA = BigDecimal.valueOf(120.00);
    private final static BigDecimal VALORFINALDESEMANA  = BigDecimal.valueOf(150.00);

    public CheckInResponseAdapter findById(Long id) {
        return checkinMapper.toChekinResponse(returnCheckin(id));
    }

    public List<CheckInResponseAdapter> findAll() {
        return checkinMapper.toChekinsDTO(checkinRepository.findAll());
    }

    public CheckInResponseAdapter register(CheckInRequestAdapter checkInRequest) {
        //LocalDateTime data = checkinDTO.getDateCheckin();

        //checkinDTO.setValueHosting(BigDecimal.valueOf(1522));

        CheckIn checkin = checkinMapper.toCheckin(checkInRequest);

        return checkinMapper.toChekinResponse(checkinRepository.save(checkin));
    }

    public CheckInResponseAdapter update(Long id, CheckInRequestAdapter checkInRequest) {
        CheckIn checkin = returnCheckin(id);

        checkinMapper.updateChekinData(checkin, checkInRequest);

        return checkinMapper.toChekinResponse(checkinRepository.save(checkin));
    }

    public String delete(Long id) {
        checkinRepository.deleteById(id);
        return "Checkin:" + id + " deleted.";
    }

    private CheckIn returnCheckin(Long id) {
        return checkinRepository.findById(id).orElseThrow(() -> new RuntimeException("Checkin not found."));

    }
}
