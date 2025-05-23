package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import controller.EmployeeController;
import model.Employee;

public class EmployeeControllerTest {
    
    private EmployeeController employeeController;
    
    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController();
    }
    
    @Test
    @DisplayName("Test at getEmployee finder eksisterende medarbejder")
    void testGetEmployeeSuccess() {
    	// Arrange
    	String employeeId = "1";
    	
        // Act
        Employee employee = employeeController.getEmployee(employeeId);
        
        // Assert
        assertNotNull(employee, "getEmployee() skal finde medarbejder med ID 1");
        assertNotNull(employee.getName(), "Medarbejder skal have et navn");
        assertFalse(employee.getName().isEmpty(), "Medarbejdernavn må ikke være tomt");
        assertTrue(employee.getEmployeeID() > 0, "Medarbejder skal have gyldigt ID");
    }
    
    @Test
    @DisplayName("Test at getEmployee returnerer null for ukendt ID")
    void testGetEmployeeNotFound() {
    	
    	// Arrange
        String nonExistentId = "999";
    	
        // Act
        Employee employee = employeeController.getEmployee(nonExistentId);
        
        // Assert
        assertNull(employee, "getEmployee() skal returnere null for ukendt ID");
    }
    
    @Test
    @DisplayName("Test at getEmployee håndterer ugyldig input")
    void testGetEmployeeInvalidInput() {
        // Act & Assert
        Employee employee1 = employeeController.getEmployee("abc");
        Employee employee2 = employeeController.getEmployee("");
        
        assertNull(employee1, "getEmployee() skal returnere null for bogstav-input");
        assertNull(employee2, "getEmployee() skal returnere null for tom string");
    }
}