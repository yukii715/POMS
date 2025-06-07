package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.hasId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockReport extends Report implements hasId{
    private String itemID;
    private String itemCategory;
    private String itemType;
    private String itemName;
    private int stock;
    private Item.Status status;

    public static final String filePath = "data/Reports/Stock/";

    public StockReport() {
    }

    public StockReport(String itemID, String itemName, String itemCategory, String itemType, int stock, Item.Status status) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.stock = stock;
        this.status = status;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Item.Status getStatus() {
        return status;
    }

    public void setStatus(Item.Status status) {
        this.status = status;
    }

    public static List<StockReport> generateStockList(List<Item> itemList) {
        List<StockReport> stockReportList = new ArrayList<>();
        for (Item item : itemList) {
            if (!Item.Status.REMOVED.equals(item.getStatus())) {
                StockReport report = new StockReport(
                        item.getItemID(),
                        item.getItemName(),
                        item.getItemCategory(),
                        item.getItemType(),
                        item.getStock(),
                        item.getStatus());
                stockReportList.add(report);
            }
        }
        return stockReportList;
    }
    
    public String toString() {
        return itemID + "\t" + itemName + "\t" + itemCategory + "\t" + itemType + "\t"
                + stock + "\t" + status;
    }
    
    public static StockReport fromString(String line) {
        String[] parts = line.split("\t");

        StockReport sr = new StockReport();
        sr.setItemID(parts[0]);
        sr.setItemCategory(parts[1]);
        sr.setItemType(parts[2]);
        sr.setItemName(parts[3]);
        sr.setStock(Integer.parseInt(parts[4]));
        sr.setStatus(Item.Status.valueOf(parts[5]));

        return sr;
    }

    public void save(List<StockReport> stockReport) {
        Report report = new Report();
        report.setReportID(generateID());
        report.setDateTime(LocalDateTime.now());

        // Create destination file name with date
        String fileName = report.getReportID() +"_"+ report.getDateTime().toString().replace(":", "-").replace(".", "-") + ".txt";
        String filePath = this.filePath + fileName;

        report.add();
        FileHandler.saveToFile(filePath, stockReport, StockReport::toString);
    }
    
    @Override
    public String generateID() {
        String prefix = "STK";
        int length = 5;
        int startNum = IdGenerator.getTotalByFolder(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }
    
    public static List<StockReport> read(String filePath){
        return FileHandler.readFromFile(filePath, StockReport::fromString);
    }
}
