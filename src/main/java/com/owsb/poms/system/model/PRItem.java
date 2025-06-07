package com.owsb.poms.system.model;


public class PRItem extends PurchaseRequisition{
    private String itemID;
    private int quantity;
    
    private static final String filePath = "data/PR/items.txt";

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
}
