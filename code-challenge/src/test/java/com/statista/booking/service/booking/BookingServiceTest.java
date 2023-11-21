package com.statista.booking.service.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.statista.booking.dto.booking.BookingRequestDTO;
import com.statista.booking.enums.Department;
import com.statista.booking.model.Booking;
import com.statista.booking.repository.BookingRepository;
import com.statista.booking.service.departments.IDepartmentService;
import com.statista.booking.service.departments.factory.DepartmentServiceFactory;
import com.statista.booking.util.email.IEmailService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DepartmentServiceFactory departmentServiceFactory;

    @Mock
    private IEmailService emailService;

    @InjectMocks
    private BookingService bookingService;

    private Booking sampleBooking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        //bookingService = new BookingService(bookingRepository, departmentServiceFactory, emailService);
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
    public void testCreateBooking() {
        BookingRequestDTO requestDTO = new
            BookingRequestDTO("Test Booking", new BigDecimal("100.00"), "USD", LocalDate.of(2023,02,01), "test@email.com", Department.SALES);
        when(bookingRepository.save(any(Booking.class))).thenReturn(sampleBooking);
        Booking createdBooking = bookingService.createBooking(requestDTO);
        assertNotNull(createdBooking);
        assertEquals(sampleBooking.getBookingId(), createdBooking.getBookingId());
        verify(emailService).sendEmail(eq(sampleBooking.getEmail()), anyString(), anyString());
    }

    @Test
    public void testGetBookingById() {
        when(bookingRepository.findById(sampleBooking.getBookingId())).thenReturn(Optional.of(sampleBooking));
        Booking foundBooking = bookingService.getBookingById(sampleBooking.getBookingId());
        assertNotNull(foundBooking);
        assertEquals(sampleBooking.getBookingId(), foundBooking.getBookingId());
    }

    @Test
    public void testGetBookingsByDepartment() {
        final String department = "MARKETING";
        when(bookingRepository.findByDepartment(department)).thenReturn(Collections.singletonList(sampleBooking));
        assertFalse(bookingService.getBookingsByDepartment(Department.valueOf(department)).isEmpty());
    }

    @Test
    public void testPerformDepartmentBusiness() {
        when(bookingRepository.findById(sampleBooking.getBookingId())).thenReturn(Optional.of(sampleBooking));
        IDepartmentService departmentService = mock(IDepartmentService.class);
        when(departmentServiceFactory.getDepartmentService("SALES")).thenReturn(departmentService);
        when(departmentService.doBusiness()).thenReturn("Sales Business Performed");

        String businessResult = bookingService.performDepartmentBusiness(sampleBooking.getBookingId());
        assertEquals("Sales Business Performed", businessResult);
    }
}
