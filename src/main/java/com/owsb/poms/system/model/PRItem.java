package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class PRItem extends PurchaseRequisition{
    private String itemID;
    private int quantity;
    
    private static final String filePath = "data/PR/items.txt";

    
    public PRItem() {
    // optionally initialize default values here
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
    

    // Parse a line from file into PRItem object
    public static PRItem fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid input line for PRItem: " + line);
        }
        PRItem prItem = new PRItem();
        prItem.setPRID(parts[0]);
        prItem.setItemID(parts[1]);
        prItem.setQuantity(Integer.parseInt(parts[2]));
        prItem.setSupplierID(parts[3]);
        prItem.setRequestDateTime(LocalDateTime.parse(parts[4]));
        prItem.setRequiredDeliveryDate(LocalDate.parse(parts[5]));
        prItem.setStatus(Status.valueOf(parts[6]));
        prItem.setCreateBy(parts[7]);
        return prItem;
    }
    public static List<PRItem> toItemList() {
    return FileHandler.readFromFile(filePath, PRItem::fromString);
}
}

