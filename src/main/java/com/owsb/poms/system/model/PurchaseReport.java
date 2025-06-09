package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseReport implements hasId, hasFile<PurchaseReport>{
    private String reportID;
    private String POID;
    private String invoiceID;
    private String transactionID;
    private LocalDateTime dateTime;
    private String verifiedBy;
    private String paymentProcessBy;
    private String message;
    
    public static final String filePath = "data/Reports/Purchase/purchase_report.txt";

    public PurchaseReport() {
    }

    public PurchaseReport(String reportID, String POID, String invoiceID, String transactionID, String verifiedBy, String paymentProcessBy, String message) {
        this.reportID = reportID;
        this.POID = POID;
        this.invoiceID = invoiceID;
        this.transactionID = transactionID;
        this.dateTime = LocalDateTime.now();
        this.verifiedBy = verifiedBy;
        this.paymentProcessBy = paymentProcessBy;
        this.message = message;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public String getPaymentProcessBy() {
        return paymentProcessBy;
    }

    public void setPaymentProcessBy(String paymentProcessBy) {
        this.paymentProcessBy = paymentProcessBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String toString() {
        return reportID + "\t"
             + POID + "\t"
             + invoiceID + "\t"
             + transactionID + "\t"
             + dateTime + "\t"
             + verifiedBy + "\t"
             + paymentProcessBy;
    }
    
    public static PurchaseReport fromString(String line) {
        String[] parts = line.split("\t");

        PurchaseReport pur = new PurchaseReport();
        pur.setReportID(parts[0]);
        pur.setPOID(parts[1]);
        pur.setInvoiceID(parts[2]);
        pur.setTransactionID(parts[3]);
        pur.setDateTime(LocalDateTime.parse(parts[4]));
        pur.setVerifiedBy(parts[5]);
        pur.setPaymentProcessBy(parts[6]);

        return pur;
    }
    
    @Override
    public String generateID() {
        String prefix = "PUR";
        int length = 5;
        int startNum = IdGenerator.getTotalByFolder(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }

    @Override
    public void saveToFile(List<PurchaseReport> list) {
        FileHandler.saveToFile(filePath, list, PurchaseReport::toString);
    }
    
    public static List<PurchaseReport> toList(){
        return FileHandler.readFromFile(filePath, PurchaseReport::fromString);
    }
    
    @Override
    public void add() {
        Report report = new Report(this.getReportID(), Report.type.PurchaseReport, this.getMessage(), this.getDateTime());
        report.add();
        
        var purs = toList();
        purs.add(this);
        this.saveToFile(purs);
    }
}
