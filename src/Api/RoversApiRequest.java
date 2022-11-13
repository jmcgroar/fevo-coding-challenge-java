package Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import com.google.gson.*;

import com.google.gson.JsonObject;

public class RoversApiRequest implements ApiRequest<RoversApiRequestParameters>{
    @Override
    public JsonObject ProcessApiRequest (RoversApiRequestParameters rarp) throws IOException, InterruptedException {
        JsonObject jsonResponseOutput = new JsonObject();

        for (LocalDate queryDate = rarp.getStartDate(); !queryDate.isAfter(rarp.getEndDate()); queryDate = queryDate.plusDays(1)) {
            JsonArray jsonOutputPhotosArray = new JsonArray();

            String cacheKey = rarp.getRover().toLowerCase() + rarp.getCamera().toLowerCase() + queryDate.toString();

            String cachedPhotos = rarp.getPhotoCache().Get(cacheKey);
            if (cachedPhotos != null) {
                jsonOutputPhotosArray.addAll(JsonParser.parseString(cachedPhotos).getAsJsonArray());
            } else {
                Integer page = 1;
                Integer totalPhotosReturned = 0;
                Integer queryPhotosReturned = 0;
                jsonOutputPhotosArray = new JsonArray();

                do {
                    // Create the NASA API request URL. 
                    String nasaRoverApiUrl = String.format(rarp.getBaseUrl() + rarp.getEndpoint(), 
                                                           rarp.getRover(), 
                                                           queryDate, 
                                                           rarp.getCamera(),
                                                           page, 
                                                           rarp.getApiKey());                    
                     // Create the request
                    HttpRequest request = HttpRequest.newBuilder(
                                            URI.create(nasaRoverApiUrl))
                                            .GET()
                                            .header("accept", "application/json")
                                            .build();

                     HttpResponse<String> response = rarp.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                     if (response.statusCode() == 200) {
                        JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                        JsonArray jsonResponsePhotosArray = (JsonArray) jsonResponse.get("photos");
                        queryPhotosReturned = jsonResponsePhotosArray.size();
                            
                        for (int index = 0; index + totalPhotosReturned < rarp.getPhotosPerDay() && index < queryPhotosReturned; index++) {
                            JsonElement photoElement = ((JsonObject) jsonResponsePhotosArray.get(index)).get("img_src");
                            jsonOutputPhotosArray.add(photoElement);
                        }                            totalPhotosReturned += queryPhotosReturned;  
                    } else {
                        // What to do here? Put Error text to console?  Quit?
                    }

                page++;
                }
                while (totalPhotosReturned < rarp.getPhotosPerDay() && queryPhotosReturned > 0);
            }

        jsonResponseOutput.addProperty(queryDate.toString(), jsonOutputPhotosArray.toString());
        rarp.getPhotoCache().Put(cacheKey, jsonOutputPhotosArray.toString());
        }

    return (jsonResponseOutput);
    }
}
