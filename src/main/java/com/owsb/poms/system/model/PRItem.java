package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.util.List;

public class PRItem extends PurchaseRequisition {
    private String itemID;
    private int quantity;
    
    private static final String filePath = "data/PR/items.txt";

    public PRItem(){
    }
    
    public PRItem(String itemID, int quantity) {
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return itemID + "\t"
             + quantity;
    }

    public static PRItem fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid input line for PurchaseRequisition: " + line);
        }

        PRItem pri = new PRItem();
        pri.setItemID(parts[0]);
        pri.setQuantity(Integer.parseInt(parts[1]));

        return pri;
    }
    
    public void save(List<PRItem> list) {
        FileHandler.saveToFile(filePath, list, PRItem::toString);
    }
    
    public static List<PRItem> read(){
        return FileHandler.readFromFile(filePath, PRItem::fromString);
    }
    
    @Override
    public void add() {
        var pri = read();
        pri.add(this);
        this.save(pri);
    }
}
