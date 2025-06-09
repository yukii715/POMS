package com.owsb.poms.ui.admin.Orders;

import com.owsb.poms.system.model.*;
import com.owsb.poms.ui.common.CommonMethod;
import java.awt.Image;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.*;


public class PODetails extends javax.swing.JDialog {
    private PurchaseOrder po;
    private PurchaseRequisition pr;
    private Admin admin;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    private boolean newPO = false;
    private boolean approvedPO = false;
    private boolean rejectedPO = false;
    private boolean processingPO = false;
    private boolean extendedPO = false;
    private boolean arrivedPO = false;
    private boolean verifiedPO = false;
    private boolean invalidPO = false;
    private boolean confirmedPO = false;
    private boolean completedOrCancelledOrDeletedPO = false;
    
    private DefaultTableModel itemsModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){
            return false;
        } 
    } ;
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Quantity", "Unit Price"};

    public PODetails(javax.swing.JDialog parent, boolean modal,PurchaseOrder po,PurchaseRequisition pr, Admin admin) {
        super(parent, modal);
        initComponents();
        
        this.po = po;
        this.pr = pr;
        this.admin = admin;
        setTitle(po.getPOID());
        new CommonMethod().setLabelIcon("/icons/edit.png", 20, 20, Image.SCALE_SMOOTH, lblEdit);
        
        lblPOID.setText(po.getPOID());
        lblPRID.setText(po.getPRID());
        lblSupplierID.setText(po.getSupplierID());
        lblSupplierName.setText(Supplier.getNameById(po.getSupplierID()));
        lblGeneratedDateTime.setText(po.getGeneratedDateTime().format(dateTimeFormatter));
        lblCreatedBy.setText(po.getCreateBy());
        lblStatus.setText(po.getStatus().name());
        lblEdit.setVisible(false);
        lblPerform.setText("Approved By:");
        lblPerformdBy.setText(po.getPerformedBy());
        
        PurchaseOrder.Status status = po.getStatus();
        
        switch (status){
            case NEW:       // approve, reject or delete
                newPO = true;
                lblPerform.setVisible(false);
                lblPerformdBy.setVisible(false);
                btn1.setText("Approve");
                btn2.setText("Reject");
                btn3.setText("Delete");
                lblEdit.setVisible(true);
                txtRemark.setEnabled(false);
                break;
            case APPROVED:  // processing or cancel
                approvedPO = true;
                btn2.setVisible(false);
                btn1.setText("Processing");
                btn3.setText("Cancel");
                break;
            case REJECTED:   // set as new
                rejectedPO = true;
                String perform = status == PurchaseOrder.Status.REJECTED ? "Rejected By" : "Deleted By";
                lblPerform.setText(perform);
                lblPerformdBy.setText(po.getPerformedBy());
                btn1.setVisible(false);
                btn3.setVisible(false);
                btn2.setText("Set as New");
                break;
            case PROCESSING:// arrived, extend or cancel
                processingPO = true;
                btn1.setText("Arrived");
                btn2.setText("Extend");
                btn3.setText("Cancel");
                break;
            case EXTENDED:  // arrived or cancel
                extendedPO = true;
                btn2.setVisible(false);
                btn1.setText("Arrived");
                btn3.setText("Cancel");
                break;
            case ARRIVED:   // verified, extend or cancel
                arrivedPO = true;
                btn1.setText("Verified");
                btn2.setText("Extend");
                btn3.setText("Cancel");
                break;
            case VERIFIED:  // invalid or confirm
                verifiedPO = true;
                btn2.setVisible(false);
                btn1.setText("Confirm");
                btn3.setText("Invalid");
                break;
            case INVALID:   // extend or cancel
                invalidPO = true;
                btn2.setVisible(false);
                btn1.setText("Extend");
                btn3.setText("Cancel");
                break;
            case CONFIRMED: // completed
                confirmedPO = true;
                btn1.setVisible(false);
                btn3.setVisible(false);
                btn2.setText("Completed");
                break;
            case COMPLETED,CANCELLED,DELETED: //ok
                completedOrCancelledOrDeletedPO = true;
                btn1.setVisible(false);
                btn3.setVisible(false);
                txtRemark.setEnabled(false);
                btn2.setText("OK");
                break;
        }
        
        refresh();
    }

    
    private void refresh(){
        lblTotalPrice.setText(String.format("RM %.2f", po.getTotalPrice()));
        lblDeliveryDate.setText(po.getDeliveryDate().format(dateFormatter));
        txtRemark.setText(po.getRemark());
        
        itemsModel.setRowCount(0);
        itemsModel.setColumnIdentifiers(itemsColumnName);
        JTableHeader header = tblItems.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlItems.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblItems.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblItems.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(3).setPreferredWidth(200);
        
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
        jLabel8 = new javax.swing.JLabel();
        lblGeneratedDateTime = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblDeliveryDate = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblCreatedBy = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        srlItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btn3 = new javax.swing.JButton();
        lblEdit = new javax.swing.JLabel();
        lblPerform = new javax.swing.JLabel();
        lblPerformdBy = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTotalPrice = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblPRID = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemark = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(650, 820));

        pnlMain.setBackground(new java.awt.Color(255, 204, 204));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("ID:");

        lblPOID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPOID.setForeground(new java.awt.Color(0, 0, 0));
        lblPOID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPOID.setText("[POID]");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Supplier ID:");

        lblSupplierID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierID.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplierID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplierID.setText("[SupplierID]");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Supplier:");

        lblSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierName.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplierName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplierName.setText("[SupplierName]");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Generated Date Time:");

        lblGeneratedDateTime.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblGeneratedDateTime.setForeground(new java.awt.Color(0, 0, 0));
        lblGeneratedDateTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGeneratedDateTime.setText("[GeneratedDateTime]");
        lblGeneratedDateTime.setToolTipText("");

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Delivery Date:");

        lblDeliveryDate.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblDeliveryDate.setForeground(new java.awt.Color(0, 0, 0));
        lblDeliveryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDeliveryDate.setText("[DeliveryDate]");

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Created By:");

        lblCreatedBy.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblCreatedBy.setForeground(new java.awt.Color(0, 0, 0));
        lblCreatedBy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCreatedBy.setText("[CreatedBy]");
        lblCreatedBy.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Status:");

        lblStatus.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 0, 0));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStatus.setText("[Status]");
        lblStatus.setToolTipText("");

        jLabel16.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Items:");

        btn1.setBackground(new java.awt.Color(255, 153, 0));
        btn1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btn1.setForeground(new java.awt.Color(252, 251, 249));
        btn1.setText("btn1");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setBackground(new java.awt.Color(255, 153, 0));
        btn2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btn2.setForeground(new java.awt.Color(252, 251, 249));
        btn2.setText("btn2");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        srlItems.setPreferredSize(new java.awt.Dimension(517, 230));

        tblItems.setBackground(new java.awt.Color(255, 255, 204));
        tblItems.setModel(itemsModel);
        tblItems.setGridColor(java.awt.Color.gray);
        tblItems.setRowSelectionAllowed(false);
        tblItems.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblItems.setShowGrid(true);
        srlItems.setViewportView(tblItems);

        btn3.setBackground(new java.awt.Color(255, 153, 0));
        btn3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btn3.setForeground(new java.awt.Color(252, 251, 249));
        btn3.setText("btn3");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        lblEdit.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblEdit.setForeground(new java.awt.Color(0, 0, 0));
        lblEdit.setText("Edit");
        lblEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEditMouseClicked(evt);
            }
        });

        lblPerform.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPerform.setForeground(new java.awt.Color(0, 0, 0));
        lblPerform.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPerform.setText("Performed By:");

        lblPerformdBy.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPerformdBy.setForeground(new java.awt.Color(0, 0, 0));
        lblPerformdBy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPerformdBy.setText("[PerformedBy]");
        lblPerformdBy.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Total Price:");

        lblTotalPrice.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblTotalPrice.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalPrice.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalPrice.setText("[TotalPrice]");
        lblTotalPrice.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("From PR:");

        lblPRID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblPRID.setForeground(new java.awt.Color(0, 0, 0));
        lblPRID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPRID.setText("[PRID]");

        jLabel17.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Remark:");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(517, 127));

        txtRemark.setColumns(20);
        txtRemark.setRows(5);
        jScrollPane1.setViewportView(txtRemark);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblEdit)
                .addGap(70, 70, 70))
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addGap(144, 144, 144)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalPrice)
                            .addComponent(lblSupplierName)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(143, 143, 143)
                        .addComponent(lblSupplierID))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(srlItems, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(lblPerform)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addGap(80, 80, 80)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPerformdBy)
                            .addComponent(lblDeliveryDate)
                            .addComponent(lblGeneratedDateTime)
                            .addComponent(lblCreatedBy)
                            .addComponent(lblStatus)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMainLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(154, 154, 154)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPOID)
                            .addComponent(lblPRID))))
                .addGap(0, 71, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPOID)
                    .addComponent(lblEdit)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblPRID))
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
                    .addComponent(jLabel9)
                    .addComponent(lblTotalPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblGeneratedDateTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblDeliveryDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblCreatedBy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPerform)
                    .addComponent(lblPerformdBy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
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

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        String remark = txtRemark.getText().isBlank() ? "None" : txtRemark.getText().trim();
        // Approve
        if (newPO){ 
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to approve this PO?",
                String.format("Approve %s", getTitle()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                po.setStatus(PurchaseOrder.Status.APPROVED);
                po.setPerformedBy(admin.getUID());
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, "This PO has been approved!");
                dispose();
            }
            return;
        }
        
        // Processing
        if (approvedPO){
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to change this PO to Processing?",
                String.format("Change %s status to Processing", getTitle()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                po.setStatus(PurchaseOrder.Status.PROCESSING);
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, String.format("Order %s has been set as Processing", getTitle()));
                dispose();
            }
            return;
        }
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        String remark = txtRemark.getText().isBlank() ? "None" : txtRemark.getText().trim();
        // Reject
        if (newPO){
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to reject this PO?",
                String.format("Reject %s", getTitle()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                po.setStatus(PurchaseOrder.Status.REJECTED);
                po.setPerformedBy(admin.getUID());
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, "This PO has been Rejected!");
                dispose();
            }
            return;
        }
        // Set as New
        if (rejectedPO){
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to reset this PO as new?",
                "Reset PO",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                po.setStatus(PurchaseOrder.Status.NEW);
                po.setPerformedBy("None");
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, "This PO has been reset to New!");
                dispose();
            }
            return;
        }
        
        // po.setDeliveryDate(LocalDate.parse(lblDeliveryDate.getText(), dateFormatter));
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        String remark = txtRemark.getText().isBlank() ? "None" : txtRemark.getText().trim();
        // Delete
        if (newPO){
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to delete this PO?",
                String.format("Delete %s", getTitle()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                pr.setStatus(PurchaseRequisition.Status.APPROVED);
                pr.updateStatus();
                po.setStatus(PurchaseOrder.Status.DELETED);
                po.setPerformedBy(admin.getUID());
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, "This PO has been Deleted!");
                dispose();
            }
            return;
        }
        if (approvedPO){
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure to cancel this PO?\nWarning: This action cannot be reverted!",
                String.format("Cancel %s", getTitle()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION){
                pr.setStatus(PurchaseRequisition.Status.DELETED);
                pr.updateStatus();
                po.setStatus(PurchaseOrder.Status.CANCELLED);
                po.setRemark(remark);
                po.updateStatus();
                JOptionPane.showMessageDialog(this, "This PO has been Cancelled!");
                dispose();
            }
            return;
        }
    }//GEN-LAST:event_btn3ActionPerformed

    private void lblEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditMouseClicked
        POModifier PO = new POModifier(this, true, po);
        PO.setLocationRelativeTo(this);
        PO.setVisible(true);
        refresh();
    }//GEN-LAST:event_lblEditMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCreatedBy;
    private javax.swing.JLabel lblDeliveryDate;
    private javax.swing.JLabel lblEdit;
    private javax.swing.JLabel lblGeneratedDateTime;
    private javax.swing.JLabel lblPOID;
    private javax.swing.JLabel lblPRID;
    private javax.swing.JLabel lblPerform;
    private javax.swing.JLabel lblPerformdBy;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblSupplierID;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblTotalPrice;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextArea txtRemark;
    // End of variables declaration//GEN-END:variables
}
