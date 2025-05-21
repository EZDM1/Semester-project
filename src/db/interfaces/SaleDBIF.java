package db.interfaces;

import model.Sale;

/**
 * Interface for Sale database operations
 */
public interface SaleDBIF {
    
    /**
     * Creates a new sale in the database
     * 
     * @return The ID of the newly created sale, or -1 if creation failed
     */
    int createSale();
    
    /**
     * Saves an existing sale to the database
     * 
     * @param sale The sale to save
     * @return true if the save was successful, false otherwise
     */
    boolean saveSale(Sale sale);
    
    /**
     * Finds a sale by its ID
     * 
     * @param saleId The sale ID
     * @return The sale if found, otherwise null
     */
    Sale findSale(int saleId);
}