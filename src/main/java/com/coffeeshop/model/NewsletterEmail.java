package com.coffeeshop.model;

import java.time.LocalDateTime;

public class NewsletterEmail {
    private int id;
    private String email;
    private LocalDateTime subscribedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getSubscribedAt() { return subscribedAt; }
    public void setSubscribedAt(LocalDateTime subscribedAt) { this.subscribedAt = subscribedAt; }
}
