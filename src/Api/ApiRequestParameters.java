package Api;

import java.net.http.HttpClient;

public abstract class ApiRequestParameters {
    private HttpClient httpClient;
    private String baseUrl;
    private String endpoint;
    private String apiKey;
    private String rover;

    public HttpClient getHttpClient () {
        return this.httpClient;
    }

    public void setHttpClient (HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getBaseUrl () {
        return (this.baseUrl);
    }

    public void setBaseUrl (String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpoint () {
        return (this.endpoint);
    }

    public void setEndpoint (String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiKey (){
        return (this.apiKey);
    }

    public void setApiKey (String apiKey){
        this.apiKey = apiKey;
    }

    public String getRover (){
        return (this.rover);
    }

    public void setRover (String rover){
        this.rover = rover;
    }
}
