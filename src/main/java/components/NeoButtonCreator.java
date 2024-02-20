package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NeoButtonCreator {

    public static JButton createNeoButton(JTextField startDateField, JTextField endDateField, ActionListener actionListener) {
        JButton neoButton = new JButton("Get NEO Data");
        neoButton.setBackground(new Color(70, 130, 180));
        neoButton.setForeground(Color.WHITE);
        neoButton.setFocusPainted(false);
        neoButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        neoButton.addActionListener(actionListener);

        return neoButton;
    }
}

