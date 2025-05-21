package controller;

import java.util.List;

import controller.interfaces.EmployeeControllerIF;
import db.EmployeeDB;
import db.interfaces.EmployeeDBIF;
import model.Employee;

/**
 * Controller class for handling employee operations
 */
public class EmployeeController implements EmployeeControllerIF {
    
    private EmployeeDBIF employeeDB;
    
    /**
     * Constructor for EmployeeController
     * 
     * @param employeeDB The employee database interface
     */
    public EmployeeController(EmployeeDBIF employeeDB) {
        this.employeeDB = employeeDB;
    }
    
    /**
     * Default constructor that initializes with EmployeeDB
     */
    public EmployeeController() {
        this.employeeDB = new EmployeeDB();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Employee getEmployee(String employeeId) {
        try {
            int id = Integer.parseInt(employeeId);
            return employeeDB.findById(id);
        } catch (NumberFormatException e) {
            System.err.println("Invalid employee ID format: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> findAll() {
        return employeeDB.findAll();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Employee e) {
        return employeeDB.update(e);
    }
}