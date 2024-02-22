package extras;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;

public class ConfigLoader {

    public static String loadApiKey(String key) {
        //create teh vars props and apiKey
        Properties prop = new Properties();
        String apiKey = "";

        //use the config.properties to set the ApiKey
        try (InputStream input = new FileInputStream("src/config.properties")) {
            //load the prop with the key and their values
            prop.load(input);
            //set apiKey with the value of the key
            apiKey = prop.getProperty(key);
        } catch (IOException ex) {
            //Error window
            JOptionPane.showMessageDialog(null, "An error was found while loading the configuration:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        //return the apikey
        return apiKey;
    }
}

