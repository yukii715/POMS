package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.hasId;
import java.time.LocalDateTime;
import java.util.List;

public class StockUpdateReport extends Report implements hasId{
    private String itemID;
    private String itemCategory;
    private String itemType;
    private String itemName;
    private int stock;
    private int update;

    public static final String filePath = "data/Reports/StockUpdate/";

    public StockUpdateReport() {
    }

    public StockUpdateReport(String itemID, String itemCategory, String itemType, String itemName, int stock, int update) {
        this.itemID = itemID;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.itemName = itemName;
        this.stock = stock;
        this.update = update;
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

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    @Override
    public String generateID() {
        String prefix = "SUP";
        int length = 5;
        int startNum = IdGenerator.getTotalByFolder(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }
    
    public String toString() {
        return itemID + "\t" + itemName + "\t" + itemCategory + "\t" + itemType + "\t"
                + stock + "\t" + update;
    }
    
    public static StockUpdateReport fromString(String line) {
        String[] parts = line.split("\t");

        StockUpdateReport sur = new StockUpdateReport();
        sur.setItemID(parts[0]);
        sur.setItemCategory(parts[1]);
        sur.setItemType(parts[2]);
        sur.setItemName(parts[3]);
        sur.setStock(Integer.parseInt(parts[4]));
        sur.setUpdate(Integer.parseInt(parts[5]));

        return sur;
    }

    public void save(List<StockUpdateReport> stockUpdateReport, String mesage) {
        Report report = new Report();
        report.setReportID(generateID());
        report.setDateTime(LocalDateTime.now());
        report.setReportType(type.StockUpdateReport);
        report.setMessage(mesage);

        String fileName = report.getReportID()+ ".txt";
        String filePath = this.filePath + fileName;
        
        report.add();
        FileHandler.saveToFile(filePath, stockUpdateReport, StockUpdateReport::toString);
    }
    
    public static List<StockUpdateReport> read(String filePath){
        return FileHandler.readFromFile(filePath, StockUpdateReport::fromString);
    }
}
