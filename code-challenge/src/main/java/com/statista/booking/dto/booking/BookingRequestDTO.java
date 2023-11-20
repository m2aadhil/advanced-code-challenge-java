package com.statista.booking.dto.booking;

import com.statista.booking.enums.Department;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookingRequestDTO {
    private String description;
    private BigDecimal price;
    private String currency;
    private LocalDate subscriptionStartDate;
    private String email;
    private Department department;
}