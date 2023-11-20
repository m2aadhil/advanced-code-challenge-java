package com.statista.booking.controller;
import com.statista.booking.dto.booking.BookingRequestDTO;
import com.statista.booking.enums.Department;
import com.statista.booking.model.Booking;
import com.statista.booking.service.booking.BookingService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookingservice")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO booking) {
        Booking newBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable UUID bookingId, @RequestBody BookingRequestDTO booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok(updatedBooking);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Booking>> getBookingsByDepartment(@PathVariable Department department) {
        List<Booking> bookings = bookingService.getBookingsByDepartment(department);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<String>> getAllCurrencies() {
        List<String> currencies = bookingService.getAllCurrencies();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping("/sum/{currency}")
    public ResponseEntity<BigDecimal> getTotalAmountForCurrency(@PathVariable String currency) {
        BigDecimal total = bookingService.getTotalAmountForCurrency(currency);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/dobusiness/{bookingId}")
    public ResponseEntity<String> performDepartmentBusiness(@PathVariable UUID bookingId) {
        String businessResult = bookingService.performDepartmentBusiness(bookingId);
        return ResponseEntity.ok(businessResult);
    }
}