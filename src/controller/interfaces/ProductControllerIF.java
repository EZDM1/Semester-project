package controller.interfaces;

import java.util.List;

import model.Product;

/**
 * Interface for product controller functionality
 */
public interface ProductControllerIF {

	/**
	 * Retrieves all products
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
	 * Updates a product
	 * 
	 * @param p The product to update
	 * @return true if the update was successful, false otherwise
	 */
	boolean update(Product p);
}