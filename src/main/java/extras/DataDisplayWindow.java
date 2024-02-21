package extras;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataDisplayWindow {
    public static void displayData(String[][] data) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("NEO Data");//Creates a new window with the given title
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 400);//size of the window

            //Set the names of the columns for the table
            //can set more names if needed
            String[] columnNames = {"ID", "Name", "Potentially Hazardous", "Diameter Min meters", "Diameter Max meters", "Close Approach Date", "Miss Distance"};

            DefaultTableModel model = new DefaultTableModel(data, columnNames);//Create a defaultTableModel with the Data and the columNames
            JTable table = new JTable(model);  //Use the table "model" to fill a JTable
            JScrollPane scrollPane = new JScrollPane(table);//Give the Jtable on a scroll

            frame.add(scrollPane, BorderLayout.CENTER);//Add the scroll to the window

            frame.setLocationRelativeTo(null);//centers the window
            frame.setVisible(true);//set the windows to be visible
        });
    }
}
