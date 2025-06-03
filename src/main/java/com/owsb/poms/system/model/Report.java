package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.util.List;

public class Report implements hasFile<Report>{
    private String reportID;
    private LocalDateTime dateTime;
    
    private static final String filePath = "data/Reports/reports.txt";

    public Report() {
    }

    public String getReportID() {
        return reportID;
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
                + dateTime;
    }

    public static Report fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid input line for PurchaseRequisition: " + line);
        }

        Report report = new Report();
        report.setReportID(parts[0]);
        report.setDateTime(LocalDateTime.parse(parts[1]));

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
}
