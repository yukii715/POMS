package com.owsb.poms.ui.common;

import java.awt.Point;
import javax.swing.JFrame;

public class ResizablePanel {
    public static void SetNorth(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getHeight() - dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth(), frame.getHeight() - dy);

        frame.setLocation(frame.getX(), frame.getY() + dy);
        }
    }
    
    public static void SetSouth(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getHeight() + dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth(), frame.getHeight() + dy);
        }
    }
    
    public static void SetWest(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        
        if (frame.getWidth() - dx > 600 && !maximise) {  
        frame.setSize(frame.getWidth() - dx, frame.getHeight());

        frame.setLocation(frame.getX() + dx, frame.getY());
        }
    }
    
    public static void SetEast(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        
        if (frame.getWidth() + dx > 600 && !maximise) {  
        frame.setSize(frame.getWidth() + dx, frame.getHeight());
        }
    }
    
    public static void SetNW(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getWidth() - dx > 600 && frame.getHeight() - dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth() - dx, frame.getHeight() - dy);
        
        frame.setLocation(frame.getX() + dx, frame.getY() + dy);
        }
    }
    
    public static void SetNE(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getWidth() + dx > 600 && frame.getHeight() - dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth() + dx, frame.getHeight() - dy);
        
        frame.setLocation(frame.getX(), frame.getY() + dy);
        }
    }
    
    public static void SetSW(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getWidth() - dx > 600 && frame.getHeight() + dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth() - dx, frame.getHeight() + dy);
        
        frame.setLocation(frame.getX() + dx, frame.getY());
        }
    }
    
    public static void SetSE(JFrame frame, java.awt.event.MouseEvent e, Point initialClick, boolean maximise){
        Point currCoords = e.getPoint();
        int dx = currCoords.x - initialClick.x;
        int dy = currCoords.y - initialClick.y;
        
        if (frame.getWidth() + dx > 600 && frame.getHeight() + dy > 200 && !maximise) {  
        frame.setSize(frame.getWidth() + dx, frame.getHeight() + dy);
        }
    }
}
