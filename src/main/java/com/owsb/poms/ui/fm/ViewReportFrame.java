
package com.owsb.poms.ui.fm;

import com.owsb.poms.system.model.PurchaseOrder;
import com.owsb.poms.system.model.PurchaseReport;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;

public class ViewReportFrame extends javax.swing.JFrame {
    
    DefaultTableModel viewPReportTable;
    
    public ViewReportFrame(String reportID) {
        initComponents();
        initViewPurchaseOrderTable();
        VPReportLabel.setText("Viewing Purchase Order: " + reportID);
        loadPurchaseOrderData(reportID);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        VPOScrollPane = new javax.swing.JScrollPane();
        ViewPurchaseReportTable = new javax.swing.JTable();
        VPReportBackButton = new javax.swing.JButton();
        VPReportLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 245, 247));
        jPanel1.setMinimumSize(new java.awt.Dimension(1220, 810));
        jPanel1.setPreferredSize(new java.awt.Dimension(1220, 810));

        ViewPurchaseReportTable.setModel(new javax.swing.table.DefaultTableModel(
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
        VPOScrollPane.setViewportView(ViewPurchaseReportTable);

        VPReportBackButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        VPReportBackButton.setText("Back");
        VPReportBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VPReportBackButtonActionPerformed(evt);
            }
        });

        VPReportLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        VPReportLabel.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(545, 545, 545)
                        .addComponent(VPReportBackButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VPReportLabel)
                            .addComponent(VPOScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(VPReportLabel)
                .addGap(18, 18, 18)
                .addComponent(VPOScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(VPReportBackButton)
                .addGap(28, 28, 28))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VPReportBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VPReportBackButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_VPReportBackButtonActionPerformed

    
       
    private void initViewPurchaseOrderTable() {
        String[] columns = {"PO ID", "PR ID", "Total Price", "Supplier ID", "Generated", "Delivery", "Status", "Created By", "Performed By"};
        viewPReportTable = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    ViewPurchaseReportTable.setModel(viewPReportTable);
}
    
    private void loadPurchaseOrderData(String reportID) {
        PurchaseReport report = PurchaseReport.toList().stream().filter(r -> r.getReportID().equals(reportID)).findFirst().orElse(null);
         if (report == null) {
             System.out.println("Report not found.");
             return;
         }
         
         String poid = report.getPOID();
        PurchaseOrder po = PurchaseOrder.getPoById(report.getPOID());
        if (po == null){
            System.out.println("PO not found for ID: " + poid);
            return;
        }
        if (po.getStatus() != PurchaseOrder.Status.REPORTED) {
            System.out.println("PO found but status is not REPORTED: " + po.getStatus());
            return;
        }
        
            viewPReportTable.setRowCount(0);
            viewPReportTable.addRow(new Object[]{
            po.getPOID(),
            po.getPRID(),
            po.getTotalPrice(),
            po.getSupplierID(),
            po.getGeneratedDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
            po.getDeliveryDate().toString(),
            po.getStatus().name(),
            po.getCreateBy(),
            po.getPerformedBy()
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane VPOScrollPane;
    private javax.swing.JButton VPReportBackButton;
    private javax.swing.JLabel VPReportLabel;
    private javax.swing.JTable ViewPurchaseReportTable;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
