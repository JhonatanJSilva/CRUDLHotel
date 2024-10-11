package com.hotel.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_in")
@NoArgsConstructor
@Getter
@Setter
public class CheckIn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest")
    private Guest guest;

    @Column(name = "check_in_date", nullable = false)
    private LocalDateTime checkInDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkoutDate;

    @Column(name = "additional_vehicle", nullable = false)
    private Boolean additionalVehicle;

    @Column(name = "hosting_value", nullable = false)
    private BigDecimal hostingValue;

    @Builder
    public CheckIn(Long id, Guest guest, LocalDateTime checkInDate, LocalDateTime checkoutDate, Boolean additionalVehicle, BigDecimal hostingValue) {
        this.id = id;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkoutDate = checkoutDate;
        this.additionalVehicle = additionalVehicle;
        this.hostingValue = hostingValue;
    }
}
