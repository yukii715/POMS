
package com.owsb.poms.ui.sm;

import java.awt.CardLayout;
import javax.swing.JPanel;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public class SalesManagerUtilities {
    public static void getFrame(JPanel parent, JPanel targetPage){
        CardLayout card = (CardLayout)parent.getLayout();
        card.show(parent, targetPage.getName());
    }
    
    public void loadItemListTable (JTable table) throws IOException{
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\cjngd\\OneDrive\\Documents\\Assignment\\APU Degree\\Year 2 [Semeseter 1]\\Object Oriented Development with Java\\Database\\ItemList.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 6){ 
                String itemID = data[0].trim();
                String itemName = data[1].trim();
                String date = data[2].trim();
                int quantity = Integer.parseInt(data[3].trim());
                double amount = Double.parseDouble(data[4].trim());
                model.addRow(new Object[]{itemID, itemName, date, quantity, amount});
            }
        }
    }
}
