package main;

import api.RestfulRequestor;
import constants.ResourceConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

/**
 *
 */
//Questions 2 a, b, c
public class NumberOfStopsEvaluator {

    /**
     * Prints the highest number of stops and lowest number of stops for a route
     */
    public void printHighestAndLowestNoOfStops() {

        RestfulRequestor restfulRequestor = new RestfulRequestor();
        String routesReqParams = "filter[type]=0,1&fields[route]=long_name";
        JsonObject routesObject = restfulRequestor.getRoutes(routesReqParams);
        JsonArray routesArray = routesObject.get("data").getAsJsonArray();
        Map<String, Integer> stopsMap = new HashMap<String, Integer>();

        Map<String, List<String>> routeWithStopsMap = new HashMap<String, List<String>>();
        for (int i = 0; i < routesArray.size(); i++) {
            JsonObject routeObject = (JsonObject) routesArray.get(i);
            //For example routeId : Red
            String routeId = routeObject.get(ResourceConstants.ID).getAsString();
            String longName = routeObject.get(ResourceConstants.ATTRIBUTES).getAsJsonObject().get(ResourceConstants.LONG_NAME).toString();
            String stopsReqParams = "filter[route]=" + routeId + "&fields[stop]=id,name";
            JsonObject stopsForTheRoute = restfulRequestor.getStops(stopsReqParams);
            JsonArray stopsArray = stopsForTheRoute.get(ResourceConstants.DATA).getAsJsonArray();

            List<String> listOfStopIds = new ArrayList<String>();
            for(int j=0 ;j< stopsArray.size() ; j ++)
            {
                listOfStopIds.add(stopsArray.get(j).getAsJsonObject().get(ResourceConstants.ATTRIBUTES).getAsJsonObject().get(ResourceConstants.NAME).getAsString());
            }
            routeWithStopsMap.put(longName, listOfStopIds);
            stopsMap.put(routeId, stopsArray.size());
        }
        printMinAndMaxStops(stopsMap);

        printRoutesWithCommonStops(routeWithStopsMap);

    }

    /**
     * @param stopsMap
     */
    private static void printMinAndMaxStops(Map<String, Integer> stopsMap) {
        int maxValueInMap=(Collections.max(stopsMap.values()));
        int minValueInMap=(Collections.min(stopsMap.values()));
        for (Map.Entry<String, Integer> entry : stopsMap.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                System.out.println(entry.getKey() + " has the highest number of routes of "+ entry.getValue());
            }
            else if (entry.getValue()==minValueInMap) {
                System.out.println(entry.getKey() + " has the lowest number of routes of "+ entry.getValue());
            }
        }
    }

    /**
     * @param routeWithStopsMap
     */
    private static void printRoutesWithCommonStops(Map<String, List<String>> routeWithStopsMap) {
        for (Map.Entry<String, List<String>> entry : routeWithStopsMap.entrySet()) {
            List<String> stops = entry.getValue();
            for(String stop: stops) {
                for (Map.Entry<String, List<String>> entry1 : routeWithStopsMap.entrySet()) {
                    if(entry.getKey() != entry1.getKey()) {
                        List<String> stops1 = entry1.getValue();
                        if (stops1.contains(stop)) {
                            System.out.println("Route : " + entry.getKey() + " and route: " + entry1.getKey() + " have a common stop at: " + stop);
                        }
                    }
                }
            }
        }
    }
}
