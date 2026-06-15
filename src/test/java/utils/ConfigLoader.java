package utils;

import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/config/config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getBaseUrl(){
        String prop = properties.getProperty("base.uri");
        if(prop != null) return prop;
        else throw new RuntimeException("property base.uri is not specified in the config.properties file");
    }

    public String getAuthUsername(){
        String prop = properties.getProperty("auth.username");
        if(prop != null) return prop;
        else throw new RuntimeException("property auth.username is not specified in the config.properties file");
    }

    public String getAuthPassword(){
        String prop = properties.getProperty("auth.password");
        if(prop != null) return prop;
        else throw new RuntimeException("property auth.password is not specified in the config.properties file");
    }
}
