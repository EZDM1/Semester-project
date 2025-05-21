package controller.interfaces;

import model.Product;
import model.Sale;

/**
 * Interface for sale controller functionality
 */
public interface SaleControllerIF {
    
    /**
     * Creates a new sale
     * 
     * @return The newly created Sale object
     */
    Sale createSale();
    
    /**
     * Scans a product and adds it to the current sale
     * 
     * @param barcode The product barcode
     * @param quantity The quantity to add
     * @return The scanned product
     */
    Product scanProduct(String barcode, int quantity);
    
    /**
     * Finalizes the current sale and calculates the total
     * 
     * @return The final total amount
     */
    double endSale();
    
    /**
     * Selects a payment method for the current sale
     * 
     * @param type The payment method type
     */
    void selectPaymentMethod(String type);
    
    /**
     * Saves the completed sale to the database
     * 
     * @param s The sale to save
     * @return true if the save was successful, false otherwise
     */
    boolean saveSale(Sale s);
}