package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Transaction implements hasFile<Transaction>, hasId{
    private String transactionID;
    private String POID;
    private String bankFrom;
    private long bankFromAccountNumber;
    private String bankReceived;
    private long bankReceivedAccountNumber;
    private LocalDateTime dateTime;
    private double amount;
    private String details;
    private String processBy;
    
    private static final String filePath = "data/Transaction/transaction_record.txt";

    public Transaction() {
    }

    public Transaction(String POID, String bankFrom, long bankFromAccountNumber, String bankReceived, long bankReceivedAccountNumber, double amount, String details, String processBy) {
        this.transactionID = generateID();
        this.POID = POID;
        this.bankFrom = bankFrom;
        this.bankFromAccountNumber = bankFromAccountNumber;
        this.bankReceived = bankReceived;
        this.bankReceivedAccountNumber = bankReceivedAccountNumber;
        this.dateTime = LocalDateTime.now();
        this.amount = amount;
        this.details = details;
        this.processBy = processBy;
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

    public String getProcessBy() {
        return processBy;
    }

    public void setProcessBy(String processBy) {
        this.processBy = processBy;
    }

    public String getPOID() {
        return POID;
    }

    public void setPOID(String POID) {
        this.POID = POID;
    }
    
    public String toString() {
        return transactionID + "\t"
             + POID + "\t"
             + bankFrom + "\t"
             + bankFromAccountNumber + "\t"
             + bankReceived + "\t"
             + bankReceivedAccountNumber + "\t"
             + dateTime + "\t"
             + amount + "\t"
             + details + "\t"
             + processBy;
    }
    
    public static Transaction fromString(String line) {
        String[] parts = line.split("\t");
        Transaction tr = new Transaction();
        tr.setTransactionID(parts[0]);
        tr.setPOID(parts[1]);
        tr.setBankFrom(parts[2]);
        tr.setBankFromAccountNumber(Long.parseLong(parts[3]));
        tr.setBankReceived(parts[4]);
        tr.setBankReceivedAccountNumber(Long.parseLong(parts[5]));
        tr.setDateTime(LocalDateTime.parse(parts[6]));
        tr.setAmount(Double.parseDouble(parts[7]));
        tr.setDetails(parts[8]);
        tr.setProcessBy(parts[9]);

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
