package com.owsb.poms.ui.admin.Orders;

import com.owsb.poms.system.model.User.*;
import com.owsb.poms.system.functions.DateResolver;
import com.owsb.poms.system.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.*;

public class PRModifier extends javax.swing.JDialog {
    private Admin admin;
    private String supplierID;
    private LocalDate requiredDate;
    private List<Item> items;
    private List<Item> supplierItems;
    private List<PRItem> selectedItems = new ArrayList<>();
    private List<PRItem> previousItems = new ArrayList<>();
    private PurchaseRequisition pr;
    private boolean edit = false;
    private boolean isInitializing = false;
    private boolean isFilling = false;
    
    private DefaultTableModel itemsModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column){
            return false;
        } 
    } ;
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Stock", "Status", "Require", "Quantity"};

    public PRModifier(javax.swing.JDialog parent, boolean modal, Admin admin) {
        super(parent, modal);
        initComponents();
        
        this.admin = admin;
        setTitle("New Purchase Requisition");
        btnPerform.setText("Create");
        
        initialSetting();
        cmbSuppliers.setSelectedIndex(-1);
        cmbYear.setSelectedIndex(-1);
        cmbMonth.setSelectedIndex(-1);
        cmbDay.setSelectedIndex(-1);
        
        refreshItem();
        
    }
    
    public PRModifier(javax.swing.JDialog parent, boolean modal, PurchaseRequisition pr) {
        super(parent, modal);
        initComponents();
        
        edit = true;
        isInitializing = true;
        setTitle(String.format("%s (Edit)", pr.getPRID()));
        btnPerform.setText("OK");
        this.pr = pr;
        
        initialSetting();
        cmbSuppliers.setSelectedItem(Supplier.getNameById(pr.getSupplierID()));
        cmbSuppliers.setEnabled(false);
        
        cmbYear.setSelectedItem(String.valueOf(pr.getRequiredDeliveryDate().getYear()));
        cmbMonth.setSelectedItem(String.format("%02d", pr.getRequiredDeliveryDate().getMonthValue()));
        cmbDay.setSelectedItem(String.format("%02d", pr.getRequiredDeliveryDate().getDayOfMonth()));
        
        isInitializing = false;
        
        refreshItem();
        
        previousItems = pr.getItems();
        selectedItems = pr.getItems();
        supplierItems = Item.getItemsFromSupplier(pr.getSupplierID());
        
        
        for (int i = 0; i < supplierItems.size(); i++){
            for (int j = 0; j < selectedItems.size(); j++){
                PRItem item = selectedItems.get(j);
                if (tblItems.getValueAt(i, 0).equals(item.getItemID()))
                {
                    tblItems.setValueAt("✓", i, 6);
                    tblItems.setValueAt(item.getQuantity(), i, 7);
                }
            }
            
        }
    }
    
    public void initialSetting(){
        var suppliers = Supplier.getAllSuppliers();
        for (String supplier : suppliers) {
            cmbSuppliers.addItem(supplier);
        }
        
        DateResolver.connect(cmbYear, cmbMonth, cmbDay, isInitializing);
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
        
        if (cmbSuppliers.getSelectedIndex() != -1){
            items = Item.getItemsFromSupplier(lblSupplierID.getText());
            for (Item item : items) {
            if (item.getStatus() != Item.Status.REMOVED)
            {
                itemsModel.addRow(new String[]{
                    item.getItemID(),
                    item.getItemCategory(),
                    item.getItemType(),
                    item.getItemName(),
                    String.valueOf(item.getStock()),
                    item.getStatus().name(),
                    "",
                    ""
                });
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
        cmbSuppliers = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        srlItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        lblSupplierID = new javax.swing.JLabel();
        btnPerform = new javax.swing.JButton();
        cmbYear = new javax.swing.JComboBox<>();
        cmbMonth = new javax.swing.JComboBox<>();
        cmbDay = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 680));

        pnlMain.setBackground(new java.awt.Color(255, 204, 204));
        pnlMain.setPreferredSize(new java.awt.Dimension(700, 700));

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Supplier:");

        cmbSuppliers.setSelectedItem(null);
        cmbSuppliers.setMinimumSize(new java.awt.Dimension(49, 24));
        cmbSuppliers.setPreferredSize(new java.awt.Dimension(49, 24));
        cmbSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSuppliersActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Required Date:");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Item:");

        tblItems.setBackground(new java.awt.Color(255, 255, 204));
        tblItems.setModel(itemsModel);
        tblItems.setGridColor(java.awt.Color.gray);
        tblItems.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblItems.setShowGrid(true);
        tblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblItemsMouseReleased(evt);
            }
        });
        srlItems.setViewportView(tblItems);

        lblSupplierID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSupplierID.setText("[SupplierID]");

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

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(70, 70, 70)
                                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlMainLayout.createSequentialGroup()
                                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(cmbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(cmbDay, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlMainLayout.createSequentialGroup()
                                        .addComponent(cmbSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(79, 79, 79)
                                        .addComponent(lblSupplierID))))
                            .addComponent(jLabel6)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(btnPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSupplierID))
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
                .addGap(18, 18, 18)
                .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(pnlMain, javax.swing.GroupLayout.PREFERRED_SIZE, 598, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSuppliersActionPerformed
        selectedItems.clear();
        if (cmbSuppliers.getSelectedIndex() >= 0){
            lblSupplierID.setVisible(true);
            lblSupplierID.setText(Supplier.getIdByName(cmbSuppliers.getSelectedItem().toString()));
            refreshItem();
        }
        else{
            lblSupplierID.setVisible(false);
            refreshItem();
        }
    }//GEN-LAST:event_cmbSuppliersActionPerformed

    private void tblItemsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemsMouseReleased
        int selectedRow = tblItems.getSelectedRow();
        Object check = tblItems.getValueAt(selectedRow, 6);
        PRItem pri = new PRItem(
                tblItems.getValueAt(selectedRow, 0).toString(),
                tblItems.getValueAt(selectedRow, 1).toString(),
                tblItems.getValueAt(selectedRow, 2).toString(),
                tblItems.getValueAt(selectedRow, 3).toString()
        );

        if (!"✓".equals(check)) {
            String input = JOptionPane.showInputDialog(this, "Enter the quantity:", tblItems.getValueAt(selectedRow, 3).toString(), JOptionPane.INFORMATION_MESSAGE);
            
            if (input != null) {
                try {
                    int quantity = Integer.parseInt(input.trim());
                    if (quantity < 0) {
                        JOptionPane.showMessageDialog(this, "Quantity cannot be negative!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    } else if (quantity == 0){
                        JOptionPane.showMessageDialog(this, "Quantity cannot be zero!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    } else {
                        tblItems.setValueAt("✓", selectedRow, 6);
                        tblItems.setValueAt(input, selectedRow, 7);

                        pri.setQuantity(quantity);
                        selectedItems.add(pri);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid integer!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            tblItems.setValueAt("", selectedRow, 6);
            tblItems.setValueAt("", selectedRow, 7);
            selectedItems.removeIf(item -> item.getItemID().equals(pri.getItemID()));
        }
    }//GEN-LAST:event_tblItemsMouseReleased

    private void btnPerformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformActionPerformed
        supplierID = lblSupplierID.getText();
        
        if (cmbSuppliers.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Please select a supplier!", "Supplier Missing", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (cmbDay.getSelectedIndex() == -1){
            JOptionPane.showMessageDialog(this, "Invalid required delivery date!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedItems.size() == 0){
            JOptionPane.showMessageDialog(this, "Please select items!", "No Items Selected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
       String Message = edit ? 
                String.format(
                        "Are you sure to edit this PR?%n%s →%n%n%s ", 
                        previousItems.stream()
                        .map(item -> String.format("%s (Qty: %d)", item.getItemName(), item.getQuantity()))
                        .collect(Collectors.joining("\n")),
                        selectedItems.stream()
                        .map(item -> String.format("%s (Qty: %d)", item.getItemName(), item.getQuantity()))
                        .collect(Collectors.joining("\n"))
                ):
                String.format(
                        "Are you sure to create a new PR?%nSupplier: %s%n%s", 
                        cmbSuppliers.getSelectedItem().toString(),
                        selectedItems.stream()
                        .map(item -> String.format("%s (Qty: %d)", item.getItemName(), item.getQuantity()))
                        .collect(Collectors.joining("\n"))
                );
       
       String successfulMessage = edit ? "Successfully make change to the PR!" : "Successfully create a new PR!";
        
        int result = JOptionPane.showConfirmDialog(
                this, 
                Message, 
                this.getTitle(), 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION){
            if (!edit){
                pr = new PurchaseRequisition(supplierID, requiredDate, admin.getUID());
                pr.add();
            }
            else
            {
                pr.setRequiredDeliveryDate(requiredDate);
                pr.updateRequiredDate();
            }
            
            PRItem pri = new PRItem();
            pri.setPRID(pr.getPRID());
            pri.save(selectedItems);
            JOptionPane.showMessageDialog(this, successfulMessage);
            this.dispose();
        }
        
    }//GEN-LAST:event_btnPerformActionPerformed

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        if (isInitializing || isFilling) return;
        cmbMonth.setSelectedIndex(-1);
        if (edit)
        {
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear();
            int currentMonth = today.getMonthValue();
            cmbMonth.removeAllItems();
            isFilling = true;
            DateResolver.populateCbMonth(cmbYear, cmbMonth, cmbDay, currentYear, currentMonth);
            cmbMonth.setSelectedIndex(-1);
            isFilling = false;
        }
    }//GEN-LAST:event_cmbYearActionPerformed

    private void cmbMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMonthActionPerformed
        if (isInitializing || isFilling) return;
        cmbDay.setSelectedIndex(-1);
        if (edit)
        {
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear();
            int currentMonth = today.getMonthValue();
            int currentDay = today.getDayOfMonth();
            cmbDay.removeAllItems();
            isFilling = true;
            DateResolver.populateCbDay(cmbYear, cmbMonth, cmbDay, currentYear, currentMonth, currentDay);
            cmbDay.setSelectedIndex(-1);
            isFilling = false;
        }
    }//GEN-LAST:event_cmbMonthActionPerformed

    private void cmbDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDayActionPerformed
        if (cmbDay.getSelectedIndex() != -1)
        {
            int year = Integer.parseInt(cmbYear.getSelectedItem().toString());
            int month = Integer.parseInt(cmbMonth.getSelectedItem().toString());
            int day = Integer.parseInt(cmbDay.getSelectedItem().toString());
            requiredDate = LocalDate.of(year, month, day);
        }
    }//GEN-LAST:event_cmbDayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPerform;
    private javax.swing.JComboBox<String> cmbDay;
    private javax.swing.JComboBox<String> cmbMonth;
    private javax.swing.JComboBox<String> cmbSuppliers;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblSupplierID;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JTable tblItems;
    // End of variables declaration//GEN-END:variables
}
