package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DailySales implements hasId, hasFile<DailySales>{
    private String salesID;
    private LocalDate date;
    private List<LocalDateTime> updatedDateTimeList;
    private double totalIncome;
    
    private static final String filePath = "data/Sales/daily_sales.txt";

    public DailySales() {
    }

    public DailySales(String salesID, LocalDate date, double totalIncome) {
        this.salesID = salesID;
        this.date = date;
        this.updatedDateTimeList.add(LocalDateTime.now());
        this.totalIncome = totalIncome;
    }
    

    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List <LocalDateTime> getUpdatedDateTimeList() {
        return updatedDateTimeList;
    }

    public void setUpdatedDateTimeList(List<LocalDateTime> updatedDateTimeList) {
        this.updatedDateTimeList = updatedDateTimeList;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }
    
    @Override
    public String toString() {
        return salesID + "\t"
             + date + "\t"
             + updatedDateTimeList + "\t"
             + totalIncome;
    }
    
    public static DailySales fromString(String line) {
        String[] parts = line.split("\t");

        DailySales ds = new DailySales();
        ds.setSalesID(parts[0]);
        ds.setDate(LocalDate.parse(parts[1]));
        ds.setTotalIncome(Double.parseDouble(parts[2]));
        ds.setUpdatedDateTimeList(Arrays.stream(parts[3].replaceAll("^\\[|\\]$", "").split(","))
                                      .map(String::trim)
                                      .map(LocalDateTime::parse)
                                      .collect(Collectors.toList()));

        return ds;
    }

    @Override
    public String generateID() {
        return String.format("DS%s", this.getDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    @Override
    public void saveToFile(List<DailySales> list) {
        FileHandler.saveToFile(filePath, list, DailySales::toString);
    }
    
    public static List<DailySales> toList(){
        return FileHandler.readFromFile(filePath, DailySales::fromString);
    }

    @Override
    public void add() {
        var dss = toList();
        dss.add(this);
        this.saveToFile(dss);
    }
    
    
}
