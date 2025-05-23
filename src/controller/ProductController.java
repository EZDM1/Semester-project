package controller;

import java.util.List;

import controller.interfaces.ProductControllerIF;
import db.ProductDB;
import db.interfaces.ProductDBIF;
import model.Product;

/**
 * Controller class for handling product operations
 */
public class ProductController implements ProductControllerIF {

	private ProductDBIF productDB;

	/**
	 * Constructor for ProductController
	 * 
	 * @param productDB The product database interface
	 */
	public ProductController(ProductDBIF productDB) {
		this.productDB = productDB;
	}

	/**
	 * Default constructor that initializes with ProductDB
	 */
	public ProductController() {
		this.productDB = new ProductDB();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAll() {
		return productDB.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product findById(int id) {
		return productDB.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Product p) {
		return productDB.update(p);
	}
}