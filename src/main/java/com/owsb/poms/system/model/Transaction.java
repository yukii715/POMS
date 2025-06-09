package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Transaction implements hasFile<Transaction>, hasId{
    private String transactionID;
    private String bankReceived;
    private long bankAccountNumber;
    private LocalDateTime dateTime;
    private double amount;
    
    private static final String filePath = "data/Transaction/transaction_record.txt";

    public Transaction() {
    }

    public Transaction(String transactionID, String bankReceived, long bankAccountNumber, LocalDateTime dateTime, double amount) {
        this.transactionID = transactionID;
        this.bankReceived = bankReceived;
        this.bankAccountNumber = bankAccountNumber;
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getBankReceived() {
        return bankReceived;
    }

    public void setBankReceived(String bankReceived) {
        this.bankReceived = bankReceived;
    }

    public long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String toString() {
        return transactionID + "\t"
             + bankReceived + "\t"
             + bankAccountNumber + "\t"
             + dateTime + "\t"
             + amount;
    }
    
    public static Transaction fromString(String line) {
        String[] parts = line.split("\t");

        Transaction tr = new Transaction();
        tr.setTransactionID(parts[0]);
        tr.setBankReceived(parts[1]);
        tr.setBankAccountNumber(Long.parseLong(parts[2]));
        tr.setDateTime(LocalDateTime.parse(parts[3]));
        tr.setAmount(Double.parseDouble(parts[4]));

        return tr;
    }

    @Override
    public void saveToFile(List<Transaction> list) {
        FileHandler.saveToFile(filePath, list, Transaction::toString);
    }
    
    public static List<Transaction> toList(){
        return FileHandler.readFromFile(filePath, Transaction::fromString);
    }

    @Override
    public void add() {
        var trs = toList();
        trs.add(this);
        this.saveToFile(trs);
    }

    @Override
    public String generateID() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ssmmHHddMMyyyy"));
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // 8-character random

        return "TRS-" + timestamp + "-" + randomPart;
    }
    
    
}
