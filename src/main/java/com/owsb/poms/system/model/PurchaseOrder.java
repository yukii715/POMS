package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.time.*;
import java.util.List;
import com.owsb.poms.system.functions.interfaces.*;

public class PurchaseOrder implements hasFile<PurchaseOrder>, hasId, hasStatus{
    private String POID;
    private String PRID;
    private double totalPrice;
    private String supplierID;
    private LocalDateTime generatedDateTime;
    private LocalDate deliveryDate;
    private Status status;
    private String createBy;
    private String performedBy;
    private String remark;
    
    private static final String filePath = "data/PO/purchase_order.txt";
    
    public enum Status{
        NEW,        // approve, reject or delete
        APPROVED,   // processing or cancel
        REJECTED,   // set as new
        DELETED,    // ok
        PROCESSING, // arrived, extend or cancel
        EXTENDED,   // arrived, extend or cancel
        ARRIVED,    // verified or invalid
        VERIFIED,   // invalid or confirm
        INVALID,    // extend or cancel
        CONFIRMED,  // process payment
        COMPLETED,  // generate report
        CANCELLED,  // ok
        REPORTED    // ok
    }
    
    public PurchaseOrder(){
    }

    public PurchaseOrder(String PRID, double totalPrice, String supplierID, LocalDate deliveryDate, String createBy, String remark) {
        this.POID = generateID();
        this.PRID = PRID;
        this.totalPrice = totalPrice;
        this.supplierID = supplierID;
        this.generatedDateTime = LocalDateTime.now();
        this.deliveryDate = deliveryDate;
        this.status = status.NEW;
        this.createBy = createBy;
        this.performedBy = "None";
        this.remark = remark;
    }

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }

    public String getPRID() {
        return PRID;
    }

    public void setPRID(String PRID) {
        this.PRID = PRID;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public String toString() {
        return POID + "\t"
             + PRID + "\t"
             + totalPrice + "\t"
             + supplierID + "\t"
             + generatedDateTime.toString() + "\t"
             + deliveryDate.toString() + "\t"
             + status.name() + "\t"
             + createBy + "\t"
             + performedBy + "\t"
             + remark;
    }
    
    public static PurchaseOrder fromString(String line) {
        String[] parts = line.split("\t");

        PurchaseOrder po = new PurchaseOrder();
        po.setPOID(parts[0]);
        po.setPRID(parts[1]);
        po.setTotalPrice(Double.parseDouble(parts[2]));
        po.setSupplierID(parts[3]);
        po.setGeneratedDateTime(LocalDateTime.parse(parts[4]));
        po.setDeliveryDate(LocalDate.parse(parts[5]));
        po.setStatus(Status.valueOf(parts[6]));
        po.setCreateBy(parts[7]);
        po.setPerformedBy(parts[8]);
        po.setRemark(parts[9]);

        return po;
    }

    
    @Override
    public String generateID() {
        String prefix = "PO";
        int length = 5;
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
        var pos = toList();
        pos.add(this);
        this.saveToFile(pos);
    }
    
    @Override
    public void updateStatus(){
        DataHandler.updateFieldAndSave(
                toList(),
                po -> po.getPOID().equals(this.getPOID()),              
                po -> {
                    po.setStatus(this.getStatus());
                    po.setPerformedBy(this.getPerformedBy());
                    po.setTotalPrice(this.getTotalPrice());
                    po.setDeliveryDate(this.getDeliveryDate());
                    po.setRemark(this.getRemark());
                            },             
                list -> this.saveToFile(list)
        );
    }
    
    public List<POItem> getItems(){
        String filePath = String.format("data/PO/%s.txt", getPOID());
        return POItem.read(filePath);
    }
    
    public static PurchaseOrder getPoById(String id){
        return DataHandler.getByKey(toList(), id, PurchaseOrder::getPOID);
    }
}
