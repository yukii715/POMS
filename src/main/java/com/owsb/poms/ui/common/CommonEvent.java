package com.owsb.poms.ui.common;

import java.awt.Point;
import javax.swing.JFrame;

public class CommonEvent {
    
    public static void mouseDragWindow(JFrame frame, java.awt.event.MouseEvent e, Point initialClick){
                // get location of Window
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
    }
}
