package controller.interfaces;

import java.util.List;

import model.Employee;

/**
 * Interface for employee controller functionality
 */
public interface EmployeeControllerIF {
    
    /**
     * Gets an employee by their ID
     * 
     * @param employeeId The employee ID
     * @return The employee if found, otherwise null
     */
    Employee getEmployee(String employeeId);
    
    /**
     * Retrieves all employees
     * 
     * @return A list of all employees
     */
    List<Employee> findAll();
    
    /**
     * Updates an employee
     * 
     * @param e The employee to update
     * @return true if the update was successful, false otherwise
     */
    boolean update(Employee e);
}