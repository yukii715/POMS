package com.owsb.poms.system.model;
import java.time.*;

public class User {
    private String UID;
    private String username;
    private String email;
    private String passwordHash;
    private Role role;
    private LocalDateTime creationDateTime;
    private int age;
    private LocalDate birthday;
    private boolean isDeleted;

    public enum Role{
        Admin,
        SalesManager,
        PurchaseManager,
        InventoryManager,
        FinanceManager
    }
    
    public User() {
    }

    public User(String UID, String username, String email, String passwordHash, Role role, int age, LocalDate birthday, boolean isDeleted) {
        this.UID = UID;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.creationDateTime = LocalDateTime.now();
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
