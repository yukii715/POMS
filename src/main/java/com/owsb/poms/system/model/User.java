package com.owsb.poms.system.model;
import java.time.*;

public class User {
    private int UID;
    private String username;
    private String email;
    private String passwordHash;
    private String role;
    private LocalDateTime creationDateTime;
    private int age;
    private LocalDate birthday;
    private boolean isDeleted;
}
