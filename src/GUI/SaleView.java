package GUI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import model.Employee;
import model.Product;
import db.DBConnection;

/**
 * This class contains methods to generate sample data for the POS system.
 * You can add this to your project, most likely in the db package.
 */
public class SaleView {
    
    /**
     * Initializes sample data in the database
     * @return true if data was generated successfully
     */
    public static boolean initializeSampleData() {
        try {
            // Create employees
            boolean employeesCreated = createSampleEmployees();
            
            // Create products
            boolean productsCreated = createSampleProducts();
            
            return employeesCreated && productsCreated;
        } catch (Exception e) {
            System.err.println("Error generating sample data: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Creates sample employees in the database
     * @return true if successful
     */
    private static boolean createSampleEmployees() {
        try {
            // Get connection
            Connection conn = DBConnection.getInstance().getConnection();
            
            // First check if employees already exist
            PreparedStatement checkPs = conn.prepareStatement("SELECT COUNT(*) FROM Employee");
            java.sql.ResultSet rs = checkPs.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkPs.close();
            
            if (count > 0) {
                System.out.println("Employees already exist in database. Skipping employee creation.");
                return true;
            }
            
            // Create sample employees
            String insertSql = "INSERT INTO Employee (Employee_id, name, cprNumber, position, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertSql);
            
            // Sample employee data
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(1, "John Doe", "010190-1234", "Cashier", "45123456", "john@qbaps.dk"));
            employees.add(new Employee(2, "Jane Smith", "020285-5678", "Manager", "45234567", "jane@qbaps.dk"));
            employees.add(new Employee(3, "Lars Jensen", "150578-9012", "Cashier", "45345678", "lars@qbaps.dk"));
            employees.add(new Employee(4, "Mette Nielsen", "250682-3456", "Supervisor", "45456789", "mette@qbaps.dk"));
            employees.add(new Employee(5, "Anders Andersen", "300976-7890", "Cashier", "45567890", "anders@qbaps.dk"));
            
            // Insert each employee
            for (Employee emp : employees) {
                ps.setInt(1, emp.getEmployeeID());
                ps.setString(2, emp.getName());
                ps.setString(3, emp.getCprNumber());
                ps.setString(4, emp.getPosition());
                ps.setString(5, emp.getPhone());
                ps.setString(6, emp.getEmail());
                ps.executeUpdate();
                System.out.println("Added employee: " + emp.getName());
            }
            
            ps.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating sample employees: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Creates sample products in the database
     * @return true if successful
     */
    private static boolean createSampleProducts() {
        try {
            // Get connection
            Connection conn = DBConnection.getInstance().getConnection();
            
            // First check if products already exist
            PreparedStatement checkPs = conn.prepareStatement("SELECT COUNT(*) FROM Product");
            java.sql.ResultSet rs = checkPs.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkPs.close();
            
            if (count > 0) {
                System.out.println("Products already exist in database. Skipping product creation.");
                return true;
            }
            
            // Create sample products
            String insertSql = "INSERT INTO Product (barcode, name, description, price, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertSql);
            
            // Sample product data by category
            addProductsForCategory(ps, "Frugt", Arrays.asList(
                new Product("5701234567890", "Æbler", "Danske æbler", 15.95, "Frugt"),
                new Product("5701234567891", "Bananer", "Økologiske bananer", 22.50, "Frugt"),
                new Product("5701234567892", "Appelsiner", "Spanske appelsiner", 25.00, "Frugt"),
                new Product("5701234567893", "Pærer", "Danske pærer", 18.50, "Frugt"),
                new Product("5701234567894", "Vindruer", "Grønne vindruer uden kerner", 32.95, "Frugt")
            ));
            
            addProductsForCategory(ps, "Grønsager", Arrays.asList(
                new Product("5701234567895", "Gulerødder", "Økologiske gulerødder", 12.50, "Grønsager"),
                new Product("5701234567896", "Kartofler", "Danske kartofler", 18.95, "Grønsager"),
                new Product("5701234567897", "Løg", "Røde løg", 14.95, "Grønsager"),
                new Product("5701234567898", "Agurk", "Dansk agurk", 9.95, "Grønsager"),
                new Product("5701234567899", "Tomater", "Danske tomater", 24.95, "Grønsager")
            ));
            
            addProductsForCategory(ps, "Brød", Arrays.asList(
                new Product("5702234567890", "Rugbrød", "Klassisk rugbrød", 22.95, "Brød"),
                new Product("5702234567891", "Franskbrød", "Hvedebrød", 18.50, "Brød"),
                new Product("5702234567892", "Boller", "4 friskbagte boller", 15.00, "Brød"),
                new Product("5702234567893", "Fuldkornsbrød", "Sundt fuldkornsbrød", 26.95, "Brød"),
                new Product("5702234567894", "Ciabatta", "Italiensk ciabatta", 28.50, "Brød")
            ));
            
            addProductsForCategory(ps, "Drikkevarer", Arrays.asList(
                new Product("5703234567890", "Mælk", "Letmælk 1 L", 12.95, "Drikkevarer"),
                new Product("5703234567891", "Juice", "Appelsinjuice 1 L", 18.50, "Drikkevarer"),
                new Product("5703234567892", "Sodavand", "Cola 1,5 L", 22.95, "Drikkevarer"),
                new Product("5703234567893", "Vand", "Kildevand 0,5 L", 8.50, "Drikkevarer"),
                new Product("5703234567894", "Kaffe", "Økologisk kaffe 400g", 45.00, "Drikkevarer")
            ));
            
            addProductsForCategory(ps, "Krydderier", Arrays.asList(
                new Product("5704234567890", "Salt", "Fint salt 500g", 10.95, "Krydderier"),
                new Product("5704234567891", "Peber", "Sort peber 100g", 22.50, "Krydderier"),
                new Product("5704234567892", "Oregano", "Tørret oregano 50g", 18.95, "Krydderier"),
                new Product("5704234567893", "Kanel", "Ceylon kanel 75g", 24.95, "Krydderier"),
                new Product("5704234567894", "Paprika", "Sød paprika 50g", 19.95, "Krydderier")
            ));
            
            addProductsForCategory(ps, "Nødder", Arrays.asList(
                new Product("5705234567890", "Mandler", "Usaltede mandler 200g", 38.95, "Nødder"),
                new Product("5705234567891", "Valnødder", "Knækkede valnødder 150g", 42.50, "Nødder"),
                new Product("5705234567892", "Cashewnødder", "Saltede cashewnødder 250g", 55.00, "Nødder"),
                new Product("5705234567893", "Peanuts", "Ristede peanuts 300g", 28.50, "Nødder"),
                new Product("5705234567894", "Pistacienødder", "Ristede pistacienødder 150g", 48.95, "Nødder")
            ));
            
            addProductsForCategory(ps, "Slik", Arrays.asList(
                new Product("5706234567890", "Chokolade", "Mørk chokolade 100g", 25.95, "Slik"),
                new Product("5706234567891", "Vingummi", "Assorteret vingummi 200g", 22.50, "Slik"),
                new Product("5706234567892", "Lakrids", "Saltlakrids 150g", 28.95, "Slik"),
                new Product("5706234567893", "Karameller", "Bløde karameller 120g", 19.95, "Slik"),
                new Product("5706234567894", "Bolcher", "Frugtbolcher 250g", 24.50, "Slik")
            ));
            
            addProductsForCategory(ps, "Ris", Arrays.asList(
                new Product("5707234567890", "Jasminris", "Jasminris 1kg", 32.95, "Ris"),
                new Product("5707234567891", "Basmatiris", "Basmatiris 1kg", 36.50, "Ris"),
                new Product("5707234567892", "Grødris", "Grødris 500g", 14.95, "Ris"),
                new Product("5707234567893", "Risotto", "Risottoris 500g", 29.95, "Ris"),
                new Product("5707234567894", "Brune ris", "Brune ris 1kg", 26.50, "Ris")
            ));
            
            ps.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating sample products: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to add products for a specific category
     */
    private static void addProductsForCategory(PreparedStatement ps, String category, List<Product> products) 
            throws SQLException {
        System.out.println("Adding products for category: " + category);
        for (Product prod : products) {
            ps.setString(1, prod.getBarcode());
            ps.setString(2, prod.getName());
            ps.setString(3, prod.getDescription());
            ps.setDouble(4, prod.getPrice());
            ps.setString(5, prod.getCategory());
            ps.executeUpdate();
            System.out.println("  - Added: " + prod.getName() + " (" + prod.getBarcode() + ")");
        }
    }
}