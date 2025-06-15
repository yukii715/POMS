package com.owsb.poms.ui.admin.Dashboard;

import com.owsb.poms.system.model.*;
import com.owsb.poms.system.model.User.User;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PurchaseReportViewer extends javax.swing.JDialog {
    private PurchaseReport purchaseReport;
    private PurchaseOrder po;
    private DefaultTableModel itemsModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Quantity", "Unit Price"};
    private DefaultTableModel trModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] trColumnName = {"ID", "Date Time", "From", "Account Number", "To", "Account Number", "Amount", "Process By", "Details"};

    public PurchaseReportViewer(javax.swing.JDialog parent, boolean modal, Report report) {
        super(parent, modal);
        initComponents();
        
        setTitle(String.format("%s (%s)", report.getReportID(), report.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        
        this.purchaseReport = PurchaseReport.getReportById(report.getReportID());
        this.po = PurchaseOrder.getPoById(purchaseReport.getPOID());
        
        lblReportID.setText(purchaseReport.getReportID());
        lblInvoiceID.setText(purchaseReport.getInvoiceID());
        lblPOID.setText(po.getPOID());
        lblPRID.setText(po.getPRID());
        lblTotalAmount.setText(String.format("RM %.2f", po.getTotalPrice()));
        lblSupplierID.setText(po.getSupplierID());
        lblSupplierName.setText(Supplier.getNameById(po.getSupplierID()));
        lblDeliveryDate.setText(po.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
        lblVerifiedBy.setText(String.format("%s %s", purchaseReport.getVerifiedBy(), User.getUserById(purchaseReport.getVerifiedBy()).getUsername()));
        
        Report();
    }

    private void Report(){
        itemsModel.setRowCount(0);
        
        itemsModel.setColumnIdentifiers(itemsColumnName);
        JTableHeader header = tblItems.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlItems.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblItems.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblItems.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblItems.getColumnModel().getColumn(3).setPreferredWidth(220);
        
        var items = po.getItems();
        
        for (POItem item : items) {
            itemsModel.addRow(new String[]{
                item.getItemID(),
                item.getItemCategory(),
                item.getItemType(),
                item.getItemName(),
                String.valueOf(item.getQuantity()),
                String.valueOf(item.getUnitPrice())
            });
        }
        
        // Create a single “center” renderer:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblItems.setDefaultRenderer(Object.class, centerRenderer);
        
        trModel.setRowCount(0);
        
        trModel.setColumnIdentifiers(trColumnName);
        header = tblTransactions.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlTransactions.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblTransactions.getColumnModel().getColumn(0).setPreferredWidth(250);
        tblTransactions.getColumnModel().getColumn(1).setPreferredWidth(170);
        tblTransactions.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblTransactions.getColumnModel().getColumn(3).setPreferredWidth(150);
        tblTransactions.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblTransactions.getColumnModel().getColumn(5).setPreferredWidth(150);
        tblTransactions.getColumnModel().getColumn(6).setPreferredWidth(150);
        tblTransactions.getColumnModel().getColumn(7).setPreferredWidth(120);
        tblTransactions.getColumnModel().getColumn(8).setPreferredWidth(200);
        
        var trs = po.getTransactions();
        
        for (Transaction t : trs) {
            trModel.addRow(new String[]{
                t.getTransactionID(),
                t.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                t.getBankFrom(),
                String.valueOf(t.getBankFromAccountNumber()),
                t.getBankReceived(),
                String.valueOf(t.getBankReceivedAccountNumber()),
                String.format("RM %.2f", t.getAmount()),
                t.getProcessBy(),
                t.getDetails()
            });
        }
        
        // Create a single “center” renderer:
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblTransactions.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblPOID = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSupplierID = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblSupplierName = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblDeliveryDate = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        srlItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        lblPerform = new javax.swing.JLabel();
        lblVerifiedBy = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblPRID = new javax.swing.JLabel();
        lblReportID = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblInvoiceID = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblTotalAmount = new javax.swing.JLabel();
        srlTransactions = new javax.swing.JScrollPane();
        tblTransactions = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setBackground(new java.awt.Color(255, 204, 204));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("POID:");

        lblPOID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPOID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPOID.setText("[POID]");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Supplier ID:");

        lblSupplierID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplierID.setText("[SupplierID]");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Supplier:");

        lblSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplierName.setText("[SupplierName]");

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Delivery Date:");

        lblDeliveryDate.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblDeliveryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDeliveryDate.setText("[DeliveryDate]");

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Items:");

        srlItems.setPreferredSize(new java.awt.Dimension(517, 230));

        tblItems.setBackground(new java.awt.Color(255, 255, 204));
        tblItems.setModel(itemsModel);
        tblItems.setGridColor(java.awt.Color.gray);
        tblItems.setRowSelectionAllowed(false);
        tblItems.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblItems.setShowGrid(true);
        srlItems.setViewportView(tblItems);

        lblPerform.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPerform.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPerform.setText("Verified By:");

        lblVerifiedBy.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblVerifiedBy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblVerifiedBy.setText("[VerifiedBy]");
        lblVerifiedBy.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("From PR:");

        lblPRID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPRID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPRID.setText("[PRID]");

        lblReportID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblReportID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblReportID.setText("[ReportID]");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("ID:");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Invoice ID:");

        lblInvoiceID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblInvoiceID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblInvoiceID.setText("[InvoiceID]");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Order Details:");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Transaction Details:");

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Total Amount:");

        lblTotalAmount.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblTotalAmount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalAmount.setText("[TotalAmount]");

        srlTransactions.setPreferredSize(new java.awt.Dimension(517, 230));

        tblTransactions.setBackground(new java.awt.Color(255, 255, 204));
        tblTransactions.setModel(trModel);
        tblTransactions.setGridColor(java.awt.Color.gray);
        tblTransactions.setRowSelectionAllowed(false);
        tblTransactions.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblTransactions.setShowGrid(true);
        srlTransactions.setViewportView(tblTransactions);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srlTransactions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(lblPerform)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel16)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGap(209, 209, 209)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSupplierID)
                                    .addComponent(lblSupplierName)
                                    .addComponent(lblDeliveryDate)
                                    .addComponent(lblVerifiedBy)
                                    .addComponent(lblTotalAmount)
                                    .addComponent(lblPOID)
                                    .addComponent(lblPRID)
                                    .addComponent(lblInvoiceID)
                                    .addComponent(lblReportID)))
                            .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 476, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblReportID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblInvoiceID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblPOID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblPRID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblTotalAmount))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblSupplierID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblSupplierName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblDeliveryDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPerform)
                    .addComponent(lblVerifiedBy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srlTransactions, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblDeliveryDate;
    private javax.swing.JLabel lblInvoiceID;
    private javax.swing.JLabel lblPOID;
    private javax.swing.JLabel lblPRID;
    private javax.swing.JLabel lblPerform;
    private javax.swing.JLabel lblReportID;
    private javax.swing.JLabel lblSupplierID;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblVerifiedBy;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JScrollPane srlTransactions;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tblTransactions;
    // End of variables declaration//GEN-END:variables
}
