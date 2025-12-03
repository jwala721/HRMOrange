package genericutilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (Exception ex) {
            throw new RuntimeException("config.properties file not found in resources folder.", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
