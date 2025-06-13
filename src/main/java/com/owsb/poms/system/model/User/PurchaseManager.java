package com.owsb.poms.system.model.User;

import com.owsb.poms.system.functions.interfaces.hasId;
import com.owsb.poms.ui.pm.PurchaseManagerDashboard;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PurchaseManager extends User implements hasId{

    public PurchaseManager() {
    }

    public PurchaseManager(String UID, String username, String email, String passwordHash, Role role, LocalDateTime creationDateTime, LocalDate birthday, boolean isDeleted) {
        super(UID, username, email, passwordHash, role, creationDateTime, birthday, isDeleted);
    }
    
    @Override
    public String generateID() {
         return generateUID(Role.PurchaseManager);
    }
    
    public void login(){
        PurchaseManagerDashboard pmDashboard = new PurchaseManagerDashboard(this);
        pmDashboard.setVisible(true);
    }  
}
