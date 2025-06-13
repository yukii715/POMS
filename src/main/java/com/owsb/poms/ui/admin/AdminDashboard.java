package com.owsb.poms.ui.admin;

import com.owsb.poms.system.functions.SecurePassword;
import com.owsb.poms.system.functions.UserValidation;
import com.owsb.poms.system.model.User.*;
import com.owsb.poms.ui.admin.Inventory.*;
import com.owsb.poms.system.model.*;
import com.owsb.poms.ui.admin.Dashboard.BankSetting;
import com.owsb.poms.ui.admin.Orders.*;
import com.owsb.poms.ui.admin.Users.UserCreation;
import com.owsb.poms.ui.common.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

public class AdminDashboard extends javax.swing.JFrame {
    private Point initialClick;
    private boolean maximise = false;
    private boolean summary = false;  
    private Admin admin;
    private String currentTab = "Dashboard";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // Users
    private int selectedUserRow;
    private User selectedUser = new User();
    private List<User> userList;
    private DefaultTableModel usersModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] usersColumnName = {"UID", "Name", "Email", "Role", "Join On", "Age", "Birthday"};
    
    // Sales
    
    // Orders
    private List<PurchaseOrder> orderList;
    private DefaultTableModel ordersModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] ordersColumnName = {"ID", "Supplier", "Date Time", "Delivery On", "Created By", "Approved By", "Status"};
    
    // Inventory
    private int selectedItemRow;
    private Item selectedItem = new Item();
    private List<Item> itemList;
    private DefaultTableModel itemsModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] itemsColumnName = {"ID", "Category", "Type", "Name", "Supplier", "Price", "Stock", "Status"};
    
    // Suppliers
    private int selectedSupplierRow;
    private Supplier selectedSupplier = new Supplier();
    private List<Supplier> suppliersList;
    private DefaultTableModel suppliersModel = new DefaultTableModel(){
       public boolean isCellEditable(int row, int column){
           return false;
       } 
    } ;
    private String[] suppliersColumnName = {"ID", "Name", "Added Time"};

    public AdminDashboard(Admin admin){
        initComponents();
        this.setLocationRelativeTo(null);
        
        CommonMethod.setRoundedLabelIcon("data/Users/admin/profile picture/11.jpg", lblProfilePicture, 60);
        CommonMethod.setRoundedLabelIcon("data/Users/admin/profile picture/11.jpg", lbltest, 300);

        new CommonMethod().setFrameIcon("/icons/logo.png", 330, 330, Image.SCALE_SMOOTH, this);
        new CommonMethod().setLabelIcon("/icons/logo.png", 90, 90, Image.SCALE_SMOOTH, lblLogo);
        new CommonMethod().setLabelIcon("/icons/minimise.png", 15, 15, Image.SCALE_SMOOTH, lblMinimise);
        new CommonMethod().setLabelIcon("/icons/maximise.png", 15, 15, Image.SCALE_SMOOTH, lblMaximise);
        new CommonMethod().setLabelIcon("/icons/close.png", 15, 15, Image.SCALE_SMOOTH, lblClose);
        new CommonMethod().setLabelIcon("/icons/summary.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
        new CommonMethod().setLabelIcon("/icons/logout.png", 23, 23, Image.SCALE_SMOOTH, lblLogout1);
        new CommonMethod().setLabelIcon("/icons/logout2.png", 30, 30, Image.SCALE_SMOOTH, lblLogout2);
        new CommonMethod().setLabelIcon("/icons/reload.png", 30, 30, Image.SCALE_SMOOTH, lblReload);
        
        
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
        
        this.admin = admin;
        lblUsername.setText(admin.getUsername());
        lblUserID.setText(admin.getUID());
        lblEmail.setText(admin.getEmail());
        
        Reload();
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
    
    private void Reload(){
        Users();
        Sales();
        Orders();
        Inventory();
        Suppliers();
    }
    
    // Users Tab
    private void Users(){
        selectedUserRow = -1;
        usersModel.setRowCount(0);
        
        lblUID.setText("None");
        lblName.setText("None");
        lblRole.setText("None");
        lblCreationDateTime.setText("");
        
        usersModel.setColumnIdentifiers(usersColumnName);
        JTableHeader header = tblUser.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlUser.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblUser.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblUser.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        userList = User.toList();
        
        for (User user : userList) {
            if (user.isIsDeleted()!= true)
            {
                usersModel.addRow(new String[]{
                    user.getUID(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().name(),
                    user.getCreationDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                    String.valueOf(user.getAge()),
                    user.getBirthday().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                });
            }
        }
        
        // Create a single “center” renderer:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblUser.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    // Sales Tab
    private void Sales(){
        
    }
    
    // Orders Tab
    private void Orders(){
        ordersModel.setRowCount(0);
        
        ordersModel.setColumnIdentifiers(ordersColumnName);
        JTableHeader header = tblOrder.getTableHeader();
        header.setBackground(new java.awt.Color(255, 255, 204));
        
        srlOrder.getViewport().setBackground(new java.awt.Color(255, 255, 204));
        
        tblOrder.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        orderList = PurchaseOrder.toList();
        
        for (PurchaseOrder po : orderList) {
            if (EnumSet.of(
                    PurchaseOrder.Status.PROCESSING,
                    PurchaseOrder.Status.EXTENDED,
                    PurchaseOrder.Status.ARRIVED,
                    PurchaseOrder.Status.VERIFIED,
                    PurchaseOrder.Status.INVALID,
                    PurchaseOrder.Status.CONFIRMED
                ).contains(po.getStatus()) )
            {
                ordersModel.addRow(new String[]{
                    po.getPOID(),
                    po.getSupplierID(),
                    po.getGeneratedDateTime().format(dateTimeFormatter),
                    po.getDeliveryDate().format(dateFormatter),
                    po.getCreateBy(),
                    po.getPerformedBy(),
                    po.getStatus().name()
                });
            }
        }
        
        // Create a single “center” renderer:
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply it as the default for any Object‐typed cell:
        tblOrder.setDefaultRenderer(Object.class, centerRenderer);
    }
    
    // Inventory Tab
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
    
    // Suppliers Tab
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
                    supplier.getAddedTime().format(dateTimeFormatter)
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

        lblLogo = new javax.swing.JLabel();
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
        pnlUserDetails = new javax.swing.JPanel();
        lblUserID = new javax.swing.JLabel();
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
        lblNotification = new javax.swing.JLabel();
        lblLogout2 = new javax.swing.JLabel();
        lblReload = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        pnlMainContent = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        lbltest = new javax.swing.JLabel();
        btnBankSetting = new javax.swing.JButton();
        pnlUsers = new javax.swing.JPanel();
        srlUser = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        lblUID = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblCreationDateTime = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        btnCreateUser = new javax.swing.JButton();
        btnChangeUsername = new javax.swing.JButton();
        btnResetPassoward = new javax.swing.JButton();
        btnEditBirthday = new javax.swing.JButton();
        btnDeleteAccount = new javax.swing.JButton();
        pnlSales = new javax.swing.JPanel();
        pnlOrders = new javax.swing.JPanel();
        srlOrder = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        btnViewAllPO = new javax.swing.JButton();
        btnViewPR = new javax.swing.JButton();
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

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setPreferredSize(new java.awt.Dimension(90, 90));

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
        mainSplitPane.setDividerLocation(200);
        mainSplitPane.setForeground(new java.awt.Color(255, 204, 204));
        mainSplitPane.setToolTipText("");
        mainSplitPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mainSplitPane.setPreferredSize(new java.awt.Dimension(1200, 800));

        pnlNavigator.setBackground(new java.awt.Color(255, 180, 180));
        pnlNavigator.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlNavigator.setMaximumSize(new java.awt.Dimension(200, 32767));
        pnlNavigator.setMinimumSize(new java.awt.Dimension(50, 0));
        pnlNavigator.setPreferredSize(new java.awt.Dimension(250, 803));

        pnlLogo.setBackground(new java.awt.Color(255, 180, 180));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setMaximumSize(new java.awt.Dimension(90, 90));
        lblLogo.setPreferredSize(new java.awt.Dimension(90, 90));

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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
        lblProfilePicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProfilePictureMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProfilePictureMouseEntered(evt);
            }
        });
        pnlProfile.add(lblProfilePicture, java.awt.BorderLayout.WEST);

        pnlUserDetails.setBackground(new java.awt.Color(255, 180, 180));
        pnlUserDetails.setLayout(new java.awt.GridLayout(3, 0));

        lblUserID.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 8)); // NOI18N
        lblUserID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUserID.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlUserDetails.add(lblUserID);

        lblUsername.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 8)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlUserDetails.add(lblUsername);

        lblEmail.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 8)); // NOI18N
        lblEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlUserDetails.add(lblEmail);

        pnlProfile.add(pnlUserDetails, java.awt.BorderLayout.CENTER);

        lblLogout1.setMaximumSize(new java.awt.Dimension(60, 0));
        lblLogout1.setMinimumSize(new java.awt.Dimension(30, 0));
        lblLogout1.setPreferredSize(new java.awt.Dimension(30, 0));
        lblLogout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogout1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogout1MouseEntered(evt);
            }
        });
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
            .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlNavigatorLayout.setVerticalGroup(
            pnlNavigatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavigatorLayout.createSequentialGroup()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        lblNotification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNotification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNotificationMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNotificationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNotificationMouseExited(evt);
            }
        });

        lblLogout2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogout2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogout2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLogout2MouseEntered(evt);
            }
        });

        lblReload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReloadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblReloadMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout pnlToolsLayout = new javax.swing.GroupLayout(pnlTools);
        pnlTools.setLayout(pnlToolsLayout);
        pnlToolsLayout.setHorizontalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addGap(0, 839, Short.MAX_VALUE)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlWindowControls, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolsLayout.createSequentialGroup()
                        .addComponent(lblReload, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLogout2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnlToolsLayout.setVerticalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addComponent(pnlWindowControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNotification, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogout2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblReload, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContainer.add(pnlTools, java.awt.BorderLayout.NORTH);

        pnlContent.setBackground(new java.awt.Color(243, 210, 210));
        pnlContent.setLayout(new java.awt.BorderLayout());

        pnlMainContent.setBackground(new java.awt.Color(255, 204, 204));
        pnlMainContent.setLayout(new java.awt.CardLayout());

        pnlDashboard.setBackground(new java.awt.Color(250, 237, 186));

        lbltest.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnBankSetting.setBackground(new java.awt.Color(255, 204, 204));
        btnBankSetting.setFont(new java.awt.Font("Berlin Sans FB", 0, 18)); // NOI18N
        btnBankSetting.setForeground(new java.awt.Color(0, 0, 0));
        btnBankSetting.setText("Bank Setting");
        btnBankSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBankSettingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(lbltest, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(316, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBankSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDashboardLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnBankSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                .addComponent(lbltest, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        pnlMainContent.add(pnlDashboard, "Dashboard");

        pnlUsers.setBackground(new java.awt.Color(243, 184, 101));

        tblUser.setBackground(new java.awt.Color(255, 255, 204));
        tblUser.setForeground(new java.awt.Color(0, 0, 0));
        tblUser.setModel(usersModel);
        tblUser.setGridColor(java.awt.Color.gray);
        tblUser.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblUser.setShowGrid(true);
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblUserMouseReleased(evt);
            }
        });
        srlUser.setViewportView(tblUser);

        jLabel12.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("UID:");

        lblUID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblUID.setForeground(new java.awt.Color(0, 0, 0));
        lblUID.setText("[UID]");

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Name:");

        lblName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 0, 0));
        lblName.setText("[Name]");

        jLabel14.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Creation Date Tme:");

        lblCreationDateTime.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblCreationDateTime.setForeground(new java.awt.Color(0, 0, 0));
        lblCreationDateTime.setText("[CreationDateTime]");

        jLabel15.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Role:");
        jLabel15.setToolTipText("");

        lblRole.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblRole.setForeground(new java.awt.Color(0, 0, 0));
        lblRole.setText("[Role]");

        btnCreateUser.setBackground(new java.awt.Color(255, 204, 204));
        btnCreateUser.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnCreateUser.setForeground(new java.awt.Color(0, 0, 0));
        btnCreateUser.setText("Create User");
        btnCreateUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateUserActionPerformed(evt);
            }
        });

        btnChangeUsername.setBackground(new java.awt.Color(255, 204, 204));
        btnChangeUsername.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnChangeUsername.setForeground(new java.awt.Color(0, 0, 0));
        btnChangeUsername.setText("Change Username");
        btnChangeUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeUsernameActionPerformed(evt);
            }
        });

        btnResetPassoward.setBackground(new java.awt.Color(255, 204, 204));
        btnResetPassoward.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnResetPassoward.setForeground(new java.awt.Color(0, 0, 0));
        btnResetPassoward.setText("Reset Password");
        btnResetPassoward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetPassowardActionPerformed(evt);
            }
        });

        btnEditBirthday.setBackground(new java.awt.Color(255, 204, 204));
        btnEditBirthday.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnEditBirthday.setForeground(new java.awt.Color(0, 0, 0));
        btnEditBirthday.setText("Edit Birthday");
        btnEditBirthday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBirthdayActionPerformed(evt);
            }
        });

        btnDeleteAccount.setBackground(new java.awt.Color(255, 204, 204));
        btnDeleteAccount.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnDeleteAccount.setForeground(new java.awt.Color(0, 0, 0));
        btnDeleteAccount.setText("Delete Account");
        btnDeleteAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAccountActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUsersLayout = new javax.swing.GroupLayout(pnlUsers);
        pnlUsers.setLayout(pnlUsersLayout);
        pnlUsersLayout.setHorizontalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlUser, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13))
                        .addGap(34, 34, 34)
                        .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(lblRole)
                            .addComponent(lblUID)))
                    .addComponent(jLabel14)
                    .addComponent(lblCreationDateTime)
                    .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetPassoward, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        pnlUsersLayout.setVerticalGroup(
            pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsersLayout.createSequentialGroup()
                        .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(lblUID))
                        .addGap(18, 18, 18)
                        .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblName))
                        .addGap(18, 18, 18)
                        .addGroup(pnlUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(lblRole))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCreationDateTime)
                        .addGap(34, 34, 34)
                        .addComponent(btnCreateUser, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChangeUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnResetPassoward, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(srlUser, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlMainContent.add(pnlUsers, "Users");

        pnlSales.setBackground(new java.awt.Color(218, 167, 112));
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
        tblOrder.setModel(ordersModel);
        tblOrder.setGridColor(java.awt.Color.gray);
        tblOrder.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tblOrder.setShowGrid(true);
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderMouseClicked(evt);
            }
        });
        srlOrder.setViewportView(tblOrder);

        btnViewAllPO.setBackground(new java.awt.Color(255, 204, 204));
        btnViewAllPO.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnViewAllPO.setText("View All Purchase Orders");
        btnViewAllPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewAllPOActionPerformed(evt);
            }
        });

        btnViewPR.setBackground(new java.awt.Color(255, 204, 204));
        btnViewPR.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnViewPR.setText("View Purchase Requisitions");
        btnViewPR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewPRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOrdersLayout = new javax.swing.GroupLayout(pnlOrders);
        pnlOrders.setLayout(pnlOrdersLayout);
        pnlOrdersLayout.setHorizontalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(srlOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnViewAllPO, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(btnViewPR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlOrdersLayout.setVerticalGroup(
            pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOrdersLayout.createSequentialGroup()
                        .addComponent(btnViewAllPO, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnViewPR, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(srlOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlMainContent.add(pnlOrders, "Orders");

        pnlInventory.setBackground(new java.awt.Color(250, 188, 126));

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

        jLabel1.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel1.setText("ID:");

        lblItemID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemID.setText("[Item ID]");

        jLabel2.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel2.setText("Category:");

        lblItemCategory.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemCategory.setText("[Category]");

        jLabel3.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel3.setText("Type:");

        lblItemType.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemType.setText("[Type]");

        jLabel4.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel4.setText("Price:");

        lblItemPrice.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemPrice.setText("[Price]");

        jLabel5.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel5.setText("Stock");

        lblItemStock.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemStock.setText("[Stock]");

        jLabel6.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel6.setText("Status:");

        lblItemStatus.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemStatus.setText("[Status]");

        jLabel7.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel7.setText("Name:");

        lblItemName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblItemName.setText("[Name]");

        btnNewItem.setBackground(new java.awt.Color(255, 204, 204));
        btnNewItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnNewItem.setText("New Item");
        btnNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewItemActionPerformed(evt);
            }
        });

        btnRemoveItem.setBackground(new java.awt.Color(255, 204, 204));
        btnRemoveItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnRemoveItem.setText("Remove Item");
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });

        btnEditItem.setBackground(new java.awt.Color(255, 204, 204));
        btnEditItem.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnEditItem.setText("Edit Item");
        btnEditItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditItemActionPerformed(evt);
            }
        });

        btnUpdateStock.setBackground(new java.awt.Color(255, 204, 204));
        btnUpdateStock.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnUpdateStock.setText("Update Stock");
        btnUpdateStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStockActionPerformed(evt);
            }
        });

        btnUpdateStatus.setBackground(new java.awt.Color(255, 204, 204));
        btnUpdateStatus.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnUpdateStatus.setText("Update Status");
        btnUpdateStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStatusActionPerformed(evt);
            }
        });

        lblSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplier.setText("[SupplierName]");

        jLabel11.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
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
                        .addGap(35, 35, 35)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemType)
                            .addComponent(lblItemName)))
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
                        .addGap(18, 18, 18)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemCategory)
                            .addComponent(lblItemID)))
                    .addGroup(pnlInventoryLayout.createSequentialGroup()
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGap(28, 28, 28)
                        .addGroup(pnlInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemPrice)
                            .addComponent(lblSupplier)
                            .addComponent(lblItemStock)
                            .addComponent(lblItemStatus))))
                .addContainerGap(46, Short.MAX_VALUE))
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
        jLabel8.setText("ID:");

        lblSupplierID.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierID.setText("[Supplier ID]");

        lblSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierName.setText("[Supplier Name]");

        jLabel9.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel9.setText("Name:");

        lblSupplierAddedTime.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        lblSupplierAddedTime.setText("[Supplier Added Time]");

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        jLabel10.setText("Added Time:");

        btnNewSupplier.setBackground(new java.awt.Color(255, 204, 204));
        btnNewSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnNewSupplier.setText("New Supplier");
        btnNewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSupplierActionPerformed(evt);
            }
        });

        btnRemoveSupplier.setBackground(new java.awt.Color(255, 204, 204));
        btnRemoveSupplier.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
        btnRemoveSupplier.setText("Remove Supplier");
        btnRemoveSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveSupplierActionPerformed(evt);
            }
        });

        btnChangeSupplierName.setBackground(new java.awt.Color(255, 204, 204));
        btnChangeSupplierName.setFont(new java.awt.Font("Berlin Sans FB", 0, 14)); // NOI18N
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

    private void lblNotificationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNotificationMouseEntered
        new CommonMethod().setLabelIcon("/icons/summary_select.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
        lblNotification.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblNotificationMouseEntered

    private void lblNotificationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNotificationMouseExited
        new CommonMethod().setLabelIcon("/icons/summary.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
        if (summary){
            new CommonMethod().setLabelIcon("/icons/summary_selected.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
        }
    }//GEN-LAST:event_lblNotificationMouseExited

    private void lblNotificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNotificationMouseClicked
        if (!summary){
            new CommonMethod().setLabelIcon("/icons/summary_selected.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
            pnlSummary.setPreferredSize(new Dimension(300, 0));
            pnlSummary.revalidate();
            pnlSummary.repaint();
            summary = true;
        }
        else{
            new CommonMethod().setLabelIcon("/icons/summary.png", 40, 40, Image.SCALE_SMOOTH, lblNotification);
            pnlSummary.setPreferredSize(new Dimension(0, 0));
            pnlSummary.revalidate();
            pnlSummary.repaint();
            summary = false;
        }
    }//GEN-LAST:event_lblNotificationMouseClicked

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
        String itemID = String.valueOf(itemsModel.getValueAt(selectedItemRow, 0));
        selectedItem = Item.getItemById(itemID);
        
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
        selectedSupplier.setAddedTime(LocalDateTime.parse(String.valueOf(suppliersModel.getValueAt(selectedSupplierRow, 2)), dateTimeFormatter));
        
        lblSupplierID.setText(selectedSupplier.getSupplierID());
        lblSupplierName.setText(selectedSupplier.getSupplierName());
        lblSupplierAddedTime.setText(selectedSupplier.getAddedTime().format(dateTimeFormatter));
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

    private void btnViewAllPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewAllPOActionPerformed
        POList pol = new POList(this, true, admin);
        pol.setLocationRelativeTo(this);
        pol.setVisible(true);
        Orders();
    }//GEN-LAST:event_btnViewAllPOActionPerformed

    private void btnViewPRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewPRActionPerformed
        PRList prl = new PRList(this, true, admin);
        prl.setLocationRelativeTo(this);
        prl.setVisible(true);
    }//GEN-LAST:event_btnViewPRActionPerformed

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
        if (evt.getClickCount() == 2 && tblOrder.getSelectedRow() != -1) {
            int row = tblOrder.getSelectedRow();
            
            PurchaseOrder po = PurchaseOrder.getPoById(String.valueOf(tblOrder.getValueAt(row, 0)));
            PurchaseRequisition pr = PurchaseRequisition.getPrById(po.getPRID());
            
            PODetails pod = new PODetails(this, true, po, pr, admin);
            pod.setLocationRelativeTo(this);
            pod.setVisible(true);
            Orders();
        }
    }//GEN-LAST:event_tblOrderMouseClicked

    private void btnBankSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBankSettingActionPerformed
        BankSetting bankSetting = new BankSetting(this, true);
        bankSetting.setLocationRelativeTo(this);
        bankSetting.setVisible(true);
    }//GEN-LAST:event_btnBankSettingActionPerformed

    private void tblUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseReleased
        selectedUserRow = tblUser.getSelectedRow();
        String uid = String.valueOf(usersModel.getValueAt(selectedUserRow, 0));
        selectedUser = User.getUserById(uid);
        
        lblUID.setText(selectedUser.getUID());
        lblName.setText(selectedUser.getUsername());
        lblRole.setText(selectedUser.getRole().name());
        lblCreationDateTime.setText(selectedUser.getCreationDateTime().format(dateTimeFormatter));
    }//GEN-LAST:event_tblUserMouseReleased

    private void btnCreateUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateUserActionPerformed
        UserCreation userCreation = new UserCreation(this, true, admin);
        userCreation.setLocationRelativeTo(this);
        userCreation.setVisible(true);
        Users();
    }//GEN-LAST:event_btnCreateUserActionPerformed

    private void btnChangeUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeUsernameActionPerformed
        if (selectedUserRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a user to change username", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (admin.getRole().equals(User.Role.Admin)){
            if (selectedUser.getRole().equals(User.Role.Root) || selectedUser.getRole().equals(User.Role.Admin)){
                JOptionPane.showMessageDialog(this, "You don't have permission to change username of this account!", "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        int result = JOptionPane.showConfirmDialog(
                this, 
                String.format(
                        "Are you sure to change username of %s %s?", 
                        selectedUser.getUID(),
                        selectedUser.getUsername()
                ), 
                "Change Username", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(this, "New Username:", "Change Username", JOptionPane.INFORMATION_MESSAGE).toUpperCase().trim();
            if (!UserValidation.validUsername(input)){
                JOptionPane.showMessageDialog(
                        this, 
                        "Invalid username, it might be:\n"
                      + "1. Username already existed\n"
                      + "2. Username contains invalid characters\n"
                      + "3. Username is blank\n"
                      + "Note: Username can only contains alphabets (At least 3)", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                );
                     return;
             }
            selectedUser.setUsername(input);
            selectedUser.changeUsername();
            JOptionPane.showMessageDialog(this, "Username Changed Successfully!");
            Users();
        }
    }//GEN-LAST:event_btnChangeUsernameActionPerformed

    private void btnResetPassowardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetPassowardActionPerformed
        if (selectedUserRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a user to reset password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (admin.getRole().equals(User.Role.Admin)){
            if (selectedUser.getRole().equals(User.Role.Root) || selectedUser.getRole().equals(User.Role.Admin)){
                JOptionPane.showMessageDialog(this, "You don't have permission to reset password of this account!", "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        int result = JOptionPane.showConfirmDialog(
                this, 
                String.format(
                        "Are you sure to reset password of %s %s?",
                        selectedUser.getUID(),
                        selectedUser.getUsername()
                ), 
                "Reset Password", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            String newPassword = SecurePassword.newPassword();
            selectedUser.changePassword(newPassword);
            
            String message = String.format("Reset Password Successfully!%nNew Password: %s", newPassword);
            JTextArea textArea = new JTextArea(message);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setBackground(UIManager.getColor("Panel.background")); 
            textArea.setBorder(null);
            textArea.setPreferredSize(new Dimension(250, 50));

            JOptionPane.showMessageDialog(this, textArea, "Password Reset", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(this, "Password Reset Successfully!");
            dispose();
        }
    }//GEN-LAST:event_btnResetPassowardActionPerformed

    private void btnEditBirthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBirthdayActionPerformed
        if (selectedUserRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a user to edit birthday", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (admin.getRole().equals(User.Role.Admin)){
            if (selectedUser.getRole().equals(User.Role.Root) || selectedUser.getRole().equals(User.Role.Admin)){
                JOptionPane.showMessageDialog(this, "You don't have permission to edit birthday of this account!", "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        int result = JOptionPane.showConfirmDialog(
                this, 
                String.format(
                        "Are you sure to edit birthday of %s %s?", 
                        selectedUser.getUID(), 
                        selectedUser.getUsername()
                ), 
                "Edit Birthday", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(this, "New Birthday:", "Edit Birthday", JOptionPane.INFORMATION_MESSAGE);
            if (!UserValidation.validBirthday(input)){
                JOptionPane.showMessageDialog(
                   this, 
                   "Invalid birthday\n"
                 + "Note: Please enter the birthday in yyyyMMdd form\n"
                 + "E.g. 20000101 (1 January 2000)\n"
                 + "*All employees must be 16 years of age or older", 
                   "Error", 
                   JOptionPane.ERROR_MESSAGE
                );
                     return;
             }
            selectedUser.setBirthday(User.getBirthdayByString(input));
            selectedUser.changeBirthday();
            JOptionPane.showMessageDialog(this, "Birthday Edited Successfully!");
            Users();
        }
    }//GEN-LAST:event_btnEditBirthdayActionPerformed

    private void lblProfilePictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProfilePictureMouseClicked
        Profile profile = new Profile(this, true, admin);
        profile.setLocationRelativeTo(this);
        profile.setVisible(true);
    }//GEN-LAST:event_lblProfilePictureMouseClicked

    private void btnDeleteAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAccountActionPerformed
        if (selectedUserRow == -1)
        {
            JOptionPane.showMessageDialog(this, "Please select a user to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (admin.getRole().equals(User.Role.Admin)){
            if (selectedUser.getRole().equals(User.Role.Root) || selectedUser.getRole().equals(User.Role.Admin)){
                JOptionPane.showMessageDialog(this, "You don't have permission to delete this account!", "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        int result = JOptionPane.showConfirmDialog(
                this, 
                String.format(
                        "Are you sure to delete this account? (%s %s)%n"
                      + "Warning: This action cannot be reverted!",
                        selectedUser.getUID(),
                        selectedUser.getUsername()
                ), 
                "Delete Account", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION)
        {
            selectedUser.delete();
            Users();
        }
    }//GEN-LAST:event_btnDeleteAccountActionPerformed

    private void lblLogout2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogout2MouseClicked
        admin.logout(this);
    }//GEN-LAST:event_lblLogout2MouseClicked

    private void lblLogout1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogout1MouseClicked
        admin.logout(this);
    }//GEN-LAST:event_lblLogout1MouseClicked

    private void lblReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReloadMouseClicked
        Reload();
    }//GEN-LAST:event_lblReloadMouseClicked

    private void lblProfilePictureMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProfilePictureMouseEntered
        lblProfilePicture.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblProfilePictureMouseEntered

    private void lblLogout1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogout1MouseEntered
        lblLogout1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblLogout1MouseEntered

    private void lblLogout2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogout2MouseEntered
        lblLogout2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblLogout2MouseEntered

    private void lblReloadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReloadMouseEntered
        lblReload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblReloadMouseEntered
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBankSetting;
    private javax.swing.JButton btnChangeSupplierName;
    private javax.swing.JButton btnChangeUsername;
    private javax.swing.JButton btnCreateUser;
    private javax.swing.JButton btnDeleteAccount;
    private javax.swing.JButton btnEditBirthday;
    private javax.swing.JButton btnEditItem;
    private javax.swing.JButton btnNewItem;
    private javax.swing.JButton btnNewSupplier;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JButton btnRemoveSupplier;
    private javax.swing.JButton btnResetPassoward;
    private javax.swing.JButton btnUpdateStatus;
    private javax.swing.JButton btnUpdateStock;
    private javax.swing.JButton btnViewAllPO;
    private javax.swing.JButton btnViewPR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblCreationDateTime;
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
    private javax.swing.JLabel lblLogout2;
    private javax.swing.JLabel lblMaximise;
    private javax.swing.JLabel lblMinimise;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNotification;
    private javax.swing.JLabel lblOrders;
    private javax.swing.JLabel lblOrdersDivider1;
    private javax.swing.JLabel lblOrdersDivider2;
    private javax.swing.JLabel lblOrdersIcon;
    private javax.swing.JPanel lblProfileDivider;
    private javax.swing.JLabel lblProfilePicture;
    private javax.swing.JLabel lblReload;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblSales;
    private javax.swing.JLabel lblSalesDivider1;
    private javax.swing.JLabel lblSalesDivider2;
    private javax.swing.JLabel lblSalesIcon;
    private javax.swing.JLabel lblSupplier;
    private javax.swing.JLabel lblSupplierAddedTime;
    private javax.swing.JLabel lblSupplierID;
    private javax.swing.JLabel lblSupplierName;
    private javax.swing.JLabel lblSuppliers;
    private javax.swing.JLabel lblSuppliersDivider1;
    private javax.swing.JLabel lblSuppliersDivider2;
    private javax.swing.JLabel lblSuppliersIcon;
    private javax.swing.JLabel lblUID;
    private javax.swing.JLabel lblUserID;
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
    private javax.swing.JPanel pnlUserDetails;
    private javax.swing.JPanel pnlUsers;
    private javax.swing.JPanel pnlUsersIndicator;
    private javax.swing.JPanel pnlUsersTab;
    private javax.swing.JPanel pnlWestMargin;
    private javax.swing.JPanel pnlWindowControls;
    private javax.swing.JScrollPane srlItems;
    private javax.swing.JScrollPane srlOrder;
    private javax.swing.JScrollPane srlSuppliers;
    private javax.swing.JScrollPane srlUser;
    private javax.swing.JTable tblItems;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTable tblSuppliers;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}
