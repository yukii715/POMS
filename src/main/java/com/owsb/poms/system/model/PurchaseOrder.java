
package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.model.*;
import static com.owsb.poms.system.model.Item.toList;
import java.time.LocalDateTime;
import java.util.*;

public class PurchaseOrder implements CommonModel<PurchaseOrder>{
    private String PoID;
    private String PrID;
    private String itemID;
    private String itemName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private String supplierID;
    private LocalDateTime generatedDateTime;
    private Status status;
    private String createdBy;
    private String approvedBy;
    

    private static final String filePath = "data/PurchaseOrders/PurchaseOrders.txt";

    public PurchaseOrder(){

    }

    
    public enum Status{
    Pending, Approved, Unapproved, Processing, Extend, Verifying, Verified, Completed, Cancelled
    }
    public PurchaseOrder(String PrID, String itemID, String itemName, int quantity, double unitPrice,double totalPrice, String supplierID, String createdBy, String approvedBy){
        this.PoID = generateID();
        this.PrID = PrID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.supplierID = supplierID;
        this.createdBy = createdBy;
        this.approvedBy = approvedBy;
    }

    public String getPoID() {
        return PoID;
    }

    public void setPoID(String PoID) {
        this.PoID = PoID;
    }
        public String getPrID() {
        return PrID;
    }

    public void setPrID(String PrID) {
        this.PrID = PrID;
    }

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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public LocalDateTime getGeneratedDateTime() {
        return generatedDateTime;
    }

    public void setGeneratedDateTime(LocalDateTime generatedDateTime) {
        this.generatedDateTime = generatedDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
        @Override
    public String toString() {
        return PoID + "\t" + PrID + "\t" + itemID + "\t" + itemName + "\t" + quantity + "\t" + unitPrice + "\t" +
                totalPrice + "\t" + supplierID + "\t" + generatedDateTime + "\t" + status + "\t" + createdBy + "\t" + approvedBy;
    }
    
    public static PurchaseOrder fromString(String line) {String[] parts = line.split("\t");
        if (parts.length != 12) {
            throw new IllegalArgumentException("Invalid input line for Item: " + line);
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setPoID(parts[0]);
        purchaseOrder.setPrID(parts[1]);
        purchaseOrder.setItemID(parts[2]);
        purchaseOrder.setItemName(parts[3]);
        purchaseOrder.setQuantity(Integer.parseInt(parts[4]));
        purchaseOrder.setUnitPrice(Double.parseDouble(parts[5]));
        purchaseOrder.setTotalPrice(Double.parseDouble(parts[6]));
        purchaseOrder.setSupplierID(parts[7]);
        purchaseOrder.setGeneratedDateTime(LocalDateTime.parse(parts[8]));
        purchaseOrder.setStatus(Status.valueOf(parts[9]));
        purchaseOrder.setCreatedBy(parts[10]);
        purchaseOrder.setApprovedBy(parts[11]);
        return purchaseOrder;
    }
    @Override
 public String generateID() {
        String prefix = "PO";
        int length = 3;
        int startNum = IdGenerator.getTotal(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }
 
    @Override
    public void saveToFile(List<PurchaseOrder> list) {
        FileHandler.saveToFile(filePath, list, PurchaseOrder::toString);
    }

    public static List<PurchaseOrder> toList(){
        return FileHandler.readFromFile(filePath, PurchaseOrder::fromString);
    }
    @Override
    public void add() {
        var purchaseOrder = toList();
        purchaseOrder.add(this);
        this.saveToFile(purchaseOrder);
    }
}

   