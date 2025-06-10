package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Transaction implements hasFile<Transaction>, hasId{
    private String transactionID;
    private String bankFrom;
    private long bankFromAccountNumber;
    private String bankReceived;
    private long bankReceivedAccountNumber;
    private LocalDateTime dateTime;
    private double amount;
    private String details;
    
    private static final String filePath = "data/Transaction/transaction_record.txt";

    public Transaction() {
    }

    public Transaction(String transactionID, String bankFrom, long bankFromAccountNumber, String bankReceived, long bankReceivedAccountNumber, LocalDateTime dateTime, double amount, String details) {
        this.transactionID = transactionID;
        this.bankFrom = bankFrom;
        this.bankFromAccountNumber = bankFromAccountNumber;
        this.bankReceived = bankReceived;
        this.bankReceivedAccountNumber = bankReceivedAccountNumber;
        this.dateTime = dateTime;
        this.amount = amount;
        this.details = details;
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

    public long getBankReceivedAccountNumber() {
        return bankReceivedAccountNumber;
    }

    public void setBankReceivedAccountNumber(long bankReceivedAccountNumber) {
        this.bankReceivedAccountNumber = bankReceivedAccountNumber;
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

    public String getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(String bankFrom) {
        this.bankFrom = bankFrom;
    }

    public long getBankFromAccountNumber() {
        return bankFromAccountNumber;
    }

    public void setBankFromAccountNumber(long bankFromAccountNumber) {
        this.bankFromAccountNumber = bankFromAccountNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    public String toString() {
        return transactionID + "\t"
             + bankFrom + "\t"
             + bankFromAccountNumber + "\t"
             + bankReceived + "\t"
             + bankReceivedAccountNumber + "\t"
             + dateTime + "\t"
             + amount + "\t"
             + details;
    }
    
    public static Transaction fromString(String line) {
        String[] parts = line.split("\t");
        Transaction tr = new Transaction();
        tr.setTransactionID(parts[0]);
        tr.setBankFrom(parts[1]);
        tr.setBankFromAccountNumber(Long.parseLong(parts[2]));
        tr.setBankReceived(parts[3]);
        tr.setBankReceivedAccountNumber(Long.parseLong(parts[4]));
        tr.setDateTime(LocalDateTime.parse(parts[5]));
        tr.setAmount(Double.parseDouble(parts[6]));
        tr.setDetails(parts[7]);

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
