import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import components.NeoButtonCreator;

public class NasaApiInterface {

    public static void main(String[] args) {
        JFrame frame = new JFrame("NASA API NeoWs");//Tittle of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);//Size of the windows
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JTextField startDateField = new JTextField(10);//Input start date
        JTextField endDateField = new JTextField(10);//Input End date

        //action listener for the button, it calls the fetchNeoData
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                fetchNeoData(startDate, endDate);
            }
        };

        //Creation of the Button
        JButton neoButton = NeoButtonCreator.createNeoButton(startDateField, endDateField, actionListener);

        //Add the components to the window and we set visible=True
        frame.getContentPane().add(new JLabel("Start Date (YYYY-MM-DD):"));
        frame.getContentPane().add(startDateField);
        frame.getContentPane().add(new JLabel("End Date (YYYY-MM-DD):"));
        frame.getContentPane().add(endDateField);
        frame.getContentPane().add(neoButton);

        frame.setVisible(true);
    }

    //Function to fetch the data of Asteroids - NeoWs
    private static void fetchNeoData(String startDate, String endDate) {

    }

}
