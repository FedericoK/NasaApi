import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import components.NeoButtonCreator;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;

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
        //Key to use the API
        String apiKey = "dMYntn9O9myFh49JeUyXopRPqhLaA4lOQcFTHRMS";
        //URI of the API with the dates
        String uri = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + startDate + "&end_date=" + endDate + "&api_key=" + apiKey;

        //ClosableHttpClient use to make a Get request froma URL
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            //Make the request from the created URL
            HttpGet request = new HttpGet(uri);
            //get the response from the API
            HttpResponse response = client.execute(request);
            //Parse the response to string
            String responseBody = EntityUtils.toString(response.getEntity());
            //Print in console(ver de cambiar a algo mas amigable)
            System.out.println(responseBody);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
