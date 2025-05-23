package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.SaleDBIF;
import model.Sale;
import model.SaleLineItem;

/**
 * Database implementation for Sale operations
 */
public class SaleDB implements SaleDBIF {

	private String findAllQuery;
	private String findByIdQuery;
	private String updateQuery;
	private String createQuery;
	private PreparedStatement findAllPs;
	private PreparedStatement findByIdPs;
	private PreparedStatement createPs;
	

	/**
	 * Constructor that initializes the prepared statements
	 */
	public SaleDB() {
		try {

			findAllQuery = "SELECT * FROM sale";
			findByIdQuery = "SELECT * FROM sale WHERE sale_id = ?";
			createQuery = "INSERT INTO Sale (dateTime, totalAmount, paymentMethod, status, receiptNumber, FK_Employee_id) VALUES (?, ?, ?, ?, ?, ?)";
			updateQuery = "UPDATE Sale SET dateTime = ?, totalAmount = ?, paymentMethod = ?, status = ?, receiptNumber = ?, FK_Employee_id = ? WHERE Sale_id = ?";
			System.out.println("Update query: " + updateQuery);
			Connection connection = DBConnection.getInstance().getConnection();

			findAllPs = connection.prepareStatement(findAllQuery);
			findByIdPs = connection.prepareStatement(findByIdQuery);
			createPs = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			System.err.println("Error initializing SaleDB: " + e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int createSale() {
		int generatedSaleId = -1;
		try {
			LocalDateTime now = LocalDateTime.now();
			createPs.setTimestamp(1, Timestamp.valueOf(now));
			createPs.setDouble(2, 0.0);
			createPs.setString(3, null);
			createPs.setString(4, "NEW");
			createPs.setString(5, "R" + System.currentTimeMillis());
			createPs.setNull(6, java.sql.Types.INTEGER);

			int rowsAffected = createPs.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet generatedKeys = createPs.getGeneratedKeys();
				if (generatedKeys.next()) {
					generatedSaleId = generatedKeys.getInt(1);
				}
				generatedKeys.close();
			}
		} catch (SQLException e) {
			System.err.println("Error creating sale: " + e.getMessage());
		}
		return generatedSaleId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveSale(Sale sale) {
		boolean success = false;
		try {
			System.out.println("Saving sale to database");

			Connection connection = DBConnection.getInstance().getConnection();

			String insertQuery = "INSERT INTO Sale (dateTime, totalAmount, paymentMethod, status, receiptNumber, FK_Employee_id) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement insertPs = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

			insertPs.setTimestamp(1, Timestamp.valueOf(sale.getDateTime()));
			insertPs.setDouble(2, sale.getTotalAmount());
			insertPs.setString(3, sale.getPaymentMethod());
			insertPs.setString(4, sale.getStatus());
			insertPs.setString(5, sale.getReceiptNumber());

			if (sale.getEmployee() != null) {
				insertPs.setInt(6, sale.getEmployee().getEmployeeID());
			} else {
				insertPs.setNull(6, java.sql.Types.INTEGER);
			}

			int rowsAffected = insertPs.executeUpdate();
			success = rowsAffected > 0;

			if (success) {
				ResultSet generatedKeys = insertPs.getGeneratedKeys();
				if (generatedKeys.next()) {
					int saleId = generatedKeys.getInt(1);
					sale.setSaleID(saleId);
				}
				generatedKeys.close();
				String lineItemQuery = "INSERT INTO SaleItem (FK_Sale_id, FK_Product_id, quantity, unitPrice, subtotal) VALUES (?, ?, ?, ?, ?)";
			    PreparedStatement lineItemPs = connection.prepareStatement(lineItemQuery);
			    
			    for (SaleLineItem item : sale.getSaleLineItems()) {
			        lineItemPs.setInt(1, sale.getSaleID());
			        lineItemPs.setInt(2, item.getProduct().getProductId());
			        lineItemPs.setInt(3, item.getQuantity());
			        lineItemPs.setDouble(4, item.getProduct().getPrice());
			        lineItemPs.setDouble(5, item.getSubtotal());
			        
			        int lineItemRows = lineItemPs.executeUpdate();
			        System.out.println("Saved line item: " + item.getProduct().getName() + 
			                          " - Rows affected: " + lineItemRows);
			    }
			    
			    lineItemPs.close();
			    System.out.println("All line items saved!");
			}

			insertPs.close();
		} catch (SQLException e) {
			System.err.println("ERROR saving sale: " + e.getMessage());
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sale findSale(int saleId) {
		Sale sale = null;
		try {
			findByIdPs.setInt(1, saleId);
			ResultSet rs = findByIdPs.executeQuery();
			if (rs.next()) {
				sale = buildObject(rs);

				System.out.println("Sale line items would be loaded here.");
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error finding sale by id: " + e.getMessage());
		}
		return sale;
	}

	/**
	 * Finds all sales in the database
	 * 
	 * @return A list of all sales
	 */
	public List<Sale> findAll() {
		List<Sale> sales = new ArrayList<>();
		try {
			ResultSet rs = findAllPs.executeQuery();
			sales = buildObjects(rs);
			rs.close();
		} catch (SQLException e) {
			System.err.println("Error finding all sales: " + e.getMessage());
		}
		return sales;
	}

	/**
	 * Builds a Sale object from a ResultSet
	 * 
	 * @param rs The ResultSet containing sale data
	 * @return The constructed Sale object
	 * @throws SQLException If a database access error occurs
	 */
	private Sale buildObject(ResultSet rs) throws SQLException {
		Sale sale = new Sale();

		int saleId = rs.getInt("sale_id");
		sale.setSaleID(saleId);

		Timestamp timestamp = rs.getTimestamp("dateTime");
		if (timestamp != null) {
			sale.setDateTime(timestamp.toLocalDateTime());
		}

		sale.setTotalAmount(rs.getDouble("totalAmount"));
		sale.setPaymentMethod(rs.getString("paymentMethod"));
		sale.setStatus(rs.getString("status"));
		sale.setReceiptNumber(rs.getString("receiptNumber"));

		int employeeId = rs.getInt("FK_Employee_id");
		if (!rs.wasNull()) {
			System.out.println("Employee with ID " + employeeId + " would be loaded here.");
		}

		return sale;
	}

	/**
	 * Builds a list of Sale objects from a ResultSet
	 * 
	 * @param rs The ResultSet containing multiple sale records
	 * @return A list of Sale objects
	 * @throws SQLException If a database access error occurs
	 */
	private List<Sale> buildObjects(ResultSet rs) throws SQLException {
		List<Sale> sales = new ArrayList<>();
		while (rs.next()) {
			sales.add(buildObject(rs));
		}
		return sales;
	}
}