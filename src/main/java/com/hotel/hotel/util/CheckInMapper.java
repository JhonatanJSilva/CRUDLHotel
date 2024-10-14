package com.hotel.hotel.util;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.CheckIn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckInMapper {

    public CheckIn toCheckin(CheckInRequestAdapter checkInRequest) {
        return com.hotel.hotel.entity.CheckIn.builder()
                .guest(checkInRequest.getGuest())
                .checkInDate(checkInRequest.getCheckInDate())
                .checkoutDate(checkInRequest.getCheckoutDate())
                .additionalVehicle(checkInRequest.getAdditionalVehicle())
                .hostingValue(checkInRequest.getHostingValue())
                .build();
    }

    public CheckInResponseAdapter toChekinResponse(CheckIn checkIn) {
        return new CheckInResponseAdapter(checkIn);
    }

    public List<CheckInResponseAdapter> toChekinsDTO(List<CheckIn> checkInList) {
        return checkInList.stream().map(CheckInResponseAdapter::new).collect(Collectors.toList());
    }

    public void updateChekinData(CheckIn checkIn, CheckInRequestAdapter checkInDTO) {
        checkIn.setGuest(checkInDTO.getGuest());
        checkIn.setCheckInDate(checkInDTO.getCheckInDate());
        checkIn.setCheckoutDate(checkInDTO.getCheckoutDate());
        checkIn.setAdditionalVehicle(checkInDTO.getAdditionalVehicle());
        checkIn.setHostingValue(checkInDTO.getHostingValue());
    }
}
