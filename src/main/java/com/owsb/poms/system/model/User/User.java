package com.owsb.poms.system.model.User;

import com.owsb.poms.system.functions.*;
import com.owsb.poms.system.functions.interfaces.*;
import com.owsb.poms.system.model.company.OWSB;
import com.owsb.poms.ui.common.Login;
import java.time.*;
import java.util.List;
import javax.swing.*;

public class User implements hasFile<User>{
    private String UID;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;
    private LocalDateTime creationDateTime;
    private LocalDate birthday;
    private boolean isDeleted;
    
    private static final String filePath = "data/Users/users.txt";

    public enum Role{
        Root,
        Admin,
        SalesManager,
        PurchaseManager,
        InventoryManager,
        FinanceManager
    }
    
    public User() {
    }

    public User(String UID, String username, String email, String password, Role role, LocalDate birthday) {
        this.UID = UID;
        this.username = username;
        this.email = email;
        this.passwordHash = getHash(password);
        this.role = role;
        this.creationDateTime = LocalDateTime.now();
        this.birthday = birthday;
        this.isDeleted = false;
    }

    public User(String UID, String username, String email, String passwordHash, Role role, LocalDateTime creationDateTime, LocalDate birthday, boolean isDeleted) {
        this.UID = UID;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.creationDateTime = creationDateTime;
        this.birthday = birthday;
        this.isDeleted = isDeleted;
    }
    

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public int getAge() {
        return Period.between(birthday, LocalDate.now()).getYears();
    }
    
    public String getHash(String password){
        return SecurePassword.encrypt(SecurePassword.sha256(SecurePassword.sha256(password)), OWSB.companyName);
    }
    
    @Override
    public String toString() {
        return UID + "\t" + username + "\t" + email + "\t" + role + "\t" + 
                creationDateTime + "\t" + birthday + "\t" + isDeleted + "\t" + passwordHash;
    }
    
    public static User fromString(String line) {String[] parts = line.split("\t");
        User user = new User();
        
        user.setUID(parts[0]);
        user.setUsername(parts[1]);
        user.setEmail(parts[2]);
        user.setRole(Role.valueOf(parts[3]));
        user.setCreationDateTime(LocalDateTime.parse(parts[4]));
        user.setBirthday(LocalDate.parse(parts[5]));
        user.setIsDeleted(Boolean.parseBoolean(parts[6]));
        user.setPasswordHash(parts[7]);
        
        return user;
    }
    
    @Override
    public void saveToFile(List<User> list) {
        FileHandler.saveToFile(filePath, list, User::toString);
    }
    
    public static List<User> toList(){
        return FileHandler.readFromFile(filePath, User::fromString);
    }

    @Override
    public void add() {
        var users = toList();
        users.add(this);
        this.saveToFile(users);
    }
    
    public String generateUID(Role role) {
         String prefix = "";
         int maxId = 0;
         
         switch (role){
            case Admin: prefix = "AD"; break;
            case SalesManager: prefix = "SM"; break;
            case PurchaseManager: prefix = "PM"; break;
            case InventoryManager: prefix = "IM"; break;
            case FinanceManager: prefix = "FM"; break;
         }
         
         for (User user : toList()){
             if (user.getUID().startsWith(prefix)){
                 maxId ++;
             }
         }
         
         return String.format("%s%03d", prefix, maxId + 1);
    }
    
    public String generateEmail(String username, String id){
        return String.format("%s-%s@owsb.com.my", username, id);
    }
    
    public static LocalDate getBirthdayByString(String birthday){
        int year = Integer.parseInt(birthday.substring(0, 4));
        int month = Integer.parseInt(birthday.substring(4, 6));
        int day = Integer.parseInt(birthday.substring(6, 8));
        return LocalDate.of(year, month, day);
    }
    
    public static User getUserById(String id){
        return DataHandler.getByKey(toList(), id, User::getUID);
    }
    
    public static User getUserByName(String name){
        return DataHandler.getByKey(toList(), name, User::getUsername);
    }
    
    public static User getUserByEmail(String email){
        return DataHandler.getByKey(toList(), email, User::getEmail);
    }
    
    public static User getUserBySimilarName(String name){
        for (User user : toList()){
            if (user.getUsername().replaceAll("\\s+", "").equalsIgnoreCase(name.replaceAll("\\s+", ""))){
                return user;
            }
        }
        return null;
    }
    
    public User getActual(){
        switch(getRole()){
            case Root, Admin:
                return new Admin(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
            case SalesManager:
                return new SalesManager(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
            case PurchaseManager:
                return new PurchaseManager(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
            case InventoryManager:    
                return new InventoryManager(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
            case FinanceManager:
                return new FinanceManager(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
            default:
                return new User(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
        }
    }
    
    public void changePassword(String password){
        DataHandler.updateFieldAndSave(
                toList(),
                user -> user.getUID().equals(this.getUID()),              
                user -> user.setPasswordHash(this.getHash(password)),           
                list -> this.saveToFile(list)
        );
    }
    
    public void changeUsername(){
        DataHandler.updateFieldAndSave(
                toList(),
                user -> user.getUID().equals(this.getUID()),              
                user -> user.setUsername(this.getUsername()),           
                list -> this.saveToFile(list)
        );
    }
    
    public void changeBirthday(){
        DataHandler.updateFieldAndSave(
                toList(),
                user -> user.getUID().equals(this.getUID()),              
                user -> user.setBirthday(this.getBirthday()),           
                list -> this.saveToFile(list)
        );
    }
    
    public void delete(){
        DataHandler.updateFieldAndSave(
                toList(),
                user -> user.getUID().equals(this.getUID()),              
                user -> user.setIsDeleted(true),           
                list -> this.saveToFile(list)
        );
    }
    
    public void logout(JFrame parent){
        int result = JOptionPane.showConfirmDialog(parent, "Are you sure to logout?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            Login login = new Login();
            login.setVisible(true);
            parent.dispose();
        }
    }
}
