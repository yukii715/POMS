package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.owsb.poms.system.functions.interfaces.*;

public class Supplier implements hasFile<Supplier>, hasId{
    private String supplierID;
    private String supplierName;
    private LocalDateTime addedTime;
    public boolean isDeleted;
    
    private static final String filePath = "data/Suppliers/suppliers.txt";
    
    public Supplier(){
    }
    
    public Supplier(String supplierName) {
        this.supplierID = generateID();
        this.supplierName = supplierName;
        this.addedTime = LocalDateTime.now();
        this.isDeleted = false;
    }
    
    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public LocalDateTime getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(LocalDateTime addedTime) {
        this.addedTime = addedTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    @Override
    public String toString() {
        return supplierID + "\t" + supplierName + "\t" + addedTime.toString() + "\t" + isDeleted;
    }
    
    public static Supplier fromString(String line) {
        String[] parts = line.split("\t");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid input line for Supplier: " + line);
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierID(parts[0]);
        supplier.setSupplierName(parts[1]);
        supplier.setAddedTime(LocalDateTime.parse(parts[2]));
        supplier.setIsDeleted(Boolean.parseBoolean(parts[3]));

        return supplier;
    }


    @Override
    public String generateID() {
        String prefix = "SUP";
        int length = 3;
        int startNum = IdGenerator.getTotal(filePath);
        return IdGenerator.generateID(prefix, length, startNum);
    }

    @Override
    public void saveToFile(List<Supplier> list) {
        FileHandler.saveToFile(filePath, list, Supplier::toString);
    }

    public static List<Supplier> toList(){
        return FileHandler.readFromFile(filePath, Supplier::fromString);
    }

    @Override
    public void add() {
        var suppliers = toList();
        suppliers.add(this);
        this.saveToFile(suppliers);
    }
    
    public void remove(){
        DataHandler.updateFieldAndSave(
                toList(),
                supplier -> supplier.getSupplierID().equals(this.getSupplierID()),              
                supplier -> supplier.setIsDeleted(true),           
                list -> this.saveToFile(list)
        );
    }
    
    public void changeName(){
        DataHandler.updateFieldAndSave(
                toList(),
                supplier -> supplier.getSupplierID().equals(this.getSupplierID()),              
                supplier -> supplier.setSupplierName(this.getSupplierName()),           
                list -> this.saveToFile(list)
        );
    }
    
    public static List<String> getAllSuppliers(){
        return toList().stream().map(Supplier::getSupplierName).collect(Collectors.toList());
    }
    
    public static String getIdByName(String name){
        return DataHandler.getValueByKey(toList(), Supplier::getSupplierName, name, Supplier::getSupplierID);
    }
    
    public static String getNameById(String id){
        return DataHandler.getValueByKey(toList(), Supplier::getSupplierID, id, Supplier::getSupplierName);
    }
}
