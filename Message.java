/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.peo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import javax.swing.*;
/**
 *
 * @author RC_Student_lab
 */
public class Message {
    private final MessageRecord[] sentMessages = new MessageRecord[100];
    private int count = 0;

    // JSON filename constant
    private static final String JSON_FILENAME = "messages.json";

    // Static inner class for individual messages
    public static class MessageRecord {
        public String messageID;
        public String recipient;
        public String message;
        public String messageHash;

        public MessageRecord(String messageID, String recipient, String message, String messageHash) {
            this.messageID = messageID;
            this.recipient = recipient;
            this.message = message;
            this.messageHash = messageHash;
        }

        // Required by Gson
        public MessageRecord() {}
    }

    public String checkMessageID(int index) {
        return String.format("%010d", index);
    }

    public boolean checkRecipientCell(String number) {
        return number != null && number.matches("\\+\\d{10,15}");
    }

    public boolean checkMessageLength(String message) {
        return message != null && message.length() <= 250;
    }

    public String createMessageHash(String messageID, int number, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        return messageID.substring(0, 2) + ":" + number + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String sentMessage(String recipient, String message) {
        if (!checkRecipientCell(recipient)) {
            return "Invalid recipient number.";
        }

        if (!checkMessageLength(message)) {
            JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            return "Message too long.";
        }

        if (count >= sentMessages.length) {
            JOptionPane.showMessageDialog(null, "Message limit reached.");
            return "Message limit reached.";
        }

        String id = checkMessageID(count);
        String[] options = {"Send", "Store", "Disregard"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose what to do with your message:",
                "Send Message",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0, 1 -> {
                String hash = createMessageHash(id, count, message);
                sentMessages[count] = new MessageRecord(id, recipient, message, hash);
                count++;
                saveMessagesToJSON(JSON_FILENAME);  // dw Automatically save after send/store
                return (choice == 0 ? "Message sent." : "Message stored.");
            }
            case 2 -> {
                return "Message disregarded.";
            }
            default -> {
                return "No action taken.";
            }
        }
    }

    public String printMessages() {
        if (count == 0) return "Coming soon.";

        StringBuilder output = new StringBuilder("Messages:\n");
        for (int i = 0; i < count; i++) {
            MessageRecord m = sentMessages[i];
            output.append((i + 1)).append(") ID: ").append(m.messageID)
                    .append(" | Hash: ").append(m.messageHash)
                    .append(" | Recipient: ").append(m.recipient)
                    .append(" | Message: ").append(m.message).append("\n");
        }
        return output.toString();
    }

    public int returnTotalMessages() {
        return count;
    }

    // ✅ Save to JSON file
    public void saveMessagesToJSON(String filename) {
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "No messages to save.");
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            MessageRecord[] toSave = new MessageRecord[count];
            System.arraycopy(sentMessages, 0, toSave, 0, count);
            gson.toJson(toSave, writer);
            System.out.println("Saved to: " + new File(filename).getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Messages saved to " + filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }

    // ✅ Load from JSON file
    public void loadMessagesFromJSON(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No previous messages found.");
            return;
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<MessageRecord[]>() {}.getType();
            MessageRecord[] loaded = gson.fromJson(reader, type);

            if (loaded != null) {
                count = Math.min(loaded.length, sentMessages.length);
                System.arraycopy(loaded, 0, sentMessages, 0, count);
                JOptionPane.showMessageDialog(null, "Messages loaded from " + filename);
            } else {
                JOptionPane.showMessageDialog(null, "No messages found in file.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading messages: " + e.getMessage());
        }
    }
}


    
     

