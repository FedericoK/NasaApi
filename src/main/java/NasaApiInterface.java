import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import components.ButtonCreator;
import extras.ConfigLoader;
import extras.DataDisplayWindow;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;
import java.util.Properties;

public class NasaApiInterface {
    //Create the apiKey and assing the value from out config.prperties
    static String apiKey = ConfigLoader.loadApiKey("nasaApiKey");

    public static void main(String[] args) {
        JFrame frame = new JFrame("NASA API NeoWs");//Tittle of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stop the program
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

        //Creation of the Button, send only the actionListener
        JButton neoButton = ButtonCreator.createNeoButton(actionListener);

        //Add the components to the window and we set visible=True
        frame.getContentPane().add(new JLabel("Start Date (YYYY-MM-DD):"));
        frame.getContentPane().add(startDateField);
        frame.getContentPane().add(new JLabel("End Date (YYYY-MM-DD):"));
        frame.getContentPane().add(endDateField);
        frame.getContentPane().add(neoButton);

        frame.setVisible(true);
    }

    //Function to fetch the data of Asteroids - NeoWs
    private static void fetchNeoData(String startDateStr, String endDateStr) {
        //Concat the uri for the request of the API
        String uri = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + startDateStr + "&end_date=" + endDateStr + "&api_key=" + apiKey;

        //in a try or catch we make a get request to the API
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(uri);
            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            //after getting response we make it a jsonObject to work with it
            JSONObject jsonResponse = new JSONObject(responseBody);
            //we get the near_earth_object JSONObject because it has all the info that we need
            JSONObject nearEarthObjects = jsonResponse.getJSONObject("near_earth_objects");

            //Parse the date to LocalDate, so we can use it on a for loop
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // show the data in a new window
            // manipulateData returns a String[][]
            DataDisplayWindow.displayData(manipulateData(nearEarthObjects, startDate, endDate));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error was encounter, please verified your input and try again. \nError:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    //Manipulation of NEOdata objects
    private static String[][] manipulateData(JSONObject nearEarthObjects, LocalDate startDate, LocalDate endDate) {
        //Initialize a List of stings, so we can gather all of our data
        List<String[]> dataRows = new ArrayList<>();

        //in the for we use date that start with our statDate
        //it will continue only if the current date is not a day after the endDate
        //it add 1 day after every loop
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            //Parse the current date to YYYY-MM-DD with DateTimeFormatter.ISO_LOCAL_DATE
            String dateKey = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
            if (!nearEarthObjects.has(dateKey)) {
                continue; // Skip dates without NEO data
            }
            //get the JSONArray that has as key the current date
            JSONArray neosForDate = nearEarthObjects.getJSONArray(dateKey);

            //we loop through every object inside our current Date array
            for (int i = 0; i < neosForDate.length(); i++) {
                JSONObject neo = neosForDate.getJSONObject(i);//get the current NEO object(i)
                //data inside other object or arrays
                JSONObject diameter = neo.getJSONObject("estimated_diameter").getJSONObject("meters");
                JSONObject closeApproachData = neo.getJSONArray("close_approach_data").getJSONObject(0);
                JSONObject relativeVelocity = closeApproachData.getJSONObject("relative_velocity");
                JSONObject missDistance = closeApproachData.getJSONObject("miss_distance");

                //add all the data we need in an Array of String
                String[] rowData = new String[]{
                        neo.getString("id"),//id of NEO
                        dateKey,//date of the object
                        neo.getString("name"),//Name
                        neo.getBoolean("is_potentially_hazardous_asteroid") ? "Yes" : "No",//if true ->yes: false ->no
                        String.valueOf(diameter.getDouble("estimated_diameter_min")),//get the value in string inside "meters" object
                        String.valueOf(diameter.getDouble("estimated_diameter_max")),//get the value in string inside "meters" object
                        relativeVelocity.getString("kilometers_per_hour"),//get KM/H inside "relative_velocity" object
                        missDistance.getString("kilometers")//get kilometers inside "miss_distance" object
                };
                //add the rowData of the current object into dataRows
                dataRows.add(rowData);
            }
        }

        //we create a bidimensional array the same size of rows as the size of ou array dataRows
        String[][] data = new String[dataRows.size()][];
        //we transform dataRows into the bidimensional array we created
        data = dataRows.toArray(data);
        //return data
        return data;
    }

}
