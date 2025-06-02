package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.util.List;

public class Item implements CommonModel<Item>{
    
    private String itemID;
    private String itemName;
    private String itemCategory;
    private String itemType;
    private double sellPrice;
    private int stock;
    private Status status;
    
    private static final String filePath = "data/Items/items.txt";
    
    public enum Status{
        NEW,
        SUFFICIENT,
        SHORTAGE,
        REMOVED
    }

    // Constructor
    public Item(String itemCategory, String itemType, String itemName, double sellPrice) {
        this.itemID = generateID();
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.sellPrice = sellPrice;
        this.stock = 0;
        this.status = Status.NEW;
    }

    // Default constructor
    public Item() {
    }

    // Getters and Setters
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
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

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return itemID + "\t" + itemName + "\t" + itemCategory + "\t" + itemType + "\t" +
                sellPrice + "\t" + stock + "\t" + status;
    }
    
    public static Item fromString(String line) {String[] parts = line.split("\t");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid input line for Item: " + line);
        }
        Item item = new Item();
        item.setItemID(parts[0]);
        item.setItemName(parts[1]);
        item.setItemCategory(parts[2]);
        item.setItemType(parts[3]);
        item.setSellPrice(Double.parseDouble(parts[4]));
        item.setStock(Integer.parseInt(parts[5]));
        item.setStatus(Status.valueOf(parts[6]));
        return item;
    }
    
    @Override
    public String generateID(){
        String prefix = "ITM";
        int length = 5;
        int startNum = IdGenerator.getTotal(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }
    
    @Override
    public void saveToFile(List<Item> list){
        FileHandler.saveToFile(filePath, list, Item::toString);
    }
    
    public static List<Item> toList(){
        return FileHandler.readFromFile(filePath, Item::fromString);
    }
    
    public static List<String> getAllCategories(){
        return DataHandler.extractUniqueField(toList(), Item::getItemCategory);
    }
    
    public static List<String> getAllTypes(String category){
        return DataHandler.filterAndExtractUnique(toList(), item -> item.getItemCategory().equals(category), Item::getItemType);
    }
    
    @Override
    public void add(){
        var items = toList();
        items.add(this);
        this.saveToFile(items);
    }
    
    public void updateStatus(){
        DataHandler.updateFieldAndSave(
                toList(),
                item -> item.getItemID().equals(this.getItemID()),              
                item -> item.setStatus(this.getStatus()),           
                list -> this.saveToFile(list)
        );
    }
    
    public void updateStock(){
        DataHandler.updateFieldAndSave(
                toList(),
                item -> item.getItemID().equals(this.getItemID()),              
                item -> item.setStock(this.getStock()),           
                list -> this.saveToFile(list)
        );
    }
    
    public void edit(){
        DataHandler.updateFieldAndSave(
                toList(),
                item -> item.getItemID().equals(this.getItemID()),              
                item -> {
                    item.setItemName(this.getItemName());
                    item.setSellPrice(this.getSellPrice());
                    },           
                list -> this.saveToFile(list)
        );
    }
}
