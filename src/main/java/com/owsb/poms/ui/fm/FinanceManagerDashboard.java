
package com.owsb.poms.ui.fm;

import com.owsb.poms.system.functions.IdGenerator;
import com.owsb.poms.system.model.*;
import com.owsb.poms.system.model.User.FinanceManager;
import com.owsb.poms.system.model.User.User;
import com.owsb.poms.system.model.company.Bank;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
public class FinanceManagerDashboard extends javax.swing.JFrame {
    private List<PurchaseOrder> orders;
    private FinanceManager financeManager ;
    public FinanceManagerDashboard(FinanceManager fm) {
        initComponents();
        financeManager = fm ;
        for (int i = 0; i < FrameTab.getTabCount(); i++) {
        FrameTab.setBackgroundAt(i, new java.awt.Color(255, 107, 149));
        }
        initUserProfile();
        initApprovePOFunctionality();
        initProcessPaymentFunctionality();
        initTransactionTable();
        initConfirmPOFunctionality();
        initPurchaseOrderView();
        initRequisitionView();
        initSupplierView();
        initGenerateReportPanel();
        initViewPurchaseReportPanel();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        FrameTab = new javax.swing.JTabbedPane();
        DashboardPanel = new javax.swing.JPanel();
        UserIdLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        WelcomeLabel = new javax.swing.JLabel();
        ChangePasswordButton = new javax.swing.JButton();
        UsernameLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        approvePOPanel = new javax.swing.JPanel();
        approvePOScrollpane = new javax.swing.JScrollPane();
        approvePOTable = new javax.swing.JTable();
        approvePOButton = new javax.swing.JButton();
        approvePORefreshButton = new javax.swing.JButton();
        approvePOStatusLabel = new javax.swing.JLabel();
        approvePOEditButton = new javax.swing.JButton();
        PurchaseOrder = new javax.swing.JPanel();
        viewPOScroolPane = new javax.swing.JScrollPane();
        viewPOTable = new javax.swing.JTable();
        viewPurchaseOrderRefreshButton = new javax.swing.JButton();
        viewPOComboBox = new javax.swing.JComboBox<>();
        ConfirmPurchaseOrder = new javax.swing.JPanel();
        InvalidPOButton = new javax.swing.JButton();
        ConfirmPOButton = new javax.swing.JButton();
        ConfirmPOLabel = new javax.swing.JLabel();
        ConfirmPOScrollpane = new javax.swing.JScrollPane();
        ConfirmPOTable = new javax.swing.JTable();
        AutoRefreshLabel = new javax.swing.JLabel();
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
        selectBankComboBox = new javax.swing.JComboBox<>();
        fromBanklabel = new javax.swing.JLabel();
        DetailsLabel = new javax.swing.JLabel();
        Detailstextfield = new javax.swing.JTextField();
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
        TransactionIDLabel = new javax.swing.JLabel();
        generatePRButton = new javax.swing.JButton();
        generatePRScrollpane = new javax.swing.JScrollPane();
        generatePRtable = new javax.swing.JTable();
        TransactionIDComboBox = new javax.swing.JComboBox<>();
        InvoiceIDLabel = new javax.swing.JLabel();
        VerifiedByLabel = new javax.swing.JLabel();
        InvoiceIdTextField = new javax.swing.JTextField();
        ApprovedByTextField = new javax.swing.JTextField();
        PRStatusLabel = new javax.swing.JLabel();
        PRMessageLabel = new javax.swing.JLabel();
        MessageTextField = new javax.swing.JTextField();
        ViewPurchaseReportPanel = new javax.swing.JPanel();
        viewReportScrollpane = new javax.swing.JScrollPane();
        viewPurchaseReportTable = new javax.swing.JTable();
        ViewPurchaseReportButton = new javax.swing.JButton();

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

        UserIdLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 50)); // NOI18N
        UserIdLabel.setText("User ID : FM001");

        logOutButton.setFont(new java.awt.Font("Comic Sans MS", 0, 25)); // NOI18N
        logOutButton.setText("Log Out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        WelcomeLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 65)); // NOI18N
        WelcomeLabel.setText("Welcome Back, Finance Manager!");

        ChangePasswordButton.setFont(new java.awt.Font("Comic Sans MS", 0, 60)); // NOI18N
        ChangePasswordButton.setText("Change Password");
        ChangePasswordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangePasswordButtonActionPerformed(evt);
            }
        });

        UsernameLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 50)); // NOI18N
        UsernameLabel.setText("User ID : FM001");

        EmailLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 50)); // NOI18N
        EmailLabel.setText("User ID : FM001");

        javax.swing.GroupLayout DashboardPanelLayout = new javax.swing.GroupLayout(DashboardPanel);
        DashboardPanel.setLayout(DashboardPanelLayout);
        DashboardPanelLayout.setHorizontalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DashboardPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(logOutButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DashboardPanelLayout.createSequentialGroup()
                        .addContainerGap(28, Short.MAX_VALUE)
                        .addComponent(WelcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DashboardPanelLayout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addGroup(DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(ChangePasswordButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DashboardPanelLayout.setVerticalGroup(
            DashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logOutButton)
                .addGap(18, 18, 18)
                .addComponent(WelcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ChangePasswordButton)
                .addContainerGap(80, Short.MAX_VALUE))
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

        ConfirmPurchaseOrder.setBackground(new java.awt.Color(255, 245, 247));

        InvalidPOButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        InvalidPOButton.setText("Invalid");
        InvalidPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InvalidPOButtonActionPerformed(evt);
            }
        });

        ConfirmPOButton.setFont(new java.awt.Font("Comic Sans MS", 0, 45)); // NOI18N
        ConfirmPOButton.setText("Confirm");
        ConfirmPOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmPOButtonActionPerformed(evt);
            }
        });

        ConfirmPOLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N

        ConfirmPOTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ConfirmPOScrollpane.setViewportView(ConfirmPOTable);

        AutoRefreshLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 20)); // NOI18N
        AutoRefreshLabel.setText("!!! This Form Auto-Refresh Every 5s !!!");

        javax.swing.GroupLayout ConfirmPurchaseOrderLayout = new javax.swing.GroupLayout(ConfirmPurchaseOrder);
        ConfirmPurchaseOrder.setLayout(ConfirmPurchaseOrderLayout);
        ConfirmPurchaseOrderLayout.setHorizontalGroup(
            ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfirmPurchaseOrderLayout.createSequentialGroup()
                .addGroup(ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfirmPurchaseOrderLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(ConfirmPOButton)
                        .addGap(296, 296, 296)
                        .addComponent(InvalidPOButton))
                    .addGroup(ConfirmPurchaseOrderLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(AutoRefreshLabel)
                            .addGroup(ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ConfirmPOLabel)
                                .addComponent(ConfirmPOScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        ConfirmPurchaseOrderLayout.setVerticalGroup(
            ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfirmPurchaseOrderLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ConfirmPOLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(AutoRefreshLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ConfirmPOScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 586, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ConfirmPurchaseOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InvalidPOButton)
                    .addComponent(ConfirmPOButton))
                .addGap(17, 17, 17))
        );

        FrameTab.addTab("Confirm Order", ConfirmPurchaseOrder);

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

        selectBankComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        selectBankComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectBankComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectBankComboBoxActionPerformed(evt);
            }
        });

        fromBanklabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        fromBanklabel.setText("From Bank :");

        DetailsLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 35)); // NOI18N
        DetailsLabel.setText("Details :");

        Detailstextfield.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N

        javax.swing.GroupLayout processPaymentPanelLayout = new javax.swing.GroupLayout(processPaymentPanel);
        processPaymentPanel.setLayout(processPaymentPanelLayout);
        processPaymentPanelLayout.setHorizontalGroup(
            processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(processPaymentPanelLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DetailsLabel)
                            .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bankAccNumberLabel)
                                    .addComponent(selectPOOrderLabel))
                                .addComponent(AmountLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(bankNamelabel, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(fromBanklabel))
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bankNameTextfield, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BankAccNumberTextfield, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectPOComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AmountTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectBankComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(Detailstextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(processPaymentPanelLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ProcessingPaymentStatusLabel)
                            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                                .addComponent(ProcessPaymentButton)
                                .addGap(161, 161, 161)
                                .addComponent(ClearFormButton, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        processPaymentPanelLayout.setVerticalGroup(
            processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processPaymentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectBankComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromBanklabel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGap(18, 18, 18)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DetailsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Detailstextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(ProcessingPaymentStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(processPaymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProcessPaymentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearFormButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

        TransactionIDLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        TransactionIDLabel.setText("Transaction ID :");

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

        TransactionIDComboBox.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        TransactionIDComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        TransactionIDComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionIDComboBoxActionPerformed(evt);
            }
        });

        InvoiceIDLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        InvoiceIDLabel.setText("Invoice ID :");

        VerifiedByLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        VerifiedByLabel.setText(" Verified By :");

        InvoiceIdTextField.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        InvoiceIdTextField.setText("jTextField1");

        ApprovedByTextField.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        ApprovedByTextField.setText("jTextField1");

        PRStatusLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        PRStatusLabel.setText("Status");

        PRMessageLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        PRMessageLabel.setText(" Message :");

        MessageTextField.setFont(new java.awt.Font("Comic Sans MS", 0, 30)); // NOI18N
        MessageTextField.setText("jTextField1");

        javax.swing.GroupLayout generateReportPanelLayout = new javax.swing.GroupLayout(generateReportPanel);
        generateReportPanel.setLayout(generateReportPanelLayout);
        generateReportPanelLayout.setHorizontalGroup(
            generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generateReportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generateReportPanelLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(generatePRScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(generateReportPanelLayout.createSequentialGroup()
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(MessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generateReportPanelLayout.createSequentialGroup()
                                    .addGap(259, 259, 259)
                                    .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(InvoiceIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ApprovedByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generateReportPanelLayout.createSequentialGroup()
                                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(InvoiceIDLabel)
                                    .addComponent(TransactionIDLabel)
                                    .addComponent(VerifiedByLabel)
                                    .addComponent(PRMessageLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TransactionIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generateReportPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(generatePRButton)
                                .addGap(111, 111, 111))
                            .addGroup(generateReportPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(PRStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        generateReportPanelLayout.setVerticalGroup(
            generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generateReportPanelLayout.createSequentialGroup()
                .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generateReportPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TransactionIDLabel)
                            .addComponent(TransactionIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InvoiceIDLabel)
                            .addComponent(InvoiceIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PRStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ApprovedByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VerifiedByLabel))
                        .addGap(18, 18, 18)
                        .addGroup(generateReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PRMessageLabel))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generateReportPanelLayout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(generatePRButton)
                        .addGap(44, 44, 44)))
                .addComponent(generatePRScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        FrameTab.addTab("Purchase Report ", generateReportPanel);

        ViewPurchaseReportPanel.setBackground(new java.awt.Color(255, 245, 247));

        viewPurchaseReportTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewReportScrollpane.setViewportView(viewPurchaseReportTable);

        ViewPurchaseReportButton.setFont(new java.awt.Font("Comic Sans MS", 0, 40)); // NOI18N
        ViewPurchaseReportButton.setText("Refresh");
        ViewPurchaseReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewPurchaseReportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ViewPurchaseReportPanelLayout = new javax.swing.GroupLayout(ViewPurchaseReportPanel);
        ViewPurchaseReportPanel.setLayout(ViewPurchaseReportPanelLayout);
        ViewPurchaseReportPanelLayout.setHorizontalGroup(
            ViewPurchaseReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewPurchaseReportPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(ViewPurchaseReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ViewPurchaseReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewReportScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        ViewPurchaseReportPanelLayout.setVerticalGroup(
            ViewPurchaseReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViewPurchaseReportPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(ViewPurchaseReportButton)
                .addGap(18, 18, 18)
                .addComponent(viewReportScrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FrameTab.addTab("View Report", ViewPurchaseReportPanel);

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
        String txID = (String)TransactionIDComboBox.getSelectedItem();
        String invoiceID = InvoiceIdTextField.getText().trim();
        String approvedBy = ApprovedByTextField.getText().trim();
        String message = MessageTextField.getText().trim();

        if (txID == null || invoiceID.isEmpty() || approvedBy.isEmpty()) {
            PRStatusLabel.setText("Please fill in all fields.");
            return;
        }
        
        Transaction tx = Transaction.toList().stream().filter(t -> t.getTransactionID().equals(txID)).findFirst().orElse(null);
        if (tx == null) {
            PRStatusLabel.setText("Invalid Transaction ID.");
            return;
        }
        
        String poid = null;
        Pattern pattern = Pattern.compile("POID=(PO\\d+)");
        Matcher matcher = pattern.matcher(tx.getDetails());
        if (matcher.find()) {
            poid = matcher.group(1);
        }
        
        if (poid == null || poid.length() < 5) {
            PRStatusLabel.setText("PO ID not found in transaction details.");
            return;
        }
        List<PurchaseReport> allReports = PurchaseReport.toList();
        int nextIndex = allReports.size();
        String newID = IdGenerator.generateID("PUR", 5, nextIndex);
        PurchaseReport report = new PurchaseReport();
        report.setReportID(newID);
        report.setPOID(poid);
        report.setInvoiceID(invoiceID);
        report.setTransactionID(txID);
        report.setDateTime(LocalDateTime.now());
        report.setVerifiedBy(approvedBy);
        report.setPaymentProcessBy("FM001");
        report.setMessage(message);

        report.add();
        
        PurchaseOrder po = com.owsb.poms.system.model.PurchaseOrder.getPoById(poid);
        if (po != null) {
            po.setStatus(com.owsb.poms.system.model.PurchaseOrder.Status.REPORTED);
            po.setPerformedBy("FM001");
            po.updateStatus();
        }
        TransactionIDComboBox.setSelectedIndex(-1);
        InvoiceIdTextField.setText("");
        ApprovedByTextField.setText("");
        MessageTextField.setText("");
        ((DefaultTableModel) generatePRtable.getModel()).setRowCount(0);

        populateTransactionIDComboBox();
        if(TransactionIDComboBox.getItemCount()==0){
            generatePRButton.setEnabled(false);
            PRStatusLabel.setText("All available transaction have been reported. ");
        }else{
        PRStatusLabel.setText("Report generated successfully for " + poid);
        }
        loadAllPurchaseReports();
    }//GEN-LAST:event_generatePRButtonActionPerformed

    private void BankAccNumberTextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BankAccNumberTextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BankAccNumberTextfieldActionPerformed

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
        String poid = (String) selectPOComboBox.getSelectedItem();
            if(poid!=null && !poid.trim().isEmpty()){
                PurchaseOrder po = com.owsb.poms.system.model.PurchaseOrder.getPoById(poid);
                if(po != null){
                    AmountTextfield.setText(String.valueOf(po.getTotalPrice()));
                    String supplierName = Supplier.getNameById(po.getSupplierID());
                    bankNameTextfield.setText(supplierName);
                    enablePaymentFields();
                }
            }else{
             clearProcessingPaymentForm();
             disablePaymentFields();
            }
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

    private void approvePOEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_approvePOEditButtonActionPerformed
        updatePOStatus("REJECTED");
    }//GEN-LAST:event_approvePOEditButtonActionPerformed

    private void InvalidPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvalidPOButtonActionPerformed
        int selectedRow = ConfirmPOTable.getSelectedRow();
    if (selectedRow == -1) {
        ConfirmPOLabel.setText("Please select a purchase order.");
        return;
    }

    String poid = confirmPOTableModel.getValueAt(selectedRow, 0).toString();
    updatePOStatusTo(poid, com.owsb.poms.system.model.PurchaseOrder.Status.INVALID);
    }//GEN-LAST:event_InvalidPOButtonActionPerformed

    private void ConfirmPOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmPOButtonActionPerformed
        int selectedRow = ConfirmPOTable.getSelectedRow();
    if (selectedRow == -1) {
        ConfirmPOLabel.setText("Please select a purchase order.");
        return;
    }  
    
    String poid = confirmPOTableModel.getValueAt(selectedRow, 0).toString();
    updatePOStatusTo(poid, com.owsb.poms.system.model.PurchaseOrder.Status.CONFIRMED);
    initProcessPaymentFunctionality();
    }//GEN-LAST:event_ConfirmPOButtonActionPerformed

    private void selectBankComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectBankComboBoxActionPerformed
       
    }//GEN-LAST:event_selectBankComboBoxActionPerformed

    private void TransactionIDComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionIDComboBoxActionPerformed
        String selectedID = (String) TransactionIDComboBox.getSelectedItem();
        if (selectedID == null)return;
        
        List<Transaction> transactions = Transaction.toList();
        DefaultTableModel model = (DefaultTableModel) generatePRtable.getModel();
        model.setRowCount(0);
        
        for (Transaction tx : transactions) {
            if (tx.getTransactionID().equals(selectedID)) {
                model.addRow(new Object[]{
                    tx.getTransactionID(),
                    tx.getBankFrom(),
                    tx.getBankFromAccountNumber(),
                    tx.getBankReceived(),
                    tx.getBankReceivedAccountNumber(),
                    tx.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    tx.getAmount(),
                    tx.getDetails()
                });
                generatePRButton.setEnabled(true);
                break;
            }
        }
    }//GEN-LAST:event_TransactionIDComboBoxActionPerformed

    private void ViewPurchaseReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPurchaseReportButtonActionPerformed
        loadAllPurchaseReports();
    }//GEN-LAST:event_ViewPurchaseReportButtonActionPerformed

    private void ChangePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangePasswordButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChangePasswordButtonActionPerformed

// public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FinanceManagerDashboard().setVisible(true);
//            }
//        });
//    }
    
    private final String currentUserId = "FM001";
    private void initUserProfile() {
        User user = User.getUserById(currentUserId);
    
    if (user != null) {
        UserIdLabel.setText(user.getUID());
        UsernameLabel.setText(user.getUsername());
        EmailLabel.setText(user.getEmail());
    } else {
        UserIdLabel.setText("Unknown");
        UsernameLabel.setText("-");
        EmailLabel.setText("-");
    }
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
                    po.getGeneratedDateTime().toString(),
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
    
   DefaultTableModel confirmPOTableModel;
   private void initConfirmPOFunctionality() {
       String[] columns = {"PO ID","PR ITEM","Total Price","Supplier ID","Generated Date", "Delivery Date", "Status", "Created By", "Approved By"};
       confirmPOTableModel = new DefaultTableModel(columns, 0) {
           public boolean isCellEditable(int row, int column) {
               return false;
           }
       };
       ConfirmPOTable.setModel(confirmPOTableModel);
       loadVerifiedPOs();
       new javax.swing.Timer(5000,e -> loadVerifiedPOs()).start();
   }
   
   private void loadVerifiedPOs() {
       confirmPOTableModel.setRowCount(0);
       List<PurchaseOrder> poList = com.owsb.poms.system.model.PurchaseOrder.toList();
       for (PurchaseOrder po : poList) {
           if (po.getStatus() == com.owsb.poms.system.model.PurchaseOrder.Status.VERIFIED) {
               confirmPOTableModel.addRow(new Object[]{
                   po.getPOID(),
                   po.getPRID(),
                   po.getTotalPrice(),
                   po.getSupplierID(),
                   po.getGeneratedDateTime().toString(),
                   po.getDeliveryDate().toString(),
                   po.getStatus().name(),
                   po.getCreateBy(),
                   po.getPerformedBy()
               });
           }
       }
   }
   
   private void updatePOStatusTo(String poid, PurchaseOrder.Status newStatus) {
       List<PurchaseOrder> poList = com.owsb.poms.system.model.PurchaseOrder.toList();
       for (PurchaseOrder po : poList) {
           if (po.getPOID().equals(poid)) {
               po.setStatus(newStatus);
               po.setPerformedBy("IM001"); 
               po.updateStatus();
               ConfirmPOLabel.setText(poid + " is " + newStatus.name().toLowerCase());
               break;
           }
       }
       loadVerifiedPOs();
   }
    
    Map<String, Object[]> approvedPOMap = new HashMap<>();
    ArrayList<Object[]> transactionList = new ArrayList<Object[]>();
    int transactionCounter = 1;
    
    DefaultTableModel transactionTableModel;
    
    private void initProcessPaymentFunctionality() {
        selectPOComboBox.removeAllItems();
        approvedPOMap.clear();
        
        List<PurchaseOrder> orders = com.owsb.poms.system.model.PurchaseOrder.toList();
        boolean hasConfirmPO = false;
         populateBankComboBox();
        
        for (PurchaseOrder po : orders) {
            if (po.getStatus() == com.owsb.poms.system.model.PurchaseOrder.Status.CONFIRMED && !isPOAlreadyPaid(po.getPOID())) {
                String label = po.getPOID();
                approvedPOMap.put(label, new Object[]{po.getPOID(),po.getSupplierID(),po.getTotalPrice()});
                selectPOComboBox.addItem(label);
                hasConfirmPO = true;
            }
        }
        
        if (!hasConfirmPO) {
            disablePaymentFields();
            selectBankComboBox.setEnabled(false);
            selectPOComboBox.setEnabled(false);
            ProcessingPaymentStatusLabel.setText("Status: No confirmed purchase orders available for payment.");
        } else {
            enablePaymentFields();
            selectPOComboBox.setEnabled(true);
            selectBankComboBox.setEnabled(true);
            ProcessingPaymentStatusLabel.setText("Status: Select a PO to proceed with payment.");
        }
    }
    
    private void populateBankComboBox() {
        selectBankComboBox.removeAllItems();
        List<Bank> banks = Bank.toList();
        for (Bank b : banks) {
            if (!b.isIsDeleted()) {
                selectBankComboBox.addItem(b.getBankName()); // 添加银行名称

            }
        }
    }
    
    private boolean isPOAlreadyPaid(String poID) {
            List <Transaction> transanctions = Transaction.toList();
            for (Transaction tx : transanctions) {
                if (tx.getDetails() != null && tx.getDetails().contains(poID)) {
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
            Detailstextfield.setText("");
            ProcessingPaymentStatusLabel.setText("Status: Cleared");
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
        String poID = (String) selectPOComboBox.getSelectedItem();
        String yourbankName = (String)selectBankComboBox.getSelectedItem();
        Bank yourbank = Bank.getBankByName(yourbankName);
        String supplierBank = bankNameTextfield.getText().trim();
        String supplierAccNumber = BankAccNumberTextfield.getText().trim();
        String amountStr = AmountTextfield.getText().trim();
        String userDetails = Detailstextfield.getText().trim();
        
        if (yourbank == null){
            ProcessingPaymentStatusLabel.setText("Status : Bank information not found.");
            return;
        }
        
        if (poID == null || yourbank == null || supplierBank.isEmpty() || supplierAccNumber.isEmpty() || amountStr.isEmpty()) {
            ProcessingPaymentStatusLabel.setText("Status: Please fill in all fields.");
            return;
        }
        
        long supplierAcc;
        double amount;
        try{
            supplierAcc = Long.parseLong(supplierAccNumber);
            amount = Double.parseDouble(amountStr);
        }catch(NumberFormatException e){
            ProcessingPaymentStatusLabel.setText("Status : Invalid Amount");
            return;
        }
        
        Transaction tx = new Transaction();
        tx.setTransactionID(tx.generateID());
        tx.setBankFrom(yourbank.getBankName());
        tx.setBankFromAccountNumber(yourbank.getAccountNumber());
        tx.setBankReceived(supplierBank);
        tx.setBankReceivedAccountNumber(supplierAcc);
        tx.setDateTime(LocalDateTime.now());
        tx.setAmount(amount);
        tx.setDetails("POID=" + poID + " | " + userDetails);
        tx.add();
        
        PurchaseOrder po = com.owsb.poms.system.model.PurchaseOrder.getPoById(poID);
        if(po != null){
            po.setStatus(com.owsb.poms.system.model.PurchaseOrder.Status.COMPLETED);
            po.setPerformedBy("FM001");
            po.updateStatus();
        }
        
        clearProcessingPaymentForm();
        refreshTransactionTable();
        initProcessPaymentFunctionality();
        populateTransactionIDComboBox();
        refreshPurchaseOrderTable(viewPOComboBox.getSelectedItem().toString());
        ProcessingPaymentStatusLabel.setText("Status : Payment Successful For "+ poID);
    }
    
    private void populateTransactionIDComboBox() {
        TransactionIDComboBox.removeAllItems();
        List<String> usedTransactions = PurchaseReport.toList().stream().map(PurchaseReport :: getTransactionID).collect(Collectors.toList());
        List<Transaction> transactions = Transaction.toList();
        
        for (Transaction tx : transactions) {
            if (tx.getDetails() != null && tx.getDetails().contains("POID=")&& !usedTransactions.contains(tx.getTransactionID())) {
                TransactionIDComboBox.addItem(tx.getTransactionID());
            }
        }
        
        if (TransactionIDComboBox.getItemCount() == 0) {
            generatePRButton.setEnabled(false);
            PRStatusLabel.setText("All transactions have been reported.");
        } else {
            TransactionIDComboBox.setEnabled(true);
            generatePRButton.setEnabled(true);
            PRStatusLabel.setText("Select a transactions to generate report.");
            TransactionIDComboBox.setSelectedIndex(-1);
        }
    }

    private void initTransactionTable(){
        String[] columns = {"Transaction ID","Bank From","From Acc","Bank to","To Account","Date Time","Amount","Details"};
        transactionTableModel = new DefaultTableModel(columns,0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        viewTransactionTable.setModel(transactionTableModel);
    }
    
    private void refreshTransactionTable() {
        transactionTableModel.setRowCount(0);
        List<Transaction> transactions = Transaction.toList();
        for (Transaction tx : transactions) {
            transactionTableModel.addRow(new Object[]{
                tx.getTransactionID(),
                tx.getBankFrom(),
                tx.getBankFromAccountNumber(),
                tx.getBankReceived(),
                tx.getBankReceivedAccountNumber(),
                tx.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                tx.getAmount(),
                tx.getDetails(),
            });
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
                    po.getGeneratedDateTime().toString(),
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
    
    private void initGenerateReportPanel() {
        TransactionIDComboBox.setSelectedIndex(-1);
        InvoiceIdTextField.setText("");
        ApprovedByTextField.setText("");
        MessageTextField.setText("");
        ((DefaultTableModel) generatePRtable.getModel()).setRowCount(0);
        
        
        TransactionIDComboBox.removeAllItems();
        List<Transaction> transactions = Transaction.toList();
        for (Transaction tx : transactions) {
            if (tx.getDetails() != null && tx.getDetails().contains("POID=")) {
            TransactionIDComboBox.addItem(tx.getTransactionID());
            }
        }
        generatePRButton.setEnabled(false);
        generatePRtable.setModel(new DefaultTableModel(
                new String[]{"Transaction ID", "Bank From", "From Acc", "Bank To", "To Acc", "Date Time", "Amount", "Details"}, 0
        ){
            public boolean isCellEditable(int row , int column){
                return false;
            }
        });
        PRStatusLabel.setText("Select a transaction to generate report.");
    }
    
    
    
    DefaultTableModel viewPurchaseReportTableModel;
    private void initViewPurchaseReportPanel() {
        String[] columns = {"Report ID", "PO ID", "Invoice ID", "Transaction ID", "Date", "Verified By", "Processed By", "Message"};
        viewPurchaseReportTableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    viewPurchaseReportTable.setModel(viewPurchaseReportTableModel);
    loadAllPurchaseReports();
    viewPurchaseReportTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && viewPurchaseReportTable.getSelectedRow() != -1) {
                String selectedReportID = viewPurchaseReportTable.getValueAt(viewPurchaseReportTable.getSelectedRow(), 0).toString();
                new ViewReportFrame(selectedReportID).setVisible(true);
            }
        }
    });
    }
    
    private void loadAllPurchaseReports() {
       viewPurchaseReportTableModel.setRowCount(0);
        List<PurchaseReport> reports = PurchaseReport.toList();
        
        for (PurchaseReport r : reports) {
            String msg = (r.getMessage() == null) ? "(No message)" : r.getMessage();
            viewPurchaseReportTableModel.addRow(new Object[]{
                r.getReportID(),
                r.getPOID(),
                r.getInvoiceID(),
                r.getTransactionID(),
                r.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                r.getVerifiedBy(),
                r.getPaymentProcessBy(),
                msg
            });
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AmountLabel;
    private javax.swing.JTextField AmountTextfield;
    private javax.swing.JTextField ApprovedByTextField;
    private javax.swing.JLabel AutoRefreshLabel;
    private javax.swing.JTextField BankAccNumberTextfield;
    private javax.swing.JButton ChangePasswordButton;
    private javax.swing.JButton ClearFormButton;
    private javax.swing.JButton ConfirmPOButton;
    private javax.swing.JLabel ConfirmPOLabel;
    private javax.swing.JScrollPane ConfirmPOScrollpane;
    private javax.swing.JTable ConfirmPOTable;
    private javax.swing.JPanel ConfirmPurchaseOrder;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JLabel DetailsLabel;
    private javax.swing.JTextField Detailstextfield;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTabbedPane FrameTab;
    private javax.swing.JButton InvalidPOButton;
    private javax.swing.JLabel InvoiceIDLabel;
    private javax.swing.JTextField InvoiceIdTextField;
    private javax.swing.JTextField MessageTextField;
    private javax.swing.JLabel PRMessageLabel;
    private javax.swing.JLabel PRStatusLabel;
    private javax.swing.JButton ProcessPaymentButton;
    private javax.swing.JLabel ProcessingPaymentStatusLabel;
    private javax.swing.JPanel PurchaseOrder;
    private javax.swing.JPanel PurchaseRequisitionPanel;
    private javax.swing.JPanel SupplierEntryPanel;
    private javax.swing.JComboBox<String> TransactionIDComboBox;
    private javax.swing.JLabel TransactionIDLabel;
    private javax.swing.JPanel TransactionsPanel;
    private javax.swing.JLabel UserIdLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JLabel VerifiedByLabel;
    private javax.swing.JButton ViewPRRefreshButton;
    private javax.swing.JButton ViewPurchaseReportButton;
    private javax.swing.JPanel ViewPurchaseReportPanel;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JButton approvePOButton;
    private javax.swing.JButton approvePOEditButton;
    private javax.swing.JPanel approvePOPanel;
    private javax.swing.JButton approvePORefreshButton;
    private javax.swing.JScrollPane approvePOScrollpane;
    private javax.swing.JLabel approvePOStatusLabel;
    private javax.swing.JTable approvePOTable;
    private javax.swing.JLabel bankAccNumberLabel;
    private javax.swing.JTextField bankNameTextfield;
    private javax.swing.JLabel bankNamelabel;
    private javax.swing.JLabel fromBanklabel;
    private javax.swing.JButton generatePRButton;
    private javax.swing.JScrollPane generatePRScrollpane;
    private javax.swing.JTable generatePRtable;
    private javax.swing.JPanel generateReportPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton logOutButton;
    private javax.swing.JPanel processPaymentPanel;
    private javax.swing.JComboBox<String> selectBankComboBox;
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
    private javax.swing.JTable viewPurchaseReportTable;
    private javax.swing.JScrollPane viewReportScrollpane;
    private javax.swing.JTable viewTransactionTable;
    private javax.swing.JButton viewTransactionsRefreshButton;
    private javax.swing.JScrollPane viewTransactionsScrollPanel;
    // End of variables declaration//GEN-END:variables

    
}
