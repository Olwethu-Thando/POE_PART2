/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.peo;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
public class Login {
    
 private String registeredUsername;
    private String registeredPassword;
    private String registeredFirstName;
    private String registeredLastName;

    public boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               !password.equals(password.toLowerCase()) &&
               password.matches(".*\\d.*") &&
               password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber(String number) {
        return number.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String username, String password, String cell, String first, String last) {
        registeredUsername = username;
        registeredPassword = password;
        registeredFirstName = first;
        registeredLastName = last;

        return """
               Registration successful!
               Username: """ + username + "\nPassword: " + password +
               "\nCell: " + cell + "\nName: " + first + " " + last;
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername.equals(registeredUsername) && inputPassword.equals(registeredPassword);
    }

    public boolean checkUsernameLoop(String inputUsername) {
        if (inputUsername.equals(registeredUsername)) return true;
        JOptionPane.showMessageDialog(null, "Incorrect username. Try again.");
        return false;
    }

    public boolean checkPasswordLoop(String inputPassword) {
        if (inputPassword.equals(registeredPassword)) return true;
        JOptionPane.showMessageDialog(null, "Incorrect password. Try again.");
        return false;
    }

    public String returnLoginStatus(boolean loginSuccess) {
        return loginSuccess ? "Welcome, " + registeredFirstName + " " + registeredLastName + "!" :
               "Login failed. Please try again.";
    }
}
   



