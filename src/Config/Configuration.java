package Config;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public final class Configuration {
    private static Configuration instance = null;

    private String nasaApiBaseUrl;
    private String nasaApiRoversEndpoint;
    private String nasaApiManifestsEndpoint;
    private String nasaApiKey;
    private String defaultRover;
    private String defaultCamera;
    private Integer defaultPhotosPerDay;
    private Integer defaultDaysPerQuery;

    private Configuration () throws IOException {
        Properties appProperties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        appProperties.load(inputStream);

        nasaApiBaseUrl = appProperties.getProperty("NASA_API_BASE_URL");
        nasaApiRoversEndpoint = appProperties.getProperty("NASA_API_ROVERS_ENDPOINT");
        nasaApiManifestsEndpoint = appProperties.getProperty("NASA_API_MANIFESTS_ENDPOINT");
        nasaApiKey = appProperties.getProperty("NASA_API_KEY");
        defaultRover = appProperties.getProperty("DEFAULT_ROVER");
        defaultCamera = appProperties.getProperty("DEFAULT_CAMERA");
        defaultPhotosPerDay = Integer.parseInt(appProperties.getProperty("DEFAULT_PHOTOS_PER_DAY"));
        defaultDaysPerQuery = Integer.parseInt(appProperties.getProperty("DEFAULT_DAYS_PER_QUERY"));
    }

    public static Configuration getInstance () throws IOException {
        if (instance == null)
            instance = new Configuration();

        return (instance);
        }

    public String getNasaApiBaseUrl () {
        return (nasaApiBaseUrl);
    }

    public String getNasaApiRoversEndpoint () {
        return (nasaApiRoversEndpoint);
    }

    public String getNasaApiManifestsEndpoint () {
        return (nasaApiManifestsEndpoint);
    }

    public String getNasaApiKey () {
        return (nasaApiKey);
    }

    public String getDefaultRover () {
        return (defaultRover);
    }

    public String getDefaultCamera () {
        return (defaultCamera);
    }

    public Integer getDefaultPhotosPerDay () {
        return (defaultPhotosPerDay);
    }

    public Integer getDefaultDaysPerQuery () {
        return (defaultDaysPerQuery);
    }
}
