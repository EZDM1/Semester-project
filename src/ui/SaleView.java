package ui;

import controller.interfaces.SaleControllerIF;
import model.Sale;
import model.SaleLineItem;

/**
 * View class for displaying sale information
 */
public class SaleView {
    
    private SaleControllerIF saleController;
    
    /**
     * Constructor for SaleView
     * 
     * @param saleController The sale controller
     */
    public SaleView(SaleControllerIF saleController) {
        this.saleController = saleController;
    }
    
    /**
     * Displays the details of a sale
     * 
     * @param sale The sale to display
     */
    public void displaySaleDetails(Sale sale) {
        if (sale == null) {
            System.out.println("No sale to display");
            return;
        }
        
        System.out.println("=== SALE DETAILS ===");
        System.out.println("Sale ID: " + sale.getSaleID());
        System.out.println("Date/Time: " + sale.getDateTime());
        System.out.println("Status: " + sale.getStatus());
        System.out.println("Payment Status: " + sale.getPaymentStatus());
        
        if (sale.getEmployee() != null) {
            System.out.println("Employee: " + sale.getEmployee().getName() + " (" + sale.getEmployee().getEmployeeID() + ")");
        }
        
        System.out.println("\nItems:");
        for (SaleLineItem item : sale.getSaleLineItems()) {
            System.out.println("  " + item.getQuantity() + "x " + item.getProduct().getName() + 
                               " - " + item.getProduct().getPrice() + " kr. each = " + 
                               item.getSubtotal() + " kr.");
        }
        
        System.out.println("\nTotal: " + sale.getTotalAmount() + " kr.");
    }
    
    /**
     * Displays the total amount of a sale
     * 
     * @param total The total amount to display
     */
    public void displayTotal(double total) {
        System.out.println("Total: " + total + " kr.");
    }
    
    /**
     * Shows a receipt for a completed sale
     * 
     * @param sale The completed sale
     */
    public void showReceipt(Sale sale) {
        if (sale == null) {
            System.out.println("No sale to display receipt for");
            return;
        }
        
        System.out.println("\n===== QB ApS =====");
        System.out.println("Danmarksgade 70 A, 9000 Aalborg");
        System.out.println("Tel: +45 XX XX XX XX");
        System.out.println("====================");
        
        System.out.println("\nReceipt: " + sale.getReceiptNumber());
        System.out.println("Date: " + sale.getDateTime().toLocalDate());
        System.out.println("Time: " + sale.getDateTime().toLocalTime());
        
        if (sale.getEmployee() != null) {
            System.out.println("Cashier: " + sale.getEmployee().getName());
        }
        
        System.out.println("\nItems:");
        System.out.println("--------------------");
        
        for (SaleLineItem item : sale.getSaleLineItems()) {
            System.out.printf("%-20s %3d x %6.2f = %7.2f kr.\n", 
                item.getProduct().getName(), 
                item.getQuantity(), 
                item.getProduct().getPrice(), 
                item.getSubtotal());
        }
        
        System.out.println("--------------------");
        System.out.printf("TOTAL: %25.2f kr.\n", sale.getTotalAmount());
        
        System.out.println("\nPayment Method: " + sale.getPaymentStatus());
        System.out.println("\nThank you for shopping at QB ApS!");
        System.out.println("Please come again");
        System.out.println("====================");
    }
}