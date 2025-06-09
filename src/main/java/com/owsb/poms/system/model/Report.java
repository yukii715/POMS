package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.util.List;

public class Report implements hasFile<Report>{
    private String reportID;
    private type reportType;
    private String message;
    private LocalDateTime dateTime;
    
    private static final String filePath = "data/Reports/reports.txt";

    public enum type{
        StockReport,
        PurchaseReport
    }
    
    public Report() {
    }

    public Report(String reportID, type reportType, String message, LocalDateTime dateTime) {
        this.reportID = reportID;
        this.reportType = reportType;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getReportID() {
        return reportID;
    }

    public type getReportType() {
        return reportType;
    }

    public void setReportType(type reportType) {
        this.reportType = reportType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public String toString() {
        return reportID + "\t"
                + reportType + "\t"
                + message + "\t"
                + dateTime;
    }

    public static Report fromString(String line) {
        String[] parts = line.split("\t");

        Report report = new Report();
        report.setReportID(parts[0]);
        report.setReportType(type.valueOf(parts[1]));
        report.setMessage(parts[2]);
        report.setDateTime(LocalDateTime.parse(parts[3]));

        return report;
    }

    @Override
    public void saveToFile(List<Report> list) {
        FileHandler.saveToFile(filePath, list, Report::toString);
    }
    
    public static List<Report> toList(){
        return FileHandler.readFromFile(filePath, Report::fromString);
    }

    @Override
    public void add() {
        var report = toList();
        report.add(this);
        this.saveToFile(report);
    }
    
    public List<StockReport> getStockReport(){
//        String filePath = String.format("data/Reports/Stock/%s.txt", FileHandler.findFileByPrefix(StockReport.filePath, reportID))
        String filePath = String.format("%s", FileHandler.findFileByPrefix(StockReport.filePath, reportID));

        System.out.println(filePath);
        return StockReport.read(filePath);
    }
}
