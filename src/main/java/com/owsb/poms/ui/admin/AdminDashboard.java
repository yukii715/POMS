package com.owsb.poms.ui.admin;

import com.owsb.poms.ui.admin.Inventory.*;
import com.owsb.poms.system.model.*;
import com.owsb.poms.ui.common.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

public class AdminDashboard extends javax.swing.JFrame {
    private Point initialClick;
    private boolean maximise = false;
    private boolean summary = false;  
    private String currentTab = "Dashboard";
    
    //Inventory
    private int selectedItemRow;
    private Item selectedItem = new Item();
    private List<Item> itemList;
    private DefaultTableModel itemsModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Supplier", "Price", "Stock", "Status"};
    
    //Suppliers
    private int selectedSupplierRow;
    private Supplier selectedSupplier = new Supplier();
    private List<Supplier> suppliersList;
    private DateTimeFormatter supplierAddedTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DefaultTableModel suppliersModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] suppliersColumnName = {"ID", "Name", "Added Time"};

    public AdminDashboard(){
        initComponents();
        this.setLocationRelativeTo(null);
        
        CommonMethod.setRoundedLabelIcon("data/Users/admin/profile picture/11.jpg", lblProfilePicture, 60);
        CommonMethod.setRoundedLabelIcon("data/Users/admin/profile picture/11.jpg", lbltest, 300);

        new CommonMethod().setFrameIcon("/icons/logo.png", 330, 330, Image.SCALE_SMOOTH, this);
        new CommonMethod().setLabelIcon("/icons/logo.png", 90, 90, Image.SCALE_SMOOTH, lblLogo);
        new CommonMethod().setLabelIcon("/icons/minimise.png", 15, 15, Image.SCALE_SMOOTH, lblMinimise);
        new CommonMethod().setLabelIcon("/icons/maximise.png", 15, 15, Image.SCALE_SMOOTH, lblMaximise);
        new CommonMethod().setLabelIcon("/icons/close.png", 15, 15, Image.SCALE_SMOOTH, lblClose);
        new CommonMethod().setLabelIcon("/icons/summary.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
        new CommonMethod().setLabelIcon("/icons/logout.png", 23, 23, Image.SCALE_SMOOTH, lblLogout1);
        
        
        new CommonMethod().setLabelIcon("/icons/dashboard.png", 30, 30, Image.SCALE_SMOOTH, lblDashboardIcon);
        new CommonMethod().setLabelIcon("/icons/users.png", 30, 30, Image.SCALE_SMOOTH, lblUsersIcon);
        new CommonMethod().setLabelIcon("/icons/sales.png", 30, 30, Image.SCALE_SMOOTH, lblSalesIcon);
        new CommonMethod().setLabelIcon("/icons/orders.png", 30, 30, Image.SCALE_SMOOTH, lblOrdersIcon);
        new CommonMethod().setLabelIcon("/icons/inventory.png", 30, 30, Image.SCALE_SMOOTH, lblInventoryIcon);
        new CommonMethod().setLabelIcon("/icons/suppliers.png", 30, 30, Image.SCALE_SMOOTH, lblSuppliersIcon);
        
        SwingUtilities.invokeLater(() -> {
            BasicSplitPaneUI ui = (BasicSplitPaneUI) mainSplitPane.getUI();
            BasicSplitPaneDivider divider = ui.getDivider();
            divider.setBorder(BorderFactory.createLineBorder(new java.awt.Color(243, 220, 220), 5));
            divider.setCursor(new Cursor(Cursor.HAND_CURSOR));
            divider.repaint();
        });
        
        Inventory();
        Suppliers();
    }
    
    private void tabEntered(JPanel tab, JPanel tabIndicator){
        if (!currentTab.equals(tab.getName())){
            tab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            tab.setBackground(new java.awt.Color(255, 153, 153));
            tabIndicator.setBackground(new java.awt.Color(255, 153, 153));
        }
    }
    
    private void tabExited(JPanel tab, JPanel tabIndicator){
        if (!currentTab.equals(tab.getName())){
            tab.setBackground(new java.awt.Color(255, 180, 180));
            tabIndicator.setBackground(new java.awt.Color(255, 180, 180));
        }
        tab.setCursor(Cursor.getDefaultCursor());
    }
    
    private void changeTab(JPanel tab, JPanel tabIndicator){
        if (!currentTab.equals(tab.getName())){
            for (Component comp : pnlTabs.getComponents()){
                if (currentTab.equals(comp.getName())){
                    comp.setBackground(new java.awt.Color(255, 180, 180));
                    comp.getComponentAt(0, 0).setBackground(new java.awt.Color(255, 180, 180));
                }
            }
            tab.setBackground(new java.awt.Color(255, 122, 122));
            tabIndicator.setBackground(new java.awt.Color(255, 53, 89));
            currentTab = tab.getName();
            CardLayout card = (CardLayout) pnlMainContent.getLayout();
            card.show(pnlMainContent, currentTab);
        }
    }
    
    //Inventory tab
    private void Inventory(){
        selectedItemRow = -1;
        itemsModel.setRowCount(0);
        
        lblItemID.setText("None");
        lblItemCategory.setText("None");
        lblItemType.setText("None");
        lblItemName.setText("None");
        lblSupplier.setText("None");
        lblItemPrice.setText("None");
        lblItemStock.setText("None");
        lblItemStatus.setText("None");
        
        itemsModel.setColumnIdentifiers(itemsColumnName);
        JTableHeader header = tblItems.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlItems.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblItems.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblItems.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblItems.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblItems.getColumnModel().getColumn(3).setPreferredWidth(220);
        tblItems.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblItems.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        itemList = Item.toList();
        
        for (Item item : itemList) {
            if (item.getStatus() != Item.Status.REMOVED)
            {
                itemsModel.addRow(new String[]{
                    item.getItemID(),
                    item.getItemCategory(),
                    item.getItemType(),
                    item.getItemName(),
                    item.getSupplierID(),
                    String.format("%.2f", item.getSellPrice()),
                    String.valueOf(item.getStock()),
                    item.getStatus().name()
                });
            }
        }
        
        // Create a single “center” renderer:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblItems.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    private void Suppliers(){
        selectedSupplierRow = -1;
        suppliersModel.setRowCount(0);
        
        lblSupplierID.setText("None");
        lblSupplierName.setText("None");
        lblSupplierAddedTime.setText("None");
        
        suppliersModel.setColumnIdentifiers(suppliersColumnName);
        JTableHeader header = tblSuppliers.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlSuppliers.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblSuppliers.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblSuppliers.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        suppliersList = Supplier.toList();
        
        for (Supplier supplier : suppliersList) {
            if (!supplier.isDeleted())
            {
                suppliersModel.addRow(new String[]{
                    supplier.getSupplierID(),
                    supplier.getSupplierName(),
                    supplier.getAddedTime().format(supplierAddedTimeFormatter)
                });
            }
        }
        
        // Create a single “center” renderer:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblSuppliers.setDefaultRenderer(Object.class, centerRenderer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNorthMargin = new javax.swing.JPanel();
        pnlNorthWestMargin = new javax.swing.JPanel();
        pnlNorthEastMargin = new javax.swing.JPanel();
        pnlSouthMargin = new javax.swing.JPanel();
        pnlSouthWestMargin = new javax.swing.JPanel();
        pnlSouthEastMargin = new javax.swing.JPanel();
        pnlSouthMargin3 = new javax.swing.JPanel();
        pnlWestMargin = new javax.swing.JPanel();
        pnlEastMargin = new javax.swing.JPanel();
        mainSplitPane = new javax.swing.JSplitPane();
        pnlNavigator = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlTabs = new javax.swing.JPanel();
        pnlDashboardTab = new javax.swing.JPanel();
        pnlDashboardIndicator = new javax.swing.JPanel();
        lblDashboardDivider1 = new javax.swing.JLabel();
        lblDashboardIcon = new javax.swing.JLabel();
        lblDashboardDivider2 = new javax.swing.JLabel();
        lblDashboard = new javax.swing.JLabel();
        pnlUsersTab = new javax.swing.JPanel();
        pnlUsersIndicator = new javax.swing.JPanel();
        lblUsersDivider1 = new javax.swing.JLabel();
        lblUsersIcon = new javax.swing.JLabel();
        lblUsersDivider2 = new javax.swing.JLabel();
        lblUsers = new javax.swing.JLabel();
        pnlSalesTab = new javax.swing.JPanel();
        pnlSalesIndicator = new javax.swing.JPanel();
        lblSalesDivider1 = new javax.swing.JLabel();
        lblSalesIcon = new javax.swing.JLabel();
        lblSalesDivider2 = new javax.swing.JLabel();
        lblSales = new javax.swing.JLabel();
        pnlOrdersTab = new javax.swing.JPanel();
        pnlOrdersIndicator = new javax.swing.JPanel();
        lblOrdersDivider1 = new javax.swing.JLabel();
        lblOrdersIcon = new javax.swing.JLabel();
        lblOrdersDivider2 = new javax.swing.JLabel();
        lblOrders = new javax.swing.JLabel();
        pnlInventoryTab = new javax.swing.JPanel();
        pnlInventoryIndicator = new javax.swing.JPanel();
        lblInventoryDivider1 = new javax.swing.JLabel();
        lblInventoryIcon = new javax.swing.JLabel();
        lblInventoryDivider2 = new javax.swing.JLabel();
        lblInventory = new javax.swing.JLabel();
        pnlSuppliersTab = new javax.swing.JPanel();
        pnlSuppliersIndicator = new javax.swing.JPanel();
        lblSuppliersDivider1 = new javax.swing.JLabel();
        lblSuppliersIcon = new javax.swing.JLabel();
        lblSuppliersDivider2 = new javax.swing.JLabel();
        lblSuppliers = new javax.swing.JLabel();
        pnlProfile = new javax.swing.JPanel();
        lblProfilePicture = new javax.swing.JLabel();
        lblUserDetails = new javax.swing.JPanel();
        lblUsername = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblLogout1 = new javax.swing.JLabel();
        lblProfileDivider = new javax.swing.JPanel();
        pnlContainer = new javax.swing.JPanel();
        pnlTools = new javax.swing.JPanel();
        pnlWindowControls = new javax.swing.JPanel();
        lblMinimise = new javax.swing.JLabel();
        lblMaximise = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        lblSummary = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlMainContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        lbltest = new javax.swing.JLabel();
        pnlUsers = new javax.swing.JPanel();
        pnlSales = new javax.swing.JPanel();
        pnlOrders = new javax.swing.JPanel();
        srlOrder = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        pnlInventory = new javax.swing.JPanel();
        srlItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblItemID = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblItemCategory = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblItemType = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblItemPrice = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblItemStock = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblItemStatus = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblItemName = new javax.swing.JLabel();
        btnNewItem = new javax.swing.JButton();
        btnRemoveItem = new javax.swing.JButton();
        btnEditItem = new javax.swing.JButton();
        btnUpdateStock = new javax.swing.JButton();
        btnUpdateStatus = new javax.swing.JButton();
        lblSupplier = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pnlSuppliers = new javax.swing.JPanel();
        srlSuppliers = new javax.swing.JScrollPane();
        tblSuppliers = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lblSupplierID = new javax.swing.JLabel();
        lblSupplierName = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblSupplierAddedTime = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnNewSupplier = new javax.swing.JButton();
        btnRemoveSupplier = new javax.swing.JButton();
        btnChangeSupplierName = new javax.swing.JButton();
        pnlSummary = new javax.swing.JPanel();
        pnlSummaryDivider = new javax.swing.JPanel();
        pnlSummaryMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard");
        setLocation(new java.awt.Point(0, 0));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1200, 800));

        pnlNorthMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlNorthMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlNorthMargin.setRequestFocusEnabled(false);
        pnlNorthMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlNorthMarginMouseDragged(evt);
            }
        });
        pnlNorthMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlNorthMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlNorthMarginMousePressed(evt);
            }
        });
        pnlNorthMargin.setLayout(new java.awt.BorderLayout());

        pnlNorthWestMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlNorthWestMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlNorthWestMargin.setRequestFocusEnabled(false);
        pnlNorthWestMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlNorthWestMarginMouseDragged(evt);
            }
        });
        pnlNorthWestMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlNorthWestMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlNorthWestMarginMousePressed(evt);
            }
        });
        pnlNorthWestMargin.setLayout(new java.awt.BorderLayout());
        pnlNorthMargin.add(pnlNorthWestMargin, java.awt.BorderLayout.WEST);

        pnlNorthEastMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlNorthEastMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlNorthEastMargin.setRequestFocusEnabled(false);
        pnlNorthEastMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlNorthEastMarginMouseDragged(evt);
            }
        });
        pnlNorthEastMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlNorthEastMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlNorthEastMarginMousePressed(evt);
            }
        });
        pnlNorthEastMargin.setLayout(new java.awt.BorderLayout());
        pnlNorthMargin.add(pnlNorthEastMargin, java.awt.BorderLayout.EAST);

        getContentPane().add(pnlNorthMargin, java.awt.BorderLayout.NORTH);

        pnlSouthMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlSouthMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlSouthMargin.setRequestFocusEnabled(false);
        pnlSouthMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlSouthMarginMouseDragged(evt);
            }
        });
        pnlSouthMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSouthMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSouthMarginMousePressed(evt);
            }
        });
        pnlSouthMargin.setLayout(new java.awt.BorderLayout());

        pnlSouthWestMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlSouthWestMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlSouthWestMargin.setRequestFocusEnabled(false);
        pnlSouthWestMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlSouthWestMarginMouseDragged(evt);
            }
        });
        pnlSouthWestMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSouthWestMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSouthWestMarginMousePressed(evt);
            }
        });
        pnlSouthWestMargin.setLayout(new java.awt.BorderLayout());
        pnlSouthMargin.add(pnlSouthWestMargin, java.awt.BorderLayout.WEST);

        pnlSouthEastMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlSouthEastMargin.setRequestFocusEnabled(false);
        pnlSouthEastMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlSouthEastMarginMouseDragged(evt);
            }
        });
        pnlSouthEastMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSouthEastMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSouthEastMarginMousePressed(evt);
            }
        });
        pnlSouthEastMargin.setLayout(new java.awt.BorderLayout());

        pnlSouthMargin3.setBackground(new java.awt.Color(243, 220, 220));
        pnlSouthMargin3.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlSouthMargin3.setRequestFocusEnabled(false);
        pnlSouthMargin3.setLayout(new java.awt.BorderLayout());
        pnlSouthEastMargin.add(pnlSouthMargin3, java.awt.BorderLayout.SOUTH);

        pnlSouthMargin.add(pnlSouthEastMargin, java.awt.BorderLayout.EAST);

        getContentPane().add(pnlSouthMargin, java.awt.BorderLayout.SOUTH);

        pnlWestMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlWestMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlWestMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlWestMarginMouseDragged(evt);
            }
        });
        pnlWestMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlWestMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlWestMarginMousePressed(evt);
            }
        });
        getContentPane().add(pnlWestMargin, java.awt.BorderLayout.WEST);

        pnlEastMargin.setBackground(new java.awt.Color(243, 220, 220));
        pnlEastMargin.setPreferredSize(new java.awt.Dimension(5, 5));
        pnlEastMargin.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlEastMarginMouseDragged(evt);
            }
        });
        pnlEastMargin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlEastMarginMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlEastMarginMousePressed(evt);
            }
        });
        getContentPane().add(pnlEastMargin, java.awt.BorderLayout.EAST);

        mainSplitPane.setBackground(new java.awt.Color(255, 237, 237));
        mainSplitPane.setBorder(null);
        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setDividerSize(5);
        mainSplitPane.setForeground(new java.awt.Color(255, 204, 204));
        mainSplitPane.setToolTipText("");
        mainSplitPane.setContinuousLayout(true);
        mainSplitPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainSplitPane.setPreferredSize(new java.awt.Dimension(1200, 800));

        pnlNavigator.setBackground(new java.awt.Color(255, 180, 180));
        pnlNavigator.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlNavigator.setMaximumSize(new java.awt.Dimension(200, 32767));
        pnlNavigator.setMinimumSize(new java.awt.Dimension(50, 0));

        pnlLogo.setBackground(new java.awt.Color(255, 180, 180));
        pnlLogo.setLayout(new java.awt.BorderLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setPreferredSize(new java.awt.Dimension(90, 90));
        pnlLogo.add(lblLogo, java.awt.BorderLayout.CENTER);

        pnlTabs.setLayout(new java.awt.GridLayout(6, 0));

        pnlDashboardTab.setBackground(new java.awt.Color(255, 122, 122));
        pnlDashboardTab.setName("Dashboard"); // NOI18N
        pnlDashboardTab.setPreferredSize(new java.awt.Dimension(200, 50));
        pnlDashboardTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlDashboardTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDashboardTabMouseExited(evt);
            }
        });
        pnlDashboardTab.setLayout(new javax.swing.BoxLayout(pnlDashboardTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlDashboardIndicator.setBackground(new java.awt.Color(255, 53, 89));
        pnlDashboardIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlDashboardIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlDashboardIndicatorLayout = new javax.swing.GroupLayout(pnlDashboardIndicator);
        pnlDashboardIndicator.setLayout(pnlDashboardIndicatorLayout);
        pnlDashboardIndicatorLayout.setHorizontalGroup(
            pnlDashboardIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlDashboardIndicatorLayout.setVerticalGroup(
            pnlDashboardIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlDashboardTab.add(pnlDashboardIndicator);

        lblDashboardDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblDashboardDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlDashboardTab.add(lblDashboardDivider1);

        lblDashboardIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlDashboardTab.add(lblDashboardIcon);

        lblDashboardDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblDashboardDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlDashboardTab.add(lblDashboardDivider2);

        lblDashboard.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboard.setText("Dashboard");
        lblDashboard.setPreferredSize(new java.awt.Dimension(400, 0));
        lblDashboard.setVerifyInputWhenFocusTarget(false);
        pnlDashboardTab.add(lblDashboard);

        pnlTabs.add(pnlDashboardTab);

        pnlUsersTab.setBackground(new java.awt.Color(255, 180, 180));
        pnlUsersTab.setName("Users"); // NOI18N
        pnlUsersTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlUsersTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlUsersTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlUsersTabMouseExited(evt);
            }
        });
        pnlUsersTab.setLayout(new javax.swing.BoxLayout(pnlUsersTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlUsersIndicator.setBackground(new java.awt.Color(255, 180, 180));
        pnlUsersIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlUsersIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlUsersIndicatorLayout = new javax.swing.GroupLayout(pnlUsersIndicator);
        pnlUsersIndicator.setLayout(pnlUsersIndicatorLayout);
        pnlUsersIndicatorLayout.setHorizontalGroup(
            pnlUsersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlUsersIndicatorLayout.setVerticalGroup(
            pnlUsersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlUsersTab.add(pnlUsersIndicator);

        lblUsersDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblUsersDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlUsersTab.add(lblUsersDivider1);

        lblUsersIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlUsersTab.add(lblUsersIcon);

        lblUsersDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblUsersDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlUsersTab.add(lblUsersDivider2);

        lblUsers.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblUsers.setForeground(new java.awt.Color(255, 255, 255));
        lblUsers.setText("Users");
        lblUsers.setPreferredSize(new java.awt.Dimension(400, 0));
        pnlUsersTab.add(lblUsers);

        pnlTabs.add(pnlUsersTab);

        pnlSalesTab.setBackground(new java.awt.Color(255, 180, 180));
        pnlSalesTab.setName("Sales"); // NOI18N
        pnlSalesTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSalesTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSalesTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSalesTabMouseExited(evt);
            }
        });
        pnlSalesTab.setLayout(new javax.swing.BoxLayout(pnlSalesTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlSalesIndicator.setBackground(new java.awt.Color(255, 180, 180));
        pnlSalesIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlSalesIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlSalesIndicatorLayout = new javax.swing.GroupLayout(pnlSalesIndicator);
        pnlSalesIndicator.setLayout(pnlSalesIndicatorLayout);
        pnlSalesIndicatorLayout.setHorizontalGroup(
            pnlSalesIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlSalesIndicatorLayout.setVerticalGroup(
            pnlSalesIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlSalesTab.add(pnlSalesIndicator);

        lblSalesDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblSalesDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlSalesTab.add(lblSalesDivider1);

        lblSalesIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlSalesTab.add(lblSalesIcon);

        lblSalesDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblSalesDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlSalesTab.add(lblSalesDivider2);

        lblSales.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblSales.setForeground(new java.awt.Color(255, 255, 255));
        lblSales.setText("Sales");
        lblSales.setPreferredSize(new java.awt.Dimension(400, 0));
        pnlSalesTab.add(lblSales);

        pnlTabs.add(pnlSalesTab);

        pnlOrdersTab.setBackground(new java.awt.Color(255, 180, 180));
        pnlOrdersTab.setName("Orders"); // NOI18N
        pnlOrdersTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlOrdersTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlOrdersTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlOrdersTabMouseExited(evt);
            }
        });
        pnlOrdersTab.setLayout(new javax.swing.BoxLayout(pnlOrdersTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlOrdersIndicator.setBackground(new java.awt.Color(255, 180, 180));
        pnlOrdersIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlOrdersIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlOrdersIndicatorLayout = new javax.swing.GroupLayout(pnlOrdersIndicator);
        pnlOrdersIndicator.setLayout(pnlOrdersIndicatorLayout);
        pnlOrdersIndicatorLayout.setHorizontalGroup(
            pnlOrdersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlOrdersIndicatorLayout.setVerticalGroup(
            pnlOrdersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlOrdersTab.add(pnlOrdersIndicator);

        lblOrdersDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblOrdersDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlOrdersTab.add(lblOrdersDivider1);

        lblOrdersIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlOrdersTab.add(lblOrdersIcon);

        lblOrdersDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblOrdersDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlOrdersTab.add(lblOrdersDivider2);

        lblOrders.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblOrders.setForeground(new java.awt.Color(255, 255, 255));
        lblOrders.setText("Orders");
        lblOrders.setPreferredSize(new java.awt.Dimension(400, 0));
        pnlOrdersTab.add(lblOrders);

        pnlTabs.add(pnlOrdersTab);

        pnlInventoryTab.setBackground(new java.awt.Color(255, 180, 180));
        pnlInventoryTab.setName("Inventory"); // NOI18N
        pnlInventoryTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlInventoryTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlInventoryTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlInventoryTabMouseExited(evt);
            }
        });
        pnlInventoryTab.setLayout(new javax.swing.BoxLayout(pnlInventoryTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlInventoryIndicator.setBackground(new java.awt.Color(255, 180, 180));
        pnlInventoryIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlInventoryIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlInventoryIndicatorLayout = new javax.swing.GroupLayout(pnlInventoryIndicator);
        pnlInventoryIndicator.setLayout(pnlInventoryIndicatorLayout);
        pnlInventoryIndicatorLayout.setHorizontalGroup(
            pnlInventoryIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlInventoryIndicatorLayout.setVerticalGroup(
            pnlInventoryIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlInventoryTab.add(pnlInventoryIndicator);

        lblInventoryDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblInventoryDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlInventoryTab.add(lblInventoryDivider1);

        lblInventoryIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlInventoryTab.add(lblInventoryIcon);

        lblInventoryDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblInventoryDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlInventoryTab.add(lblInventoryDivider2);

        lblInventory.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblInventory.setForeground(new java.awt.Color(255, 255, 255));
        lblInventory.setText("Inventory");
        lblInventory.setPreferredSize(new java.awt.Dimension(400, 0));
        pnlInventoryTab.add(lblInventory);

        pnlTabs.add(pnlInventoryTab);

        pnlSuppliersTab.setBackground(new java.awt.Color(255, 180, 180));
        pnlSuppliersTab.setName("Suppliers"); // NOI18N
        pnlSuppliersTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSuppliersTabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSuppliersTabMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSuppliersTabMouseExited(evt);
            }
        });
        pnlSuppliersTab.setLayout(new javax.swing.BoxLayout(pnlSuppliersTab, javax.swing.BoxLayout.LINE_AXIS));

        pnlSuppliersIndicator.setBackground(new java.awt.Color(255, 180, 180));
        pnlSuppliersIndicator.setMinimumSize(new java.awt.Dimension(5, 0));
        pnlSuppliersIndicator.setPreferredSize(new java.awt.Dimension(5, 50));

        javax.swing.GroupLayout pnlSuppliersIndicatorLayout = new javax.swing.GroupLayout(pnlSuppliersIndicator);
        pnlSuppliersIndicator.setLayout(pnlSuppliersIndicatorLayout);
        pnlSuppliersIndicatorLayout.setHorizontalGroup(
            pnlSuppliersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlSuppliersIndicatorLayout.setVerticalGroup(
            pnlSuppliersIndicatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        pnlSuppliersTab.add(pnlSuppliersIndicator);

        lblSuppliersDivider1.setMinimumSize(new java.awt.Dimension(10, 0));
        lblSuppliersDivider1.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlSuppliersTab.add(lblSuppliersDivider1);

        lblSuppliersIcon.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlSuppliersTab.add(lblSuppliersIcon);

        lblSuppliersDivider2.setMinimumSize(new java.awt.Dimension(10, 0));
        lblSuppliersDivider2.setPreferredSize(new java.awt.Dimension(10, 0));
        pnlSuppliersTab.add(lblSuppliersDivider2);

        lblSuppliers.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        lblSuppliers.setForeground(new java.awt.Color(255, 255, 255));
        lblSuppliers.setText("Suppliers");
        lblSuppliers.setPreferredSize(new java.awt.Dimension(400, 0));
        pnlSuppliersTab.add(lblSuppliers);

        pnlTabs.add(pnlSuppliersTab);

        pnlProfile.setBackground(new java.awt.Color(255, 180, 180));
        pnlProfile.setPreferredSize(new java.awt.Dimension(0, 70));
        pnlProfile.setLayout(new java.awt.BorderLayout());

        lblProfilePicture.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfilePicture.setMaximumSize(new java.awt.Dimension(60, 0));
        lblProfilePicture.setMinimumSize(new java.awt.Dimension(60, 0));
        lblProfilePicture.setPreferredSize(new java.awt.Dimension(60, 0));
        pnlProfile.add(lblProfilePicture, java.awt.BorderLayout.WEST);

        lblUserDetails.setBackground(new java.awt.Color(255, 180, 180));
        lblUserDetails.setLayout(new java.awt.GridLayout(2, 0));
        lblUserDetails.add(lblUsername);
        lblUserDetails.add(lblEmail);

        pnlProfile.add(lblUserDetails, java.awt.BorderLayout.CENTER);

        lblLogout1.setMaximumSize(new java.awt.Dimension(60, 0));
        lblLogout1.setMinimumSize(new java.awt.Dimension(30, 0));
        lblLogout1.setPreferredSize(new java.awt.Dimension(30, 0));
        pnlProfile.add(lblLogout1, java.awt.BorderLayout.EAST);

        lblProfileDivider.setBackground(new java.awt.Color(243, 220, 220));
        lblProfileDivider.setPreferredSize(new java.awt.Dimension(94, 3));

        javax.swing.GroupLayout lblProfileDividerLayout = new javax.swing.GroupLayout(lblProfileDivider);
        lblProfileDivider.setLayout(lblProfileDividerLayout);
        lblProfileDividerLayout.setHorizontalGroup(
            lblProfileDividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        lblProfileDividerLayout.setVerticalGroup(
            lblProfileDividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        pnlProfile.add(lblProfileDivider, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout pnlNavigatorLayout = new javax.swing.GroupLayout(pnlNavigator);
        pnlNavigator.setLayout(pnlNavigatorLayout);
        pnlNavigatorLayout.setHorizontalGroup(
            pnlNavigatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(pnlProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlNavigatorLayout.setVerticalGroup(
            pnlNavigatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavigatorLayout.createSequentialGroup()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlTabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
                .addComponent(pnlProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainSplitPane.setLeftComponent(pnlNavigator);

        pnlContainer.setBackground(new java.awt.Color(243, 210, 210));
        pnlContainer.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlContainer.setMinimumSize(new java.awt.Dimension(800, 0));
        pnlContainer.setPreferredSize(new java.awt.Dimension(100, 798));
        pnlContainer.setLayout(new java.awt.BorderLayout());

        pnlTools.setBackground(new java.awt.Color(243, 220, 220));
        pnlTools.setPreferredSize(new java.awt.Dimension(0, 100));
        pnlTools.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlToolsMouseDragged(evt);
            }
        });
        pnlTools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlToolsMousePressed(evt);
            }
        });

        pnlWindowControls.setBackground(new java.awt.Color(243, 220, 220));
        pnlWindowControls.setPreferredSize(new java.awt.Dimension(90, 20));
        pnlWindowControls.setLayout(new java.awt.GridLayout(1, 0));

        lblMinimise.setBackground(new java.awt.Color(243, 220, 220));
        lblMinimise.setFont(new java.awt.Font("Franklin Gothic Heavy", 1, 18)); // NOI18N
        lblMinimise.setForeground(new java.awt.Color(0, 0, 0));
        lblMinimise.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimise.setToolTipText("Minimise");
        lblMinimise.setOpaque(true);
        lblMinimise.setPreferredSize(new java.awt.Dimension(12, 21));
        lblMinimise.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimiseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMinimiseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMinimiseMouseExited(evt);
            }
        });
        pnlWindowControls.add(lblMinimise);

        lblMaximise.setBackground(new java.awt.Color(243, 220, 220));
        lblMaximise.setFont(new java.awt.Font("Franklin Gothic Heavy", 1, 18)); // NOI18N
        lblMaximise.setForeground(new java.awt.Color(0, 0, 0));
        lblMaximise.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaximise.setToolTipText("Maximise");
        lblMaximise.setOpaque(true);
        lblMaximise.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMaximiseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMaximiseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMaximiseMouseExited(evt);
            }
        });
        pnlWindowControls.add(lblMaximise);

        lblClose.setBackground(new java.awt.Color(243, 220, 220));
        lblClose.setFont(new java.awt.Font("Franklin Gothic Heavy", 1, 18)); // NOI18N
        lblClose.setForeground(new java.awt.Color(0, 0, 0));
        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setToolTipText("Close");
        lblClose.setOpaque(true);
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCloseMouseExited(evt);
            }
        });
        pnlWindowControls.add(lblClose);

        lblSummary.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSummary.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSummaryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSummaryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSummaryMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlToolsLayout = new javax.swing.GroupLayout(pnlTools);
        pnlTools.setLayout(pnlToolsLayout);
        pnlToolsLayout.setHorizontalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlWindowControls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSummary, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlToolsLayout.setVerticalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addComponent(pnlWindowControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(lblSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContainer.add(pnlTools, java.awt.BorderLayout.NORTH);

        pnlContent.setBackground(new java.awt.Color(243, 210, 210));
        pnlContent.setLayout(new java.awt.BorderLayout());

        pnlMainContent.setBackground(new java.awt.Color(255, 204, 204));
        pnlMainContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(153, 255, 153));

        lbltest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(lbltest, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(lbltest, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        pnlMainContent.add(pnlDashboard, "Dashboard");

        pnlUsers.setBackground(new java.awt.Color(102, 255, 204));

        javax.swing.GroupLayout pnlUsersLayout = new javax.swing.GroupLayout(pnlUsers);
        pnlUsers.setLayout(pnlUsersLayout);
        pnlUsersLayout.setHorizontalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
        );
        pnlUsersLayout.setVerticalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        pnlMainContent.add(pnlUsers, "Users");

        pnlSales.setBackground(new java.awt.Color(204, 255, 51));
        pnlSales.setName(""); // NOI18N

        javax.swing.GroupLayout pnlSalesLayout = new javax.swing.GroupLayout(pnlSales);
        pnlSales.setLayout(pnlSalesLayout);
        pnlSalesLayout.setHorizontalGroup(
            pnlSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
        );
        pnlSalesLayout.setVerticalGroup(
            pnlSalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );

        pnlMainContent.add(pnlSales, "Sales");

        pnlOrders.setBackground(new java.awt.Color(244, 219, 162));
        pnlOrders.setName(""); // NOI18N

        tblOrder.setBackground(new java.awt.Color(255, 255, 204));
        tblOrder.setForeground(new java.awt.Color(0, 0, 0));
        tblOrder.setModel(itemsModel);
        tblOrder.setGridColor(java.awt.Color.gray);
        tblOrder.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblOrder.setShowGrid(true);
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblOrderMouseReleased(evt);
            }
        });
        srlOrder.setViewportView(tblOrder);

        javax.swing.GroupLayout pnlOrdersLayout = new javax.swing.GroupLayout(pnlOrders);
        pnlOrders.setLayout(pnlOrdersLayout);
        pnlOrdersLayout.setHorizontalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(246, Short.MAX_VALUE))
        );
        pnlOrdersLayout.setVerticalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlOrder)
                .addContainerGap())
        );

        pnlMainContent.add(pnlOrders, "Orders");

        pnlInventory.setBackground(new java.awt.Color(255, 153, 51));

        tblItems.setBackground(new java.awt.Color(255, 255, 204));
        tblItems.setForeground(new java.awt.Color(0, 0, 0));
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

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID:");

        lblItemID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemID.setForeground(new java.awt.Color(0, 0, 0));
        lblItemID.setText("[Item ID]");

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Category:");

        lblItemCategory.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemCategory.setForeground(new java.awt.Color(0, 0, 0));
        lblItemCategory.setText("[Category]");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Type:");

        lblItemType.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemType.setForeground(new java.awt.Color(0, 0, 0));
        lblItemType.setText("[Type]");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Price:");

        lblItemPrice.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemPrice.setForeground(new java.awt.Color(0, 0, 0));
        lblItemPrice.setText("[Price]");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Stock");

        lblItemStock.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemStock.setForeground(new java.awt.Color(0, 0, 0));
        lblItemStock.setText("[Stock]");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Status:");

        lblItemStatus.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemStatus.setForeground(new java.awt.Color(0, 0, 0));
        lblItemStatus.setText("[Status]");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Name:");

        lblItemName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemName.setForeground(new java.awt.Color(0, 0, 0));
        lblItemName.setText("[Name]");

        btnNewItem.setBackground(new java.awt.Color(255, 204, 204));
        btnNewItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnNewItem.setForeground(new java.awt.Color(0, 0, 0));
        btnNewItem.setText("New Item");
        btnNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewItemActionPerformed(evt);
            }
        });

        btnRemoveItem.setBackground(new java.awt.Color(255, 204, 204));
        btnRemoveItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnRemoveItem.setForeground(new java.awt.Color(0, 0, 0));
        btnRemoveItem.setText("Remove Item");
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });

        btnEditItem.setBackground(new java.awt.Color(255, 204, 204));
        btnEditItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnEditItem.setForeground(new java.awt.Color(0, 0, 0));
        btnEditItem.setText("Edit Item");
        btnEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditItemActionPerformed(evt);
            }
        });

        btnUpdateStock.setBackground(new java.awt.Color(255, 204, 204));
        btnUpdateStock.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnUpdateStock.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdateStock.setText("Update Stock");
        btnUpdateStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStockActionPerformed(evt);
            }
        });

        btnUpdateStatus.setBackground(new java.awt.Color(255, 204, 204));
        btnUpdateStatus.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnUpdateStatus.setForeground(new java.awt.Color(0, 0, 0));
        btnUpdateStatus.setText("Update Status");
        btnUpdateStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStatusActionPerformed(evt);
            }
        });

        lblSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplier.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplier.setText("[SupplierName]");

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Supplier");

        javax.swing.GroupLayout pnlInventoryLayout = new javax.swing.GroupLayout(pnlInventory);
        pnlInventory.setLayout(pnlInventoryLayout);
        pnlInventoryLayout.setHorizontalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlItems, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(66, 66, 66)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemName)
                            .addComponent(lblItemType)))
                    .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnNewItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRemoveItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEditItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateStock, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(49, 49, 49)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemID)
                            .addComponent(lblItemCategory)))
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGap(59, 59, 59)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSupplier)
                            .addComponent(lblItemStock)
                            .addComponent(lblItemPrice)
                            .addComponent(lblItemStatus))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInventoryLayout.setVerticalGroup(
            pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblItemID))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItemCategory)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblItemType))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lblItemName))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSupplier))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblItemPrice))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblItemStock))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItemStatus)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditItem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateStock, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 152, Short.MAX_VALUE))
                    .addComponent(srlItems))
                .addContainerGap())
        );

        pnlMainContent.add(pnlInventory, "Inventory");

        pnlSuppliers.setBackground(new java.awt.Color(255, 198, 157));

        tblSuppliers.setBackground(new java.awt.Color(255, 255, 204));
        tblSuppliers.setForeground(new java.awt.Color(0, 0, 0));
        tblSuppliers.setModel(suppliersModel);
        tblSuppliers.setGridColor(java.awt.Color.gray);
        tblSuppliers.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblSuppliers.setShowGrid(true);
        tblSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblSuppliersMouseReleased(evt);
            }
        });
        srlSuppliers.setViewportView(tblSuppliers);

        jLabel8.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("ID:");

        lblSupplierID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierID.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplierID.setText("[Supplier ID]");

        lblSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierName.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplierName.setText("[Supplier Name]");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Name:");

        lblSupplierAddedTime.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierAddedTime.setForeground(new java.awt.Color(0, 0, 0));
        lblSupplierAddedTime.setText("[Supplier Added Time]");

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Added Time:");

        btnNewSupplier.setBackground(new java.awt.Color(255, 204, 204));
        btnNewSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnNewSupplier.setForeground(new java.awt.Color(0, 0, 0));
        btnNewSupplier.setText("New Supplier");
        btnNewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSupplierActionPerformed(evt);
            }
        });

        btnRemoveSupplier.setBackground(new java.awt.Color(255, 204, 204));
        btnRemoveSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnRemoveSupplier.setForeground(new java.awt.Color(0, 0, 0));
        btnRemoveSupplier.setText("Remove Supplier");
        btnRemoveSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSupplierActionPerformed(evt);
            }
        });

        btnChangeSupplierName.setBackground(new java.awt.Color(255, 204, 204));
        btnChangeSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnChangeSupplierName.setForeground(new java.awt.Color(0, 0, 0));
        btnChangeSupplierName.setText("Change Name");
        btnChangeSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeSupplierNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSuppliersLayout = new javax.swing.GroupLayout(pnlSuppliers);
        pnlSuppliers.setLayout(pnlSuppliersLayout);
        pnlSuppliersLayout.setHorizontalGroup(
            pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addGroup(pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSuppliersLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(100, 100, 100)
                        .addComponent(lblSupplierID))
                    .addGroup(pnlSuppliersLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(75, 75, 75)
                        .addComponent(lblSupplierName))
                    .addGroup(pnlSuppliersLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(40, 40, 40)
                        .addComponent(lblSupplierAddedTime))
                    .addComponent(btnNewSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveSupplier)
                    .addComponent(btnChangeSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlSuppliersLayout.setVerticalGroup(
            pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuppliersLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(lblSupplierID))
                .addGap(18, 18, 18)
                .addGroup(pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(lblSupplierName))
                .addGap(18, 18, 18)
                .addGroup(pnlSuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lblSupplierAddedTime))
                .addGap(53, 53, 53)
                .addComponent(btnNewSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRemoveSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChangeSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlSuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlSuppliers)
                .addContainerGap())
        );

        pnlMainContent.add(pnlSuppliers, "Suppliers");

        pnlContent.add(pnlMainContent, java.awt.BorderLayout.CENTER);

        pnlSummary.setBackground(new java.awt.Color(255, 153, 153));
        pnlSummary.setPreferredSize(new java.awt.Dimension(0, 0));
        pnlSummary.setLayout(new java.awt.BorderLayout());

        pnlSummaryDivider.setBackground(new java.awt.Color(243, 210, 210));
        pnlSummaryDivider.setPreferredSize(new java.awt.Dimension(5, 690));

        javax.swing.GroupLayout pnlSummaryDividerLayout = new javax.swing.GroupLayout(pnlSummaryDivider);
        pnlSummaryDivider.setLayout(pnlSummaryDividerLayout);
        pnlSummaryDividerLayout.setHorizontalGroup(
            pnlSummaryDividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );
        pnlSummaryDividerLayout.setVerticalGroup(
            pnlSummaryDividerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlSummary.add(pnlSummaryDivider, java.awt.BorderLayout.WEST);

        pnlSummaryMain.setBackground(new java.awt.Color(255, 153, 153));
        pnlSummaryMain.setPreferredSize(new java.awt.Dimension(200, 690));

        javax.swing.GroupLayout pnlSummaryMainLayout = new javax.swing.GroupLayout(pnlSummaryMain);
        pnlSummaryMain.setLayout(pnlSummaryMainLayout);
        pnlSummaryMainLayout.setHorizontalGroup(
            pnlSummaryMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlSummaryMainLayout.setVerticalGroup(
            pnlSummaryMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnlSummary.add(pnlSummaryMain, java.awt.BorderLayout.CENTER);

        pnlContent.add(pnlSummary, java.awt.BorderLayout.EAST);

        pnlContainer.add(pnlContent, java.awt.BorderLayout.CENTER);

        mainSplitPane.setRightComponent(pnlContainer);

        getContentPane().add(mainSplitPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblMinimiseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimiseMouseClicked
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinimiseMouseClicked

    private void lblMinimiseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimiseMouseEntered
        lblMinimise.setBackground(Color.white);
    }//GEN-LAST:event_lblMinimiseMouseEntered

    private void lblMinimiseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimiseMouseExited
        lblMinimise.setBackground(new java.awt.Color(243, 220, 220));
    }//GEN-LAST:event_lblMinimiseMouseExited

    private void lblMaximiseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaximiseMouseClicked
        if (!maximise){
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            lblMaximise.setToolTipText("Restore");
            new CommonMethod().setLabelIcon("/icons/normal.png", 15, 15, Image.SCALE_SMOOTH, lblMaximise);
            maximise = true;
        }
        else{
            setExtendedState(JFrame.NORMAL);
            lblMaximise.setToolTipText("Maximise");
            new CommonMethod().setLabelIcon("/icons/maximise.png", 15, 15, Image.SCALE_SMOOTH, lblMaximise);
            maximise = false;
        }
    }//GEN-LAST:event_lblMaximiseMouseClicked

    private void lblMaximiseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaximiseMouseEntered
        lblMaximise.setBackground(Color.white);
    }//GEN-LAST:event_lblMaximiseMouseEntered

    private void lblMaximiseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMaximiseMouseExited
        lblMaximise.setBackground(new java.awt.Color(243, 220, 220));
    }//GEN-LAST:event_lblMaximiseMouseExited

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseEntered
        lblClose.setBackground(Color.red);
        new CommonMethod().setLabelIcon("/icons/close_selected.png", 15, 15, Image.SCALE_SMOOTH, lblClose);
    }//GEN-LAST:event_lblCloseMouseEntered

    private void lblCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseExited
        lblClose.setBackground(new java.awt.Color(243, 220, 220));
        new CommonMethod().setLabelIcon("/icons/close.png", 15, 15, Image.SCALE_SMOOTH, lblClose);
    }//GEN-LAST:event_lblCloseMouseExited

    private void pnlToolsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlToolsMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlToolsMousePressed

    private void pnlToolsMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlToolsMouseDragged
        if (maximise){
            setExtendedState(JFrame.NORMAL);
            lblMaximise.setToolTipText("Maximise");
            new CommonMethod().setLabelIcon("/icons/maximise.png", 15, 15, Image.SCALE_SMOOTH, lblMaximise);
            maximise = false;
        }
        CommonEvent.mouseDragWindow(this, evt, initialClick);
    }//GEN-LAST:event_pnlToolsMouseDragged

    private void pnlNorthMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthMarginMouseEntered
        setCursor(Cursor.N_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlNorthMarginMouseEntered

    private void pnlNorthMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlNorthMarginMousePressed

    private void pnlNorthMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthMarginMouseDragged
        ResizablePanel.SetNorth(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlNorthMarginMouseDragged

    private void pnlSouthMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthMarginMouseEntered
        setCursor(Cursor.S_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlSouthMarginMouseEntered

    private void pnlSouthMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthMarginMouseDragged
        ResizablePanel.SetSouth(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlSouthMarginMouseDragged

    private void pnlWestMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWestMarginMouseEntered
        setCursor(Cursor.W_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlWestMarginMouseEntered

    private void pnlWestMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWestMarginMouseDragged
        ResizablePanel.SetWest(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlWestMarginMouseDragged

    private void pnlSouthMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlSouthMarginMousePressed

    private void pnlWestMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlWestMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlWestMarginMousePressed

    private void pnlEastMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEastMarginMouseEntered
        setCursor(Cursor.E_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlEastMarginMouseEntered

    private void pnlEastMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEastMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlEastMarginMousePressed

    private void pnlEastMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEastMarginMouseDragged
        ResizablePanel.SetEast(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlEastMarginMouseDragged

    private void pnlNorthWestMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthWestMarginMouseEntered
        setCursor(Cursor.NW_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlNorthWestMarginMouseEntered

    private void pnlNorthWestMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthWestMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlNorthWestMarginMousePressed

    private void pnlNorthWestMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthWestMarginMouseDragged
        ResizablePanel.SetNW(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlNorthWestMarginMouseDragged

    private void pnlNorthEastMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthEastMarginMouseEntered
        setCursor(Cursor.NE_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlNorthEastMarginMouseEntered

    private void pnlNorthEastMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthEastMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlNorthEastMarginMousePressed

    private void pnlNorthEastMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNorthEastMarginMouseDragged
        ResizablePanel.SetNE(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlNorthEastMarginMouseDragged

    private void pnlSouthWestMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthWestMarginMouseEntered
        setCursor(Cursor.SW_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlSouthWestMarginMouseEntered

    private void pnlSouthWestMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthWestMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlSouthWestMarginMousePressed

    private void pnlSouthWestMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthWestMarginMouseDragged
        ResizablePanel.SetSW(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlSouthWestMarginMouseDragged

    private void pnlSouthEastMarginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthEastMarginMouseEntered
        setCursor(Cursor.SE_RESIZE_CURSOR);
        if (maximise){
            setCursor(Cursor.DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_pnlSouthEastMarginMouseEntered

    private void pnlSouthEastMarginMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthEastMarginMousePressed
        initialClick = evt.getPoint();
    }//GEN-LAST:event_pnlSouthEastMarginMousePressed

    private void pnlSouthEastMarginMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSouthEastMarginMouseDragged
        ResizablePanel.SetSE(this, evt, initialClick, maximise);
    }//GEN-LAST:event_pnlSouthEastMarginMouseDragged

    private void lblSummaryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSummaryMouseEntered
        new CommonMethod().setLabelIcon("/icons/summary_select.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
        lblSummary.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblSummaryMouseEntered

    private void lblSummaryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSummaryMouseExited
        new CommonMethod().setLabelIcon("/icons/summary.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
        if (summary){
            new CommonMethod().setLabelIcon("/icons/summary_selected.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
        }
    }//GEN-LAST:event_lblSummaryMouseExited

    private void lblSummaryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSummaryMouseClicked
        if (!summary){
            new CommonMethod().setLabelIcon("/icons/summary_selected.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
            pnlSummary.setPreferredSize(new Dimension(300, 0));
            pnlSummary.revalidate();
            pnlSummary.repaint();
            summary = true;
        }
        else{
            new CommonMethod().setLabelIcon("/icons/summary.png", 23, 23, Image.SCALE_SMOOTH, lblSummary);
            pnlSummary.setPreferredSize(new Dimension(0, 0));
            pnlSummary.revalidate();
            pnlSummary.repaint();
            summary = false;
        }
    }//GEN-LAST:event_lblSummaryMouseClicked

    private void pnlDashboardTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardTabMouseEntered
        tabEntered(pnlDashboardTab, pnlDashboardIndicator);
    }//GEN-LAST:event_pnlDashboardTabMouseEntered

    private void pnlDashboardTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardTabMouseExited
        tabExited(pnlDashboardTab, pnlDashboardIndicator);
    }//GEN-LAST:event_pnlDashboardTabMouseExited

    private void pnlDashboardTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardTabMouseClicked
        changeTab(pnlDashboardTab, pnlDashboardIndicator);
    }//GEN-LAST:event_pnlDashboardTabMouseClicked

    private void pnlUsersTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUsersTabMouseEntered
        tabEntered(pnlUsersTab,pnlUsersIndicator);
    }//GEN-LAST:event_pnlUsersTabMouseEntered

    private void pnlUsersTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUsersTabMouseExited
        tabExited(pnlUsersTab, pnlUsersIndicator);
    }//GEN-LAST:event_pnlUsersTabMouseExited

    private void pnlUsersTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlUsersTabMouseClicked
        changeTab(pnlUsersTab, pnlUsersIndicator);
    }//GEN-LAST:event_pnlUsersTabMouseClicked

    private void pnlSalesTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSalesTabMouseEntered
        tabEntered(pnlSalesTab,pnlSalesIndicator);
    }//GEN-LAST:event_pnlSalesTabMouseEntered

    private void pnlSalesTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSalesTabMouseExited
        tabExited(pnlSalesTab, pnlSalesIndicator);
    }//GEN-LAST:event_pnlSalesTabMouseExited

    private void pnlSalesTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSalesTabMouseClicked
        changeTab(pnlSalesTab, pnlSalesIndicator);
    }//GEN-LAST:event_pnlSalesTabMouseClicked

    private void pnlSuppliersTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSuppliersTabMouseClicked
        Suppliers();
        changeTab(pnlSuppliersTab, pnlSuppliersIndicator);
    }//GEN-LAST:event_pnlSuppliersTabMouseClicked

    private void pnlSuppliersTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSuppliersTabMouseEntered
        tabEntered(pnlSuppliersTab,pnlSuppliersIndicator);
    }//GEN-LAST:event_pnlSuppliersTabMouseEntered

    private void pnlSuppliersTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSuppliersTabMouseExited
        tabExited(pnlSuppliersTab, pnlSuppliersIndicator);
    }//GEN-LAST:event_pnlSuppliersTabMouseExited

    private void pnlInventoryTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInventoryTabMouseClicked
        Inventory();
        changeTab(pnlInventoryTab, pnlInventoryIndicator);
    }//GEN-LAST:event_pnlInventoryTabMouseClicked

    private void pnlInventoryTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInventoryTabMouseEntered
        tabEntered(pnlInventoryTab,pnlInventoryIndicator);
    }//GEN-LAST:event_pnlInventoryTabMouseEntered

    private void pnlInventoryTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlInventoryTabMouseExited
        tabExited(pnlInventoryTab, pnlInventoryIndicator);
    }//GEN-LAST:event_pnlInventoryTabMouseExited

    private void pnlOrdersTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlOrdersTabMouseClicked
        changeTab(pnlOrdersTab, pnlOrdersIndicator);
    }//GEN-LAST:event_pnlOrdersTabMouseClicked

    private void pnlOrdersTabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlOrdersTabMouseEntered
        tabEntered(pnlOrdersTab,pnlOrdersIndicator);
    }//GEN-LAST:event_pnlOrdersTabMouseEntered

    private void pnlOrdersTabMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlOrdersTabMouseExited
        tabExited(pnlOrdersTab, pnlOrdersIndicator);
    }//GEN-LAST:event_pnlOrdersTabMouseExited

    private void tblItemsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemsMouseReleased
        selectedItemRow = tblItems.getSelectedRow();
        selectedItem.setItemID(String.valueOf(itemsModel.getValueAt(selectedItemRow, 0)));
        selectedItem.setItemCategory(String.valueOf(itemsModel.getValueAt(selectedItemRow, 1)));
        selectedItem.setItemType(String.valueOf(itemsModel.getValueAt(selectedItemRow, 2)));
        selectedItem.setItemName(String.valueOf(itemsModel.getValueAt(selectedItemRow, 3)));
        selectedItem.setSupplierID(String.valueOf(itemsModel.getValueAt(selectedItemRow, 4)));
        selectedItem.setSellPrice(Double.parseDouble(String.valueOf(itemsModel.getValueAt(selectedItemRow, 5))));
        selectedItem.setStock(Integer.parseInt(String.valueOf(itemsModel.getValueAt(selectedItemRow, 6))));
        selectedItem.setStatus(Item.Status.valueOf(String.valueOf(itemsModel.getValueAt(selectedItemRow, 7))));
        
        lblItemID.setText(selectedItem.getItemID());
        lblItemCategory.setText(selectedItem.getItemCategory());
        lblItemType.setText(selectedItem.getItemType());
        lblItemName.setText(selectedItem.getItemName());
        lblSupplier.setText(Supplier.getNameById(selectedItem.getSupplierID()));
        lblItemPrice.setText(String.valueOf(selectedItem.getSellPrice()));
        lblItemStock.setText(String.valueOf(selectedItem.getStock()));
        lblItemStatus.setText(selectedItem.getStatus().name());
    }//GEN-LAST:event_tblItemsMouseReleased

    private void btnNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewItemActionPerformed
        NewItem newItem = new NewItem(this, true);
        newItem.setLocationRelativeTo(this);
        newItem.setVisible(true);
        Inventory();
    }//GEN-LAST:event_btnNewItemActionPerformed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
        if (selectedItemRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an item to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Are you sure to remove this item?", "Remove Item", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION)
        {
            itemsModel.removeRow(selectedItemRow);
            selectedItem.setStatus(Item.Status.REMOVED);
            selectedItem.updateStatus();
            Inventory();
        }
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void btnEditItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditItemActionPerformed
        if (selectedItemRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an item to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        EditItem editItem = new EditItem(this, true, selectedItem);
        editItem.setLocationRelativeTo(this);
        editItem.setVisible(true);
        Inventory();
    }//GEN-LAST:event_btnEditItemActionPerformed

    private void btnUpdateStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStockActionPerformed
        if (selectedItemRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an item to update stock", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String input = JOptionPane.showInputDialog(this, String.format("Stock: %d", selectedItem.getStock()), "Update Stock", JOptionPane.INFORMATION_MESSAGE);
        
        if (input != null) {
            try {
                int newStock = Integer.parseInt(input.trim());
                if (newStock < 0) {
                    JOptionPane.showMessageDialog(this, "Stock cannot be negative!", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                } else {
                    selectedItem.setStock(newStock);
                    selectedItem.updateStock(); 
                    JOptionPane.showMessageDialog(this, "Stock updated successfully!");
                    Inventory();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnUpdateStockActionPerformed

    private void btnUpdateStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStatusActionPerformed
        if (selectedItemRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select an item to update status", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UpdateStatus updateStatus = new UpdateStatus(this, true, selectedItem);
        updateStatus.setLocationRelativeTo(this);
        updateStatus.setVisible(true);
        Inventory();
    }//GEN-LAST:event_btnUpdateStatusActionPerformed

    private void tblSuppliersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuppliersMouseReleased
        selectedSupplierRow = tblSuppliers.getSelectedRow();
       
        selectedSupplier.setSupplierID(String.valueOf(suppliersModel.getValueAt(selectedSupplierRow, 0)));
        selectedSupplier.setSupplierName(String.valueOf(suppliersModel.getValueAt(selectedSupplierRow, 1)));
        selectedSupplier.setAddedTime(LocalDateTime.parse(String.valueOf(suppliersModel.getValueAt(selectedSupplierRow, 2)), supplierAddedTimeFormatter));
        
        lblSupplierID.setText(selectedSupplier.getSupplierID());
        lblSupplierName.setText(selectedSupplier.getSupplierName());
        lblSupplierAddedTime.setText(selectedSupplier.getAddedTime().format(supplierAddedTimeFormatter));
    }//GEN-LAST:event_tblSuppliersMouseReleased

    private void btnNewSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSupplierActionPerformed
        String input = JOptionPane.showInputDialog(this, "Enter the Supplier's Name:", "New Supplier", JOptionPane.INFORMATION_MESSAGE);
        
        if (input == null || input.isBlank()) {
            JOptionPane.showMessageDialog(this, "Invalid Supplier Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Supplier supplier = new Supplier(input.trim());
        supplier.add();
        JOptionPane.showMessageDialog(this, String.format("Successfully added supplier: %s!", supplier.getSupplierName()));
        Suppliers();
    }//GEN-LAST:event_btnNewSupplierActionPerformed

    private void btnRemoveSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveSupplierActionPerformed
        if (selectedSupplierRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a supplier to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(this, "Are you sure to remove this supplier?", "Remove Supplier", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION)
        {
            suppliersModel.removeRow(selectedSupplierRow);
            selectedSupplier.remove();
            Suppliers();
        }
    }//GEN-LAST:event_btnRemoveSupplierActionPerformed

    private void btnChangeSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeSupplierNameActionPerformed
        if (selectedSupplierRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a supplier to change name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog(this, String.format("Supplier: %s", selectedSupplier.getSupplierName()), "New Supplier Name", JOptionPane.INFORMATION_MESSAGE);
        
        if (input == null || input.isBlank()) {
            JOptionPane.showMessageDialog(this, "Invalid Supplier Name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        selectedSupplier.setSupplierName(input.trim());
        selectedSupplier.changeName();
        JOptionPane.showMessageDialog(this, String.format("Successfully change supplier's name to %s!", selectedSupplier.getSupplierName()));
        Suppliers();
    }//GEN-LAST:event_btnChangeSupplierNameActionPerformed

    private void tblOrderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblOrderMouseReleased
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeSupplierName;
    private javax.swing.JButton btnEditItem;
    private javax.swing.JButton btnNewItem;
    private javax.swing.JButton btnNewSupplier;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JButton btnRemoveSupplier;
    private javax.swing.JButton btnUpdateStatus;
    private javax.swing.JButton btnUpdateStock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblDashboardDivider1;
    private javax.swing.JLabel lblDashboardDivider2;
    private javax.swing.JLabel lblDashboardIcon;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblInventory;
    private javax.swing.JLabel lblInventoryDivider1;
    private javax.swing.JLabel lblInventoryDivider2;
    private javax.swing.JLabel lblInventoryIcon;
    private javax.swing.JLabel lblItemCategory;
    private javax.swing.JLabel lblItemID;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblItemPrice;
    private javax.swing.JLabel lblItemStatus;
    private javax.swing.JLabel lblItemStock;
    private javax.swing.JLabel lblItemType;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblLogout1;
    private javax.swing.JLabel lblMaximise;
    private javax.swing.JLabel lblMinimise;
    private javax.swing.JLabel lblOrders;
    private javax.swing.JLabel lblOrdersDivider1;
    private javax.swing.JLabel lblOrdersDivider2;
    private javax.swing.JLabel lblOrdersIcon;
    private javax.swing.JPanel lblProfileDivider;
    private javax.swing.JLabel lblProfilePicture;
    private javax.swing.JLabel lblSales;
    private javax.swing.JLabel lblSalesDivider1;
    private javax.swing.JLabel lblSalesDivider2;
    private javax.swing.JLabel lblSalesIcon;
    private javax.swing.JLabel lblSummary;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblSupplierAddedTime;
    private javax.swing.JLabel lblSupplierID;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblSuppliers;
    private javax.swing.JLabel lblSuppliersDivider1;
    private javax.swing.JLabel lblSuppliersDivider2;
    private javax.swing.JLabel lblSuppliersIcon;
    private javax.swing.JPanel lblUserDetails;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsers;
    private javax.swing.JLabel lblUsersDivider1;
    private javax.swing.JLabel lblUsersDivider2;
    private javax.swing.JLabel lblUsersIcon;
    private javax.swing.JLabel lbltest;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlDashboardIndicator;
    private javax.swing.JPanel pnlDashboardTab;
    private javax.swing.JPanel pnlEastMargin;
    private javax.swing.JPanel pnlInventory;
    private javax.swing.JPanel pnlInventoryIndicator;
    private javax.swing.JPanel pnlInventoryTab;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlMainContent;
    private javax.swing.JPanel pnlNavigator;
    private javax.swing.JPanel pnlNorthEastMargin;
    private javax.swing.JPanel pnlNorthMargin;
    private javax.swing.JPanel pnlNorthWestMargin;
    private javax.swing.JPanel pnlOrders;
    private javax.swing.JPanel pnlOrdersIndicator;
    private javax.swing.JPanel pnlOrdersTab;
    private javax.swing.JPanel pnlProfile;
    private javax.swing.JPanel pnlSales;
    private javax.swing.JPanel pnlSalesIndicator;
    private javax.swing.JPanel pnlSalesTab;
    private javax.swing.JPanel pnlSouthEastMargin;
    private javax.swing.JPanel pnlSouthMargin;
    private javax.swing.JPanel pnlSouthMargin3;
    private javax.swing.JPanel pnlSouthWestMargin;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JPanel pnlSummaryDivider;
    private javax.swing.JPanel pnlSummaryMain;
    private javax.swing.JPanel pnlSuppliers;
    private javax.swing.JPanel pnlSuppliersIndicator;
    private javax.swing.JPanel pnlSuppliersTab;
    private javax.swing.JPanel pnlTabs;
    private javax.swing.JPanel pnlTools;
    private javax.swing.JPanel pnlUsers;
    private javax.swing.JPanel pnlUsersIndicator;
    private javax.swing.JPanel pnlUsersTab;
    private javax.swing.JPanel pnlWestMargin;
    private javax.swing.JPanel pnlWindowControls;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JScrollPane srlOrder;
    private javax.swing.JScrollPane srlSuppliers;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTable tblSuppliers;
    // End of variables declaration//GEN-END:variables
}
