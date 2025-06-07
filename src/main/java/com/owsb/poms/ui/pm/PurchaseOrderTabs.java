
package com.owsb.poms.ui.pm;

import com.owsb.poms.system.model.POItem;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import javax.swing.*;
import java.time.*;
import java.util.*;


public class PurchaseOrderTabs extends javax.swing.JFrame {

    private POItem po;
    private List<POItem> POItemList = new ArrayList<>();

    private DefaultTableModel PurchaseOrderTable = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){
           return false;
       } 
    };
    public void Table(){
        PurchaseOrderTable.setColumnIdentifiers(columnName);
        PurchaseOrderTable.setRowCount(0);
        
        POItemList = POItem.toItemList();
  
                
        for (POItem poItem : POItemList) {
            PurchaseOrderTable.addRow(new String[]{
                poItem.getPOID(),
                poItem.getPRID(),
                poItem.getItemID(),
                String.valueOf(poItem.getQuantity()),
                String.format("%.2f", poItem.getUnitPrice()),
                String.format("%.2f", poItem.getTotalPrice()),
                poItem.getSupplierID(),
                String.valueOf(poItem.getGenerateDateTime()),
                String.valueOf(poItem.getDeliveryDate()),
                poItem.getStatus().name(),
                poItem.getCreateBy(),
                poItem.getApprovedBy(),
            });
        }
    }
    private String[] columnName = {"Purchase Order ID","Purchase Requisition ID","Item ID","Quantity", "Unit Price", "Total Price","Supplier ID", "Date & Time of generated","Delivery Date", "status", "Created By", "Approved By"};
    private int row = -1;
    

    public void ClearTextField(){
    txtQuantity.setText("");    
    txtUnitPrice.setText("");     
    txtSupplierID.setText("");       
    txtCreateBy.setText("");
    txtApprovedBy.setText("");
    lblItemName.setText(""); 
    jTextField1.setText("");
    if (cmbItemID.getItemCount() > 0) {
        cmbItemID.setSelectedIndex(0);
    }
    String currentPOID = lblPOID.getText();
            String newPOID = incrementPOID(currentPOID);
            lblPOID.setText(newPOID);
    }
        int selectedRow = 0;

private void clearForm() {
    boolean wasEditing = selectedRow >= 0; // remember if editing
    selectedRow = -1; // reset selection status early

    jTextField1.setText("");
    txtQuantity.setText("");
    txtUnitPrice.setText("");
    txtSupplierID.setText("");
    txtCreateBy.setText("");
    txtApprovedBy.setText("");
    cmbItemID.setSelectedIndex(0);
    lblItemName.setText("");

    if (wasEditing) {
        lblPOID.setText(incrementPOID(lblPOID.getText()));
    }
}

private void loadItemsFromFileToComboBox() {
    cmbItemID.removeAllItems(); // Clear existing items
    try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\user\\Downloads\\POMS\\data\\Items\\items.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\t");
            if (parts.length > 0) {
                cmbItemID.addItem(parts[0].trim()); // Assuming first column is item ID
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
    }
    
    // Add listener to update item name when selection changes
    cmbItemID.addActionListener(e -> updateItemNameLabel());
}
private void updateItemNameLabel() {
    String selectedItemId = (String) cmbItemID.getSelectedItem();
    if (selectedItemId != null) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\user\\Downloads\\POMS\\data\\Items\\items.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\t");
                if (parts.length >= 2 && parts[0].trim().equals(selectedItemId)) {
                    lblItemName.setText(parts[1].trim()); // Assuming second column is item name
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading item details: " + e.getMessage());
        }
    }
}


private void saveTableToFile(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) { // overwrite file
        int rowCount = PurchaseOrderTable.getRowCount();
        int colCount = PurchaseOrderTable.getColumnCount();

        for (int i = 0; i < rowCount; i++) {
            StringBuilder lineBuilder = new StringBuilder();
            for (int j = 0; j < colCount; j++) {
                Object cellValue = PurchaseOrderTable.getValueAt(i, j);
                lineBuilder.append(cellValue != null ? cellValue.toString() : "");
                if (j < colCount - 1) {
                    lineBuilder.append("\t"); // use actual tab character
                }
            }
            writer.write(lineBuilder.toString());
            writer.newLine();
        }
        JOptionPane.showMessageDialog(this, rowCount + " entries saved to " + filename);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
    }
}

private String getHighestPOIDFromFile(String filename) {
    String highestPOID = "PO00000"; // default if no entries
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\t");
            if (parts.length > 0) {
                String currentPOID = parts[0].trim();
                if (comparePOID(currentPOID, highestPOID) > 0) {
                    highestPOID = currentPOID;
                }
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading PO file: " + e.getMessage());
    }
    return highestPOID;
}


// Compare two POIDs, returns positive if poid1 > poid2
private int comparePOID(String poid1, String poid2) {
    // Remove "PO" prefix and parse the number part
    int num1 = Integer.parseInt(poid1.substring(2));
    int num2 = Integer.parseInt(poid2.substring(2));
    return Integer.compare(num1, num2);
}

// Increment POID by 1, e.g. PO00005 -> PO00006
private String incrementPOID(String poid) {
    int number = Integer.parseInt(poid.substring(2));
    number++;
    return String.format("PO%05d", number);
}



    public PurchaseOrderTabs() {
        initComponents();
        Table();
        String highestPOID = getHighestPOIDFromFile("C:\\Users\\user\\Downloads\\POMS\\data\\PO\\items.txt");
        String nextPOID = incrementPOID(highestPOID);  
        lblPOID.setText(nextPOID);
        
//        lblPOID.setText(new PurchaseOrder().generateID());
        loadItemsFromFileToComboBox();
    
    // Set up table selection listener

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane5 = new javax.swing.JScrollPane();
        poTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();
        txtUnitPrice = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtSupplierID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        lblItemName = new javax.swing.JLabel();
        cmbItemID = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCreateBy = new javax.swing.JTextField();
        txtApprovedBy = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblPOID = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        poTable.setModel(PurchaseOrderTable);
        poTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                poTableMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(poTable);

        addButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        editButton.setText("Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        saveButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        returnButton.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        returnButton.setText("Return");
        returnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel1.setText("Purchase Order ID:");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel2.setText("Item name:");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setText("Item ID: ");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel4.setText("Quantity:");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setText("Unit Price: ");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel7.setText("Supplier ID: ");
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setText("Purchase Orders");

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        lblItemName.setText("Stainless Steel Pan");

        cmbItemID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbItemID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemIDActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel9.setText("Created By:");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("Approved by:");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Purchase Requisition ID: ");
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblPOID.setText("Purchase Order ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(980, 980, 980)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(74, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel6))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(193, 193, 193)
                                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(211, 211, 211)
                                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(92, 92, 92)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblPOID)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(cmbItemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                        .addGap(63, 63, 63)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(51, 51, 51)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(12, 12, 12)
                                                                .addComponent(jLabel10)))
                                                        .addGap(74, 74, 74)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtCreateBy, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtSupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(txtApprovedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(returnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPOID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCreateBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmbItemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApprovedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItemName)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSupplierID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void poTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poTableMouseReleased
row = poTable.getSelectedRow();
    if (row < 0) return;  // No row selected
    if (po == null) {
        po = new POItem();  // Initialize po if null
    }
    po.setPOID(String.valueOf(PurchaseOrderTable.getValueAt(row, 0)));
    String prId = String.valueOf(PurchaseOrderTable.getValueAt(row, 1));
    String itemId = String.valueOf(PurchaseOrderTable.getValueAt(row, 2)); 
    String quantity = String.valueOf(PurchaseOrderTable.getValueAt(row, 3));
    String unitPriceStr = String.valueOf(PurchaseOrderTable.getValueAt(row, 4));
    String supplierId = String.valueOf(PurchaseOrderTable.getValueAt(row, 6));
    String createdBy = String.valueOf(PurchaseOrderTable.getValueAt(row, 10));
    String approvedBy = String.valueOf(PurchaseOrderTable.getValueAt(row, 11));

    lblPOID.setText(po.getPOID());
    txtQuantity.setText(quantity);
    txtUnitPrice.setText(unitPriceStr);
    txtSupplierID.setText(supplierId);
    jTextField1.setText(prId);
    txtCreateBy.setText(createdBy);
    txtApprovedBy.setText(approvedBy);

    // Set the combo box selection to the item ID from the table
    cmbItemID.setSelectedItem(itemId);
    // The item name label will update automatically through the action listener

    }//GEN-LAST:event_poTableMouseReleased

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String quantityStr = txtQuantity.getText();
        String unitPriceStr = txtUnitPrice.getText();
        String supplierId = txtSupplierID.getText();
        String poId = lblPOID.getText();
        String prId = jTextField1.getText();
        String createBy = txtCreateBy.getText();
        String approvedBy = txtApprovedBy.getText();
        String itemId = cmbItemID.getSelectedItem().toString();
        String itemName = lblItemName.getText();
        String status = "NEW";
        LocalDateTime currentDate = LocalDateTime.now();
        String dateGenerated = String.valueOf(currentDate);
        LocalDate deliveryDate = LocalDate.now();
        String DeliveryDateGenerated = String.valueOf(deliveryDate);

        if (quantityStr.isEmpty() || unitPriceStr.isEmpty() || supplierId.isEmpty() || poId.isEmpty() || prId.isEmpty() || createBy.isEmpty() || approvedBy.isEmpty()){
            JOptionPane.showMessageDialog(null,
                "Please fill in all the fields before submitting.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE);
            return;  // Stop further processing
        }

        try{
            int Quantity = Integer.parseInt(quantityStr);
            double unitPrice = Double.parseDouble(unitPriceStr);
            double totalPriceDbl = Quantity * unitPrice;
            String totalPrice = Double.toString(totalPriceDbl);

            String[] newRow = {poId, prId, itemId, quantityStr, unitPriceStr, totalPrice, supplierId, dateGenerated,DeliveryDateGenerated, status, createBy, approvedBy};
            PurchaseOrderTable.addRow(newRow);
            String currentPOID = lblPOID.getText();
            String newPOID = incrementPOID(currentPOID);
            ClearTextField();
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null,
                "Invalid input for Quantity or Price. Please enter numeric values.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            return;
        }

        String quantityStr = txtQuantity.getText();
        String unitPriceStr = txtUnitPrice.getText();
        String supplierId = txtSupplierID.getText();
        String poId = lblPOID.getText();
        String prId = jTextField1.getText();
        String createBy = txtCreateBy.getText();
        String approvedBy = txtApprovedBy.getText();
        String itemId = cmbItemID.getSelectedItem().toString();
        String itemName = lblItemName.getText();

        // Validate required fields
        if (quantityStr.isEmpty() || unitPriceStr.isEmpty() || prId.isEmpty() || supplierId.isEmpty() ||
            poId.isEmpty() || createBy.isEmpty() || approvedBy.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all the fields before saving changes.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Calculate total price
            int quantity = Integer.parseInt(quantityStr);
            double unitPrice = Double.parseDouble(unitPriceStr);
            double totalPrice = quantity * unitPrice;

            // Update the selected row with new values
            PurchaseOrderTable.setValueAt(poId, row, 0);
            PurchaseOrderTable.setValueAt(itemId, row, 1);
            PurchaseOrderTable.setValueAt(itemName, row, 2);
            PurchaseOrderTable.setValueAt(quantityStr, row, 3);
            PurchaseOrderTable.setValueAt(unitPriceStr, row, 4);
            PurchaseOrderTable.setValueAt(String.valueOf(totalPrice), row, 5);
            PurchaseOrderTable.setValueAt(supplierId, row, 6);
            // Date remains unchanged when editing
            // Status remains unchanged when editing
            PurchaseOrderTable.setValueAt(createBy, row, 10);
            PurchaseOrderTable.setValueAt(approvedBy, row, 11);

            JOptionPane.showMessageDialog(this, "Purchase order updated successfully!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input for Quantity or Price. Please enter numeric values.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (row == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an item to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Are you sure to remove this item?", "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION)
        {
            PurchaseOrderTable.removeRow(row);
            String currentPOID = lblPOID.getText();
            String newPOID = incrementPOID(currentPOID);
            lblPOID.setText(newPOID);
            row = -1;
            
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveTableToFile("C:\\Users\\user\\Downloads\\POMS\\data\\PO\\items.txt");
    }//GEN-LAST:event_saveButtonActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_returnButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearForm();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void cmbItemIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemIDActionPerformed

    }//GEN-LAST:event_cmbItemIDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrderTabs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PurchaseOrderTabs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> cmbItemID;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblPOID;
    private javax.swing.JTable poTable;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField txtApprovedBy;
    private javax.swing.JTextField txtCreateBy;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSupplierID;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
