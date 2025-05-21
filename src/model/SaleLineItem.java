package model;

/**
 * Represents a line item in a sale transaction
 */
public class SaleLineItem {
    private int lineItemID; // Database reference
    private Product product;
    private int quantity;
    private double subtotal;
    
    /**
     * Constructor for creating a new SaleLineItem
     * 
     * @param product The product
     * @param quantity The quantity
     */
    public SaleLineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        calculateSubtotal();
    }
    
    /**
     * Calculates the subtotal for this line item
     */
    private void calculateSubtotal() {
        if (product != null) {
            this.subtotal = product.getPrice() * quantity;
        } else {
            this.subtotal = 0.0;
        }
    }
    
    /**
     * Gets the line item ID
     * 
     * @return The line item ID
     */
    public int getLineItemID() {
        return lineItemID;
    }
    
    /**
     * Sets the line item ID
     * 
     * @param lineItemID The line item ID
     */
    public void setLineItemID(int lineItemID) {
        this.lineItemID = lineItemID;
    }
    
    /**
     * Gets the product
     * 
     * @return The product
     */
    public Product getProduct() {
        return product;
    }
    
    /**
     * Gets the quantity
     * 
     * @return The quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Gets the subtotal
     * 
     * @return The subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }
    
    /**
     * Updates the quantity and recalculates the subtotal
     * 
     * @param quantity The new quantity
     */
    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        calculateSubtotal();
    }
    
    @Override
    public String toString() {
        return quantity + " x " + product.getName() + " - " + subtotal + " kr.";
    }
}