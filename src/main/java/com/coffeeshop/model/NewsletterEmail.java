package com.coffeeshop.model;

import java.time.LocalDateTime;

/**
 * Represents a newsletter subscriber email.
 */
public final class NewsletterEmail {

    /** The unique identifier. */
    private int id;

    /** The subscriber email address. */
    private String email;

    /** The timestamp when the email was subscribed. */
    private LocalDateTime subscribedAt;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the subscribedAt.
     *
     * @return the subscribedAt
     */
    public LocalDateTime getSubscribedAt() {
        return subscribedAt;
    }

    /**
     * Sets the subscribedAt.
     *
     * @param subscribedAt the subscribedAt to set
     */
    public void setSubscribedAt(final LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }
}
