package com.statista.booking.util.email;

public interface IEmailService {

    void sendEmail(String to, String subject, String body);

}
