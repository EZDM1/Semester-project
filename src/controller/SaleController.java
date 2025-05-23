package controller;

import java.util.List; 

import controller.interfaces.ProductControllerIF;
import controller.interfaces.EmployeeControllerIF;
import controller.interfaces.SaleControllerIF;
import db.SaleDB;
import db.interfaces.SaleDBIF;
import model.Employee;
import model.Product;
import model.Sale;
import model.SaleLineItem;

/**
 * Controller class for handling sale operations
 */
public class SaleController implements SaleControllerIF {

	private SaleDBIF saleDB;
	private Sale currentSale;
	private ProductControllerIF productController;
	private String paymentMethod;

	/**
	 * Constructor for SaleController with database interface
	 * 
	 * @param saleDB The sale database interface
	 */
	public SaleController(SaleDBIF saleDB) {
		this.saleDB = saleDB;
		this.productController = new ProductController();
	}

	/**
	 * Default constructor that initializes with SaleDB
	 */
	public SaleController() {
		this.saleDB = new SaleDB();
		this.productController = new ProductController();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sale createSale() {
		currentSale = new Sale();
		return currentSale;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Product scanProduct(String barcode, int quantity) {

	    if (barcode == null || barcode.trim().isEmpty()) {
	        System.err.println("FEJL: Stregkode må ikke være tom");
	        return null;
	    }
	    
	    if (quantity <= 0) {
	        System.err.println("FEJL: Antal skal være større end 0 (modtaget: " + quantity + ")");
	        return null;
	    }
	    
	    if (currentSale == null) {
	        System.err.println("FEJL: Ingen aktivt salg. Opret et nyt salg først.");
	        return null;
	    }

	    try {
	        Product product = null;
	        List<Product> allProducts = productController.findAll();

	        for (Product p : allProducts) {
	            if (p.getBarcode().equals(barcode)) {
	                product = p;
	                break;
	            }
	        }

	        if (product != null) {
	            SaleLineItem item = new SaleLineItem(product, quantity);
	            currentSale.addSaleLineItem(item);
	            System.out.println("SUCCESS: Produkt tilføjet - " + product.getName() + " x" + quantity);
	        } else {
	            System.err.println("FEJL: Produkt ikke fundet med stregkode: " + barcode);
	        }

	        return product;
	        
	    } catch (Exception e) {
	        System.err.println("UVENTET FEJL ved scanning: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double endSale() {
		if (currentSale == null) {
			throw new IllegalStateException("No active sale to end.");
		}

		currentSale.calculateTotal();
		currentSale.setStatus("COMPLETED");

		return currentSale.getTotalAmount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectPaymentMethod(String paymentMethod) {
		if (currentSale == null) {
			throw new IllegalStateException("No active sale to set payment method.");
		}

			currentSale.setPaymentMethod(paymentMethod);
		}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean saveSale(Sale s) {
		if (s == null) {
			throw new IllegalArgumentException("Cannot save null sale.");
		}
	
		if (s != currentSale) {
			currentSale = s;
		}

		if (currentSale.getReceiptNumber() == null || currentSale.getReceiptNumber().isEmpty()) {
			String receiptNumber = "R" + System.currentTimeMillis();
			currentSale.setReceiptNumber(receiptNumber);
		}
	
		boolean success = saleDB.saveSale(currentSale);

		if (success) {
			currentSale.setStatus("SAVED");
		}

		return success;
	}

	/**
	 * Assigns an employee to the current sale
	 * 
	 * @param employeeId The ID of the employee
	 * @return true if successfully assigned, false otherwise
	 */
	public boolean assignEmployee(String employeeId) {
		if (currentSale == null) {
			throw new IllegalStateException("No active sale to assign employee to.");
		}

		EmployeeControllerIF employeeController = new EmployeeController();
		Employee employee = employeeController.getEmployee(employeeId);

		if (employee != null) {
			currentSale.setEmployee(employee);
			return true;
		}

		return false;
	}

	/**
	 * Gets the current active sale
	 * 
	 * @return The current sale or null if no active sale
	 */
	public Sale getCurrentSale() {
		return currentSale;
	}
}