import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Custom imports
import Custom.*;
import Sports.*;

public class Main {
    public static void main(String[] args) {
        // Creating first main window
        CustomFrame frame = new CustomFrame("Sports Selection" , 500 , 550);

        JLabel welcomeLabel = new JLabel("Please Select The Sport You Want To Manage");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(40 , 20 , 500 , 50);
        
        // Creating all buttons
        CustomButton cricketButton = new CustomButton("Cricket" , 110 , 80 , 250 , 70);
        CustomButton volleyballButton = new CustomButton("Volleyball" , 110 , 180 , 250 , 70);
        CustomButton basketballButton = new CustomButton("Basketball" , 110 , 280 , 250 , 70);
        CustomButton footballButton = new CustomButton("Football" , 110 , 380 , 250 , 70);

        // Adding event listeners to all buttons
        cricketButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Cricket cricket = new Cricket();
                CustomFrame cricketFrame = cricket.getFrame();
                cricketFrame.setDefaultCloseOperation(0);

                cricketFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("The frame is about to close.");
                        frame.setVisible(true);
                        cricketFrame.setVisible(false);
                    }
                
                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
            }
            
        });

        basketballButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Basketball basketball = new Basketball();
                CustomFrame basketballFrame = basketball.getFrame();
                basketballFrame.setDefaultCloseOperation(0);

                basketballFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("The frame is about to close.");
                        frame.setVisible(true);
                        basketballFrame.setVisible(false);
                    }
                
                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
            }
            
        });

        footballButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Football football = new Football();
                CustomFrame footballFrame = football.getFrame();
                footballFrame.setDefaultCloseOperation(0);

                footballFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("The frame is about to close.");
                        frame.setVisible(true);
                        footballFrame.setVisible(false);
                    }
                
                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
            }
            
        });

        volleyballButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Volleyball volleyball = new Volleyball();
                CustomFrame volleyballFrame = volleyball.getFrame();
                volleyballFrame.setDefaultCloseOperation(0);

                volleyballFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("The frame is about to close.");
                        frame.setVisible(true);
                        volleyballFrame.setVisible(false);
                    }
                
                    @Override
                    public void windowClosed(WindowEvent e) {
                    }
                });
            }
            
        });
        
        frame.add(welcomeLabel);
        frame.add(cricketButton);
        frame.add(volleyballButton);
        frame.add(basketballButton);
        frame.add(footballButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}