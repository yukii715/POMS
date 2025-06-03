package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import java.time.LocalDate;
import java.util.List;

public class POItem extends PurchaseOrder{
    private String itemID;
    private int quantity;
    private double unitPrice;
    
    private static final String filePath = "data/PO/items.txt";

    public POItem() {
    }

    public POItem(String PRID, double totalPrice, String supplierID, LocalDate deliveryDate, String createBy, String approvedBy) {
        super(PRID, totalPrice, supplierID, deliveryDate, createBy, approvedBy);
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String ItemID) {
        this.itemID = ItemID;
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
    
    @Override
    public String toString() {
        return itemID + "\t"
                + quantity + "\t"
                + unitPrice;
    }

    public static POItem fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input line for PurchaseRequisition: " + line);
        }

        POItem pro = new POItem();
        pro.setItemID(parts[0]);
        pro.setQuantity(Integer.parseInt(parts[1]));
        pro.setUnitPrice(Double.parseDouble(parts[2]));

        return pro;
    }
    
    public void save(List<POItem> list) {
        FileHandler.saveToFile(filePath, list, POItem::toString);
    }
    
    public static List<POItem> read(){
        return FileHandler.readFromFile(filePath, POItem::fromString);
    }
    
    @Override
    public void add() {
        var pro = read();
        pro.add(this);
        this.save(pro);
    }
}
