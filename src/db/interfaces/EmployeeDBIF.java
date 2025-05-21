package db.interfaces;
import java.util.List;

import model.Employee;

/**
 * Interface for Employee database operations
 */
public interface EmployeeDBIF {
    
    /**
     * Retrieves all employees from the database
     * 
     * @return A list of all employees
     */
    List<Employee> findAll();
    
    /**
     * Finds an employee by their ID
     * 
     * @param id The employee ID
     * @return The employee if found, otherwise null
     */
    Employee findById(int id);
    
    /**
     * Updates an employee in the database
     * 
     * @param e The employee to update
     * @return true if the update was successful, false otherwise
     */
    boolean update(Employee e);
}