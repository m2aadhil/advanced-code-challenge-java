package com.statista.booking.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.statista.booking.model.Booking;

@SpringBootTest(classes = BookingRepository.class)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    private Booking sampleBooking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sampleBooking = Booking.builder()
            .bookingId(UUID.randomUUID())
            .description("Test Booking")
            .price(new BigDecimal("100.00"))
            .department("SALES")
            .currency(Currency.getInstance("USD"))
            .subscriptionStartDate(LocalDate.of(2023,02,01))
            .email("test@email.com")
            .build();
    }

    @Test
    void testSaveAndGetBooking() {
        bookingRepository.save(sampleBooking);
        Booking foundBooking = bookingRepository.findById(sampleBooking.getBookingId()).orElse(null);
        assertNotNull(foundBooking);
        assertEquals(sampleBooking.getBookingId(), foundBooking.getBookingId());
    }

    @Test
    void testFindAllBookings() {
        bookingRepository.save(sampleBooking);
        assertFalse(bookingRepository.findAll().isEmpty());
    }

    @Test
    void testFindByDepartment() {
        bookingRepository.save(sampleBooking);
        List<Booking> bookings = bookingRepository.findByDepartment("SALES");
        assertFalse(bookings.isEmpty());
        assertEquals("SALES", bookings.get(0).getDepartment());
    }
}
