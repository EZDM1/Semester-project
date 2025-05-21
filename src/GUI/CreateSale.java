package GUI;

import java.awt.EventQueue;

import controller.SaleController;
import controller.interfaces.SaleControllerIF;
import model.Sale;
import model.Product;
import model.SaleLineItem;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class CreateSale extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private SaleControllerIF saleController;
	private Sale currentSale;
	private DefaultTableModel tableModel;
	private JTextField barcodeField;
	private JTextField quantityField;
	private double currentTotal = 0.0;
	private JLabel subTotalLabel;
	private Sale completedSale;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateSale frame = new CreateSale();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreateSale() {
		// Initialize controller
		saleController = new SaleController();
		
		// Create a new sale
		currentSale = saleController.createSale();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel displayArea = new JPanel();
		panel.add(displayArea, BorderLayout.CENTER);
		displayArea.setLayout(new BorderLayout(0, 0));
		
		// Add input panel for barcode and quantity
		JPanel inputPanel = new JPanel();
		barcodeField = new JTextField(15);
		quantityField = new JTextField("1", 5);
		JLabel barcodeLabel = new JLabel("Barcode:");
		JLabel quantityLabel = new JLabel("Qty:");
		inputPanel.add(barcodeLabel);
		inputPanel.add(barcodeField);
		inputPanel.add(quantityLabel);
		inputPanel.add(quantityField);
		displayArea.add(inputPanel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		displayArea.add(panel_1, BorderLayout.SOUTH);
		
		subTotalLabel = new JLabel("Sub total: 0.00 kr.");
		subTotalLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		panel_1.add(subTotalLabel);
		
		// Initialize table model with column names
		tableModel = new DefaultTableModel(
			new Object[][] {},
			new String[] {"Product", "Quantity", "Price", "Subtotal"}
		);
		
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		displayArea.add(scrollPane, BorderLayout.CENTER);
		
		JPanel numericKeypad = new JPanel();
		panel.add(numericKeypad, BorderLayout.SOUTH);
		numericKeypad.setLayout(new GridLayout(0, 5, 0, 0));
		
		JButton ekspButton = new JButton("Eksp. Nr.");
		ekspButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String employeeId = JOptionPane.showInputDialog("Enter Employee ID:");
		        if (employeeId != null && !employeeId.isEmpty()) {
		            try {
		                boolean success = ((SaleController)saleController).assignEmployee(employeeId);
		                if (success) {
		                    JOptionPane.showMessageDialog(null, "Employee #" + employeeId + " assigned to sale");
		                } else {
		                    JOptionPane.showMessageDialog(null, "Employee not found with ID: " + employeeId);
		                }
		            } catch (Exception ex) {
		                JOptionPane.showMessageDialog(null, "Error assigning employee: " + ex.getMessage());
		            }
		        }
		    }
		});
		numericKeypad.add(ekspButton);
		
		JButton one = new JButton("1");
		one.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("1");
			}
		});
		numericKeypad.add(one);
		
		JButton two = new JButton("2");
		two.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("2");
			}
		});
		numericKeypad.add(two);
		
		JButton three = new JButton("3");
		three.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("3");
			}
		});
		numericKeypad.add(three);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setForeground(Color.WHITE);
		clearButton.setBackground(new Color(255, 0, 0));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barcodeField.setText("");
				quantityField.setText("1");
			}
		});
		numericKeypad.add(clearButton);
		
		JButton fejlButton = new JButton("Fejl");
		fejlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Remove the last item if table has items
				if (tableModel.getRowCount() > 0) {
					tableModel.removeRow(tableModel.getRowCount() - 1);
					updateTotalDisplay();
				}
			}
		});
		numericKeypad.add(fejlButton);
		
		JButton four = new JButton("4");
		four.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("4");
			}
		});
		numericKeypad.add(four);
		
		JButton five = new JButton("5");
		five.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("5");
			}
		});
		numericKeypad.add(five);
		
		JButton six = new JButton("6");
		six.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("6");
			}
		});
		numericKeypad.add(six);
		
		JButton dankortButton = new JButton("Dankort");
		dankortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				completePayment("CARD");
			}
		});
		numericKeypad.add(dankortButton);
		
		JButton returButton = new JButton("Retur");
		returButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Return functionality would be complex, just show message for now
				JOptionPane.showMessageDialog(null, "Return functionality not implemented");
			}
		});
		numericKeypad.add(returButton);
		
		JButton seven = new JButton("7");
		seven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("7");
			}
		});
		numericKeypad.add(seven);
		
		JButton eight = new JButton("8");
		eight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("8");
			}
		});
		numericKeypad.add(eight);
		
		JButton nine = new JButton("9");
		nine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("9");
			}
		});
		numericKeypad.add(nine);
		
		JButton stregkodeButton = new JButton("Stregkode");
		stregkodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scanProduct();
			}
		});
		
		// If you have an icon for the barcode button
		try {
			ImageIcon originalIcon = new ImageIcon("src/project.png");
			Image image = originalIcon.getImage().getScaledInstance(40, 20, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(image);
			stregkodeButton.setIcon(resizedIcon);
		} catch (Exception e) {
			// If icon not found, just use text
			System.out.println("Icon not found, using text only");
		}
		
		stregkodeButton.setVerticalTextPosition(SwingConstants.TOP);
		stregkodeButton.setHorizontalTextPosition(SwingConstants.CENTER);
		numericKeypad.add(stregkodeButton);
		
		JButton opButton = new JButton("Op");
		opButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Select previous row in table if possible
				if (table.getSelectedRow() > 0) {
					table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
				}
			}
		});
		numericKeypad.add(opButton);
		
		JButton quantityButton = new JButton("X");
		quantityButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Focus on quantity field
				quantityField.requestFocus();
				quantityField.selectAll();
			}
		});
		numericKeypad.add(quantityButton);
		
		JButton zero = new JButton("0");
		zero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("0");
			}
		});
		numericKeypad.add(zero);
		
		JButton punktumButton = new JButton(".");
		punktumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField(".");
			}
		});
		numericKeypad.add(punktumButton);
		
		JButton kontantButton = new JButton("Kontant");
		kontantButton.setBackground(Color.GREEN);
		kontantButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            if (tableModel.getRowCount() == 0) {
		                JOptionPane.showMessageDialog(null, "No items in the sale");
		                return;
		            }
		            
		            // Check if employee is assigned
		            if (currentSale.getEmployee() == null) {
		                int choice = JOptionPane.showConfirmDialog(null, 
		                    "No employee assigned. Assign employee now?",
		                    "Employee Required", 
		                    JOptionPane.YES_NO_OPTION);
		                
		                if (choice == JOptionPane.YES_OPTION) {
		                    // Ask for employee ID
		                    String employeeId = JOptionPane.showInputDialog("Enter Employee ID:");
		                    if (employeeId != null && !employeeId.isEmpty()) {
		                        boolean success = ((SaleController)saleController).assignEmployee(employeeId);
		                        if (!success) {
		                            JOptionPane.showMessageDialog(null, "Employee not found. Cannot complete sale.");
		                            return;
		                        }
		                    } else {
		                        JOptionPane.showMessageDialog(null, "Employee ID required. Cannot complete sale.");
		                        return;
		                    }
		                } else {
		                    JOptionPane.showMessageDialog(null, "Employee required. Cannot complete sale.");
		                    return;
		                }
		            }
		            
		            // End the sale and get final total
		            currentTotal = saleController.endSale();
		            
		            // Set payment method
		            saleController.selectPaymentMethod("CASH");
		            
		            // Debug info
		            System.out.println("Saving sale with employee ID: " + 
		                currentSale.getEmployee().getEmployeeID());
		            
		            // Save the sale
		            boolean success = saleController.saveSale(currentSale);
		            
		            if (success) {
		                JOptionPane.showMessageDialog(null, 
		                    "Sale completed successfully\nTotal: " + 
		                    String.format("%.2f", currentTotal) + " kr.");
		                
		                // Store for receipt printing
		                completedSale = currentSale;
		                
		                // Reset for new sale
		                resetSale();
		            } else {
		                JOptionPane.showMessageDialog(null, "Error saving sale");
		            }
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		            ex.printStackTrace();
		        }
		    }
		});
		numericKeypad.add(kontantButton);
		
		JButton nedButton = new JButton("Ned");
		nedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Select next row in table if possible
				if (table.getSelectedRow() < table.getRowCount() - 1) {
					table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
				}
			}
		});
		numericKeypad.add(nedButton);
		
		JButton salgHistorieButton = new JButton("Salg Historie");
		salgHistorieButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Sales history functionality not implemented");
			}
		});
		numericKeypad.add(salgHistorieButton);
		
		JButton dobbeltZero = new JButton("00");
		dobbeltZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appendToActiveField("00");
			}
		});
		numericKeypad.add(dobbeltZero);
		
		JButton subTotalButton = new JButton("Sub Total");
		subTotalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTotalDisplay();
			}
		});
		numericKeypad.add(subTotalButton);
		
		
		
		JButton skrivBonButton = new JButton("Skriv Bon");
		skrivBonButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (completedSale != null) {
		            // Show receipt for completedSale
		            showReceipt(completedSale);
		        } else {
		            JOptionPane.showMessageDialog(null, "No completed sale to print receipt for");
		        }
		    }
		});
		
		numericKeypad.add(skrivBonButton);
		
		JPanel productGridPanel = new JPanel();
		contentPane.add(productGridPanel, BorderLayout.EAST);
		GridBagLayout gbl_productGridPanel = new GridBagLayout();
		gbl_productGridPanel.columnWidths = new int[]{120, 120, 120, 120, 0};
		gbl_productGridPanel.rowHeights = new int[]{70, 70, 0};
		gbl_productGridPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_productGridPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		productGridPanel.setLayout(gbl_productGridPanel);
		
		JButton frugtButton = new JButton("Frugt");
		frugtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Frugt");
			}
		});
		GridBagConstraints gbc_frugtButton = new GridBagConstraints();
		gbc_frugtButton.fill = GridBagConstraints.BOTH;
		gbc_frugtButton.insets = new Insets(0, 0, 5, 5);
		gbc_frugtButton.gridx = 0;
		gbc_frugtButton.gridy = 0;
		productGridPanel.add(frugtButton, gbc_frugtButton);
		
		JButton grønsagerButton = new JButton("Grønsager");
		grønsagerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Grønsager");
			}
		});
		GridBagConstraints gbc_grønsagerButton = new GridBagConstraints();
		gbc_grønsagerButton.fill = GridBagConstraints.BOTH;
		gbc_grønsagerButton.insets = new Insets(0, 0, 5, 5);
		gbc_grønsagerButton.gridx = 1;
		gbc_grønsagerButton.gridy = 0;
		productGridPanel.add(grønsagerButton, gbc_grønsagerButton);
		
		JButton drikkevarerButton = new JButton("Drikkevarer");
		drikkevarerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Drikkevarer");
			}
		});
		GridBagConstraints gbc_drikkevarerButton = new GridBagConstraints();
		gbc_drikkevarerButton.fill = GridBagConstraints.BOTH;
		gbc_drikkevarerButton.insets = new Insets(0, 0, 5, 5);
		gbc_drikkevarerButton.gridx = 2;
		gbc_drikkevarerButton.gridy = 0;
		productGridPanel.add(drikkevarerButton, gbc_drikkevarerButton);
		
		JButton brødButton = new JButton("Brød");
		brødButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Brød");
			}
		});
		GridBagConstraints gbc_brødButton = new GridBagConstraints();
		gbc_brødButton.fill = GridBagConstraints.BOTH;
		gbc_brødButton.insets = new Insets(0, 0, 5, 0);
		gbc_brødButton.gridx = 3;
		gbc_brødButton.gridy = 0;
		productGridPanel.add(brødButton, gbc_brødButton);
		
		JButton krydderierButton = new JButton("Krydderier");
		krydderierButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Krydderier");
			}
		});
		GridBagConstraints gbc_krydderierButton = new GridBagConstraints();
		gbc_krydderierButton.fill = GridBagConstraints.BOTH;
		gbc_krydderierButton.insets = new Insets(0, 0, 0, 5);
		gbc_krydderierButton.gridx = 0;
		gbc_krydderierButton.gridy = 1;
		productGridPanel.add(krydderierButton, gbc_krydderierButton);
		
		JButton nødderButton = new JButton("Nødder/Kerner");
		nødderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Nødder");
			}
		});
		GridBagConstraints gbc_nødderButton = new GridBagConstraints();
		gbc_nødderButton.fill = GridBagConstraints.BOTH;
		gbc_nødderButton.insets = new Insets(0, 0, 0, 5);
		gbc_nødderButton.gridx = 1;
		gbc_nødderButton.gridy = 1;
		productGridPanel.add(nødderButton, gbc_nødderButton);
		
		JButton slikButton = new JButton("Slik/Kage");
		slikButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Slik");
			}
		});
		GridBagConstraints gbc_slikButton = new GridBagConstraints();
		gbc_slikButton.fill = GridBagConstraints.BOTH;
		gbc_slikButton.insets = new Insets(0, 0, 0, 5);
		gbc_slikButton.gridx = 2;
		gbc_slikButton.gridy = 1;
		productGridPanel.add(slikButton, gbc_slikButton);
		
		JButton risButton = new JButton("Ris");
		risButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCategoryItems("Ris");
			}
		});
		GridBagConstraints gbc_risButton = new GridBagConstraints();
		gbc_risButton.fill = GridBagConstraints.BOTH;
		gbc_risButton.gridx = 3;
		gbc_risButton.gridy = 1;
		productGridPanel.add(risButton, gbc_risButton);
	}
	
	/**
	 * Method to scan a product by barcode
	 */
	private void scanProduct() {
		String barcode = barcodeField.getText().trim();
		if (barcode.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a barcode");
			return;
		}
		
		int quantity = 1;
		try {
			quantity = Integer.parseInt(quantityField.getText().trim());
			if (quantity <= 0) {
				JOptionPane.showMessageDialog(this, "Quantity must be positive");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Invalid quantity");
			return;
		}
		
		Product product = saleController.scanProduct(barcode, quantity);
		if (product != null) {
			updateTableWithProduct(product, quantity);
			updateTotalDisplay();
			
			// Clear fields for next scan
			barcodeField.setText("");
			quantityField.setText("1");
			barcodeField.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this, "Product not found: " + barcode);
		}
	}
	
	/**
	 * Updates the table with a scanned product
	 */
	private void updateTableWithProduct(Product product, int quantity) {
		tableModel.addRow(new Object[] {
			product.getName(),
			quantity,
			product.getPrice(),
			product.getPrice() * quantity
		});
	}
	
	/**
	 * Shows a receipt for a completed sale
	 * 
	 * @param sale The sale to show the receipt for
	 */
	private void showReceipt(Sale sale) {
	    if (sale == null) {
	        JOptionPane.showMessageDialog(this, "No sale to show receipt for");
	        return;
	    }
	    
	    StringBuilder receipt = new StringBuilder();
	    receipt.append("===== QB ApS =====\n");
	    receipt.append("Receipt: ").append(sale.getReceiptNumber()).append("\n");
	    receipt.append("Date: ").append(sale.getDateTime().toLocalDate()).append("\n");
	    receipt.append("Time: ").append(sale.getDateTime().toLocalTime()).append("\n");
	    
	    if (sale.getEmployee() != null) {
	        receipt.append("Employee: ").append(sale.getEmployee().getName()).append("\n");
	    }
	    
	    receipt.append("\nItems:\n");
	    receipt.append("--------------------\n");
	    
	    for (SaleLineItem item : sale.getSaleLineItems()) {
	        receipt.append(String.format("%-20s %3d x %6.2f = %7.2f kr.\n", 
	            item.getProduct().getName(), 
	            item.getQuantity(), 
	            item.getProduct().getPrice(), 
	            item.getSubtotal()));
	    }
	    
	    receipt.append("--------------------\n");
	    receipt.append(String.format("TOTAL: %25.2f kr.\n", sale.getTotalAmount()));
	    
	    receipt.append("\nPayment: ").append(sale.getPaymentStatus()).append("\n");
	    receipt.append("\nThank you for shopping at QB ApS!\n");
	    
	    // Show in a dialog
	    JTextArea textArea = new JTextArea(receipt.toString());
	    textArea.setEditable(false);
	    textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
	    
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setPreferredSize(new Dimension(400, 400));
	    
	    JOptionPane.showMessageDialog(this, scrollPane, "Receipt", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Updates the total display based on items in table
	 */
	private void updateTotalDisplay() {
		double total = 0;
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			total += (double) tableModel.getValueAt(i, 3);
		}
		subTotalLabel.setText("Sub total: " + String.format("%.2f", total) + " kr.");
		currentTotal = total;
	}
	
	/**
	 * Completes the sale with the given payment method
	 */
	private void completePayment(String paymentMethod) {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "No items in the sale");
			return;
		}
		
		// End the sale and get final total
		currentTotal = saleController.endSale();
		
		// Set payment method
		saleController.selectPaymentMethod(paymentMethod);
		
		// Save the sale
		boolean success = saleController.saveSale(currentSale);
		
		if (success) {
			JOptionPane.showMessageDialog(this, 
				"Sale completed successfully\nTotal: " + String.format("%.2f", currentTotal) + " kr.\nPayment: " + paymentMethod);
			resetSale();
		} else {
			JOptionPane.showMessageDialog(this, "Error saving sale");
		}
	}
	
	/**
	 * Resets the sale after completion
	 */
	private void resetSale() {
		// Clear the table
		tableModel.setRowCount(0);
		
		// Reset fields
		barcodeField.setText("");
		quantityField.setText("1");
		
		// Reset labels
		subTotalLabel.setText("Sub total: 0.00 kr.");
		
		// Create a new sale
		currentSale = saleController.createSale();
	}
	
	/**
	 * Helper method to add text to the currently active text field
	 */
	private void appendToActiveField(String text) {
		if (barcodeField.hasFocus()) {
			barcodeField.setText(barcodeField.getText() + text);
		} else if (quantityField.hasFocus()) {
			quantityField.setText(quantityField.getText() + text);
		} else {
			// Default to barcode field if nothing has focus
			barcodeField.setText(barcodeField.getText() + text);
			barcodeField.requestFocus();
		}
	}
	
	/**
	 * Creates a product based on category and entered price
	 */
	private void showCategoryItems(String category) {
		// Get price from barcode field (which we'll use for price entry)
		double price = 0.0;
		try {
			price = Double.parseDouble(barcodeField.getText().trim());
			if (price < 0) {
				JOptionPane.showMessageDialog(this, "Price cannot be negative");
				return;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Please enter a valid price in the barcode field");
			return;
		}
		
		// Get quantity
		int quantity = 1;
		try {
			quantity = Integer.parseInt(quantityField.getText().trim());
			if (quantity <= 0) {
				JOptionPane.showMessageDialog(this, "Quantity must be positive");
				return;
			}
		} catch (NumberFormatException e) {
			quantity = 1;
		}
		
		// Create a dynamic product
		String productName = category;
		String description = "Product in " + category + " category";
		String barcode = "CAT-" + category.toUpperCase() + "-" + System.currentTimeMillis();
		
		// Create the product object
		Product newProduct = new Product(barcode, productName, description, price, category);
		
		// Add to sale using a custom method that doesn't require DB lookup
		addProductToSale(newProduct, quantity);
		
		// Clear price input
		barcodeField.setText("");
	}
	
	/**
	 * Add a product directly to the sale without scanning
	 */
	private void addProductToSale(Product product, int quantity) {
		if (currentSale == null) {
			currentSale = saleController.createSale();
		}
		
		// Add to sale line items directly
		SaleLineItem item = new SaleLineItem(product, quantity);
		currentSale.addSaleLineItem(item);
		
		// Update the table
		updateTableWithProduct(product, quantity);
		updateTotalDisplay();
	}
}