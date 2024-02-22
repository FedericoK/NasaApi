package extras;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataDisplayWindow {
    public static void displayData(String[][] data) {
        //we run the function async
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NEO Data");//Creates a new window with the given title
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//only close the window
            frame.setSize(1000, 500);//size of the window

            //Set the names of the columns for the table
            //can set more names if needed
            String[] columnNames = {"ID", "Date", "Name", "Potentially Hazardous", "Diameter Min meters", "Diameter Max meters", "Relative Velocity(KM/h)", "Miss Distance(KM)"};

            //Create a DefaultTableModel with the Data and the columNames
            //DefaultTableModel uses bidimensional array to create a table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);  //Use the table "model" to fill a JTable
            JScrollPane scrollPane = new JScrollPane(table);//Give the Jtable on a scroll

            frame.add(scrollPane, BorderLayout.CENTER);//Add the scroll to the window

            frame.setLocationRelativeTo(null);//centers the window
            frame.setVisible(true);//set the windows to be visible
        });
    }
}

