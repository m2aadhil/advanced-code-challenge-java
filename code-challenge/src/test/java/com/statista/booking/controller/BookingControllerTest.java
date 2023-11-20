package com.statista.booking.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statista.booking.dto.booking.BookingRequestDTO;
import com.statista.booking.enums.Department;
import com.statista.booking.model.Booking;
import com.statista.booking.service.booking.BookingService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookingController.class)
public class BookingControllerTest{

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    private Booking sampleBooking;

    @BeforeEach
    void setup() {
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
    void testCreateBooking() throws Exception {
        BookingRequestDTO requestDTO = new
            BookingRequestDTO("Test Booking", new BigDecimal("100.00"), "USD", LocalDate.of(2023,02,01), "test@email.com", Department.SALES);

        when(bookingService.createBooking(requestDTO)).thenReturn(sampleBooking);

        this.mockMvc.perform(post("/bookingservice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.description").value("Test Booking"));
    }

    @Test
    void testUpdateBooking() throws Exception {
        BookingRequestDTO requestDTO = new
            BookingRequestDTO("Test Booking", new BigDecimal("100.00"), "USD", LocalDate.of(2023,02,01), "test@email.com", Department.SALES);

        this.mockMvc.perform(put("/bookingservice/"+ sampleBooking.getBookingId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
            .andExpect(status().isOk());

        verify(bookingService).updateBooking(sampleBooking.getBookingId(), requestDTO);
    }

    @Test
    void testGetBooking() throws Exception {
        this.mockMvc.perform(get("/bookingservice/"+ sampleBooking.getBookingId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(bookingService).getBookingById(sampleBooking.getBookingId());
    }
}
