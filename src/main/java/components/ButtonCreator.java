package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonCreator {

    public static JButton createNeoButton( ActionListener actionListener) {
        JButton neoButton = new JButton("Get NEO Data");//text of the button
        neoButton.setBackground(new Color(70, 130, 180));//color
        neoButton.setForeground(Color.WHITE);
        neoButton.setFocusPainted(false);
        neoButton.setFont(new Font("Tahoma", Font.BOLD, 12));//font and size

        neoButton.addActionListener(actionListener);//adds the action listener from the parameters

        //returns the button
        return neoButton;
    }
}

