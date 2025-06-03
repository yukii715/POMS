
package com.owsb.poms.ui.pm;


import com.owsb.poms.system.model.PurchaseOrder;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import javax.swing.*;
import java.time.*;
import java.util.*;


public class PurchaseOrderTabs extends javax.swing.JFrame {

    private List<PurchaseOrder> purchaseOrderList;

    private DefaultTableModel PurchaseOrderTable = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){
           return false;
       } 
    };
    public void PurchaseOrder(){
        PurchaseOrderTable.setColumnIdentifiers(columnName);
        PurchaseOrderTable.setRowCount(0);
        
        purchaseOrderList = PurchaseOrder.toList();
        
        poTable.getColumnModel().getColumn(0).setPreferredWidth(125);
        poTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        poTable.getColumnModel().getColumn(3).setPreferredWidth(155);
        poTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        poTable.getColumnModel().getColumn(8).setPreferredWidth(150);


        
        for (PurchaseOrder purchaseOrder : purchaseOrderList) {
            PurchaseOrderTable.addRow(new String[]{
                purchaseOrder.getPoID(),
                purchaseOrder.getPrID(),
                purchaseOrder.getItemID(),
                purchaseOrder.getItemName(),
                String.valueOf(purchaseOrder.getQuantity()),
                String.format("%.2f", purchaseOrder.getUnitPrice()),
                String.format("%.2f", purchaseOrder.getTotalPrice()),
                purchaseOrder.getSupplierID(),
                String.valueOf(purchaseOrder.getGeneratedDateTime()),
                purchaseOrder.getStatus().name(),
                purchaseOrder.getCreatedBy(),
                purchaseOrder.getApprovedBy(),
            });
        }
    }
    private String[] columnName = {"Purchase Order ID", "Purchase Requisition ID", "Item ID","Item Name","Quantity","Unit Price","Total Price","Supplier ID", "Date", "status", "Created By", "Approved By"};
    private int row = -1;
    private Map<String, String> itemMap = new HashMap<>();
    

    public void ClearTextField(){
    txtQuantity.setText("");    
    txtUnitPrice.setText("");     
    txtSuID.setText("");       
    jTextField4.setText(""); 
    txtPmID.setText("");
    txtSmID.setText("");
    lblItemName.setText(""); 
    jTextField1.setText("");
    if (cmbItemID.getItemCount() > 0) {
        cmbItemID.setSelectedIndex(0);
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


    public PurchaseOrderTabs() {
        initComponents();
        PurchaseOrder();
        loadItemsFromFileToComboBox();
    
    // Set up table selection listener
    poTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            poTableMouseReleased(evt);
        }
    });
    
    
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        poTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        returnButton = new javax.swing.JButton();
        txtUnitPrice = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtSuID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        lblItemName = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        cmbItemID = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPmID = new javax.swing.JTextField();
        txtSmID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        poTable.setModel(PurchaseOrderTable);
        poTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                poTableMouseClicked(evt);
            }
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
        jLabel9.setText("PM ID:");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("SM ID: ");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Purchase Requisition ID: ");

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
                                                .addGap(92, 92, 92)
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
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(cmbItemID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 0, Short.MAX_VALUE)))
                                                .addGap(63, 63, 63)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addGap(39, 39, 39)
                                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(146, 146, 146)
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(89, 89, 89)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtPmID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtSuID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtSmID, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(193, 193, 193)
                                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(211, 211, 211)
                                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPmID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(txtSmID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItemName)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSuID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void poTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_poTableMouseClicked

    private void poTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_poTableMouseReleased
        row = poTable.getSelectedRow();
        if (row < 0) return;  // No row selected

        String poId = String.valueOf(PurchaseOrderTable.getValueAt(row, 0));
        String prId = String.valueOf(PurchaseOrderTable.getValueAt(row, 1));
        String itemId = String.valueOf(PurchaseOrderTable.getValueAt(row, 2)); // Get item ID from table
        String itemName = String.valueOf(PurchaseOrderTable.getValueAt(row, 3));
        String Quantity = String.valueOf(PurchaseOrderTable.getValueAt(row, 4));
        String unitPriceStr = String.valueOf(PurchaseOrderTable.getValueAt(row, 5));
        String supplierId = String.valueOf(PurchaseOrderTable.getValueAt(row, 7));
        String createdBy = String.valueOf(PurchaseOrderTable.getValueAt(row, 10));
        String approvedBy = String.valueOf(PurchaseOrderTable.getValueAt(row, 11));

        txtQuantity.setText(Quantity);
        txtUnitPrice.setText(unitPriceStr);
        txtSuID.setText(supplierId);
        jTextField4.setText(poId);
        jTextField1.setText(prId);
        txtPmID.setText(createdBy);
        txtSmID.setText(approvedBy);

        // Set the combo box selection to the item ID from the table
        cmbItemID.setSelectedItem(itemId);
        // The item name label will update automatically through the action listener
    }//GEN-LAST:event_poTableMouseReleased

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String quantityStr = txtQuantity.getText();
        String unitPriceStr = txtUnitPrice.getText();
        String supplierId = txtSuID.getText();
        String poId = jTextField4.getText();
        String prId = jTextField1.getText();
        String pmId = txtPmID.getText();
        String smId = txtSmID.getText();
        String itemId = cmbItemID.getSelectedItem().toString();
        String itemName = lblItemName.getText();
        String status = "Pending";
        LocalDateTime currentDate = LocalDateTime.now();
        String date = String.valueOf(currentDate);


        if (quantityStr.isEmpty() || unitPriceStr.isEmpty() || supplierId.isEmpty() || poId.isEmpty() || prId.isEmpty() || pmId.isEmpty() || smId.isEmpty()){
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

            String[] newRow = {poId, prId, itemId, itemName, quantityStr, unitPriceStr, totalPrice, supplierId, date, status, pmId, smId};
            PurchaseOrderTable.addRow(newRow);

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
        String supplierId = txtSuID.getText();
        String poId = jTextField4.getText();
        String prId = jTextField1.getText();
        String pmId = txtPmID.getText();
        String smId = txtSmID.getText();
        String itemId = cmbItemID.getSelectedItem().toString();
        String itemName = lblItemName.getText();

        // Validate required fields
        if (quantityStr.isEmpty() || unitPriceStr.isEmpty() || prId.isEmpty() || supplierId.isEmpty() ||
            poId.isEmpty() || pmId.isEmpty() || smId.isEmpty()) {
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
            PurchaseOrderTable.setValueAt(pmId, row, 9);
            PurchaseOrderTable.setValueAt(smId, row, 10);

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
            row = -1;
            
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveTableToFile("C:\\Users\\user\\Downloads\\POMS\\data\\PurchaseOrders\\PurchaseOrders.txt");
    }//GEN-LAST:event_saveButtonActionPerformed

    private void returnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_returnButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        ClearTextField();
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
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JTable poTable;
    private javax.swing.JButton returnButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField txtPmID;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSmID;
    private javax.swing.JTextField txtSuID;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
