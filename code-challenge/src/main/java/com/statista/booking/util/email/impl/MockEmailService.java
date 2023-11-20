package com.statista.booking.util.email.impl;

import com.statista.booking.util.email.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MockEmailService implements IEmailService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("Sending mock email");
        log.info("To: {}", to);
        log.info("Subject: {}", subject);
        log.info("Body: {}", body);
    }
}
