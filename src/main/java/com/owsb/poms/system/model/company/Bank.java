package com.owsb.poms.system.model.company;

import com.owsb.poms.system.functions.DataHandler;
import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.hasFile;
import java.util.List;

public class Bank extends OWSB implements hasFile<Bank>{
    private String bankName;
    private long accountNumber;
    private boolean isDeleted;
    
    private static final String filePath = "data/OWSB/bank.txt";

    public Bank() {
    }
    
    public Bank(String bankName, long accountNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.isDeleted = false;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    @Override
    public String toString() {
        return bankName + "\t"
             + accountNumber + "\t"
             + isDeleted;
    }
    
    public static Bank fromString(String line) {
        String[] parts = line.split("\t");

        Bank bank = new Bank();
        bank.setBankName(parts[0]);
        bank.setAccountNumber(Long.parseLong(parts[1]));
        bank.setIsDeleted(Boolean.parseBoolean(parts[2]));

        return bank;
    }

    @Override
    public void saveToFile(List<Bank> list) {
        FileHandler.saveToFile(filePath, list, Bank::toString);
    }
    
    public static List<Bank> toList(){
        return FileHandler.readFromFile(filePath, Bank::fromString);
    }

    @Override
    public void add() {
        var banks = toList();
        banks.add(this);
        this.saveToFile(banks);
    }
    
    public static Bank getBankByName(String name){
        return DataHandler.getByKey(toList(), name, Bank::getBankName);
    }
    
    public void update(){
        DataHandler.updateFieldAndSave(
                toList(),
                bank -> bank.getBankName().equals(this.getBankName()),              
                bank -> {
                    bank.setAccountNumber(this.getAccountNumber());
                    bank.setIsDeleted(this.isIsDeleted());
                    },           
                list -> this.saveToFile(list)
        );
    }
}
