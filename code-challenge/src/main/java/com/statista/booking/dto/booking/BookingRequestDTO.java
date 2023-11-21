package com.statista.booking.dto.booking;

import com.statista.booking.enums.Department;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Currency;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookingRequestDTO {
    @NotNull
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull
    @Min(value = 0, message = "Price should be greater than 0")
    private BigDecimal price;

    private String currency = "USD";

    private LocalDate subscriptionStartDate = LocalDate.now();

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    private Department department;
}