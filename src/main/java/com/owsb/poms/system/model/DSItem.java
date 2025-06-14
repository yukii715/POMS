package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import java.time.LocalDate;
import java.util.List;

public class DSItem extends DailySales{
    private String itemID;
    private String itemCategory;
    private String itemType;
    private String itemName;
    private int quantity;
    private int stock;
    private double sellPrice;
    
    private static final String filePath = "data/Sales/";

    public DSItem() {
    }

    public DSItem(String itemID, String itemCategories, String itemType, String itemName, int quantity, int stock, double sellPrice) {
        this.itemID = itemID;
        this.itemCategory = itemCategories;
        this.itemType = itemType;
        this.itemName = itemName;
        this.quantity = quantity;
        this.stock = stock;
        this.sellPrice = sellPrice;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
    
    @Override
    public String toString() {
        return itemID + "\t"
                + itemCategory + "\t"
                + itemType + "\t"
                + itemName + "\t"
                + quantity + "\t"
                + stock + "\t"
                + sellPrice;
    }

    public static DSItem fromString(String line) {
        String[] parts = line.split("\t");

        DSItem dsi = new DSItem();
        dsi.setItemID(parts[0]);
        dsi.setItemCategory(parts[1]);
        dsi.setItemType(parts[2]);
        dsi.setItemName(parts[3]);
        dsi.setQuantity(Integer.parseInt(parts[4]));
        dsi.setStock(Integer.parseInt(parts[5]));
        dsi.setSellPrice(Double.parseDouble(parts[6]));

        return dsi;
    }
    
    public void save(List<DSItem> list, LocalDate date, double income ) {
        DailySales ds = new DailySales(date, income);
        ds.setSalesID(ds.generateID());
        
        String fileName = ds.getSalesID()+ ".txt";
        String filePath = this.filePath + fileName;
        
        ds.add();
        FileHandler.saveToFile(filePath, list, DSItem::toString);
    }
    
    public void update(List<DSItem> list, LocalDate date, double income ,DailySales sales) {
        String fileName = sales.getSalesID()+ ".txt";
        String filePath = this.filePath + fileName;
        
        sales.setTotalIncome(income);
        sales.update();
        FileHandler.saveToFile(filePath, list, DSItem::toString);
    }
    
    public static List<DSItem> read(String fileName){
        return FileHandler.readFromFile(filePath + fileName, DSItem::fromString);
    }
}
