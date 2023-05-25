package com.example.shippingservicesystemapi.service;

import org.springframework.stereotype.Service;

public interface EmailSenderService {
    void send(String email, String content);
}
