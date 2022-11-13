import java.net.http.HttpClient;
import java.time.LocalDate;
import com.google.gson.*;

import Cache.PhotoCache;
import Config.Configuration;
import Parser.*;
import Api.*;

public class App {
    public static void main(String[] args) throws Exception {
        String consoleInput = "";

        // Get the instance of our (Singleton) configuration class.
        Configuration configuration = Configuration.getInstance();

        // Load detault options from the configuration file.  Unless these are overridden on the command line.  We'll use these.
        String rover = configuration.getDefaultRover();
        String camera = configuration.getDefaultCamera();
        Integer photosPerDay = configuration.getDefaultPhotosPerDay();
        Integer daysPerQuery = configuration.getDefaultDaysPerQuery();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(daysPerQuery);

        // Initialize our caching mechanism
        PhotoCache photoCache = new PhotoCache();

        // create a HTTP client.  We'll keep it for the duration
        var client = HttpClient.newHttpClient();

        // User can break out of the loop below by entering 'quit' on the command line.
        while (true) {
            System.out.println("Press <Enter> to retrieve photos from Mars rover '" + rover + "' or 'quit' to exit");

            consoleInput = System.console().readLine();

            // Check for user wanting out.
            if (consoleInput.equalsIgnoreCase("quit"))
                break;
            else if (consoleInput.trim().length() > 0) {
                OptionsParser optionsParser = new OptionsParser();
                optionsParser.ParseOptionString(consoleInput);

                rover = optionsParser.getRover() != null ? optionsParser.getRover() : rover;
                camera = optionsParser.getCamera() != null ? optionsParser.getCamera() : camera;
                photosPerDay = optionsParser.getPhotosPerDay() != null ? optionsParser.getPhotosPerDay() : photosPerDay;
                daysPerQuery = optionsParser.getDaysPerQuery() != null ? optionsParser.getDaysPerQuery() : daysPerQuery;
                startDate = optionsParser.getStartDate() != null ? optionsParser.getStartDate() : startDate;
                endDate = optionsParser.getEndDate() != null ? optionsParser.getEndDate() : endDate;
            }

            System.out.println("\nRetrieving Mars photos:");
            System.out.println("   From Rover: " + rover);
            System.out.println("   Using Camera: " + camera);
            System.out.println("   Photos Per Day: " + photosPerDay.toString());
            //System.out.println("   Days Per Query: " + daysPerQuery.toString());
            System.out.println("   Start Date: " + startDate.toString());
            System.out.println("   End Date: " + endDate.toString() + "\n");

            RoversApiRequestParameters requestParameters = new RoversApiRequestParameters();
            requestParameters.setHttpClient(client);
            requestParameters.setPhotoCache(photoCache);
            requestParameters.setBaseUrl(configuration.getNasaApiBaseUrl());
            requestParameters.setEndpoint(configuration.getNasaApiRoversEndpoint());
            requestParameters.setApiKey(configuration.getNasaApiKey());
            requestParameters.setRover(rover);
            requestParameters.setCamera(camera);
            requestParameters.setStartDate(startDate);
            requestParameters.setEndDate(endDate);
            requestParameters.setPhotosPerDay(photosPerDay);

            RoversApiRequest apiRequest = new RoversApiRequest();
            JsonObject jsonResponseOutput = apiRequest.ProcessApiRequest(requestParameters);

            System.out.println("\nOutput from Rover Photos request...\n");
            System.out.println(jsonResponseOutput.toString().replaceAll("\\\\",""));

            ManifestsApiRequestParameters mrp = new ManifestsApiRequestParameters();
            mrp.setHttpClient(client);
            mrp.setBaseUrl(configuration.getNasaApiBaseUrl());
            mrp.setEndpoint(configuration.getNasaApiManifestsEndpoint());
            mrp.setApiKey(configuration.getNasaApiKey());
            mrp.setRover(rover);

            ManifestsApiRequest mr = new ManifestsApiRequest();
            jsonResponseOutput = mr.ProcessApiRequest(mrp);

            System.out.println("\nOutput from Rover Manifest request...\n");
            //System.out.println(jsonResponseOutput.toString().replaceAll("\\\\",""));
        }
    }
}
