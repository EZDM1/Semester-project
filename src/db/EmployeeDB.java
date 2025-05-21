package db;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.EmployeeDBIF;
import model.Employee;

/**
 * Database implementation for Employee operations
 */
public class EmployeeDB implements EmployeeDBIF {
    
    private String findAllQuery;
    private String findByIdQuery;
    private String updateQuery;
    private PreparedStatement findAllPs;
    private PreparedStatement findByIdPs;
    private PreparedStatement updatePs;
    
    /**
     * Constructor that initializes the prepared statements
     */
    public EmployeeDB() {
        try {
            // Initialize queries
        	findAllQuery = "SELECT * FROM Employee";
            findByIdQuery = "SELECT * FROM Employee WHERE Employee_id = ?";
            updateQuery = "UPDATE Employee SET name = ?, cprNumber = ?, position = ?, phone = ?, email = ? WHERE Employee_id = ?";
            
            
            // Get database connection
            Connection connection = DBConnection.getInstance().getConnection();
            
            // Prepare statements
            findAllPs = connection.prepareStatement(findAllQuery);
            findByIdPs = connection.prepareStatement(findByIdQuery);
            updatePs = connection.prepareStatement(updateQuery);
        } catch (SQLException e) {
            System.err.println("Error initializing EmployeeDB: " + e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet rs = findAllPs.executeQuery();
            employees = buildObjects(rs);
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error finding all employees: " + e.getMessage());
        }
        return employees;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee findById(int id) {
        Employee employee = null;
        try {
            findByIdPs.setInt(1, id);
            ResultSet rs = findByIdPs.executeQuery();
            if (rs.next()) {
                employee = buildObject(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Error finding employee by id: " + e.getMessage());
        }
        return employee;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Employee e) {
        boolean success = false;
        try {
            // Set parameters for update statement
            updatePs.setString(1, e.getName());
            updatePs.setString(2, e.getCprNumber());
            updatePs.setString(3, e.getPosition());
            updatePs.setString(4, e.getPhone());
            updatePs.setString(5, e.getEmail());
            updatePs.setInt(6, e.getEmployeeID());
            
            // Execute update
            int rowsAffected = updatePs.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException ex) {
            System.err.println("Error updating employee: " + ex.getMessage());
        }
        return success;
    }
    
    /**
     * Builds an Employee object from a ResultSet
     * 
     * @param rs The ResultSet containing employee data
     * @return The constructed Employee object
     * @throws SQLException If a database access error occurs
     */
    private Employee buildObject(ResultSet rs) throws SQLException {
    	 int employeeID = rs.getInt("Employee_id");
         String name = rs.getString("name");
         String cprNumber = rs.getString("cprNumber");
         String position = rs.getString("position");
         String phone = rs.getString("phone");
         String email = rs.getString("email");
        
        return new Employee(employeeID, name, cprNumber, position, phone, email);
    }
    
    /**
     * Builds a list of Employee objects from a ResultSet
     * 
     * @param rs The ResultSet containing multiple employee records
     * @return A list of Employee objects
     * @throws SQLException If a database access error occurs
     */
    private List<Employee> buildObjects(ResultSet rs) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (rs.next()) {
            employees.add(buildObject(rs));
        }
        return employees;
    }
}