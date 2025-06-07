
package com.owsb.poms.ui.pm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseManagerTables {
    public static void loadPurchaseOrdersToTable(JTable table, String filename, java.awt.Component parent) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    String purchaseOrderId = data[0].trim();
                    String itemId = data[1].trim();
                    String itemName = data[2].trim();
                    int quantity = Integer.parseInt(data[3].trim());
                    double unitPrice = Double.parseDouble(data[4].trim());
                    double totalPrice = Double.parseDouble(data[5].trim());
                    String supplierId = data[6].trim();
                    String date = data[7].trim();
                    String status = data[8].trim();
                    String purchaseManager = data[9].trim();
                    String financeManager = data[10].trim();

                    model.addRow(new Object[]{
                        purchaseOrderId, itemId, itemName, quantity, unitPrice, totalPrice,
                        supplierId, date, status, purchaseManager, financeManager
                    });
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Error loading purchase orders: " + ex.getMessage());
        }
    }
    public static void loadPurchaseRequisitionsToTable(JTable table, String filename, java.awt.Component parent) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear existing rows

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    String itemId = data[0].trim();
                    String itemName = data[1].trim();
                    int quantity = Integer.parseInt(data[2].trim());
                    String supplierId = data[3].trim();
                    String dateTimeRequested = data[4].trim();
                    String status = data[5].trim();
                    String salesManager = data[6].trim();

                    model.addRow(new Object[]{
                        itemId, itemName, quantity, supplierId, dateTimeRequested, status, salesManager
                    });
                } else {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Error loading purchase requisitions: " + ex.getMessage());
        }
    }
}
