package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.time.*;
import java.util.List;
import com.owsb.poms.system.functions.interfaces.*;

public class PurchaseRequisition implements hasFile<PurchaseRequisition>, hasId, hasStatus{
    private String PRID;
    private String SupplierID;
    private LocalDateTime requestDateTime;
    private LocalDate requiredDeliveryDate;
    private Status status;
    private String createBy;
    
    private static final String filePath = "data/PR/purchase_requisition.txt";
    
    public enum Status{
        NEW,
        APPROVED,
        RETURNED
    }

    public PurchaseRequisition() {
    }

    public PurchaseRequisition(String SupplierID, LocalDate requiredDeliveryDate, String createBy) {
        this.PRID = generateID();
        this.SupplierID = SupplierID;
        this.requiredDeliveryDate = requiredDeliveryDate;
        this.status = Status.NEW;
        this.createBy = createBy;
    }

    public String getPRID() {
        return PRID;
    }

    public void setPRID(String PRID) {
        this.PRID = PRID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String SupplierID) {
        this.SupplierID = SupplierID;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(LocalDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public LocalDate getRequiredDeliveryDate() {
        return requiredDeliveryDate;
    }

    public void setRequiredDeliveryDate(LocalDate requiredDeliveryDate) {
        this.requiredDeliveryDate = requiredDeliveryDate;
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
    
    @Override
    public String toString() {
        return PRID + "\t"
             + SupplierID + "\t"
             + requestDateTime.toString() + "\t"
             + requiredDeliveryDate.toString() + "\t"
             + status.name() + "\t"
             + createBy; 
    }

    public static PurchaseRequisition fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid input line for PurchaseRequisition: " + line);
        }

        PurchaseRequisition pr = new PurchaseRequisition();
        pr.setPRID(parts[0]);
        pr.setSupplierID(parts[1]);
        pr.setRequestDateTime(LocalDateTime.parse(parts[2]));
        pr.setRequiredDeliveryDate(LocalDate.parse(parts[3]));
        pr.setStatus(Status.valueOf(parts[4]));
        pr.setCreateBy(parts[5]);

        return pr;
    }

    
    @Override
    public String generateID() {
        String prefix = "PR";
        int length = 5;
        int startNum = IdGenerator.getTotal(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }

    @Override
    public void saveToFile(List<PurchaseRequisition> list) {
        FileHandler.saveToFile(filePath, list, PurchaseRequisition::toString);
    }
    
    public static List<PurchaseRequisition> toList(){
        return FileHandler.readFromFile(filePath, PurchaseRequisition::fromString);
    }

    @Override
    public void add() {
        var prs = toList();
        prs.add(this);
        this.saveToFile(prs);
    }
    
    @Override
    public void updateStatus(){
        DataHandler.updateFieldAndSave(
                toList(),
                pr -> pr.getPRID().equals(this.getPRID()),              
                pr -> pr.setStatus(this.getStatus()),           
                list -> this.saveToFile(list)
        );
    }
}
