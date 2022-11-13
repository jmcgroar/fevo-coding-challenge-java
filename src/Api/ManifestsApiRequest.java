package Api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

import com.google.gson.JsonObject;

public class ManifestsApiRequest implements ApiRequest<ManifestsApiRequestParameters> {
    @Override
    public JsonObject ProcessApiRequest (ManifestsApiRequestParameters marp) throws IOException, InterruptedException{
        JsonObject jsonResponseOutput = new JsonObject();

        // Create the NASA API request URL. 
        String nasaRoverApiUrl = String.format(marp.getBaseUrl() + marp.getEndpoint(), 
                                               marp.getRover(), 
                                               marp.getApiKey());

        // Create the request
        HttpRequest request = HttpRequest.newBuilder(
                                URI.create(nasaRoverApiUrl))
                                .GET()
                                .header("accept", "application/json")
                                .build();

        HttpResponse<String> response = marp.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            jsonResponseOutput = JsonParser.parseString(response.body()).getAsJsonObject();
        }

    return (jsonResponseOutput);
    }
}
