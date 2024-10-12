package com.hotel.hotel.service;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.CheckIn;
import com.hotel.hotel.repository.CheckinRepository;
import com.hotel.hotel.util.CheckinMapper;
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
    private final static LocalTime CHECKOUT_HOUR_LIMIT = LocalTime.of(16, 30);

    public CheckInResponseAdapter create(CheckInRequestAdapter checkInRequest) {
        checkInRequest.setHostingValue(calculateAccommodationValue(checkInRequest));

        CheckIn checkin = checkinMapper.toCheckin(checkInRequest);

        return checkinMapper.toChekinResponse(checkinRepository.save(checkin));
    }

    public CheckInResponseAdapter findById(Long id) {
        return checkinMapper.toChekinResponse(returnCheckin(id));
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

    public List<CheckInResponseAdapter> list() {
        return checkinMapper.toChekinsDTO(checkinRepository.findAll());
    }

    public List<CheckInResponseAdapter> listGuestsAtTheHotel() {
        List<CheckIn> listCheckIns = checkinRepository.findAll();

        LocalDate today = LocalDate.now();

        List<CheckIn> listAtTheHotel = listCheckIns.stream().filter(checkIn -> checkIn.getCheckoutDate().isAfter(today.atStartOfDay())).collect(Collectors.toList());

        return checkinMapper.toChekinsDTO(listAtTheHotel);
    }

    private CheckIn returnCheckin(Long id) {
        return checkinRepository.findById(id).orElseThrow(() -> new RuntimeException("Checkin not found."));

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
}
