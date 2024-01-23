package com.enviro.assessment.grad001.kamielahheuvel.Services;

import org.springframework.stereotype.Service;
@Service
public class NotificationServcie {

    public void sendNotification(String Name, String subject, String message) {

        System.out.println("Sending notification to: " + Name);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
    
}
