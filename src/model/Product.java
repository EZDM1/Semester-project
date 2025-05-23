package model;
/**
 * Represents a product in the QB inventory system
 */
public class Product {
	private int productId;
    private String barcode;
    private String name;
    private String description;
    private double price;
    private String category;
    
    /**
     * Constructor for creating a new Product
     * 
     * @param barcode The product barcode
     * @param name The product name
     * @param description The product description
     * @param price The product price
     * @param category The product category
     */
    public Product(int productId, String barcode, String name, String description, double price, String category) {
    	this.productId = productId;
    	this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    public int getProductId() {
        return productId;
    }
    
    /**
     * Gets the product barcode
     * 
     * @return The barcode
     */
    public String getBarcode() {
        return barcode;
    }
    
    /**
     * Gets the product name
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the product price
     * 
     * @return The price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Gets the product description
     * 
     * @return The description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the product category
     * 
     * @return The category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Sets the product name
     * 
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the product description
     * 
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Sets the product price
     * 
     * @param price The new price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Sets the product category
     * 
     * @param category The new category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Sets the product barcode
     * 
     * @param barcode The new barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    @Override
    public String toString() {
        return name + " [" + barcode + "] - " + price + " kr.";
    }
}