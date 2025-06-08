
package com.owsb.poms.ui.fm;
import com.owsb.poms.system.functions.FileHandler;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.owsb.poms.system.model.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class PODetailFrame extends javax.swing.JFrame {
    private DefaultTableModel poItemTableModel;
    private String currentPOID;
    public PODetailFrame(String poid) {
        initComponents();
        this.currentPOID = poid;
        initPODetailTable();
        loadPODetailData(currentPOID);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PODetailsBackButton = new javax.swing.JButton();
        PODetailsLabel = new javax.swing.JLabel();
        POScrollPane = new javax.swing.JScrollPane();
        POItemTable = new javax.swing.JTable();
        PODetailsEditButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 245, 247));

        PODetailsBackButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        PODetailsBackButton.setText("Back");
        PODetailsBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PODetailsBackButtonActionPerformed(evt);
            }
        });

        PODetailsLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        PODetailsLabel.setText("jLabel1");

        POItemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        POScrollPane.setViewportView(POItemTable);

        PODetailsEditButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        PODetailsEditButton.setText("Edit");
        PODetailsEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PODetailsEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(PODetailsLabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addComponent(PODetailsBackButton)
                        .addGap(296, 296, 296)
                        .addComponent(PODetailsEditButton)))
                .addContainerGap(364, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addComponent(POScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(PODetailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 599, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PODetailsEditButton)
                    .addComponent(PODetailsBackButton))
                .addGap(33, 33, 33))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(131, 131, 131)
                    .addComponent(POScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(132, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PODetailsEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PODetailsEditButtonActionPerformed
        int selectedRow = POItemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row.");
            return;
        }
        
        String input = JOptionPane.showInputDialog(this, "Enter new quantity:");
        if (input == null || input.trim().isEmpty()) return;
        
       int newQty;
       try {
           newQty = Integer.parseInt(input.trim());
           if (newQty <= 0) {
               JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
               return;
           }
       } catch (NumberFormatException e) {
           JOptionPane.showMessageDialog(this, "Invalid quantity input.");
           return;
       }
       String itemID = POItemTable.getValueAt(selectedRow, 0).toString();
       String filePath = "data/PO/" + currentPOID + ".txt";
       List<POItem> items = POItem.read(filePath);
       
       for (POItem item : items) {
           if (item.getItemID().equals(itemID)) {
               item.setQuantity(newQty);
               break;
           }
       }
       
       FileHandler.saveToFile(filePath, items, POItem::toString);
       double newTotal = 0;
       for(POItem item : items){
           newTotal =+ item.getQuantity()*item.getUnitPrice();
       }
       List<PurchaseOrder> poList = PurchaseOrder.toList();
       for(PurchaseOrder po : poList){
           if(po.getPOID().equals(currentPOID)){
               po.setTotalPrice(newTotal);
               break;
           }
       }
       FileHandler.saveToFile("data/PO/purchase_order.txt", poList, PurchaseOrder::toString);
       loadPODetailData(currentPOID);
       JOptionPane.showMessageDialog(this, "Edit successfully.");

    }//GEN-LAST:event_PODetailsEditButtonActionPerformed

    private void PODetailsBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PODetailsBackButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_PODetailsBackButtonActionPerformed

   private void initPODetailTable() {
    String[] columns = {"Item ID", "Category", "Type", "Name", "Quantity", "Unit Price"};
    poItemTableModel = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    POItemTable.setModel(poItemTableModel);
    }
   
   public void loadPODetailData(String poid) {
       PODetailsLabel.setText("Purchase Order Detail - " + poid);
       poItemTableModel.setRowCount(0);
       List<POItem> items = POItem.read("data/PO/" + poid + ".txt");
       
       for (POItem item : items) {
        poItemTableModel.addRow(new Object[]{
            item.getItemID(), item.getItemCategory(), item.getItemType(),
            item.getItemName(), item.getQuantity(), item.getUnitPrice()
        });
    }
   }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PODetailsBackButton;
    private javax.swing.JButton PODetailsEditButton;
    private javax.swing.JLabel PODetailsLabel;
    private javax.swing.JTable POItemTable;
    private javax.swing.JScrollPane POScrollPane;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
