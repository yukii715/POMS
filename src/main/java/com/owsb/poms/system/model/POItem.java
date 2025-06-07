
package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class POItem extends PurchaseOrder{
    private String itemID;
    private int quantity;
    private double unitPrice;

    private static final String filePath = "data/PO/items.txt";

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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
   public static POItem fromString(String line) {
    String[] parts = line.split("\t");
    if(parts.length != 12) {
        throw new IllegalArgumentException("Invalid input line for POItem: " + line);
    }
    POItem item = new POItem();
    item.setPOID(parts[0]);
    item.setPRID(parts[1]);
    item.setItemID(parts[2]);
    item.setQuantity(Integer.parseInt(parts[3]));
    item.setUnitPrice(Double.parseDouble(parts[4]));
    item.setTotalPrice(Double.parseDouble(parts[5]));
    item.setSupplierID(parts[6]);
    item.setGenerateDateTime(LocalDateTime.parse(parts[7]));
    item.setDeliveryDate(LocalDate.parse(parts[8]));
    item.setStatus(Status.valueOf(parts[9]));
    item.setCreateBy(parts[10]);
    item.setApprovedBy(parts[11]);
    return item;
}

public static List<POItem> toItemList() {
    return FileHandler.readFromFile(filePath, POItem::fromString);
}
}
