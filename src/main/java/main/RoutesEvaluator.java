package main;

import api.RestfulRequestor;
import constants.ResourceConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

/**
 *
 */
//Questions 3
public class RoutesEvaluator {

    /**
     * @param source
     * @param destination
     */
    public void printRoutesForProvidedStops(String source, String destination) {
        //We could input the variables as args to the main method or hardcode as below.
        //String stop1 = "Davis";
        //String stop2 = "Kendall/MIT";
        String stop1 = "Ashmont";
        String stop2 = "Arlington";
        RestfulRequestor restfulRequestor = new RestfulRequestor();
        String routesReqParams = "filter[type]=0,1&fields[route]=long_name";
        JsonObject routesObject = restfulRequestor.getRoutes(routesReqParams);
        JsonArray routesArray = routesObject.get("data").getAsJsonArray();

        Map<String, List<String>> routeWithStopsMap = new HashMap<String, List<String>>();
        for (int i = 0; i < routesArray.size(); i++) {
            JsonObject routeObject = (JsonObject) routesArray.get(i);
            //For example routeId : Red
            String routeId = routeObject.get("id").getAsString();
            String longName = routeObject.get(ResourceConstants.ATTRIBUTES).getAsJsonObject().get(ResourceConstants.LONG_NAME).toString();
            String stopsReqParams = "filter[route]=" + routeId + "&fields[stop]=id,name";
            JsonObject stopsForTheRoute = restfulRequestor.getStops(stopsReqParams);
            JsonArray stopsArray = stopsForTheRoute.get(ResourceConstants.DATA).getAsJsonArray();

            List<String> listOfStopNames = new ArrayList<String>();
            for (int j = 0; j < stopsArray.size(); j++) {
                listOfStopNames.add(stopsArray.get(j).getAsJsonObject().get(ResourceConstants.ATTRIBUTES).getAsJsonObject().get(ResourceConstants.NAME).getAsString());
            }
            routeWithStopsMap.put(longName, listOfStopNames);
        }
        printAllRoutesAvailable(routeWithStopsMap, stop1, stop2);

    }

    /**
     * @param routeWithStopsMap
     * @param stop1
     * @param stop2
     */
    private static void printAllRoutesAvailable(Map<String, List<String>> routeWithStopsMap, String stop1, String stop2) {
        List<String> availableRoutes = new ArrayList<String>();
        for (Map.Entry<String, List<String>> entry : routeWithStopsMap.entrySet()) {
            List<String> stops = entry.getValue();
            for (String stop : stops) {
                if (stop.equals(stop1)) {
                    availableRoutes.add(entry.getKey());
                } else if (stop.equals(stop2)) {
                    if (!availableRoutes.contains(entry.getKey())) {
                        availableRoutes.add(entry.getKey());
                    }
                }
            }
        }
        String availableRoutesString = "";
        for (String availableRoute : availableRoutes) {
            availableRoutesString += availableRoute + ',';
        }
        System.out.println(stop1 + " to " + stop2 + " -> " + availableRoutesString);
    }
}
