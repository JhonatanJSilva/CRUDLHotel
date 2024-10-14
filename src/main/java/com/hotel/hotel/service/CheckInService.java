package com.hotel.hotel.service;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.entity.Guest;
import com.hotel.hotel.repository.CheckInRepository;
import com.hotel.hotel.util.CheckInMapper;
import com.hotel.hotel.util.CustomDateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@Primary
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;

    private final static BigDecimal WEEKDAY_VALUE = BigDecimal.valueOf(120.00);
    private final static BigDecimal WEEKEND_VALUE = BigDecimal.valueOf(150.00);
    private final static BigDecimal WEEKDAY_GARAGE_VALUE = BigDecimal.valueOf(15.00);
    private final static BigDecimal WEEKEND_GARAGE_VALUE = BigDecimal.valueOf(20.00);
    private final static LocalTime CHECKOUT_HOUR_LIMIT = LocalTime.of(16, 30);

    public CheckInResponseAdapter create(CheckInRequestAdapter checkInRequest) {
        validateFieldsCheckInRequest(checkInRequest);

        checkInRequest.setHostingValue(calculateAccommodationValue(checkInRequest));
        CheckIn checkIn = checkInMapper.toCheckin(checkInRequest);
        return checkInMapper.toChekinResponse(checkInRepository.save(checkIn));
    }

    public CheckInResponseAdapter findById(Long id) {
        validateFieldsId(id);

        return checkInMapper.toChekinResponse(returnCheckIn(id));
    }

    public CheckInResponseAdapter update(Long id, CheckInRequestAdapter checkInRequest) {
        validateFieldsCheckInRequest(checkInRequest);
        validateFieldsId(id);

        checkInRequest.setHostingValue(calculateAccommodationValue(checkInRequest));
        CheckIn checkin = returnCheckIn(id);
        checkInMapper.updateChekinData(checkin, checkInRequest);
        return checkInMapper.toChekinResponse(checkInRepository.save(checkin));
    }

    public String delete(Long id) {
        validateFieldsId(id);

        checkInRepository.deleteById(id);
        return "Check in:" + id + " deletado.";
    }

    public List<CheckInResponseAdapter> list() {
        return checkInMapper.toChekinsDTO(checkInRepository.findAll());
    }

    public List<Map<Integer, BigDecimal>> valueTotalAllGuests() {
        return checkInRepository.valueTotalAllGuests(LocalDate.now());
    }

    public List<CheckInResponseAdapter> guestsAtTheHotel(Boolean guestsAtTheHotel) {
        if (guestsAtTheHotel == null) throw new IllegalArgumentException("O boolean não pode ser nulo ou vazio.");

        if (guestsAtTheHotel) {
            return checkInMapper.toChekinsDTO(checkInRepository.guestsAtTheHotel(LocalDate.now()));
        }

        return checkInMapper.toChekinsDTO(checkInRepository.guestsAreNotInTheHotel(LocalDate.now()));
    }

    private CheckIn returnCheckIn(Long id) {
        return checkInRepository.findById(id).orElseThrow(() -> new RuntimeException("Check in nao encontrado."));
    }

    private BigDecimal calculateAccommodationValue(CheckInRequestAdapter checkInRequest) {
        LocalDateTime startDate = checkInRequest.getCheckInDate();
        LocalDateTime endDate = checkInRequest.getCheckoutDate();

        int weekday = 0;
        int weekend = 0;

        while (startDate.isBefore(endDate)) {
            DayOfWeek dayOfWeek = startDate.getDayOfWeek();
            startDate = startDate.plusDays(1);

            if (startDate.getDayOfYear() == endDate.getDayOfYear() && !endDate.toLocalTime().isAfter(CHECKOUT_HOUR_LIMIT)) continue;

            if (!CustomDateUtils.isWeekendDay(dayOfWeek)) {
                weekday++;
            } else {
                weekend++;
            }
        }

        BigDecimal weekdayTotalValue = WEEKDAY_VALUE;
        BigDecimal weekendTotalValue = WEEKEND_VALUE;

        if (checkInRequest.getAdditionalVehicle()) {
            weekdayTotalValue = weekdayTotalValue.add(WEEKDAY_GARAGE_VALUE);
            weekendTotalValue = weekendTotalValue.add(WEEKEND_GARAGE_VALUE);
        }

        BigDecimal valueDaysOfWeek = (weekdayTotalValue.multiply(BigDecimal.valueOf(weekday)));
        BigDecimal valueDaysWeekend = (weekendTotalValue.multiply(BigDecimal.valueOf(weekend)));

        return (valueDaysOfWeek.add(valueDaysWeekend));
    }

    private void validateFieldsCheckInRequest(CheckInRequestAdapter checkInRequest) {
        Guest guest = checkInRequest.getGuest();
        LocalDateTime checkInDate = checkInRequest.getCheckInDate();
        LocalDateTime checkoutDate = checkInRequest.getCheckoutDate();
        Boolean additionalVehicle = checkInRequest.getAdditionalVehicle();

        if (guest == null) throw new IllegalArgumentException("O ID do hospede não pode ser nulo ou vazio.");
        if (checkInDate == null) throw new IllegalArgumentException("A data de check in não pode ser nulo ou vazio.");
        if (checkoutDate == null) throw new IllegalArgumentException("A data de checkout não pode ser nulo ou vazio.");
        if (additionalVehicle == null) throw new IllegalArgumentException("O adicional veiculo não pode ser nulo ou vazio.");
    }

    private void validateFieldsId(Long id) {
        if (id == null) throw new IllegalArgumentException("O ID não pode ser nulo ou vazio.");
    }
}
