package ui;

import java.util.Scanner;

import controller.SaleController;
import controller.interfaces.SaleControllerIF;
import model.Product;
import model.Sale;

/**
 * Dialog class for creating a new sale
 */
public class CreateSaleDialog {
    
    private SaleControllerIF saleController;
    private SaleView saleView;
    private Scanner scanner;
    
    /**
     * Constructor for CreateSaleDialog
     * 
     * @param saleController The sale controller
     * @param saleView The sale view
     */
    public CreateSaleDialog(SaleControllerIF saleController, SaleView saleView) {
        this.saleController = saleController;
        this.saleView = saleView;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Creates a new sale
     * 
     * @return The newly created Sale object
     */
    public Sale createNewSale() {
        System.out.println("Creating new sale...");
        Sale sale = saleController.createSale();
        System.out.println("New sale created.");
        
        // Optionally assign an employee to the sale
        System.out.print("Enter employee ID (or press Enter to skip): ");
        String employeeId = scanner.nextLine().trim();
        
        if (!employeeId.isEmpty()) {
            if (saleController instanceof SaleController) {
                ((SaleController) saleController).assignEmployee(employeeId);
            }
        }
        
        return sale;
    }
    
    /**
     * Scans a product and adds it to the current sale
     */
    public void scanItem() {
        System.out.print("Enter product barcode: ");
        String barcode = scanner.nextLine().trim();
        
        System.out.print("Enter quantity: ");
        int quantity = 1; // Default quantity
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
            if (quantity <= 0) {
                System.out.println("Quantity must be positive. Using default value of 1.");
                quantity = 1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Using default value of 1.");
        }
        
        Product product = saleController.scanProduct(barcode, quantity);
        
        if (product != null) {
            System.out.println("Added: " + quantity + "x " + product.getName() + " - " + product.getPrice() + " kr. each");
        } else {
            System.out.println("Product not found for barcode: " + barcode);
        }
    }
    
    /**
     * Ends the current sale and displays the total
     * 
     * @return The final total amount
     */
    public double endSale() {
        System.out.println("Finalizing sale...");
        double total = saleController.endSale();
        saleView.displayTotal(total);
        return total;
    }
    
    /**
     * Selects a payment method for the current sale
     */
    public void selectPaymentMethod() {
        System.out.println("Select payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. MobilePay");
        
        System.out.print("Enter selection (1-3): ");
        String selection = scanner.nextLine().trim();
        
        String paymentType;
        switch (selection) {
            case "1":
                paymentType = "CASH";
                break;
            case "2":
                paymentType = "CARD";
                break;
            case "3":
                paymentType = "MOBILEPAY";
                break;
            default:
                System.out.println("Invalid selection. Defaulting to Cash.");
                paymentType = "CASH";
                break;
        }
        
        saleController.selectPaymentMethod(paymentType);
        System.out.println("Payment method set to: " + paymentType);
    }
    
    /**
     * Executes the complete sale process
     */
    public void executeSaleProcess() {
        Sale sale = createNewSale();
        
        boolean continueShopping = true;
        while (continueShopping) {
            scanItem();
            
            System.out.print("Add another item? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            continueShopping = answer.equals("y") || answer.equals("yes");
        }
        
        double total = endSale();
        
        System.out.print("Proceed to payment? (y/n): ");
        String proceedAnswer = scanner.nextLine().trim().toLowerCase();
        
        if (proceedAnswer.equals("y") || proceedAnswer.equals("yes")) {
            selectPaymentMethod();
            
            if (saleController.saveSale(sale)) {
                System.out.println("Sale saved successfully.");
                saleView.showReceipt(sale);
            } else {
                System.out.println("Failed to save sale.");
            }
        } else {
            System.out.println("Sale canceled.");
        }
    }
}