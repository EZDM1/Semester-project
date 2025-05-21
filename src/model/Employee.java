package model;
/**
 * Represents an employee in the QB system
 */
public class Employee {
    private int employeeID; // Adding this attribute based on getEmployeeID() method
    private String name;
    private String cprNumber;
    private String position;
    private String phone;
    private String email;
    
    /**
     * Constructor for creating a new Employee
     * 
     * @param employeeID The unique employee ID
     * @param name The employee name
     * @param cprNumber The employee CPR number
     * @param position The employee position
     * @param phone The employee phone number
     * @param email The employee email
     */
    public Employee(int employeeID, String name, String cprNumber, String position, 
                   String phone, String email) {
        this.employeeID = employeeID;
        this.name = name;
        this.cprNumber = cprNumber;
        this.position = position;
        this.phone = phone;
        this.email = email;
    }
    
    /**
     * Gets the employee ID
     * 
     * @return The employee ID
     */
    public int getEmployeeID() {
        return employeeID;
    }
    
    /**
     * Gets the employee name
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the employee position
     * 
     * @return The position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * Gets the employee CPR number
     * 
     * @return The CPR number
     */
    public String getCprNumber() {
        return cprNumber;
    }
    
    /**
     * Gets the employee phone number
     * 
     * @return The phone number
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Gets the employee email
     * 
     * @return The email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the employee name
     * 
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the employee position
     * 
     * @param position The new position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     * Sets the employee phone number
     * 
     * @param phone The new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * Sets the employee email
     * 
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return name + " [" + employeeID + "] - " + position;
    }
}