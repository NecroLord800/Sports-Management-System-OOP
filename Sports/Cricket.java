package Sports;

import Custom.*;
import Helper.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Cricket {
    public CustomFrame frame;

    public Cricket() {
        this.frame = new CustomFrame("Cricket", 500, 450);
        JLabel label = new JLabel("Choose What You Want To Do");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setBounds(70 , 30 , 450 , 30);

        CustomButton button1 = new CustomButton("Teams" , 120 , 100 , 250 , 50);
        CustomButton button2 = new CustomButton("Schedules" , 120 , 170 , 250 , 50);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Teams teamsFrame = new Teams("cricket", "./Database/cricket_team.txt");
                
                // Add a WindowListener to the Teams frame
                teamsFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(true); // Make the main frame visible again
                        teamsFrame.setVisible(false);; // Dispose of the Teams frame
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
                
                teamsFrame.setVisible(true);
            }
        });
        
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Schedule schedule = new Schedule("./Database/cricket_schedule.txt");
                JFrame scheduleFrame = new JFrame("Schedule");
                scheduleFrame.add(schedule.tabbedPane(), BorderLayout.CENTER);
                scheduleFrame.setSize(600, 650);
                
                // Add a WindowListener to the Schedule frame
                scheduleFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(true);
                        scheduleFrame.setVisible(false);
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
                
                scheduleFrame.setVisible(true);
            }
        });

        this.frame.add(label);
        this.frame.add(button1);
        this.frame.add(button2);

        this.frame.setVisible(true);
    }

    public CustomFrame getFrame() {
        return this.frame;
    }
}
