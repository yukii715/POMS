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

    private static final String filePath = "data/Reports/Stock/";

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
}
