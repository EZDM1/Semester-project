package db.interfaces;

import java.util.List;

import model.Product;

/**
 * Interface for Product database operations
 */
public interface ProductDBIF {
    
    /**
     * Retrieves all products from the database
     * 
     * @return A list of all products
     */
    List<Product> findAll();
    
    /**
     * Finds a product by its ID
     * 
     * @param id The product ID
     * @return The product if found, otherwise null
     */
    Product findById(int id);
    
    /**
     * Updates a product in the database
     * 
     * @param p The product to update
     * @return true if the update was successful, false otherwise
     */
    boolean update(Product p);
}