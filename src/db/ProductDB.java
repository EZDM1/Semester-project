package db;

import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.ProductDBIF;
import model.Product;

/**
 * Database implementation for Product operations
 */
public class ProductDB implements ProductDBIF {

	private String findAllQuery;
	private String findByIdQuery;
	private String updateQuery;
	private PreparedStatement findAllPs;
	private PreparedStatement findByIdPs;
	private PreparedStatement updatePs;

	/**
	 * Constructor that initializes the prepared statements
	 */
	public ProductDB() {
		try {
			findAllQuery = "SELECT * FROM Product";
			findByIdQuery = "SELECT * FROM Product WHERE Product_id = ?";
			updateQuery = "UPDATE Product SET barcode = ?, name = ?, description = ?, price = ?, category = ? WHERE Product_id = ?";

			Connection connection = DBConnection.getInstance().getConnection();

			findAllPs = connection.prepareStatement(findAllQuery);
			findByIdPs = connection.prepareStatement(findByIdQuery);
			updatePs = connection.prepareStatement(updateQuery);
		} catch (SQLException e) {
			System.err.println("Error initializing ProductDB: " + e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findAll() {
		List<Product> products = new ArrayList<>();
		try {
			ResultSet rs = findAllPs.executeQuery();
			products = buildObjects(rs);
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error finding all products: " + e.getMessage());
		}
		return products;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product findById(int id) {
		Product product = null;
		try {
			findByIdPs.setInt(1, id);
			ResultSet rs = findByIdPs.executeQuery();
			if (rs.next()) {
				product = buildObject(rs);
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error finding product by id: " + e.getMessage());
		}
		return product;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Product p) {
		boolean success = false;
		try {
			updatePs.setString(1, p.getName());
			updatePs.setString(2, p.getDescription());
			updatePs.setDouble(3, p.getPrice());
			updatePs.setString(4, p.getCategory());
			updatePs.setString(5, p.getBarcode());
			updatePs.setInt(6, Integer.parseInt(p.getBarcode()));

			int rowsAffected = updatePs.executeUpdate();
			success = rowsAffected > 0;
		} catch (SQLException e) {
			System.err.println("Error updating product: " + e.getMessage());
		}
		return success;
	}

	/**
	 * Builds a Product object from a ResultSet
	 * 
	 * @param rs The ResultSet containing product data
	 * @return The constructed Product object
	 * @throws SQLException If a database access error occurs
	 */
	private Product buildObject(ResultSet rs) throws SQLException {
		int productId = rs.getInt("Product_id");
		String barcode = rs.getString("barcode");
		String name = rs.getString("name");
		String description = rs.getString("description");
		double price = rs.getDouble("price");
		String category = rs.getString("category");

		return new Product(productId, barcode, name, description, price, category);
	}

	/**
	 * Builds a list of Product objects from a ResultSet
	 * 
	 * @param rs The ResultSet containing multiple product records
	 * @return A list of Product objects
	 * @throws SQLException If a database access error occurs
	 */
	private List<Product> buildObjects(ResultSet rs) throws SQLException {
		List<Product> products = new ArrayList<>();
		while (rs.next()) {
			products.add(buildObject(rs));
		}
		return products;
	}
}