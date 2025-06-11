package com.owsb.poms.system.functions;

import com.owsb.poms.system.model.User.*;
import java.time.*;

public class UserValidation {
    public static boolean validUsername(String username){
        var users = User.toList();
        if (users.stream().anyMatch(
                user -> user.getUsername().equalsIgnoreCase(username.toUpperCase())) ||
                username.length() < 4
                ) return false;
        return username.matches("^[A-Z ]+$");
    }
    
    public static boolean validBirthday(String birthday){
        if (birthday.length() != 8) return false;
        LocalDate birthDate;
        try{
            int year = Integer.parseInt(birthday.substring(0, 4));
            int month = Integer.parseInt(birthday.substring(4, 6));
            int day = Integer.parseInt(birthday.substring(6, 8));
            birthDate = LocalDate.of(year, month, day);
        } catch (Exception e){
            return false;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() > 16;
    }
    
    public static boolean validPassword(String password){
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#&*_+=:;?,.]).{8,20}$");
    }
}
