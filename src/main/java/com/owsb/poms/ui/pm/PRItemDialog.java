
package com.owsb.poms.ui.pm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.owsb.poms.system.model.*;
import static com.owsb.poms.system.model.PRItem.read;

public class PRItemDialog extends JDialog {
    public PRItemDialog(JFrame parent, String prID) {
    super(parent, "Items for " + prID, true);

    String[] columns = {"Item ID", "Item Category", "Item Type", "Item Name", "Quantity"};
    DefaultTableModel model = new DefaultTableModel(columns, 0) {
        public boolean isCellEditable(int row, int column) { return false; }
    };

    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);

    // Use the new read method to get list of PRItem objects
    List<PRItem> items = read("data/PR/" + prID + ".txt");
    if (items != null && !items.isEmpty()) {
        for (PRItem item : items) {
            model.addRow(new Object[] {
                item.getItemID(),
                item.getItemCategory(),
                item.getItemType(),
                item.getItemName(),
                item.getQuantity()
            });
        }
    } else {
        JOptionPane.showMessageDialog(this, "No items found or file " + prID + ".txt not found.");
    }

    setLayout(new java.awt.BorderLayout());
    add(scrollPane, java.awt.BorderLayout.CENTER);

    setSize(600, 300);
    setLocationRelativeTo(parent);
}

}
