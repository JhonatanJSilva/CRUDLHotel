package com.hotel.hotel.util;

import com.hotel.hotel.adapter.checkin.CheckInRequestAdapter;
import com.hotel.hotel.adapter.checkin.CheckInResponseAdapter;
import com.hotel.hotel.entity.CheckIn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckinMapper {

    public CheckIn toCheckin(CheckInRequestAdapter checkInRequest) {
        return CheckIn.builder()
                .guest(checkInRequest.getGuest())
                .checkInDate(checkInRequest.getDateCheckin())
                .checkoutDate(checkInRequest.getDateCheckout())
                .additionalVehicle(checkInRequest.getAdditionalVehicle())
                .hostingValue(checkInRequest.getValueHosting())
                .build();
    }

    public CheckInResponseAdapter toChekinResponse(CheckIn checkin) {
        return new CheckInResponseAdapter(checkin);
    }

    public List<CheckInResponseAdapter> toChekinsDTO(List<CheckIn> checkInList) {
        return checkInList.stream().map(CheckInResponseAdapter::new).collect(Collectors.toList());
    }

    public void updateChekinData(CheckIn checkin, CheckInRequestAdapter checkinDTO) {
        checkin.setGuest(checkinDTO.getGuest());
        checkin.setCheckInDate(checkinDTO.getDateCheckin());
        checkin.setCheckoutDate(checkinDTO.getDateCheckout());
        checkin.setAdditionalVehicle(checkinDTO.getAdditionalVehicle());
        checkin.setHostingValue(checkinDTO.getValueHosting());
    }


}
