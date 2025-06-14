package com.owsb.poms.system.model;

import com.owsb.poms.system.functions.FileHandler;
import com.owsb.poms.system.functions.interfaces.hasFile;
import java.time.LocalDateTime;
import java.util.List;

public class Notification implements  hasFile<Notification>{
    private String sendBy;
    private LocalDateTime dateTime;
    private RequestType requestType;
    private boolean done;
    
    private static final String filePath = "data/Notification/notification.txt";
    
    public enum RequestType{
        ResetPassword
    }

    public Notification() {
    }

    public Notification(String sendBy, RequestType requestType) {
        this.sendBy = sendBy;
        this.dateTime = LocalDateTime.now();
        this.requestType = requestType;
        this.done = false;
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

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
    
    @Override
    public String toString() {
        return sendBy + "\t"
             + dateTime + "\t"
             + requestType + "\t"
             + done;
    }
    
    public static Notification fromString(String line) {
        String[] parts = line.split("\t");

        Notification notification = new Notification();
        notification.setSendBy(parts[0]);
        notification.setDateTime(LocalDateTime.parse(parts[1]));
        notification.setRequestType(RequestType.valueOf(parts[2]));
        notification.setDone(Boolean.parseBoolean(parts[3]));

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
