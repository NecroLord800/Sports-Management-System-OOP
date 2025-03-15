package Helper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;
import Exceptions.PathDoesNotExistException;

public class Teams extends JFrame{
    public int noOfPlayers = 0;
    public String filePath = "";
    public String sport = "";

    public Teams(String sport , String filePath){
        this.sport = sport;
        this.filePath = filePath;
        this.setSize(600 , 650);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(225, 215, 198));

        if (sport == "cricket" || sport == "basketball"  || sport == "football"){
            noOfPlayers = 16;
        } else if (sport == "volleyball"){
            noOfPlayers = 11;
        }

        try{
            File file = new File(this.filePath);

            if (file.exists()) {
                System.out.println("The file exists.");
                
                JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
                tabbedPane.setBounds(0, 0, 600, 650);

                // Create the panel
                JPanel addPanel = new JPanel();
                addPanel.setLayout(null); // Use null layout to set bounds manually
                addPanel.setBackground(new Color(225, 215, 198));

                // Create and configure components
                JLabel playerIdLabel1 = new JLabel("Player ID:");
                playerIdLabel1.setBounds(50, 50, 100, 30); // x, y, width, height

                JTextField playerIdField1 = new JTextField();
                playerIdField1.setBounds(150, 50, 200, 30); // x, y, width, height

                JLabel playerNameLabel1 = new JLabel("Player Name:");
                playerNameLabel1.setBounds(50, 100, 100, 30); // x, y, width, height

                JTextField playerNameField1 = new JTextField();
                playerNameField1.setBounds(150, 100, 200, 30); // x, y, width, height

                JLabel positionLabel = new JLabel("Position:");
                positionLabel.setBounds(50, 150, 100, 30);

                JComboBox<String> positionDropdown = new JComboBox<>(new String[]{"", "Batsman","Bowler", "WicketKeeper" , "All-rounder" ,"Point Guard","Shooting Guard","Small Forward","Power Forward","Center","Setter","MidBlocker","Outside Hitter","Libro","Goalkeeper", "Defender", "Midfielder", "Forward"});
                positionDropdown.setBounds(150, 150, 200, 30);

                JButton addButton1 = new JButton("Add Player");
                addButton1.setBounds(150, 150, 120, 30); // x, y, width, height

                // Add ActionListener for the button
                addButton1.addActionListener(e -> {
                    try {
                        int playerId = Integer.parseInt(playerIdField1.getText());
                        String playerName = playerNameField1.getText();
                        String position = (String) positionDropdown.getSelectedItem();
                        Player newPlayer = new Player(playerId, playerName, position); // Assuming Player has a constructor
                        if (addPlayer(newPlayer)) {
                            JOptionPane.showMessageDialog(addPanel, "Player added successfully!");
                            playerIdField1.setText("");
                            playerNameField1.setText("");
                            positionDropdown.setSelectedIndex(0);
                        } else {
                            JOptionPane.showMessageDialog(addPanel, "Failed to add player. Team is full.");
                            playerIdField1.setText("");
                            playerNameField1.setText("");
                            positionDropdown.setSelectedIndex(0);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(addPanel, "Please enter a valid ID.");
                    }
                });

                // Add components to the panel
                addPanel.add(playerIdLabel1);
                addPanel.add(playerIdField1);
                addPanel.add(playerNameLabel1);
                addPanel.add(playerNameField1);
                //addPanel.add(positionLabel);
                //addPanel.add(positionDropdown);
                addPanel.add(addButton1);


                JPanel remPanel = new JPanel();
                remPanel.setLayout(null); // Use null layout to set bounds manually
                remPanel.setBackground(new Color(225, 215, 198));

                // Create and configure components
                JLabel playerIdLabel3 = new JLabel("Player ID:");
                playerIdLabel3.setBounds(50, 50, 100, 30); // x, y, width, height

                JTextField playerIdField3 = new JTextField();
                playerIdField3.setBounds(150, 50, 200, 30); // x, y, width, height

                JButton removeButton = new JButton("Remove Player");
                removeButton.setBounds(150, 100, 120, 30); // x, y, width, height

                // Add ActionListener for the button
                removeButton.addActionListener(e -> {
                    try {
                        int playerId = Integer.parseInt(playerIdField3.getText());
                        if (remPlayer(playerId)) {
                            JOptionPane.showMessageDialog(remPanel, "Player removed successfully!");
                            playerIdField3.setText("");
                        } else {
                            JOptionPane.showMessageDialog(remPanel, "Failed to remove player. Player not found.");
                            playerIdField1.setText("");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(remPanel, "Please enter a valid ID.");
                    }
                });

                // Add components to the panel
                remPanel.add(playerIdLabel3);
                remPanel.add(playerIdField3);
                remPanel.add(removeButton);


                JPanel updPanel = new JPanel();
                updPanel.setLayout(null); // Use null layout to set bounds manually
                updPanel.setBackground(new Color(225, 215, 198));

                // Create and configure components
                JLabel playerIdLabel2 = new JLabel("Player ID:");
                playerIdLabel2.setBounds(50, 50, 100, 30); // x, y, width, height

                JTextField playerIdField2 = new JTextField();
                playerIdField2.setBounds(150, 50, 200, 30); // x, y, width, height

                JLabel newNameLabel = new JLabel("New Name:");
                newNameLabel.setBounds(50, 100, 100, 30); // x, y, width, height

                JTextField newNameField = new JTextField();
                newNameField.setBounds(150, 100, 200, 30); // x, y, width, height

                JButton updateButton = new JButton("Update Player");
                updateButton.setBounds(150, 150, 120, 30); // x, y, width, height

                // Add ActionListener for the button
                updateButton.addActionListener(e -> {
                    try {
                        int playerId = Integer.parseInt(playerIdField2.getText());
                        String newName = newNameField.getText();
                        if (updPlayer(playerId, newName)) {
                            JOptionPane.showMessageDialog(updPanel, "Player updated successfully!");
                            playerIdField2.setText("");
                            newNameField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(updPanel, "Failed to update player. Player not found.");
                            playerIdField2.setText("");
                            newNameField.setText("");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(updPanel, "Please enter a valid ID.");
                    }
                });

                // Add components to the panel
                updPanel.add(playerIdLabel2);
                updPanel.add(playerIdField2);
                updPanel.add(newNameLabel);
                updPanel.add(newNameField);
                updPanel.add(updateButton);


                JPanel disPanel = new JPanel();
                disPanel.setLayout(new BorderLayout()); // Use BorderLayout for better layout management
                disPanel.setBackground(new Color(225, 215, 198));

                // Create a JTextArea to display players
                JTextArea playerDisplayArea = new JTextArea();
                playerDisplayArea.setEditable(false); // Make it non-editable
                playerDisplayArea.setBackground(new Color(225, 215, 198));
                playerDisplayArea.setFont(new Font("Arial", Font.BOLD, 20));

                ArrayList<Player> players = getPlayers();
                StringBuilder displayText = new StringBuilder("Players List:\n\n");

                for (Player player : players) {
                    displayText.append("ID: ").append(player.id).append(", Name: ").append(player.name).append(", Position: ").append(player.position).append("\n");
                }

                playerDisplayArea.setText(displayText.toString());

                JScrollPane scrollPane = new JScrollPane(playerDisplayArea);
                disPanel.add(scrollPane, BorderLayout.CENTER);

                tabbedPane.addChangeListener(e -> {
                    int selectedIndex = tabbedPane.getSelectedIndex();
                    if (selectedIndex == 3) { // Assuming disPanel is the 4th tab (index 3)
                        updatePlayerDisplay(disPanel);
                    }
                });

                tabbedPane.addTab("addPlayer", addPanel);
                tabbedPane.addTab("removePlayer", remPanel);
                tabbedPane.addTab("updatePlayer", updPanel);
                tabbedPane.addTab("displayTeam", disPanel);
                
                this.add(tabbedPane);
            } else {
                throw new PathDoesNotExistException("The file does not exist.");
            }
        }catch(PathDoesNotExistException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updatePlayerDisplay(JPanel disPanel) {
        JTextArea playerDisplayArea = (JTextArea) ((JScrollPane) disPanel.getComponent(0)).getViewport().getView();
        ArrayList<Player> players = getPlayers();
        StringBuilder displayText = new StringBuilder("Players List:\n\n");
    
        for (Player player : players) {
            displayText.append("ID: ").append(player.id).append(", Name: ").append(player.name).append(", Position: ").append(player.position).append(player.position).append("\n");
        }
    
        playerDisplayArea.setText(displayText.toString());
    }

    public boolean addPlayer(Player newPlayer) {
        try {
            File file = new File(this.filePath);
            ArrayList<Player> players;
    
            // Check if the file exists
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    players = (ArrayList<Player>) ois.readObject();
    
                    // Check if the player already exists with the same ID
                    for (Player player : players) {
                        if (player.id == newPlayer.id) { // Assuming Player class has a getId() method
                            System.out.println("Player with ID " + newPlayer.id + " already exists.");
                            return false;
                        }
                    }
    
                    // Check if the team is full
                    if (players.size() >= this.noOfPlayers) {
                        System.out.println("Team is full. Cannot add more players.");
                        return false;
                    }
                } catch (Exception e) {
                    players = new ArrayList<>();
                }
            } else {
                players = new ArrayList<>();
            }
    
            // Add the new player
            players.add(newPlayer);
    
            // Save the updated list back to the file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(players);
            }
    
            System.out.println("Player added successfully!");
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("An error occurred while adding the player.");
            return false;
        }
    
        return true;
    }
    

    public boolean remPlayer(int playerId) {
        try {
            File file = new File(this.filePath);
    
            // Check if the file exists
            if (!file.exists()) {
                System.out.println("No players found to remove.");
                return false;
            }
    
            // Read the existing players from the file
            ArrayList<Player> players;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                players = (ArrayList<Player>) ois.readObject();
            }
    
            // Find and remove the player by ID
            boolean removed = players.removeIf(player -> player.id == playerId);
    
            if (removed) {
                // Write the updated list back to the file
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(players);
                }
                System.out.println("Player removed successfully!");
                return true;
            } else {
                System.out.println("Player not found.");
                return false;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while removing the player.");
            return false;
        }
    }
    
    public boolean updPlayer(int playerId, String newName) {
        try {
            File file = new File(this.filePath);
    
            // Check if the file exists
            if (!file.exists()) {
                System.out.println("No players found to update.");
                return false;
            }
    
            // Read the existing players from the file
            ArrayList<Player> players;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                players = (ArrayList<Player>) ois.readObject();
            }
    
            // Find the player by ID and update their details
            boolean updated = false;
            for (Player player : players) {
                if (player.id == playerId) {
                    player.name = newName;  // Update the player's name
                    updated = true;
                    break;
                }
            }
    
            if (updated) {
                // Write the updated list back to the file
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(players);
                }
                System.out.println("Player updated successfully!");
                return true;
            } else {
                System.out.println("Player not found.");
                return false;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the player.");
            return false;
        }
    }    

    public ArrayList<Player> getPlayers() {
        try {
            File file = new File(this.filePath);

            if (!file.exists()) {
                return new ArrayList<>();
            }

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Player>) ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
}