package com.hotel.hotel.service;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.repository.CheckinRepository;
import com.hotel.hotel.util.CheckinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CheckinService {

    private final CheckinRepository checkinRepository;

    private final CheckinMapper checkinMapper;

    private final static BigDecimal WEEKDAY_VALUE = BigDecimal.valueOf(120.00);
    private final static BigDecimal WEEKEND_VALUE = BigDecimal.valueOf(150.00);
    private final static BigDecimal WEEKDAY_GARAGE_VALUE = BigDecimal.valueOf(15.00);
    private final static BigDecimal WEEKEND_GARAGE_VALUE = BigDecimal.valueOf(20.00);

    public CheckInResponseAdapter findById(Long id) {
        return checkinMapper.toChekinResponse(returnCheckin(id));
    }

    public List<CheckInResponseAdapter> list() {
        return checkinMapper.toChekinsDTO(checkinRepository.findAll());
    }

    public List<CheckInResponseAdapter> listGuestsAtTheHotel() {

        List<CheckIn> listCheckIns = checkinRepository.findAll();

        LocalDate today = LocalDate.now();

        List<CheckIn> listAtTheHotel = listCheckIns.stream().filter(checkIn -> checkIn.getCheckoutDate().isAfter(today.atStartOfDay())).collect(Collectors.toList());

        return checkinMapper.toChekinsDTO(listAtTheHotel);
    }

    public List<CheckInResponseAdapter> listGuestsAreNotInTheHotel() {

        List<CheckIn> listCheckIns = checkinRepository.findAll();

        LocalDate today = LocalDate.now();

        List<CheckIn> listAtTheHotel = listCheckIns.stream().filter(checkIn -> checkIn.getCheckoutDate().isBefore(today.atStartOfDay())).collect(Collectors.toList());

        return checkinMapper.toChekinsDTO(listAtTheHotel);
    }

    public CheckInResponseAdapter create(CheckInRequestAdapter checkInRequest) {
        checkInRequest.setValueHosting(calculateAccommodationValue(checkInRequest));

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

    private BigDecimal calculateAccommodationValue(CheckInRequestAdapter checkInRequest) {
        LocalDateTime startDate = checkInRequest.getDateCheckin();
        LocalDateTime endDate = checkInRequest.getDateCheckout();

        int weekday = 0;
        int weekend = 0;


        while (startDate.isBefore(endDate)) {
            DayOfWeek dayOfWeek = startDate.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                weekday++;
            } else {
                weekend++;
            }
            startDate = startDate.plusDays(1);
        }

        LocalTime limitHour = LocalTime.of(16, 30);

        if(endDate.toLocalTime().isBefore(limitHour)) {

            DayOfWeek dayOfWeek = endDate.getDayOfWeek();

            if(dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                weekday--;
            } else {
                weekend--;
            }
        }

        BigDecimal valueDaysOfWeek = (WEEKDAY_VALUE.multiply(BigDecimal.valueOf(weekday)));
        BigDecimal valueDaysWeekend = (WEEKEND_VALUE.multiply(BigDecimal.valueOf(weekend)));
        BigDecimal valueGarageWeekday = BigDecimal.valueOf(0.0);
        BigDecimal valueGarageWeekend = BigDecimal.valueOf(0.0);

        if(checkInRequest.getAdditionalVehicle()) {
            valueGarageWeekday = (WEEKDAY_GARAGE_VALUE.multiply(BigDecimal.valueOf(weekday)));
            valueGarageWeekend = (WEEKEND_GARAGE_VALUE.multiply(BigDecimal.valueOf(weekend)));
        }

        BigDecimal finalValue = valueDaysOfWeek.add(valueDaysWeekend).add(valueGarageWeekday).add(valueGarageWeekend);

        return (finalValue);
    }
}
