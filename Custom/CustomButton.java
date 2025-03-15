package Custom;

import java.awt.*;
import javax.swing.*;

public class CustomButton extends JButton {

    public CustomButton(String text , int posX , int posY , int width , int height){
        super(text);
        this.setBounds(posX , posY , width , height);
        this.setBackground(new Color(87, 155, 177));
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setFont(new Font("Arial", Font.BOLD, 20));
    }
}