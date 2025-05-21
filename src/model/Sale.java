package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction in the QB system
 */
public class Sale {
    private int saleID; // Adding this for database reference
    private LocalDateTime dateTime;
    private double totalAmount;
    private String paymentStatus;
    private String status;
    private String receiptNumber;
    private Employee employee;
    private List<SaleLineItem> saleLineItems;
    
    /**
     * Constructor for creating a new Sale
     */
    public Sale() {
        this.dateTime = LocalDateTime.now();
        this.totalAmount = 0.0;
        this.paymentStatus = "PENDING";
        this.status = "NEW";
        this.saleLineItems = new ArrayList<>();
    }
    
    /**
     * Adds a sale line item to this sale
     * 
     * @param item The SaleLineItem to add
     */
    public void addSaleLineItem(SaleLineItem item) {
        saleLineItems.add(item);
        calculateTotal();
    }
    
    /**
     * Sets the employee responsible for this sale
     * 
     * @param employee The employee
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    /**
     * Calculates the total amount of the sale
     */
    public void calculateTotal() {
        totalAmount = 0.0;
        for (SaleLineItem item : saleLineItems) {
            totalAmount += item.getSubtotal();
        }
    }
    
    /**
     * Gets the sale ID
     * 
     * @return The sale ID
     */
    public int getSaleID() {
        return saleID;
    }
    
    /**
     * Sets the sale ID
     * 
     * @param saleID The sale ID
     */
    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }
    
    /**
     * Sets the total amount of the sale
     * 
     * @param totalAmount The new total amount
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    /**
     * Sets the date and time of the sale
     * 
     * @param dateTime The new date and time
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    /**
     * Gets the date and time of the sale
     * 
     * @return The date and time
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    /**
     * Gets the total amount of the sale
     * 
     * @return The total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }
    
    /**
     * Gets the payment status of the sale
     * 
     * @return The payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    /**
     * Sets the payment status of the sale
     * 
     * @param paymentStatus The new payment status
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    /**
     * Gets the status of the sale
     * 
     * @return The status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Sets the status of the sale
     * 
     * @param status The new status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Gets the receipt number of the sale
     * 
     * @return The receipt number
     */
    public String getReceiptNumber() {
        return receiptNumber;
    }
    
    /**
     * Sets the receipt number of the sale
     * 
     * @param receiptNumber The new receipt number
     */
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    
    /**
     * Gets the employee responsible for this sale
     * 
     * @return The employee
     */
    public Employee getEmployee() {
        return employee;
    }
    
    /**
     * Gets the list of sale line items
     * 
     * @return The list of sale line items
     */
    public List<SaleLineItem> getSaleLineItems() {
        return saleLineItems;
    }
    
    @Override
    public String toString() {
        return "Sale #" + saleID + " - " + dateTime + " - " + totalAmount + " kr.";
    }
}