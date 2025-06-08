package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import java.util.List;

public class POItem extends PurchaseOrder{
    private String itemID;
    private String itemCategory;
    private String itemType;
    private String itemName;
    private int quantity;
    private double unitPrice;
    
    private static final String filePath = "data/PO/";

    public POItem() {
    }

    public POItem(String itemID, String itemCategory, String itemType, String itemName) {
        this.itemID = itemID;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String ItemID) {
        this.itemID = ItemID;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
                + itemCategory + "\t"
                + itemType + "\t"
                + itemName + "\t"
                + quantity + "\t"
                + unitPrice;
    }

    public static POItem fromString(String line) {
        String[] parts = line.split("\t");

        POItem pro = new POItem();
        pro.setItemID(parts[0]);
        pro.setItemCategory(parts[1]);
        pro.setItemType(parts[2]);
        pro.setItemName(parts[3]);
        pro.setQuantity(Integer.parseInt(parts[4]));
        pro.setUnitPrice(Double.parseDouble(parts[5]));

        return pro;
    }
    
    public void save(List<POItem> list) {
        String fileName = this.getPOID() + ".txt";
        String filePath = this.filePath + fileName;
        
        FileHandler.saveToFile(filePath, list, POItem::toString);
    }
    
    public static List<POItem> read(String filePath){
        return FileHandler.readFromFile(filePath, POItem::fromString);
    }
}
