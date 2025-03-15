package Helper;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.*;

public class Schedule {
    private String filePath;
    Match match;

    public Schedule(String file) {
        this.filePath = file;
    }

    public JTabbedPane tabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.add("Add Schedule", addMatch());
        tabbedPane.add("View Schedule", displaySchedule());
        tabbedPane.add("Remove Schedule", removeMatch());
        tabbedPane.add("Update Schedule", updateMatch());
        return tabbedPane;
    }

    public JPanel addMatch() {
        JPanel addMatch = new JPanel();
        addMatch.setLayout(new GridLayout(6, 2, 10, 10)); // GridLayout with 6 rows, 2 columns
        addMatch.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        JLabel dateLabel = new JLabel("Enter date of match (yyyy-mm-dd):");
        JLabel team1Label = new JLabel("Enter team 1 name:");
        JLabel team2Label = new JLabel("Enter team 2 name:");
        JLabel venueLabel = new JLabel("Enter the Venue:");
        JLabel timeLabel = new JLabel("Enter time of match (HH:mm):");

        JTextField dateField = createStyledTextField();
        JTextField team1Field = createStyledTextField();
        JTextField team2Field = createStyledTextField();
        JTextField venueField = createStyledTextField();
        JTextField timeField = createStyledTextField();

        JButton addButton = createStyledButton("Add");
        addButton.addActionListener(new AddScheduleAction(team1Field, team2Field, venueField, timeField, dateField));

        // Adding components to the panel
        addMatch.add(dateLabel);
        addMatch.add(dateField);
        addMatch.add(team1Label);
        addMatch.add(team1Field);
        addMatch.add(team2Label);
        addMatch.add(team2Field);
        addMatch.add(venueLabel);
        addMatch.add(venueField);
        addMatch.add(timeLabel);
        addMatch.add(timeField);
        addMatch.add(new JLabel()); // Empty label for alignment
        addMatch.add(addButton);

        return addMatch;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30)); // Fixed size for text fields
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return textField;
    }
    private JTextField createStyledTextFieldUpdate() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(180, 50));  // Adjust the width and height as needed
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 105, 217));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 123, 255));
            }
        });

        return button;
    }

    private class AddScheduleAction implements ActionListener {
        JTextField team1Field;
        JTextField team2Field;
        JTextField venueField;
        JTextField timeField;
        JTextField dateField;
    
        AddScheduleAction(JTextField team1Field, JTextField team2Field, JTextField venueField, JTextField timeField, JTextField dateField) {
            this.team1Field = team1Field;
            this.team2Field = team2Field;
            this.venueField = venueField;
            this.timeField = timeField;
            this.dateField = dateField;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String team1Name = team1Field.getText().trim();
                String team2Name = team2Field.getText().trim();
                String venueName = venueField.getText().trim();
                String timeSpeci = timeField.getText().trim();
                String dateSpeci = dateField.getText().trim();
    
                if ( venueName.isEmpty() || timeSpeci.isEmpty() || dateSpeci.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.");
                    return;
                }

                if (!validateDate(dateSpeci)) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
                    return;
                }

                if (!validateTime(timeSpeci)) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm.");
                    return;
                }
    
                ArrayList<Match> matchList = new ArrayList<>();
                try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filePath))) {
                    matchList = (ArrayList<Match>) read.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                }
    
                for (Match existingMatch : matchList) {
                    if (existingMatch.getDate().equals(dateSpeci) &&
                        existingMatch.getTime().equals(timeSpeci) &&
                        existingMatch.getVenue().equals(venueName)) {
                            JOptionPane.showMessageDialog(null, "A match already exists with the same date, time, and venue.");
                            return;
                    }
                }
    
                match = new Match(team1Name, team2Name, venueName, dateSpeci, timeSpeci);
                matchList.add(match);
    
                try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filePath))) {
                    write.writeObject(matchList);
                }
    
                JOptionPane.showMessageDialog(null, "Match added successfully!");
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } 
            
        }
    }
    

    private boolean validateDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    private boolean validateTime(String time) {
        return time.matches("\\d{2}:\\d{2}");
    }

    private JPanel displaySchedule() {
        JPanel displaySchedule = new JPanel();
        displaySchedule.setLayout(new BorderLayout());

        JTextArea scheduleArea = new JTextArea(50, 40);
        scheduleArea.setEditable(false);

        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filePath))) {
            ArrayList<Match> list = (ArrayList<Match>) read.readObject();
            StringBuilder content = new StringBuilder();

            for (Match match : list) {
                content.append("Date: ").append(match.getDate()).append("\n")
                        .append("Time: ").append(match.getTime()).append("\n")
                        .append("Team 1: ").append(match.getTeam1()).append("\n")
                        .append("Team 2: ").append(match.getTeam2()).append("\n")
                        .append("Venue: ").append(match.getVenue()).append("\n")
                        .append("-----------------------------\n");
            }
            scheduleArea.setText(content.toString());

        } catch (EOFException e) {
            // End of file reached
        } catch (ClassNotFoundException | IOException e) {
            scheduleArea.setText("Error displaying schedule: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(scheduleArea);
        displaySchedule.add(scrollPane, BorderLayout.CENTER);

        return displaySchedule;
    }

   
    private JPanel removeMatch() {
        JPanel removeMatch = new JPanel();
        removeMatch.setLayout(new GridLayout(4, 2, 10, 10));
    
        JLabel dateLabel = new JLabel("Enter date of match to remove (yyyy-mm-dd):");
        JTextField dateField = createStyledTextField();  
    
        JLabel venueLabel = new JLabel("Enter Venue of Match:");
        JTextField venueTxt = createStyledTextField();  
    
        JLabel timeLabel = new JLabel("Enter time of match to remove (HH:MM):");
        JTextField timeTxt = createStyledTextField();  
    
        JButton removeButton = createStyledButton("Remove");
        removeButton.addActionListener(new RemoveScheduleAction(dateField, timeTxt, venueTxt));
    
        removeMatch.add(dateLabel);
        removeMatch.add(dateField);
        removeMatch.add(venueLabel);
        removeMatch.add(venueTxt);
        removeMatch.add(timeLabel);
        removeMatch.add(timeTxt);
    
        removeMatch.add(new JLabel()); 
        removeMatch.add(removeButton);
    
        return removeMatch;
    }
    

    private class RemoveScheduleAction implements ActionListener {
        JTextField dateField;
        JTextField venue;
        JTextField time;
        RemoveScheduleAction(JTextField dateField,JTextField time,JTextField venue) {
            this.dateField = dateField;
            this.venue=venue;
            this.time=time;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String dateSpeci = dateField.getText().trim();
                String venueName = this.venue.getText().trim();
                String timeSpeci = this.time.getText().trim();
    
                
                if ( venueName.isEmpty() || timeSpeci.isEmpty() || dateSpeci.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.");
                    return;
                }

                if (!validateDate(dateSpeci)) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
                    return;
                }

                if (!validateTime(timeSpeci)) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm.");
                    return;
                }
                ArrayList<Match> matchList = new ArrayList<>();
                try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filePath))) {
                    matchList = (ArrayList<Match>) read.readObject();
                }
    
                Match matchToRemove = null;
                for (Match match : matchList) {
                    // Match if the date, teams, and time are the same
                    if (match.getDate().equals(dateSpeci) &&
                        match.getVenue().equals(venueName) &&
                        match.getTime().equals(timeSpeci)) {
                        matchToRemove = match;
                        break;
                    }
                }
    
                if (matchToRemove != null) {
                    matchList.remove(matchToRemove);
                    try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filePath))) {
                        write.writeObject(matchList);
                    }
                    JOptionPane.showMessageDialog(null, "Match removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "No match found with the specified date, teams, and time.");
                }
    
            } catch (IllegalArgumentException | IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    // New Method to Update Match
    private JPanel updateMatch() {
        JPanel updateMatch = new JPanel();
        updateMatch.setLayout(new GridLayout(9, 2, 10, 10));

        JLabel dateLabel = new JLabel("Enter date of match to update (yyyy-mm-dd):");
        JLabel venueLabel = new JLabel("Enter venue of match to update :");
        JLabel timeLabel = new JLabel("Enter time of match to update (HH-MM):");
        JLabel team1Label = new JLabel("Enter new team 1 name:");
        JLabel team2Label = new JLabel("Enter new team 2 name:");
        JLabel dateLabelUpdate = new JLabel("Enter date of match (yyyy-mm-dd):");
        JLabel venueLabelUpdate = new JLabel("Enter new venue:");
        JLabel timeLabelUpdate = new JLabel("Enter new time (HH:mm):");

        JTextField dateField = createStyledTextFieldUpdate();
        JTextField dateFieldUpdate = createStyledTextFieldUpdate();
        JTextField venueFieldUpdate = createStyledTextFieldUpdate();
        JTextField timeFieldUpdate = createStyledTextFieldUpdate();
        JTextField team1Field = createStyledTextFieldUpdate();
        JTextField team2Field = createStyledTextFieldUpdate();
        JTextField venueField = createStyledTextFieldUpdate();
        JTextField timeField = createStyledTextFieldUpdate();

        JButton updateButton = createStyledButton("Update");
        updateButton.addActionListener(new UpdateScheduleAction(dateField, team1Field, team2Field, venueField, timeField,dateFieldUpdate,venueFieldUpdate,timeFieldUpdate));

        updateMatch.add(dateLabel);
        updateMatch.add(dateField);

        updateMatch.add(venueLabel);
        updateMatch.add(venueField);
        updateMatch.add(timeLabel);
        updateMatch.add(timeField);
        updateMatch.add(team1Label);
        updateMatch.add(team1Field);
        updateMatch.add(team2Label);
        updateMatch.add(team2Field);
        updateMatch.add(dateLabelUpdate);
        updateMatch.add(dateFieldUpdate);
        updateMatch.add(venueLabelUpdate);
        updateMatch.add(venueFieldUpdate);
        updateMatch.add(timeLabelUpdate);
        updateMatch.add(timeFieldUpdate);
        updateMatch.add(new JLabel()); // Empty label for alignment
        updateMatch.add(updateButton);

        return updateMatch;
    }

    private class UpdateScheduleAction implements ActionListener {
        JTextField dateField, team1Field, team2Field, venueField, timeField,dateFieldUpdate, venueFieldUpdate, timeFieldUpdate;
    
        UpdateScheduleAction(JTextField dateField, JTextField team1Field, JTextField team2Field, JTextField venueField, JTextField timeField,JTextField dateUpdate,JTextField venue,JTextField time) {
            this.dateField = dateField;
            this.team1Field = team1Field;
            this.team2Field = team2Field;
            this.venueField = venueField;
            this.timeField = timeField;

            this.dateFieldUpdate=dateUpdate;
            this.venueFieldUpdate=venue;
            this.timeFieldUpdate=time;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String dateSpeci = dateField.getText().trim();
                String team1Name = team1Field.getText().trim();
                String team2Name = team2Field.getText().trim();
                String venueName = venueField.getText().trim();
                String timeSpeci = timeField.getText().trim();

                String dateSpeciUpdate = dateFieldUpdate.getText().trim();
                String venueNameUpdate = venueFieldUpdate.getText().trim();
                String timeSpeciUpdate = timeFieldUpdate.getText().trim();
    
                if ( venueName.isEmpty() || timeSpeci.isEmpty() || dateSpeci.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.");
                    return;
                }

                if (!validateDate(dateSpeci) && !validateDate(dateSpeciUpdate)) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
                    return;
                }

                if (!validateTime(timeSpeci) && !validateTime(timeSpeciUpdate)) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm.");
                    return;
                }
    
                ArrayList<Match> matchList = new ArrayList<>();
                try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(filePath))) {
                    matchList = (ArrayList<Match>) read.readObject();
                }
    
                boolean matchFound = false;
                for (Match match : matchList) {
                    if (match.getDate().equals(dateSpeci)) {
                        // Check if the venue and time match before updating
                        if (match.getVenue().equals(venueName) && match.getTime().equals(timeSpeci)) {
                            // If we find a match with the same date, venue, and time, update it
                            match.setTeam1(team1Name);
                            match.setTeam2(team2Name);
                            match.setVenue(venueNameUpdate);
                            match.setTime(timeSpeciUpdate);
                            match.setDate(dateSpeciUpdate);
                            matchFound = true;
                            break;
                        } else {
                            // If the date matches but the venue or time doesn't, throw an exception
                            throw new IllegalArgumentException("No match found with the specified venue and time.");
                        }
                    }
                }
    
                if (matchFound) {
                    try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(filePath))) {
                        write.writeObject(matchList);
                    }
                    JOptionPane.showMessageDialog(null, "Match updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "No match found with the specified date.");
                }
    
            } catch (IllegalArgumentException | IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
