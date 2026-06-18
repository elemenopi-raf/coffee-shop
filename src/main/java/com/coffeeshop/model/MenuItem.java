package com.coffeeshop.model;

<<<<<<< Updated upstream
import java.math.BigDecimal;

public class MenuItem {
=======
/**
 * Represents a menu item in the coffee shop.
 */
public final class MenuItem {

    /** The unique identifier. */
>>>>>>> Stashed changes
    private int id;

    /** The item name. */
    private String name;
<<<<<<< Updated upstream
    private BigDecimal price;
    private String category;
    private String imageUrl;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
=======

    /** The item price as a string. */
    private String price;

    /** The icon class or path. */
    private String icon;

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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the price to set
     */
    public void setPrice(final String price) {
        this.price = price;
    }

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the icon.
     *
     * @param icon the icon to set
     */
    public void setIcon(final String icon) {
        this.icon = icon;
    }
>>>>>>> Stashed changes
}
