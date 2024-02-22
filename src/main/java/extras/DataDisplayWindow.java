package extras;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataDisplayWindow {
    public static void displayData(String[][] data, int elementCount) {
        //we run the function async
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NEO Data");//Creates a new window with the given title
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//only close the window
            frame.setSize(1000, 500);//size of the window

            JLabel titleLabel = new JLabel("Total NEOs: " + elementCount);
            titleLabel.setHorizontalAlignment(JLabel.LEFT); // put the title to the left side
            titleLabel.setFont(new Font("Serif", Font.BOLD, 20));

            //Set the names of the columns for the table
            //can set more names if needed
            String[] columnNames = {"ID", "Date", "Name", "Potentially Hazardous", "Diameter Min meters", "Diameter Max meters", "Relative Velocity(KM/h)", "Miss Distance(KM)"};

            //Create a DefaultTableModel with the Data and the columNames
            //DefaultTableModel uses bidimensional array to create a table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);  //Use the table "model" to fill a JTable
            JScrollPane scrollPane = new JScrollPane(table);//add the Jtable to the scroll component

            frame.setLayout(new BorderLayout()); // Usa BorderLayout en el frame
            frame.add(titleLabel, BorderLayout.NORTH); // add the label to the top side of the frame
            frame.add(scrollPane, BorderLayout.CENTER);//Add the scroll(with the tbl) to the window

            frame.setLocationRelativeTo(null);//centers the window
            frame.setVisible(true);//set the windows to be visible
        });
    }
}

