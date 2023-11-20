package com.statista.booking.service.booking;

import com.statista.booking.dto.booking.BookingRequestDTO;
import com.statista.booking.enums.Department;
import com.statista.booking.service.departments.factory.DepartmentServiceFactory;
import com.statista.booking.model.Booking;
import com.statista.booking.repository.BookingRepository;
import com.statista.booking.service.departments.IDepartmentService;
import com.statista.booking.util.email.IEmailService;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DepartmentServiceFactory departmentServiceFactory;
    private final IEmailService emailService;

    public Booking createBooking(BookingRequestDTO booking) {
        Booking newBooking = Booking.builder()
            .bookingId(UUID.randomUUID())
            .description(booking.getDescription())
            .price(booking.getPrice())
            .currency(Currency.getInstance(booking.getCurrency()))
            .subscriptionStartDate(booking.getSubscriptionStartDate())
            .email(booking.getEmail())
            .department(booking.getDepartment().name()).build();

        Booking createdBooking = bookingRepository.save(newBooking);

        emailService.sendEmail(booking.getEmail(), "Booking Confirmation", "Your booking details has been created...");

        return createdBooking;
    }

    public Booking updateBooking(UUID bookingId, BookingRequestDTO booking) {
        Booking updateBooking = Booking.builder()
            .bookingId(bookingId)
            .description(booking.getDescription())
            .price(booking.getPrice())
            .currency(Currency.getInstance(booking.getCurrency()))
            .subscriptionStartDate(booking.getSubscriptionStartDate())
            .email(booking.getEmail())
            .department(booking.getDepartment().name()).build();

        Booking updatedBooking = bookingRepository.save(updateBooking);
        emailService.sendEmail(booking.getEmail(), "Booking Confirmation", "Your booking details has been updated...");
        return updatedBooking;
    }

    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<Booking> getBookingsByDepartment(Department department) {
        return bookingRepository.findByDepartment(department.name());
    }

    public List<String> getAllCurrencies() {
        return bookingRepository.findAll().stream()
            .map(Booking::getCurrency)
            .distinct()
            .collect(Collectors.toList());
    }

    public BigDecimal getTotalAmountForCurrency(String currency) {
        return bookingRepository.findAll().stream()
            .filter(booking -> booking.getCurrency().equalsIgnoreCase(currency))
            .map(Booking::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String performDepartmentBusiness(UUID bookingId) {
        Booking booking = getBookingById(bookingId);
        IDepartmentService departmentService = departmentServiceFactory.getDepartmentService(booking.getDepartment());
        return departmentService.doBusiness();
    }
}
