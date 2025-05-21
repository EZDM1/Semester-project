package ui;

import GUI.CreateSale;
import GUI.SaleView;

/**
 * Main class for starting the QB POS application
 */
public class Main {
    
    /**
     * Main method - entry point for the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Starting QB POS System...");
        
        // Initialize sample data (employees and products)
        System.out.println("Checking for sample data...");
        boolean dataInitialized = SaleView.initializeSampleData();
        if (dataInitialized) {
            System.out.println("Sample data initialized successfully.");
        } else {
            System.out.println("WARNING: Sample data initialization failed. Application may have limited functionality.");
        }
        
        
        
        // Create and show the GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                CreateSale frame = new CreateSale();
                frame.setTitle("QB Point of Sale System");
                frame.setVisible(true);
            }
        });
    }
}