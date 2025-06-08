package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.util.List;

public class PRItem extends PurchaseRequisition {
    private String itemID;
    private String itemCategory;
    private String itemType;
    private String itemName;
    private int quantity;
    
    private static final String filePath = "data/PR/";

    public PRItem(){
    }

    public PRItem(String itemID, String itemCategory, String itemType, String itemName) {
        this.itemID = itemID;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemName = itemName;
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
                + itemCategory + "\t"
                + itemType + "\t"
                + itemName + "\t"
                + quantity;
    }

    public static PRItem fromString(String line) {
        String[] parts = line.split("\t");

        PRItem pri = new PRItem();
        pri.setItemID(parts[0]);
        pri.setItemCategory(parts[1]);
        pri.setItemType(parts[2]);
        pri.setItemName(parts[3]);
        pri.setQuantity(Integer.parseInt(parts[4]));

        return pri;
    }
    
    public void save(List<PRItem> list) {
        String fileName = this.getPRID() + ".txt";
        String filePath = this.filePath + fileName;
        
        FileHandler.saveToFile(filePath, list, PRItem::toString);
    }
    
    public static List<PRItem> read(String filePath){
        return FileHandler.readFromFile(filePath, PRItem::fromString);
    }
}
