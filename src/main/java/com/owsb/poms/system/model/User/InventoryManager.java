package com.owsb.poms.system.model.User;

import com.owsb.poms.system.functions.interfaces.hasId;
import com.owsb.poms.system.model.User.User;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InventoryManager extends User implements hasId{

    public InventoryManager() {
    }

    public InventoryManager(String UID, String username, String email, String passwordHash, Role role, LocalDateTime creationDateTime, LocalDate birthday, boolean isDeleted) {
        super(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
    }
    
    @Override
    public String generateID() {
         return generateUID(Role.InventoryManager);
    }
    
    public void login(){
        
    }
}
