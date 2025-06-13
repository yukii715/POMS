
package com.owsb.poms.ui.pm;

import com.owsb.poms.system.functions.FileHandler;
import javax.swing.table.DefaultTableModel;
import com.owsb.poms.system.model.*;
import static com.owsb.poms.system.model.StockReport.filePath;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.*;

public class POEditor extends javax.swing.JFrame {

    private PurchaseOrder po;
    private POItem poItem;
    private double newTotalPrice;
    private List<POItem> orderItems = new ArrayList<>();
    private List<PurchaseOrder> poItems = new ArrayList<>();


    private DefaultTableModel itemsModel = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return column == 4 || column == 5; // Only Quantity and Unit Price are editable
        }
    };
    
    private String[] itemsColumnName = {"Item ID", "Category", "Type", "Name", "Quantity", "Unit Price"};
    private List<POItem> currentItems = new ArrayList<>();
    private String currentPOID = "";

    public POEditor() {
        setTitle("Purchase Order Editor");
        setSize(400, 300); 
        initComponents();
        initializeTable();
        loadPOList();
        cmbPOID.setSelectedIndex(-1);

    }

    private void initializeTable() {
        itemsModel.setColumnIdentifiers(itemsColumnName);
        tblItems.setModel(itemsModel);
        itemsModel.setRowCount(0);
    }
    
    private void loadPOList() {
        File poDir = new File("data/PO/");
        if (poDir.exists() && poDir.isDirectory()) {
            File[] poFiles = poDir.listFiles((dir, name) -> name.startsWith("PO") && name.endsWith(".txt"));
            if (poFiles != null) {
                Arrays.sort(poFiles);
                cmbPOID.removeAllItems();
                for (File file : poFiles) {
                    String fileName = file.getName();
                    String poID = fileName.substring(0, fileName.lastIndexOf('.'));
                    cmbPOID.addItem(poID);
                }
            }
        }
    }

    private void loadPOItems(String poID) {
    if (poID == null || poID.equals("-- Select Purchase Order --") || poID.isEmpty()) {
        itemsModel.setRowCount(0);
        currentItems.clear();
        return;
    }
    
    // Clear current data
    currentItems.clear();
    itemsModel.setRowCount(0);
    
    try {
        // Read from file
        List<POItem> items = POItem.read("data/PO/" + poID + ".txt");
        currentItems.addAll(items);
        
        // Debug output
        System.out.println("Loaded " + items.size() + " items from file:");
        
        // Populate table
        for (POItem item : items) {
            System.out.println(item);  // Debug output
            
            itemsModel.addRow(new Object[]{
                item.getItemID(),
                item.getItemCategory(),
                item.getItemType(),
                item.getItemName(),
                item.getQuantity(),
                item.getUnitPrice()
            });
        }
    } catch (Exception e) {
        System.err.println("Error loading PO items: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Error loading PO items: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    private void saveChanges() {
    String selectedPOID = (String) cmbPOID.getSelectedItem();
    if (selectedPOID == null) return;

    // 1. Calculate new total
    double newTotal = 0;
    for (int i = 0; i < itemsModel.getRowCount(); i++) {
        POItem item = currentItems.get(i);
        Object qtyObj = itemsModel.getValueAt(i, 4); // quantity column
        Object priceObj = itemsModel.getValueAt(i, 5); // price column

        try {
            int quantity = Integer.parseInt(qtyObj.toString());
            double price = Double.parseDouble(priceObj.toString());
            newTotal += quantity * price;
            
            // Update the item
            item.setQuantity(quantity);
            item.setUnitPrice(price);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid values in row " + (i+1));
            return;
        }
    }

    // 2. Save to individual PO file (POxxxxx.txt)
    File poFile = new File("data/PO/" + selectedPOID + ".txt");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(poFile))) {
        for (POItem item : currentItems) {
            writer.write(item.toString());
            writer.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Failed to save PO items: " + e.getMessage());
        return;
    }

    // 3. Update master purchase_orders.txt file (tab-delimited)
    File masterFile = new File("data/PO/purchase_order.txt");
    List<String> updatedLines = new ArrayList<>();
    boolean poFound = false;
    
    try (BufferedReader reader = new BufferedReader(new FileReader(masterFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(selectedPOID + "\t")) {
                String[] parts = line.split("\t");
                if (parts.length >= 3) { // Ensure we have at least totalPrice field
                    // Update only the totalPrice (3rd field)
                    parts[2] = String.format("%.2f", newTotal);
                    line = String.join("\t", parts);
                }
                poFound = true;
            }
            updatedLines.add(line);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error reading master file: " + e.getMessage());
        return;
    }

    if (!poFound) {
        JOptionPane.showMessageDialog(this, "PO not found in master record");
        return;
    }

    // Write updated master file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(masterFile))) {
        for (String line : updatedLines) {
            writer.write(line);
            writer.newLine();
        }
        JOptionPane.showMessageDialog(this, 
            "Changes saved successfully!\nNew Total: $" + String.format("%.2f", newTotal));
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error updating master record: " + e.getMessage());
    }
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbPOID = new javax.swing.JComboBox<>();
        btnConfirm = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbPOID.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        cmbPOID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbPOID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPOIDActionPerformed(evt);
            }
        });

        btnConfirm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tblItems.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tblItems.setModel(itemsModel);
        jScrollPane1.setViewportView(tblItems);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(cmbPOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnConfirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPOID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirm)
                    .addComponent(btnCancel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPOIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPOIDActionPerformed
        String selectedPO = (String) cmbPOID.getSelectedItem();
    if (selectedPO != null && !selectedPO.isEmpty() && !selectedPO.equals("-- Select Purchase Order --")) {
        currentPOID = selectedPO;  // Ensure currentPOID is always updated
        loadPOItems(selectedPO);
    } else {
        currentPOID = "";  // Clear current PO ID
        itemsModel.setRowCount(0);
        currentItems.clear();
    }
    }//GEN-LAST:event_cmbPOIDActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
    // Validate PO selection
    if (cmbPOID.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(this, "Please select a PO!", "PO Missing", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Show confirmation dialog
    int option = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to save these changes?",
        "Confirm Save",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (option == JOptionPane.YES_OPTION) {
        saveChanges();
        this.dispose();
    }
    
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(POEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(POEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(POEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(POEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new POEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JComboBox<String> cmbPOID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblItems;
    // End of variables declaration//GEN-END:variables
}
