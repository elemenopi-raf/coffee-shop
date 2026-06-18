package com.coffeeshop.model;

/**
 * Represents a customer testimonial displayed on the website.
 */
public final class Testimonial {

    /** The unique identifier. */
    private int id;

    /** The testimonial text. */
    private String text;

    /** The author's name. */
    private String author;

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
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text.
     *
     * @param text the text to set
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Gets the author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     *
     * @param author the author to set
     */
    public void setAuthor(final String author) {
        this.author = author;
    }
}
