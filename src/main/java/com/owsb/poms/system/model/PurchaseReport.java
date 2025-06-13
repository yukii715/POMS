package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseReport implements hasId, hasFile<PurchaseReport>{
    private String reportID;
    private String POID;
    private String invoiceID;
    private LocalDateTime dateTime;
    private String verifiedBy;
    private String message;
    
    public static final String filePath = "data/Reports/Purchase/purchase_report.txt";

    public PurchaseReport() {
    }

    public PurchaseReport(String POID, String invoiceID, String verifiedBy, String message) {
        this.reportID = generateID();
        this.POID = POID;
        this.invoiceID = invoiceID;
        this.dateTime = LocalDateTime.now();
        this.verifiedBy = verifiedBy;
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
             + dateTime + "\t"
             + verifiedBy;
    }
    
    public static PurchaseReport fromString(String line) {
        String[] parts = line.split("\t");

        PurchaseReport pur = new PurchaseReport();
        pur.setReportID(parts[0]);
        pur.setPOID(parts[1]);
        pur.setInvoiceID(parts[2]);
        pur.setDateTime(LocalDateTime.parse(parts[3]));
        pur.setVerifiedBy(parts[4]);

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
