
package com.owsb.poms.ui.fm;

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
public class PRDetailsFrame extends javax.swing.JFrame {

    public PRDetailsFrame() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PRDetailspanel = new javax.swing.JPanel();
        PRScrollPane = new javax.swing.JScrollPane();
        prItemTable = new javax.swing.JTable();
        PRDetailsBackButton = new javax.swing.JButton();
        PRDetailsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 245, 247));
        setPreferredSize(new java.awt.Dimension(1220, 800));

        PRDetailspanel.setBackground(new java.awt.Color(255, 245, 247));

        prItemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        PRScrollPane.setViewportView(prItemTable);

        PRDetailsBackButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        PRDetailsBackButton.setText("Back");
        PRDetailsBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PRDetailsBackButtonActionPerformed(evt);
            }
        });

        PRDetailsLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        PRDetailsLabel.setText("jLabel1");

        javax.swing.GroupLayout PRDetailspanelLayout = new javax.swing.GroupLayout(PRDetailspanel);
        PRDetailspanel.setLayout(PRDetailspanelLayout);
        PRDetailspanelLayout.setHorizontalGroup(
            PRDetailspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PRDetailspanelLayout.createSequentialGroup()
                .addGap(518, 518, 518)
                .addComponent(PRDetailsBackButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRDetailspanelLayout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(PRDetailspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PRDetailsLabel)
                    .addComponent(PRScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );
        PRDetailspanelLayout.setVerticalGroup(
            PRDetailspanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PRDetailspanelLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(PRDetailsLabel)
                .addGap(28, 28, 28)
                .addComponent(PRScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PRDetailsBackButton)
                .addGap(52, 52, 52))
        );

        getContentPane().add(PRDetailspanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PRDetailsBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PRDetailsBackButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_PRDetailsBackButtonActionPerformed

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PRDetailsFrame().setVisible(true);
            }
        });
    }

    
    DefaultTableModel prItemTableModel;

    public void loadPRDetails(String prid) {
        PRDetailsLabel.setText("Purchase Requisition Detail - " + prid);    
    if (prItemTableModel == null) {
        String[] columns = {"Item ID", "Category", "Type", "Name", "Quantity"};
        prItemTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        prItemTable.setModel(prItemTableModel);
    }

    prItemTableModel.setRowCount(0);

    String filePath = "data/PR/" + prid + ".txt";
    List<PRItem> items = PRItem.read(filePath);

    for (PRItem item : items) {
        prItemTableModel.addRow(new Object[]{
            item.getItemID(),
            item.getItemCategory(),
            item.getItemType(),
            item.getItemName(),
            item.getQuantity()
        });
    }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PRDetailsBackButton;
    private javax.swing.JLabel PRDetailsLabel;
    private javax.swing.JPanel PRDetailspanel;
    private javax.swing.JScrollPane PRScrollPane;
    private javax.swing.JTable prItemTable;
    // End of variables declaration//GEN-END:variables
}
