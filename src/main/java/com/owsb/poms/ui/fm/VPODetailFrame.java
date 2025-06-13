
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
public class VPODetailFrame extends javax.swing.JFrame {
private DefaultTableModel poDetailTableModel;
    public VPODetailFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PODetailPanel = new javax.swing.JPanel();
        VPOScrollPane = new javax.swing.JScrollPane();
        VPOItemTable = new javax.swing.JTable();
        VPODetailsBackButton = new javax.swing.JButton();
        VPODetailsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PODetailPanel.setBackground(new java.awt.Color(255, 245, 247));
        PODetailPanel.setMinimumSize(new java.awt.Dimension(1220, 800));

        VPOItemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        VPOScrollPane.setViewportView(VPOItemTable);

        VPODetailsBackButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        VPODetailsBackButton.setText("Back");
        VPODetailsBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VPODetailsBackButtonActionPerformed(evt);
            }
        });

        VPODetailsLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        VPODetailsLabel.setText("jLabel1");

        javax.swing.GroupLayout PODetailPanelLayout = new javax.swing.GroupLayout(PODetailPanel);
        PODetailPanel.setLayout(PODetailPanelLayout);
        PODetailPanelLayout.setHorizontalGroup(
            PODetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PODetailPanelLayout.createSequentialGroup()
                .addGroup(PODetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PODetailPanelLayout.createSequentialGroup()
                        .addGap(525, 525, 525)
                        .addComponent(VPODetailsBackButton))
                    .addGroup(PODetailPanelLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(VPODetailsLabel)))
                .addContainerGap(566, Short.MAX_VALUE))
            .addGroup(PODetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PODetailPanelLayout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addComponent(VPOScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(56, Short.MAX_VALUE)))
        );
        PODetailPanelLayout.setVerticalGroup(
            PODetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PODetailPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(VPODetailsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 600, Short.MAX_VALUE)
                .addComponent(VPODetailsBackButton)
                .addGap(30, 30, 30))
            .addGroup(PODetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PODetailPanelLayout.createSequentialGroup()
                    .addGap(126, 126, 126)
                    .addComponent(VPOScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(127, Short.MAX_VALUE)))
        );

        getContentPane().add(PODetailPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VPODetailsBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VPODetailsBackButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_VPODetailsBackButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VPODetailFrame().setVisible(true);
            }
        });
    }
    
    public void initPODetailTable() {
    String[] columns = {"Item ID", "Category", "Type", "Name", "Quantity", "Unit Price"};
    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    this.poDetailTableModel = model;
    VPOItemTable.setModel(model); 
    }
    
    public void loadPODetailData(String poid) {
    VPODetailsLabel.setText("Purchase Order Detail - " + poid);
    poDetailTableModel.setRowCount(0);

    List<POItem> items = POItem.read("data/PO/" + poid + ".txt");
    for (POItem item : items) {
        poDetailTableModel.addRow(new Object[] {
            item.getItemID(),
            item.getItemCategory(),
            item.getItemType(),
            item.getItemName(),
            item.getQuantity(),
            item.getUnitPrice()
        });
    }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PODetailPanel;
    private javax.swing.JButton VPODetailsBackButton;
    private javax.swing.JLabel VPODetailsLabel;
    private javax.swing.JTable VPOItemTable;
    private javax.swing.JScrollPane VPOScrollPane;
    // End of variables declaration//GEN-END:variables
}
