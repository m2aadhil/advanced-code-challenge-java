package com.statista.booking.repository;

import com.statista.booking.model.Booking;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class BookingRepository {

    private final Map<UUID, Booking> bookings = new ConcurrentHashMap<>();

    public Booking save(Booking booking) {
        bookings.put(booking.getBookingId(), booking);
        return booking;
    }

    public Optional<Booking> findById(UUID bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public List<Booking> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public List<Booking> findByDepartment(String department) {
        List<Booking> departmentBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getDepartment().equalsIgnoreCase(department)) {
                departmentBookings.add(booking);
            }
        }
        return departmentBookings;
    }

    public boolean delete(UUID bookingId) {
        return bookings.remove(bookingId) != null;
    }
}
