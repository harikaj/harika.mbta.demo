package main;

import api.RestfulRequestor;
import constants.ResourceConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 */
//Question No 1
public class RouteLongNameEvaluator {
    public void getLongNameForRoute() {
        RestfulRequestor restfulRequestor = new RestfulRequestor();
        String routesReqParams = "filter[type]=0,1&fields[route]=long_name";
        JsonObject routesObject = restfulRequestor.getRoutes(routesReqParams);
        JsonArray arr = routesObject.get(ResourceConstants.DATA).getAsJsonArray();
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = (JsonObject) arr.get(i);
            String longName = obj.get(ResourceConstants.ATTRIBUTES).getAsJsonObject().get(ResourceConstants.LONG_NAME).toString();
            System.out.println("Long Name: " + longName);
        }
    }
}
