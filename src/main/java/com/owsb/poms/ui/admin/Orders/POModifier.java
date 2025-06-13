package com.owsb.poms.ui.admin.Orders;

import com.owsb.poms.system.model.User.*;
import com.owsb.poms.system.functions.DateResolver;
import com.owsb.poms.system.model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;
import javax.swing.*;
import javax.swing.table.*;

public class POModifier extends javax.swing.JDialog {
    private Admin admin;
    private String supplierID;
    private LocalDate deliveryDate;
    private double totalPrice;
    private List<Item> items;
    private List<POItem> orderItems = new ArrayList<>();
    private List<POItem> previousItems = new ArrayList<>();
    private PurchaseRequisition pr;
    private PurchaseOrder po;
    private PurchaseOrder previousOrder;
    private boolean edit = false;
    private boolean isInitializing = false;
    private boolean isFilling = false; // prevent action performed when add items
    
    private DefaultTableModel itemsModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){
            return column == 6 || column == 7;
        } 
    } ;
    
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Stock", "Status", "Quantity", "Unit Price"};
    
    public POModifier(javax.swing.JDialog parent, boolean modal, Admin admin) {
        super(parent, modal);
        initComponents();
        
        this.admin = admin;
        setTitle("New Purchase Order");
        btnPerform.setText("Create");
        
        isFilling = true;
        var prs = PurchaseRequisition.getAllPRs();
        for (String prid : prs) {
            if(PurchaseRequisition.getPrById(prid).getStatus() == PurchaseRequisition.Status.APPROVED)
            {
                cmbPR.addItem(prid);
            }
        }
        isFilling = false;
        cmbYear.setEnabled(false);
        cmbMonth.setEnabled(false);
        cmbDay.setEnabled(false);
        
        cmbPR.setSelectedIndex(-1);
        
        refreshItem();
    }
    
    public POModifier(javax.swing.JDialog parent, boolean modal, PurchaseOrder po) {
        super(parent, modal);
        initComponents();
        
        this.po = po;
        this.previousOrder = po;
        setTitle(String.format("%s (Edit)", po.getPOID()));
        btnPerform.setText("OK");
        txtRemark.setText(po.getRemark());
        edit = true;
        
        isFilling = true;
        cmbPR.addItem(po.getPRID());
        isFilling = false;
        cmbPR.setSelectedIndex(0);
        cmbPR.setEnabled(false);
        
        refreshItem();
        
        orderItems = po.getItems();
        previousItems = po.getItems();
    }
    
    public void refreshItem(){
        itemsModel.setRowCount(0);
        
        itemsModel.setColumnIdentifiers(itemsColumnName);
        JTableHeader header = tblItems.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlItems.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblItems.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblItems.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(3).setPreferredWidth(200);
        tblItems.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        if (cmbPR.getSelectedIndex() != -1){
            if (!edit)
            {
                var pri = pr.getItems();
                items = Item.getItemsFromPR(pri);

                Map<String, PRItem> priMap = pri.stream().collect(Collectors.toMap(PRItem::getItemID, Function.identity()));

                for (Item item : items) {
                    if (item.getStatus() != Item.Status.REMOVED)
                    {
                        PRItem prItem = priMap.get(item.getItemID());

                        itemsModel.addRow(new String[]{
                            item.getItemID(),
                            item.getItemCategory(),
                            item.getItemType(),
                            item.getItemName(),
                            String.valueOf(item.getStock()),
                            item.getStatus().name(),
                            String.valueOf(prItem.getQuantity()),
                            ""
                        });
                    }
                }
            }
            else{
                var pro = po.getItems();
                items = Item.getItemsFromPO(pro);

                Map<String, POItem> proMap = pro.stream().collect(Collectors.toMap(POItem::getItemID, Function.identity()));

                for (Item item : items) {
                    if (item.getStatus() != Item.Status.REMOVED)
                    {
                        POItem poItem = proMap.get(item.getItemID());

                        itemsModel.addRow(new String[]{
                            item.getItemID(),
                            item.getItemCategory(),
                            item.getItemType(),
                            item.getItemName(),
                            String.valueOf(item.getStock()),
                            item.getStatus().name(),
                            String.valueOf(poItem.getQuantity()),
                            String.format("%.2f", poItem.getUnitPrice())
                        });
                    }
                }
            }
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
        cmbPR = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        srlItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        btnPerform = new javax.swing.JButton();
        cmbYear = new javax.swing.JComboBox<>();
        cmbMonth = new javax.swing.JComboBox<>();
        cmbDay = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblSupplier = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRemark = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlMain.setBackground(new java.awt.Color(255, 204, 204));
        pnlMain.setPreferredSize(new java.awt.Dimension(700, 700));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("PR:");

        cmbPR.setSelectedItem(null);
        cmbPR.setMinimumSize(new java.awt.Dimension(49, 24));
        cmbPR.setPreferredSize(new java.awt.Dimension(49, 24));
        cmbPR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPRActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Delivery Date:");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Item:");

        tblItems.setBackground(new java.awt.Color(255, 255, 204));
        tblItems.setModel(itemsModel);
        tblItems.setGridColor(java.awt.Color.gray);
        tblItems.setRowSelectionAllowed(false);
        tblItems.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblItems.setShowGrid(true);
        srlItems.setViewportView(tblItems);

        btnPerform.setBackground(new java.awt.Color(255, 153, 0));
        btnPerform.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnPerform.setForeground(new java.awt.Color(252, 251, 249));
        btnPerform.setText("Perform");
        btnPerform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerformActionPerformed(evt);
            }
        });

        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        cmbMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMonthActionPerformed(evt);
            }
        });

        cmbDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDayActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("/");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("/");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Supplier:");

        lblSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplier.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplier.setText("[Supplier]");

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Remark:");

        txtRemark.setColumns(20);
        txtRemark.setRows(5);
        jScrollPane1.setViewportView(txtRemark);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(srlItems, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7))
                                .addGap(107, 107, 107)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSupplier)
                                    .addComponent(cmbPR, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(76, 76, 76)
                                .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(276, 276, 276)
                        .addComponent(btnPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbPR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSupplier))
                .addGap(18, 18, 18)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 848, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPRActionPerformed
        if (isFilling) return;
        if (cmbPR.getSelectedIndex() >= 0){
            pr = PurchaseRequisition.getPrById(cmbPR.getSelectedItem().toString());
            lblSupplier.setVisible(true);
            
            supplierID = pr.getSupplierID();
            lblSupplier.setText(String.format("%s %s",Supplier.getNameById(supplierID),supplierID));
            cmbYear.setEnabled(true);
            
            isInitializing = true;
            
            DateResolver.connect(cmbYear, cmbMonth, cmbDay, isInitializing);
            
            deliveryDate = edit ? po.getDeliveryDate() : pr.getRequiredDeliveryDate();
            cmbYear.setSelectedItem(String.valueOf(deliveryDate.getYear()));
            cmbMonth.setSelectedItem(String.format("%02d", deliveryDate.getMonthValue()));
            cmbDay.setSelectedItem(String.format("%02d", deliveryDate.getDayOfMonth()));

            isInitializing = false;
            
            refreshItem();
        }
        else{
            lblSupplier.setVisible(false);
            refreshItem();
        }
    }//GEN-LAST:event_cmbPRActionPerformed

    private void btnPerformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformActionPerformed
        if (cmbPR.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Please select a PR!", "PR Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cmbDay.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Invalid delivery date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        orderItems.clear();
        totalPrice = 0;
        
        for (int i = 0; i < tblItems.getRowCount(); i++){
            POItem pro = new POItem(
                tblItems.getValueAt(i, 0).toString(),
                tblItems.getValueAt(i, 1).toString(),
                tblItems.getValueAt(i, 2).toString(),
                tblItems.getValueAt(i, 3).toString()
            );
            
            int quantity;
            double unitPrice;

            try{
                quantity = Integer.parseInt(tblItems.getValueAt(i, 6).toString().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try{
                unitPrice = Double.parseDouble(tblItems.getValueAt(i, 7).toString().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity cannot be negative!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (quantity == 0){
                JOptionPane.showMessageDialog(this, "Quantity cannot be zero!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                pro.setQuantity(quantity);
            }

            if (unitPrice < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (unitPrice == 0){
                JOptionPane.showMessageDialog(this, "Price cannot be zero!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                pro.setUnitPrice(unitPrice);
                totalPrice += quantity * unitPrice;
            }
            
            orderItems.add(pro);
        }

        String Message = edit ?
        String.format("Are you sure to edit this PO (ID: %s)?%n"
                + "Delivery Date: %s%n"
                + "Total Price: RM %.2f%n"
                + "Items:%n"
                + "%s →%n%n"
                + "Delivery Date: %s%n"
                + "Total Price: RM %.2f%n"
                + "Items:%n"
                + "%s",
            po.getPOID(),
            previousOrder.getDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy MMMM dd")),
            previousOrder.getTotalPrice(),
            IntStream.range(0, orderItems.size())
            .mapToObj(i -> String.format("%d. %s (Qty: %d) (U/P: RM %.2f)",
                i + 1,
                previousItems.get(i).getItemName(),
                previousItems.get(i).getQuantity(),
                previousItems.get(i).getUnitPrice()))
            .collect(Collectors.joining("\n")),
            deliveryDate.format(DateTimeFormatter.ofPattern("yyyy MMMM dd")),
            totalPrice,
            IntStream.range(0, orderItems.size())
            .mapToObj(i -> String.format("%d. %s (Qty: %d) (U/P: RM %.2f)",
                i + 1,
                orderItems.get(i).getItemName(),
                orderItems.get(i).getQuantity(),
                orderItems.get(i).getUnitPrice()))
            .collect(Collectors.joining("\n"))
        ):
        String.format("Are you sure to create a new PO from %s?%n"
                    + "Supplier: %s%n"
                    + "Delivery Date: %s%n"
                    + "Total Price: RM %.2f%n"
                    + "Items:%n"
                    + "%s",
            cmbPR.getSelectedItem().toString(),
            lblSupplier.getText(),
            deliveryDate.format(DateTimeFormatter.ofPattern("yyyy MMMM dd")),
            totalPrice,
            IntStream.range(0, orderItems.size())
            .mapToObj(i -> String.format("%d. %s (Qty: %d) (U/P: RM %.2f)",
                i + 1,
                orderItems.get(i).getItemName(),
                orderItems.get(i).getQuantity(),
                orderItems.get(i).getUnitPrice()))
            .collect(Collectors.joining("\n")));

        String successfulMessage = edit ? "Successfully make change to the PO!" : "Successfully create a new PO!";

        int result = JOptionPane.showConfirmDialog(
            this,
            Message,
            this.getTitle(),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION){
            String remark = txtRemark.getText().isBlank() ? "None" : txtRemark.getText().trim();
            if (!edit){
                po = new PurchaseOrder(pr.getPRID(), totalPrice, pr.getSupplierID(), deliveryDate, admin.getUID(), remark);
                po.add();
                pr.setStatus(PurchaseRequisition.Status.POGENERATED);
                pr.updateStatus();
            }
            else{
                po.setDeliveryDate(deliveryDate);
                po.setTotalPrice(totalPrice);
                po.setRemark(remark);
                po.updateStatus();
            }

            POItem poi = new POItem();
            poi.setPOID(po.getPOID());
            poi.save(orderItems);
            JOptionPane.showMessageDialog(this, successfulMessage);
            this.dispose();
        }
    }//GEN-LAST:event_btnPerformActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        if (isInitializing || isFilling) return;
        
        cmbMonth.setSelectedIndex(-1);
        
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        
        cmbMonth.removeAllItems();
        
        isFilling = true;
        DateResolver.populateCbMonth(cmbYear, cmbMonth, cmbDay, currentYear, currentMonth);
        cmbMonth.setSelectedIndex(-1);
        isFilling = false;
    }//GEN-LAST:event_cmbYearActionPerformed

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        if (isInitializing || isFilling) return;
        
        cmbDay.setSelectedIndex(-1);        
        
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();
        
        cmbDay.removeAllItems();
        
        isFilling = true;
        DateResolver.populateCbDay(cmbYear, cmbMonth, cmbDay, currentYear, currentMonth, currentDay);
        cmbDay.setSelectedIndex(-1);
        isFilling = false;
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDayActionPerformed
        if (cmbDay.getSelectedIndex() != -1)
        {
            int year = Integer.parseInt(cmbYear.getSelectedItem().toString());
            int month = Integer.parseInt(cmbMonth.getSelectedItem().toString());
            int day = Integer.parseInt(cmbDay.getSelectedItem().toString());
            deliveryDate = LocalDate.of(year, month, day);
        }
    }//GEN-LAST:event_cmbDayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPerform;
    private javax.swing.JComboBox<String> cmbDay;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JComboBox<String> cmbPR;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextArea txtRemark;
    // End of variables declaration//GEN-END:variables
}
