import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesGetter {

    public static String getValue(String key) throws IOException {
        String result = "";
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";

            inputStream = PropertiesGetter.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }

            result = prop.getProperty(key);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }

}
