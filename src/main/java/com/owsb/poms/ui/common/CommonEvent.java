package com.owsb.poms.ui.common;

import java.awt.Point;
import java.util.function.Function;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;

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
    
    public static void bindTextToLabel(JTextComponent textComponent, JLabel label, Function<String, String> formatter) {
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String input = textComponent.getText();
                label.setText(formatter.apply(input));
            }

            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }
}
