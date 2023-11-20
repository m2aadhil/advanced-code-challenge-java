package com.statista.booking.model;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
public class Booking {

    @Getter
    private UUID bookingId;
    @Getter
    private String description;
    @Getter
    private BigDecimal price;
    private Currency currency;
    @Getter
    private LocalDate subscriptionStartDate;
    @Getter
    private String email;
    @Getter
    private String department;

    public String getCurrency() {
        return currency.getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }
}