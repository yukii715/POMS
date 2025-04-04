package com.owsb.poms;
import javax.swing.SwingUtilities;
import com.owsb.poms.ui.common.Login;
import com.owsb.poms.ui.im.InventoryManagerDashboard;

public class Main {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new InventoryManagerDashboard().setVisible(true);
        });
    }
}
