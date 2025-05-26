/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.peo;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
public class Peo {

    public static void main(String[] args) {
        
        Login login = new Login();
        Message messageHandler = new Message(); // Enhanced Message class

        // === REGISTRATION ===
        String username, password, cellPhoneNumber, firstName, lastName;

        while (true) {
            username = JOptionPane.showInputDialog("Enter your username:");
            if (login.checkUsername(username)) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted.\nIt must contain an underscore and be no more than 5 characters.");
            }
        }

        while (true) {
            password = JOptionPane.showInputDialog("Enter your password:");
            if (login.checkPasswordComplexity(password)) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Password is not correctly formatted.\nIt must be at least 8 characters, include uppercase, number, and special character.");
            }
        }

        while (true) {
            cellPhoneNumber = JOptionPane.showInputDialog("Enter your SA cellphone number (e.g. +27831234567):");
            if (login.checkCellPhoneNumber(cellPhoneNumber)) {
                JOptionPane.showMessageDialog(null, "Cellphone number successfully captured.");
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Cellphone number is not correctly formatted.");
            }
        }

        firstName = JOptionPane.showInputDialog("Enter your first name:");
        lastName = JOptionPane.showInputDialog("Enter your last name:");

        JOptionPane.showMessageDialog(null, login.registerUser(username, password, cellPhoneNumber, firstName, lastName));

        // === LOGIN ===
        JOptionPane.showMessageDialog(null, "=== LOGIN ===");
        String loginUsername, loginPassword;
        while (true) {
            loginUsername = JOptionPane.showInputDialog("Enter username:");
            if (login.checkUsernameLoop(loginUsername)) break;
        }

        while (true) {
            loginPassword = JOptionPane.showInputDialog("Enter password:");
            if (login.checkPasswordLoop(loginPassword)) break;
        }

        boolean success = login.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(success));

        // === MAIN MENU ===
        if (success) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            boolean running = true;

            while (running) {
                String option = """
                        1) Send Messages
                        2) Show Sent Messages
                     3) Quit
                        """;
                String choice = JOptionPane.showInputDialog(option);
                if (choice == null) continue;

                switch (choice) {
                    case "1" -> {
                        int numMessages = 0;
                        while (true) {
                            try {
                                numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
                                if (numMessages > 0) break;
                            } catch (Exception ignored) {}
                            JOptionPane.showMessageDialog(null, "Please enter a valid number greater than 0.");
                        }

                        for (int i = 0; i < numMessages; i++) {
                            String recipient = JOptionPane.showInputDialog("Enter recipient number (e.g. +27831234567):");
                            String msg = JOptionPane.showInputDialog("Enter message:");

                            String result = messageHandler.sentMessage(recipient, msg);
                            if (!result.equals("Message disregarded.") && !result.startsWith("Invalid")) {
                                JOptionPane.showMessageDialog(null, result);
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Total messages sent/stored: " + messageHandler.returnTotalMessages());
                    }
                    case "2" -> JOptionPane.showMessageDialog(null, messageHandler.printMessages());
                    
                    case "3" -> {
                        JOptionPane.showMessageDialog(null, "Goodbye!");
                        running = false;
                    }
                    default -> JOptionPane.showMessageDialog(null, "Invalid option. Choose 1-3.");
                }
            }
        }
    }
}
