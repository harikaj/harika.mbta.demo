package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


/**
 * Restful requestor to call into MBTA Api. Based on the specified request,
 * this methods will fetch the http response from the vendor service.
 */
public class RestfulRequestor {

    /**Gets the routes based on the params specified.
     * @param params
     * @return
     */
    public JsonObject getRoutes(String params) {
        return this.getApi("https://api-v3.mbta.com/routes?" + params);
    }

    /**
     * Gets the stops based on the params specified.
     * @param params
     * @return
     */
    public JsonObject getStops(String params) {
        return this.getApi("https://api-v3.mbta.com/stops?" + params);
    }

    /**
     * Call the get restful api based on the request url
     * and converts the resposne into jsonobject and returns it
     * @param getUrl
     * @return
     */
    public JsonObject getApi(String getUrl) {
        JsonObject jsonObject = new JsonObject();
        try {

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(getUrl);
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                if (response.getStatusLine().getStatusCode() == 429) {
                    throw new RuntimeException("Failed : Maximum requests reached: "
                            + response.getStatusLine().getStatusCode());
                } else {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
                }
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            String output = EntityUtils.toString(response.getEntity());
            jsonObject = new JsonParser().parse(output).getAsJsonObject();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return jsonObject;
    }
}
