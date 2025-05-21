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
            // Initialize queries
            findAllQuery = "SELECT * FROM sale";
            findByIdQuery = "SELECT * FROM sale WHERE sale_id = ?";
            createQuery = "INSERT INTO Sale (dateTime, totalAmount, paymentMethod, status, receiptNumber, FK_Employee_id) VALUES (?, ?, ?, ?, ?, ?)";
            updateQuery = "UPDATE Sale SET dateTime = ?, totalAmount = ?, paymentMethod = ?, status = ?, receiptNumber = ?, FK_Employee_id = ? WHERE Sale_id = ?";
            
            // Get database connection
            Connection connection = DBConnection.getInstance().getConnection();
            
            // Prepare statements
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
            // Create new sale with default values
            LocalDateTime now = LocalDateTime.now();
            createPs.setTimestamp(1, Timestamp.valueOf(now));
            createPs.setDouble(2, 0.0);
            createPs.setString(3, "PENDING");
            createPs.setString(4, "NEW");
            createPs.setString(5, "R" + System.currentTimeMillis());
            createPs.setNull(6, java.sql.Types.INTEGER); // No employee assigned yet
            
            // Execute insert
            int rowsAffected = createPs.executeUpdate();
            
            // Get the generated sale ID
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
            // Debug output
            System.out.println("Saving sale to database:");
            System.out.println("- Employee ID: " + 
                (sale.getEmployee() != null ? sale.getEmployee().getEmployeeID() : "None"));
            
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement updatePs = connection.prepareStatement(updateQuery);
            
            // Set parameters
            updatePs.setTimestamp(1, Timestamp.valueOf(sale.getDateTime()));
            updatePs.setDouble(2, sale.getTotalAmount());
            updatePs.setString(3, sale.getPaymentStatus());
            updatePs.setString(4, sale.getStatus());
            updatePs.setString(5, sale.getReceiptNumber());
            
            // Set employee ID - THIS IS THE IMPORTANT PART
            if (sale.getEmployee() != null) {
                updatePs.setInt(6, sale.getEmployee().getEmployeeID());
                System.out.println("Setting employee ID: " + sale.getEmployee().getEmployeeID());
            } else {
                updatePs.setNull(6, java.sql.Types.INTEGER);
                System.out.println("No employee ID to set");
            }
            
            updatePs.setInt(7, sale.getSaleID());
            
            // Execute update
            int rowsAffected = updatePs.executeUpdate();
            success = rowsAffected > 0;
            
            updatePs.close();
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
                
                // Load associated sale line items (would need a separate SaleLineItemDB class)
                // This is a placeholder for that logic
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
        sale.setPaymentStatus(rs.getString("paymentStatus"));
        sale.setStatus(rs.getString("status"));
        sale.setReceiptNumber(rs.getString("receiptNumber"));
        
        // Load employee if available (would need to use EmployeeDB)
        int employeeId = rs.getInt("FK_Employee_id");
        if (!rs.wasNull()) {
            // This is a placeholder - would normally use EmployeeDB to load the employee
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