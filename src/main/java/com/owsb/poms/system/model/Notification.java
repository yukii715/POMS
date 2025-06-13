package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.hasFile;
import java.time.LocalDateTime;
import java.util.List;

public class Notification implements  hasFile<Notification>{
    private String sendBy;
    private boolean done;
    private LocalDateTime dateTime;
    
    private static final String filePath = "data/Notification/notification.txt";

    public Notification() {
    }

    public Notification(String sendBy) {
        this.sendBy = sendBy;
        this.done = false;
        this.dateTime = LocalDateTime.now();
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    @Override
    public String toString() {
        return sendBy + "\t"
             + done + "\t"
             + dateTime ;
    }
    
    public static Notification fromString(String line) {
        String[] parts = line.split("\t");

        Notification notification = new Notification();
        notification.setSendBy(parts[0]);
        notification.setDone(Boolean.parseBoolean(parts[1]));
        notification.setDateTime(LocalDateTime.parse(parts[2]));

        return notification;
    }
    
    @Override
    public void saveToFile(List<Notification> list) {
        FileHandler.saveToFile(filePath, list, Notification::toString);
    }
    
    public static List<Notification> toList(){
        return FileHandler.readFromFile(filePath, Notification::fromString);
    }

    @Override
    public void add() {
        var notes = toList();
        notes.add(this);
        this.saveToFile(notes);
    }
}
