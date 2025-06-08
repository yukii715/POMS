
package com.owsb.poms.ui.pm;

import com.owsb.poms.system.model.POItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import com.owsb.poms.system.model.*;
import static com.owsb.poms.system.model.POItem.read;
import com.owsb.poms.system.model.PRItem;

public class POItemDialog extends JDialog{
    public POItemDialog(JFrame parent, String poID) {
        super(parent, "Items for " + poID, true);

        String[] columns = {"Item ID", "Item Category", "Item Type", "Item Name", "Quantity", "Unit Price"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Read items from file named prID.txt
        // Read items from file named prID.txt
    List<POItem> items = read("data/PR/" + poID + ".txt");
    if (items != null && !items.isEmpty()) {
        for (POItem item : items) {
            model.addRow(new Object[] {
                item.getItemID(),
                item.getItemCategory(),
                item.getItemType(),
                item.getItemName(),
                item.getQuantity(),
                item.getUnitPrice()
            });
        }
    } else {
        JOptionPane.showMessageDialog(this, "No items found or file " + poID + ".txt not found.");
    }

    setLayout(new java.awt.BorderLayout());
    add(scrollPane, java.awt.BorderLayout.CENTER);

    setSize(600, 300);
    setLocationRelativeTo(parent);
    }
}
