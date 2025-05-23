package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import controller.SaleController;
import model.Sale;
import model.Product;

public class SaleControllerTest {
    
    private SaleController saleController;
    
    @BeforeEach
    void setUp() {
        saleController = new SaleController();
    }
    
    @Test
    @DisplayName("Test at createSale opretter et nyt salg")
    void testCreateSale() {
        // Act
        Sale sale = saleController.createSale();
        
        // Assert
        assertNotNull(sale, "createSale() skal returnere et Sale objekt");
        assertEquals("NEW", sale.getStatus(), "Nyt salg skal have status 'NEW'");
        assertTrue(sale.getTotalAmount() == 0.0, "Nyt salg skal have totalbeløb 0");
    }
    
    @Test
    @DisplayName("Test scanning af eksisterende produkt")
    void testScanProductSuccess() {
        // Arrange
        saleController.createSale();
        
        // Act
        Product product = saleController.scanProduct("123", 1);
        
        // Assert
        assertNotNull(product, "scanProduct() skal finde produkt med stregkode 123");
        assertNotNull(product.getName(), "Produkt skal have et navn");
        assertTrue(product.getPrice() > 0, "Produkt skal have en pris større end 0");
    }
    
    @Test
    @DisplayName("Test scanning af ukendt produkt")
    void testScanProductNotFound() {
        // Arrange
        saleController.createSale();
        
        // Act
        Product product = saleController.scanProduct("UNKNOWN", 1);
        
        // Assert
        assertNull(product, "scanProduct() skal returnere null for ukendt stregkode");
    }
}