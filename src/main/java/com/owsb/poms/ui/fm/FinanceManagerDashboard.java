
package com.owsb.poms.ui.fm;

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
public class FinanceManagerDashboard extends javax.swing.JFrame {
    private List<PurchaseOrder> orders;
    public FinanceManagerDashboard() {
        initComponents();
        for (int i = 0; i < FrameTab.getTabCount(); i++) {
        FrameTab.setBackgroundAt(i, new java.awt.Color(255, 107, 149));
        }
        initApprovePOFunctionality();
        initProcessPaymentFunctionality();
        initTransactionTable();
        initPurchaseOrderView();
        initRequisitionView();
        initSupplierView();
        initGenerateReportFunctionality();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        FrameTab = new javax.swing.JTabbedPane();
        DashboardPanel = new javax.swing.JPanel();
        dashBoardLabel1 = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        approvePOPanel = new javax.swing.JPanel();
        approvePOScrollpane = new javax.swing.JScrollPane();
        approvePOTable = new javax.swing.JTable();
        approvePOButton = new javax.swing.JButton();
        approvePORejectButton = new javax.swing.JButton();
        approvePORefreshButton = new javax.swing.JButton();
        approvePOStatusLabel = new javax.swing.JLabel();
        approvePOEditButton = new javax.swing.JButton();
        PurchaseOrder = new javax.swing.JPanel();
        viewPOScroolPane = new javax.swing.JScrollPane();
        viewPOTable = new javax.swing.JTable();
        viewPurchaseOrderRefreshButton = new javax.swing.JButton();
        viewPOComboBox = new javax.swing.JComboBox<>();
        processPaymentPanel = new javax.swing.JPanel();
        selectPOOrderLabel = new javax.swing.JLabel();
        selectPOComboBox = new javax.swing.JComboBox<>();
        bankAccNumberLabel = new javax.swing.JLabel();
        BankAccNumberTextfield = new javax.swing.JTextField();
        bankNamelabel = new javax.swing.JLabel();
        bankNameTextfield = new javax.swing.JTextField();
        AmountLabel = new javax.swing.JLabel();
        AmountTextfield = new javax.swing.JTextField();
        ProcessPaymentButton = new javax.swing.JButton();
        ClearFormButton = new javax.swing.JButton();
        ProcessingPaymentStatusLabel = new javax.swing.JLabel();
        TransactionsPanel = new javax.swing.JPanel();
        viewTransactionsRefreshButton = new javax.swing.JButton();
        viewTransactionsScrollPanel = new javax.swing.JScrollPane();
        viewTransactionTable = new javax.swing.JTable();
        PurchaseRequisitionPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        viewPRTable = new javax.swing.JTable();
        ViewPRRefreshButton = new javax.swing.JButton();
        SupplierEntryPanel = new javax.swing.JPanel();
        supplierEntryButton = new javax.swing.JButton();
        supplierEntryScrollPanel = new javax.swing.JScrollPane();
        supplierEntryTable = new javax.swing.JTable();
        generateReportPanel = new javax.swing.JPanel();
        generatePRSlabel = new javax.swing.JLabel();
        generatePRlabel2 = new javax.swing.JLabel();
        generatePRButton = new javax.swing.JButton();
        generatePRScrollpane = new javax.swing.JScrollPane();
        generatePRtable = new javax.swing.JTable();
        generatePRexportButton = new javax.swing.JButton();
        generatePRStartDateComboBox = new javax.swing.JComboBox<>();
        generatePREndDateComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1200, 800));

        jPanel1.setBackground(new java.awt.Color(255, 245, 247));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setMaximumSize(new java.awt.Dimension(1200, 800));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        FrameTab.setBackground(new java.awt.Color(255, 107, 149));
        FrameTab.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        DashboardPanel.setBackground(new java.awt.Color(255, 245, 247));

        dashBoardLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 65)); // NOI18N
        dashBoardLabel1.setText("Welcome Back, Finance Manager!");

        logOutButton.setFont(new java.awt.Font("Comic Sans MS", 0, 25)); // NOI18N
        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DashboardPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logOutButton))
                    .addGroup(DashboardPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(dashBoardLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE)))
                .addContainerGap())
        );
        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logOutButton)
                .addGap(134, 134, 134)
                .addComponent(dashBoardLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        FrameTab.addTab("Dashboard", DashboardPanel);
        DashboardPanel.getAccessibleContext().setAccessibleName("DashboardPanel");
        DashboardPanel.getAccessibleContext().setAccessibleDescription("");

        approvePOPanel.setBackground(new java.awt.Color(255, 245, 247));

        approvePOTable.setModel(new javax.swing.table.DefaultTableModel(
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
        approvePOScrollpane.setViewportView(approvePOTable);

        approvePOButton.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        approvePOButton.setText("Approve");
        approvePOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvePOButtonActionPerformed(evt);
            }
        });

        approvePORejectButton.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        approvePORejectButton.setText("Edit");
        approvePORejectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvePORejectButtonActionPerformed(evt);
            }
        });

        approvePORefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        approvePORefreshButton.setText("Refresh List");
        approvePORefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvePORefreshButtonActionPerformed(evt);
            }
        });

        approvePOStatusLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        approvePOStatusLabel.setText("Status");

        approvePOEditButton.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        approvePOEditButton.setText("Reject");
        approvePOEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                approvePOEditButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout approvePOPanelLayout = new javax.swing.GroupLayout(approvePOPanel);
        approvePOPanel.setLayout(approvePOPanelLayout);
        approvePOPanelLayout.setHorizontalGroup(
            approvePOPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(approvePOPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(approvePOPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(approvePOScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(approvePOStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(approvePOPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(approvePORefreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(approvePORejectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(approvePOButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(approvePOEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(125, Short.MAX_VALUE))
        );
        approvePOPanelLayout.setVerticalGroup(
            approvePOPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(approvePOPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(approvePOPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(approvePOScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(approvePOPanelLayout.createSequentialGroup()
                        .addComponent(approvePOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(approvePOEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(approvePORejectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(approvePORefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(approvePOStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        FrameTab.addTab("Approve Purchase Order", approvePOPanel);

        PurchaseOrder.setBackground(new java.awt.Color(255, 245, 247));

        viewPOTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewPOScroolPane.setViewportView(viewPOTable);

        viewPurchaseOrderRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        viewPurchaseOrderRefreshButton.setText("Refresh");
        viewPurchaseOrderRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPurchaseOrderRefreshButtonActionPerformed(evt);
            }
        });

        viewPOComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        viewPOComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        viewPOComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPOComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PurchaseOrderLayout = new javax.swing.GroupLayout(PurchaseOrder);
        PurchaseOrder.setLayout(PurchaseOrderLayout);
        PurchaseOrderLayout.setHorizontalGroup(
            PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseOrderLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewPOScroolPane, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PurchaseOrderLayout.createSequentialGroup()
                        .addComponent(viewPOComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(viewPurchaseOrderRefreshButton)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        PurchaseOrderLayout.setVerticalGroup(
            PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseOrderLayout.createSequentialGroup()
                .addGroup(PurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PurchaseOrderLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(viewPOComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PurchaseOrderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(viewPurchaseOrderRefreshButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewPOScroolPane, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab(" Purchase Order", PurchaseOrder);

        processPaymentPanel.setBackground(new java.awt.Color(255, 245, 247));

        selectPOOrderLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        selectPOOrderLabel.setText("Select Purchase Order :");

        selectPOComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        selectPOComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectPOComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPOComboBoxActionPerformed(evt);
            }
        });

        bankAccNumberLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        bankAccNumberLabel.setText("Bank Account Number :");

        BankAccNumberTextfield.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        BankAccNumberTextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BankAccNumberTextfieldActionPerformed(evt);
            }
        });

        bankNamelabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        bankNamelabel.setText("Bank Name :");

        bankNameTextfield.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N

        AmountLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        AmountLabel.setText("Amount :");

        AmountTextfield.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N

        ProcessPaymentButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        ProcessPaymentButton.setText("Process Payment");
        ProcessPaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProcessPaymentButtonActionPerformed(evt);
            }
        });

        ClearFormButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        ClearFormButton.setText("Clear Form");
        ClearFormButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearFormButtonActionPerformed(evt);
            }
        });

        ProcessingPaymentStatusLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        ProcessingPaymentStatusLabel.setText("Status");

        javax.swing.GroupLayout processPaymentPanelLayout = new javax.swing.GroupLayout(processPaymentPanel);
        processPaymentPanel.setLayout(processPaymentPanelLayout);
        processPaymentPanelLayout.setHorizontalGroup(
            processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(processPaymentPanelLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bankAccNumberLabel)
                                .addComponent(selectPOOrderLabel))
                            .addComponent(AmountLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bankNamelabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(44, 44, 44)
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(bankNameTextfield, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BankAccNumberTextfield, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectPOComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 463, Short.MAX_VALUE)
                            .addComponent(AmountTextfield)))
                    .addGroup(processPaymentPanelLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProcessingPaymentStatusLabel)
                            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                                .addComponent(ProcessPaymentButton)
                                .addGap(161, 161, 161)
                                .addComponent(ClearFormButton, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        processPaymentPanelLayout.setVerticalGroup(
            processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectPOOrderLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectPOComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bankAccNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BankAccNumberTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bankNamelabel)
                    .addComponent(bankNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AmountTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addComponent(ProcessingPaymentStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProcessPaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearFormButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108))
        );

        FrameTab.addTab("Processing Payment", processPaymentPanel);

        TransactionsPanel.setBackground(new java.awt.Color(255, 245, 247));

        viewTransactionsRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        viewTransactionsRefreshButton.setText("Refresh");
        viewTransactionsRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewTransactionsRefreshButtonActionPerformed(evt);
            }
        });

        viewTransactionTable.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        viewTransactionTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewTransactionsScrollPanel.setViewportView(viewTransactionTable);

        javax.swing.GroupLayout TransactionsPanelLayout = new javax.swing.GroupLayout(TransactionsPanel);
        TransactionsPanel.setLayout(TransactionsPanelLayout);
        TransactionsPanelLayout.setHorizontalGroup(
            TransactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionsPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(TransactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewTransactionsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewTransactionsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 963, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        TransactionsPanelLayout.setVerticalGroup(
            TransactionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionsPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(viewTransactionsRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(viewTransactionsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab("Transactions ", TransactionsPanel);

        PurchaseRequisitionPanel.setBackground(new java.awt.Color(255, 245, 247));

        viewPRTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(viewPRTable);

        ViewPRRefreshButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        ViewPRRefreshButton.setText("Refresh");
        ViewPRRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewPRRefreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PurchaseRequisitionPanelLayout = new javax.swing.GroupLayout(PurchaseRequisitionPanel);
        PurchaseRequisitionPanel.setLayout(PurchaseRequisitionPanelLayout);
        PurchaseRequisitionPanelLayout.setHorizontalGroup(
            PurchaseRequisitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseRequisitionPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(PurchaseRequisitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ViewPRRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        PurchaseRequisitionPanelLayout.setVerticalGroup(
            PurchaseRequisitionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PurchaseRequisitionPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ViewPRRefreshButton)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab("Purchase Requisition", PurchaseRequisitionPanel);

        SupplierEntryPanel.setBackground(new java.awt.Color(255, 245, 247));

        supplierEntryButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        supplierEntryButton.setText("Refresh");
        supplierEntryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierEntryButtonActionPerformed(evt);
            }
        });

        supplierEntryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        supplierEntryScrollPanel.setViewportView(supplierEntryTable);

        javax.swing.GroupLayout SupplierEntryPanelLayout = new javax.swing.GroupLayout(SupplierEntryPanel);
        SupplierEntryPanel.setLayout(SupplierEntryPanelLayout);
        SupplierEntryPanelLayout.setHorizontalGroup(
            SupplierEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplierEntryPanelLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(SupplierEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplierEntryButton)
                    .addComponent(supplierEntryScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        SupplierEntryPanelLayout.setVerticalGroup(
            SupplierEntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SupplierEntryPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(supplierEntryButton)
                .addGap(18, 18, 18)
                .addComponent(supplierEntryScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab("Supplier Entry", SupplierEntryPanel);

        generateReportPanel.setBackground(new java.awt.Color(255, 245, 247));

        generatePRSlabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePRSlabel.setText("Start Date :");

        generatePRlabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePRlabel2.setText("End Date :");

        generatePRButton.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePRButton.setText("Generate Report");
        generatePRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePRButtonActionPerformed(evt);
            }
        });

        generatePRtable.setModel(new javax.swing.table.DefaultTableModel(
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
        generatePRScrollpane.setViewportView(generatePRtable);

        generatePRexportButton.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePRexportButton.setText("Export");
        generatePRexportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePRexportButtonActionPerformed(evt);
            }
        });

        generatePRStartDateComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePRStartDateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        generatePREndDateComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        generatePREndDateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout generateReportPanelLayout = new javax.swing.GroupLayout(generateReportPanel);
        generateReportPanel.setLayout(generateReportPanelLayout);
        generateReportPanelLayout.setHorizontalGroup(
            generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generateReportPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generateReportPanelLayout.createSequentialGroup()
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generatePRSlabel)
                            .addComponent(generatePRlabel2))
                        .addGap(43, 43, 43)
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generatePREndDateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(generatePRStartDateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(146, 146, 146)
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generatePRButton)
                            .addComponent(generatePRexportButton)))
                    .addComponent(generatePRScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        generateReportPanelLayout.setVerticalGroup(
            generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generateReportPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatePRSlabel)
                    .addComponent(generatePRButton)
                    .addComponent(generatePREndDateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatePRlabel2)
                    .addComponent(generatePRexportButton)
                    .addComponent(generatePRStartDateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(generatePRScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab("Purchase Report ", generateReportPanel);

        jPanel1.add(FrameTab, java.awt.BorderLayout.CENTER);
        FrameTab.getAccessibleContext().setAccessibleName("FrameTabPane");
        FrameTab.getAccessibleContext().setAccessibleDescription("Dashboard");

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClearFormButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearFormButtonActionPerformed
        clearProcessingPaymentForm();
    }//GEN-LAST:event_ClearFormButtonActionPerformed

    private void generatePRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePRButtonActionPerformed
        generateReport();
    }//GEN-LAST:event_generatePRButtonActionPerformed

    private void BankAccNumberTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BankAccNumberTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BankAccNumberTextfieldActionPerformed

    private void approvePORejectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvePORejectButtonActionPerformed
        updatePOStatus("REJECTED");
    }//GEN-LAST:event_approvePORejectButtonActionPerformed

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void ProcessPaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProcessPaymentButtonActionPerformed
        processPayment();
    }//GEN-LAST:event_ProcessPaymentButtonActionPerformed

    private void viewTransactionsRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewTransactionsRefreshButtonActionPerformed
        refreshTransactionTable();
    }//GEN-LAST:event_viewTransactionsRefreshButtonActionPerformed

    private void selectPOComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectPOComboBoxActionPerformed
        String selected = (String) selectPOComboBox.getSelectedItem();
            if (selected == null || !approvedPOMap.containsKey(selected)) {
                disablePaymentFields();
                return;
            }
            Object[] po = approvedPOMap.get(selected);
            int quantity = (int) po[2];
            double price = (double) po[3];
            double total = quantity * price;

            AmountTextfield.setText(String.valueOf(total));
            enablePaymentFields();
    }//GEN-LAST:event_selectPOComboBoxActionPerformed

    private void approvePOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvePOButtonActionPerformed
        updatePOStatus("APPROVED");
        initProcessPaymentFunctionality();
    }//GEN-LAST:event_approvePOButtonActionPerformed

    private void approvePORefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvePORefreshButtonActionPerformed
            refreshApprovePOList();
    }//GEN-LAST:event_approvePORefreshButtonActionPerformed

    private void viewPurchaseOrderRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPurchaseOrderRefreshButtonActionPerformed
        String selectedType = viewPOComboBox.getSelectedItem().toString();
        refreshPurchaseOrderTable(selectedType);    
    }//GEN-LAST:event_viewPurchaseOrderRefreshButtonActionPerformed

    private void viewPOComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPOComboBoxActionPerformed
        String selectedType = (String) viewPOComboBox.getSelectedItem();
        if (selectedType != null) {
            refreshPurchaseOrderTable(selectedType);
        }
    }//GEN-LAST:event_viewPOComboBoxActionPerformed

    private void ViewPRRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPRRefreshButtonActionPerformed
        loadRequisitionData();
    }//GEN-LAST:event_ViewPRRefreshButtonActionPerformed

    private void supplierEntryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierEntryButtonActionPerformed
         loadActiveSuppliers();
    }//GEN-LAST:event_supplierEntryButtonActionPerformed

    private void generatePRexportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatePRexportButtonActionPerformed
        exportReportToFile();
    }//GEN-LAST:event_generatePRexportButtonActionPerformed

    private void approvePOEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvePOEditButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_approvePOEditButtonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanceManagerDashboard().setVisible(true);
            }
        });
    }
    
    DefaultTableModel approvePOTableModel;
    private void initApprovePOFunctionality(){
        String[] columns = {"PO ID","PR ITEM","Total Price","Supplier ID","Generated Date", "Delivery Date", "Status", "Created By", "Approved By"};
        approvePOTableModel = new DefaultTableModel(columns,0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        approvePOTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && approvePOTable.getSelectedRow() != -1) {
                    int selectedRow = approvePOTable.getSelectedRow();
                    String selectedPOID  = approvePOTable.getValueAt(selectedRow, 0).toString();
                    new PODetailFrame(selectedPOID).setVisible(true);
                }
            }
        });
        approvePOTable.setModel(approvePOTableModel);
        loadApprovePODataFromMainList();
    }
    
        private void loadApprovePODataFromMainList() {
        approvePOTableModel.setRowCount(0);
        orders = com.owsb.poms.system.model.PurchaseOrder.toList();
        for (PurchaseOrder po : orders) {
            if (po.getStatus() == com.owsb.poms.system.model.PurchaseOrder.Status.NEW) {
                approvePOTableModel.addRow(new Object[]{
                    po.getPOID(),
                    po.getPRID(),
                    po.getTotalPrice(),
                    po.getSupplierID(),
                    po.getGenerateDateTime().toString(),
                    po.getDeliveryDate().toString(),
                    po.getStatus().name(),
                    po.getCreateBy(),
                    po.getPerformedBy()
                });
            }
        }
    }
    private void updatePOStatus(String status) {
        int selectedRow = approvePOTable.getSelectedRow();
        
        if (selectedRow == -1) {
            approvePOStatusLabel.setText("Status: Please select a row.");
            return;
        }
        
        String poID = approvePOTableModel.getValueAt(selectedRow, 0).toString();
        String currentStatus = approvePOTableModel.getValueAt(selectedRow, 6).toString();

        if (!currentStatus.equalsIgnoreCase(com.owsb.poms.system.model.PurchaseOrder.Status.NEW.name())) {
            approvePOStatusLabel.setText("Status: Cannot update. Already " + currentStatus + ".");
            return;
        }
        orders = com.owsb.poms.system.model.PurchaseOrder.toList();
        for (PurchaseOrder po : orders) {
            if (po.getPOID().equals(poID)) {
                po.setStatus(com.owsb.poms.system.model.PurchaseOrder.Status.valueOf(status.toUpperCase()));
                po.setPerformedBy("FM001");
                po.updateStatus(); 
                approvePOStatusLabel.setText("Status: Order " + status.toLowerCase() + " successfully.");
                break;
            }
        }
        
        refreshApprovePOList();
        refreshPurchaseOrderTable(viewPOComboBox.getSelectedItem().toString());
        
    }
    
    private void refreshApprovePOList(){
        loadApprovePODataFromMainList();
        approvePOStatusLabel.setText("Status: List refreshed. Only NEW orders shown.");
    }
    
    
    
    private Object[] getRowData(DefaultTableModel model, int rowIndex) {
        Object[] row = new Object[model.getColumnCount()];
        for (int col = 0; col < model.getColumnCount(); col++) {
            row[col] = model.getValueAt(rowIndex, col);
        }
        return row;
    }
    
    Map<String, Object[]> approvedPOMap = new HashMap<>();
    ArrayList<Object[]> transactionList = new ArrayList<Object[]>();
    int transactionCounter = 1;
    
    DefaultTableModel transactionTableModel;
    
    private void initProcessPaymentFunctionality() {
        selectPOComboBox.removeAllItems();
        approvedPOMap.clear();
        
        boolean hasVerified = false;
        List<Item> items = Item.toList();
        
        for (Item item : items) {
            if (item.getStatus() == Item.Status.SHORTAGE && !isPOAlreadyPaid(item.getItemID())) {
                String label = item.getItemID() + " - " + item.getItemName();
                approvedPOMap.put(label, new Object[]{item.getItemID(), item.getItemName(), item.getStock(), item.getSellPrice()});
                selectPOComboBox.addItem(label);
                hasVerified = true;
            }
        }
        
        if (selectPOComboBox.getItemCount() == 0) {
            disablePaymentFields();
            ProcessingPaymentStatusLabel.setText("Status: No verified purchase orders found.");
        } else {
            enablePaymentFields();
            selectPOComboBox.setEnabled(true);
            ProcessingPaymentStatusLabel.setText("Status: Select a PO to proceed with payment.");
        }
    }
        
        private boolean isPOAlreadyPaid(String poID) {
            for (Object[] tx : transactionList) {
                if (tx[0].toString().contains(poID)) {
                    return true;
                }
            }
            return false;
        }
        
        private void clearProcessingPaymentForm() {
            selectPOComboBox.setSelectedIndex(-1);
            bankNameTextfield.setText("");
            BankAccNumberTextfield.setText("");
            AmountTextfield.setText("");
            ProcessingPaymentStatusLabel.setText("Status: Please select another PO to continue payment.");
        }
        private void disablePaymentFields() {
            bankNameTextfield.setEnabled(false);
            BankAccNumberTextfield.setEnabled(false);
            AmountTextfield.setEnabled(false);
            ProcessPaymentButton.setEnabled(false);
            ClearFormButton.setEnabled(false);
            selectPOComboBox.setEnabled(false);
        }
        private void enablePaymentFields() {
            bankNameTextfield.setEnabled(true);
            BankAccNumberTextfield.setEnabled(true);
            AmountTextfield.setEnabled(true);
            ProcessPaymentButton.setEnabled(true);
            ClearFormButton.setEnabled(true);
        }
        
        
    private void processPayment() {
        int index = selectPOComboBox.getSelectedIndex();
        String selected = (String) selectPOComboBox.getSelectedItem();
        String bank = bankNameTextfield.getText().trim();
        String accountnumber = BankAccNumberTextfield.getText().trim();
        String amountText = AmountTextfield.getText().trim();

        if (index == -1 || bank.isEmpty() || accountnumber.isEmpty() || amountText.isEmpty()) {
            ProcessingPaymentStatusLabel.setText("Status: Please fill in all fields.");
            return;
        }

        String transactionID = "PO" + String.format("%03d", transactionCounter++);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = java.time.LocalDateTime.now().format(formatter);
        double amount = Double.parseDouble(amountText);

        transactionList.add(new Object[]{transactionID, bank, accountnumber, dateTime, amount});
        Object[] poDetails = approvedPOMap.get(selected);
        String poID = poDetails[0].toString();
        Item paidItem = new Item();
        paidItem.setItemID(poID);
        paidItem.setStatus(Item.Status.REMOVED);
        paidItem.updateStatus();
                
        approvedPOMap.remove(selected);
        selectPOComboBox.removeItemAt(index);
        
        refreshTransactionTable();
        updatePurchaseReportListFromTransactions();
        populateReportDateComboboxes();
        refreshPurchaseOrderTable(viewPOComboBox.getSelectedItem().toString());

    if (selectPOComboBox.getItemCount() == 0) {
        disablePaymentFields();
        ProcessingPaymentStatusLabel.setText("Status: All verified orders are paid.");
    } else {
        enablePaymentFields();
        ProcessingPaymentStatusLabel.setText("Status: Payment successful for " + transactionID);
    }
    }
   

    private void initTransactionTable(){
        String[] columns = {"Transaction ID","Bank Name","Bank Account","Date Time","Amount"};
        transactionTableModel = new DefaultTableModel(columns,0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        viewTransactionTable.setModel(transactionTableModel);
    }
    
    private void refreshTransactionTable() {
        transactionTableModel.setRowCount(0);
        for (Object[] row : transactionList) {
            transactionTableModel.addRow(row);
        }   
    }
    
    DefaultTableModel purchaseOrderTableModel;

    DefaultTableModel requisitionTableModel;
    ArrayList<Object[]> requisitionList = new ArrayList<>();

    DefaultTableModel supplierTableModel;
    ArrayList<Object[]> supplierList = new ArrayList<>();
    
    
    
    private void refreshPurchaseOrderTable(String filterStatus) {
        List<PurchaseOrder> allPOs = com.owsb.poms.system.model.PurchaseOrder.toList();
        purchaseOrderTableModel.setRowCount(0);
        for (PurchaseOrder po : allPOs) {
            if (filterStatus.equals("All") || po.getStatus().name().equalsIgnoreCase(filterStatus)) {
                purchaseOrderTableModel.addRow(new Object[]{
                    po.getPOID(),
                    po.getPRID(),
                    po.getTotalPrice(),
                    po.getSupplierID(),
                    po.getGenerateDateTime().toString(),
                    po.getDeliveryDate().toString(),
                    po.getStatus().name(),
                    po.getCreateBy(),
                    po.getPerformedBy()
                });
            }
        }
    }
    
    private void loadAllPurchaseOrders() {
        purchaseOrderTableModel.setRowCount(0);
        List<PurchaseOrder> allPOs = com.owsb.poms.system.model.PurchaseOrder.toList();
        for (PurchaseOrder po : allPOs) {
            purchaseOrderTableModel.addRow(new Object[]{
                po.getPOID(),
                po.getPRID(),
                "-",  
                po.getSupplierID(),
                po.getTotalPrice(),
                po.getStatus().name()
            });
        }
    }
    
    private void initPurchaseOrderView() {
        String[] columns = {"PO ID","PR ITEM","Total Price","Supplier ID","Generated Date", "Delivery Date", "Status", "Created By", "Approved By"};
        purchaseOrderTableModel = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        viewPOTable.setModel(purchaseOrderTableModel);
        loadAllPurchaseOrders();
        viewPOComboBox.removeAllItems();
        viewPOComboBox.addItem("All");
        for (PurchaseOrder.Status s : com.owsb.poms.system.model.PurchaseOrder.Status.values()) {
        viewPOComboBox.addItem(s.name());
        }
        refreshPurchaseOrderTable("All");
        initProcessPaymentFunctionality();
        
        viewPOTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && viewPOTable.getSelectedRow() != -1) {
                    String poid = viewPOTable.getValueAt(viewPOTable.getSelectedRow(), 0).toString();
                    openPurchaseOrderDetailsFrame(poid);
                }
            }
        });
    }
    
    private void openPurchaseOrderDetailsFrame(String poid) {
        VPODetailFrame poDetailFrame = new VPODetailFrame();
        poDetailFrame.setVisible(true);
        poDetailFrame.initPODetailTable();
        poDetailFrame.loadPODetailData(poid);
    }
    
    
    private void refreshRequisitionTable() {
        requisitionTableModel.setRowCount(0);
        for (Object[] row : requisitionList) {
            requisitionTableModel.addRow(row);
        }
    }
    
    private void initRequisitionView() {
        String[] cols = {"PR ID", "Supplier ID", "Request Date", "Delivery Date", "Status","Created By", "Performed By"};
        requisitionTableModel = new DefaultTableModel(cols, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        viewPRTable.setModel(requisitionTableModel);
        loadRequisitionData();
        
        viewPRTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && viewPRTable.getSelectedRow() != -1) {
                String prid = viewPRTable.getValueAt(viewPRTable.getSelectedRow(), 0).toString();
                openRequisitionDetailsFrame(prid);
            }
        }
        });
    }
    
    public void loadRequisitionData() {
        requisitionTableModel.setRowCount(0);
        List<PurchaseRequisition> prList = PurchaseRequisition.toList();
        
        for (PurchaseRequisition pr : prList) {
            requisitionTableModel.addRow(new Object[]{
                pr.getPRID(),
                pr.getSupplierID(),
                pr.getRequestDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                pr.getRequiredDeliveryDate().toString(),
                pr.getStatus().name(),
                pr.getCreateBy(),
                pr.getPerformedBy()
            });
        }
    }

    
    private void openRequisitionDetailsFrame(String prid) {
        PRDetailsFrame detailFrame = new PRDetailsFrame();
        detailFrame.setVisible(true);
        detailFrame.loadPRDetails(prid);
    }
    
    private void initSupplierView() {
        String[] cols = {"Supplier ID", "Name", "Date Added"};
        supplierTableModel = new DefaultTableModel(cols, 0){
        public boolean isCellEditable(int row,int colum){
                return false;
            }
        };
        supplierEntryTable.setModel(supplierTableModel);
        loadActiveSuppliers();
    }
    
    private void loadActiveSuppliers() {
        supplierTableModel.setRowCount(0);
        List<Supplier> suppliers = Supplier.toList();
        
        for (Supplier s : suppliers) {
            if (!s.isDeleted()) {  
            supplierTableModel.addRow(new Object[]{
                s.getSupplierID(),
                s.getSupplierName(),
                s.getAddedTime().toLocalDate().toString(),
            });
            }
        };
    }
    
    DefaultTableModel reportTableModel;
    ArrayList<Object[]> purchasereportList = new ArrayList<>();

    private void initGenerateReportFunctionality() {
        String[] cols = {"POID", "InvoiceID", "TransactionID", "Supplier", "ItemID", "Qty", "UnitPrice", "Total", "Date", "FM", "IM"};
        reportTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        generatePRtable.setModel(reportTableModel);
        populateReportDateComboboxes();
    }
    
    private void updatePurchaseReportListFromTransactions() {
        purchasereportList.clear();
        
        for (Object[] tx : transactionList) {
        String transactionID = tx[0].toString();
        String bank = tx[1].toString(); 
        String account = tx[2].toString();
        String date = tx[3].toString().substring(0, 10);
        double amount = (double) tx[4];
        
        String poID = transactionID.substring(0, 5);
        for (Item item : Item.toList()) {
            if (item.getItemID().equals(poID)) {
                String supplier = "-";
                String itemID = item.getItemID();
                String itemName = item.getItemName();
                int qty = item.getStock();
                double unitPrice = item.getSellPrice();
                String invoiceID = "INV" + transactionID.substring(2);
                String fm = "FM1";
                String im = "IM1";

                purchasereportList.add(new Object[]{
                    poID, invoiceID, transactionID, supplier, itemID, qty,
                    unitPrice, amount, date, fm, im
                });
                break;
            }
        }
        }
    }
    
    private void populateReportDateComboboxes() {
        updatePurchaseReportListFromTransactions();
        
        Set<String> dateSet = new TreeSet<>();
        for (Object[] row : purchasereportList) {
            if (row.length >8){
                String date = row[8].toString(); 
                dateSet.add(date);
            }
        }

        generatePRStartDateComboBox.removeAllItems();
        generatePREndDateComboBox.removeAllItems();

        for (String date : dateSet) {
            generatePRStartDateComboBox.addItem(date);
            generatePREndDateComboBox.addItem(date);
        }

        boolean hasDates = !dateSet.isEmpty();
        generatePRButton.setEnabled(hasDates);
        generatePRStartDateComboBox.setEnabled(hasDates);
        generatePREndDateComboBox.setEnabled(hasDates);
    }
    
    private void generateReport() {
        if (transactionList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found. Cannot generate report.");
            return;
        }
        String startDate = (String) generatePRStartDateComboBox.getSelectedItem();
        String endDate = (String) generatePREndDateComboBox.getSelectedItem();
        
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both start and end dates.");
            return;
        }
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            reportTableModel.setRowCount(0); 

            for (Object[] row : purchasereportList) {
                LocalDate reportDate = LocalDate.parse(row[8].toString());
            if (!reportDate.isBefore(start) && !reportDate.isAfter(end)) {
                    reportTableModel.addRow(row);
                }
            }
        } catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
        }
    }
    
    
    private void exportReportToFile() {
    if (reportTableModel.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "No data to export.");
        return;
    }

    try {
        File file = new File("purchase_report_output.txt");
        PrintWriter writer = new PrintWriter(file);

        for (int i = 0; i < reportTableModel.getRowCount(); i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < reportTableModel.getColumnCount(); j++) {
                sb.append(reportTableModel.getValueAt(i, j)).append("\t");
            }
            writer.println(sb.toString().trim());
        }

        writer.close();
        JOptionPane.showMessageDialog(this, "Report exported to: " + file.getAbsolutePath());
        reportTableModel.setRowCount(0);
    } catch (IOException e){
        JOptionPane.showMessageDialog(this, "Error writing file.");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmountLabel;
    private javax.swing.JTextField AmountTextfield;
    private javax.swing.JTextField BankAccNumberTextfield;
    private javax.swing.JButton ClearFormButton;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JTabbedPane FrameTab;
    private javax.swing.JButton ProcessPaymentButton;
    private javax.swing.JLabel ProcessingPaymentStatusLabel;
    private javax.swing.JPanel PurchaseOrder;
    private javax.swing.JPanel PurchaseRequisitionPanel;
    private javax.swing.JPanel SupplierEntryPanel;
    private javax.swing.JPanel TransactionsPanel;
    private javax.swing.JButton ViewPRRefreshButton;
    private javax.swing.JButton approvePOButton;
    private javax.swing.JButton approvePOEditButton;
    private javax.swing.JPanel approvePOPanel;
    private javax.swing.JButton approvePORefreshButton;
    private javax.swing.JButton approvePORejectButton;
    private javax.swing.JScrollPane approvePOScrollpane;
    private javax.swing.JLabel approvePOStatusLabel;
    private javax.swing.JTable approvePOTable;
    private javax.swing.JLabel bankAccNumberLabel;
    private javax.swing.JTextField bankNameTextfield;
    private javax.swing.JLabel bankNamelabel;
    private javax.swing.JLabel dashBoardLabel1;
    private javax.swing.JButton generatePRButton;
    private javax.swing.JComboBox<String> generatePREndDateComboBox;
    private javax.swing.JScrollPane generatePRScrollpane;
    private javax.swing.JLabel generatePRSlabel;
    private javax.swing.JComboBox<String> generatePRStartDateComboBox;
    private javax.swing.JButton generatePRexportButton;
    private javax.swing.JLabel generatePRlabel2;
    private javax.swing.JTable generatePRtable;
    private javax.swing.JPanel generateReportPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton logOutButton;
    private javax.swing.JPanel processPaymentPanel;
    private javax.swing.JComboBox<String> selectPOComboBox;
    private javax.swing.JLabel selectPOOrderLabel;
    private javax.swing.JButton supplierEntryButton;
    private javax.swing.JScrollPane supplierEntryScrollPanel;
    private javax.swing.JTable supplierEntryTable;
    private javax.swing.JComboBox<String> viewPOComboBox;
    private javax.swing.JScrollPane viewPOScroolPane;
    private javax.swing.JTable viewPOTable;
    private javax.swing.JTable viewPRTable;
    private javax.swing.JButton viewPurchaseOrderRefreshButton;
    private javax.swing.JTable viewTransactionTable;
    private javax.swing.JButton viewTransactionsRefreshButton;
    private javax.swing.JScrollPane viewTransactionsScrollPanel;
    // End of variables declaration//GEN-END:variables

    
}
