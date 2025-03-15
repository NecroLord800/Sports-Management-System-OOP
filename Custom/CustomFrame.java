package Custom;

import java.awt.Color;

import javax.swing.JFrame;

public class CustomFrame extends JFrame {
    public CustomFrame(String title, int width, int height) {
        super(title);
        this.setSize(width, height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(225, 215, 198));
    }
}
