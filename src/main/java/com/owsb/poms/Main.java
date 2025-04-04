package com.owsb.poms;
import javax.swing.SwingUtilities;
import com.owsb.poms.ui.common.Login;

public class Main {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
