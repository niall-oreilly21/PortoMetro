package com.metroporto;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Test2
{

    public static void main(String[] args) throws IOException
    {
        String api = "AIzaSyCENxP1GM6FrQA2okaazh7Hh-hVSyMGZjY";
        ZoneId timeZone = ZoneId.of("Europe/Lisbon");
        List<String> stations = Arrays.asList("Santo%20Ovídio,%204430%20Vila%20Nova%20de%20Gaia,", "Av.%20de%20Dom%20João%20II,%204200%20Porto,", "Joao%20De%20Deus", "Câmara%20de%20Gaia", "General%20Torres", "Jardim%20Do%20Morro",
         "São%20Bento", "Aliados","Trindade", "Faria%20Guimarães", "Marquês", "Combatentes", "Salgueiros", "Pólo%20Universitário",
          "IPO", "Hospital%20De%20São%20João");

        List<String> stationsStrings = Arrays.asList("Santo Ovídio", "D. João II", "João De Deus", "Câmara de Gaia", "General Torres", "Jardim Do Morro",
                "São Bento", "Aliados","Trindade", "Faria Guimarães", "Marquês", "Combatentes", "Salgueiros", "Pólo Universitário",
                "IPO", "Hospital De São João");

        LocalDateTime startTime = LocalDateTime.of(2023, 4, 12, 7, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 4, 12, 7, 30);

        for (int i = 0; i < 9; i++)
        {
            String origin = stations.get(i);
            String destination = stations.get(i + 1);

            for (LocalDateTime departureTime = startTime; departureTime.isBefore(endTime); departureTime = departureTime.plusMinutes(30))
            {
                ZonedDateTime zonedDepartureTime = ZonedDateTime.of(departureTime, timeZone);
                long departureTimestamp = zonedDepartureTime.toEpochSecond();

                try
                {
                    String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + ",%20Porto&destination=" + destination + ",%20Porto&mode=transit&subway_mode=D&transit_mode=subway&departure_time=" + departureTimestamp + "&key=" + api;
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNext())
                    {
                        builder.append(scanner.nextLine());
                    }
                    String response = builder.toString();
                    //String response= "{   \"geocoded_waypoints\" : [      {         \"geocoder_status\" : \"OK\", \"partial_match\" : true, \"place_id\" : \"ChIJq6oudttkJA0RcYh6_dWVjEk\", \"types\" : [ \"establishment\", \"point_of_interest\", \"transit_station\" ]      }, {         \"geocoder_status\" : \"OK\", \"place_id\" : \"ChIJqezitd1kJA0RmyhPjA6_g5c\", \"types\" : [ \"route\" ]      }   ], \"routes\" : [      {         \"bounds\" : {            \"northeast\" : {               \"lat\" : 41.13693689999999, \"lng\" : -8.6075468            }, \"southwest\" : {               \"lat\" : 41.13370219999999, \"lng\" : -8.6091462            }         }, \"copyrights\" : \"Map data ©2023\", \"legs\" : [            {               \"distance\" : {                  \"text\" : \"0.4 km\", \"value\" : 406               }, \"duration\" : {                  \"text\" : \"5 mins\", \"value\" : 298               }, \"end_address\" : \"Jardim do Morro, 4430-999 Vila Nova de Gaia, Portugal\", \"end_location\" : {                  \"lat\" : 41.13693689999999, \"lng\" : -8.6091373               }, \"start_address\" : \"General Torres, 4430-200 Vila Nova de Gaia, Portugal\", \"start_location\" : {                  \"lat\" : 41.13370219999999, \"lng\" : -8.6075468               }, \"steps\" : [                  {                     \"distance\" : {                        \"text\" : \"0.4 km\", \"value\" : 406                     }, \"duration\" : {                        \"text\" : \"5 mins\", \"value\" : 298                     }, \"end_location\" : {                        \"lat\" : 41.13693689999999, \"lng\" : -8.6091373                     }, \"html_instructions\" : \"Walk to Jardim do Morro, 4430-999 Vila Nova de Gaia, Portugal\", \"polyline\" : {                        \"points\" : \"s|`zFddps@y@PyA\\\\M@sB^UFk@NKBo@PC?WF]HWDADSj@AFECC?C?C@ABCBIHMJSP?AA??AA??AA?A?A?A?A?A@A??@A??@?@A??@?@?@?@A@@??@?@?@?@@??@IHc@^CA\"                     }, \"start_location\" : {                        \"lat\" : 41.13370219999999, \"lng\" : -8.6075468                     }, \"steps\" : [                        {                           \"distance\" : {                              \"text\" : \"0.3 km\", \"value\" : 277                           }, \"duration\" : {                              \"text\" : \"3 mins\", \"value\" : 187                           }, \"end_location\" : {                              \"lat\" : 41.13612699999999, \"lng\" : -8.608314                           }, \"html_instructions\" : \"Head \\u003cb\\u003enorth\\u003c/b\\u003e on \\u003cb\\u003eAv. da República\\u003c/b\\u003e toward \\u003cb\\u003eR. Luís de Camões\\u003c/b\\u003e\", \"polyline\" : {                              \"points\" : \"s|`zFddps@y@PyA\\\\M@sB^UFk@NKBo@PC?WF]HWD\"                           }, \"start_location\" : {                              \"lat\" : 41.13370219999999, \"lng\" : -8.6075468                           }, \"travel_mode\" : \"WALKING\"                        }, {                           \"distance\" : {                              \"text\" : \"28 m\", \"value\" : 28                           }, \"duration\" : {                              \"text\" : \"1 min\", \"value\" : 25                           }, \"end_location\" : {                              \"lat\" : 41.1362496, \"lng\" : -8.6085993                           }, \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eR. Rocha Leão\\u003c/b\\u003e\", \"maneuver\" : \"turn-left\", \"polyline\" : {                              \"points\" : \"ykazF|hps@ADSj@AF\"                           }, \"start_location\" : {                              \"lat\" : 41.13612699999999, \"lng\" : -8.608314                           }, \"travel_mode\" : \"WALKING\"                        }, {                           \"distance\" : {                              \"text\" : \"68 m\", \"value\" : 68                           }, \"duration\" : {                              \"text\" : \"1 min\", \"value\" : 57                           }, \"end_location\" : {                              \"lat\" : 41.1366889, \"lng\" : -8.608943699999999                           }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e toward \\u003cb\\u003eJardim do Morro\\u003c/b\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : {                              \"points\" : \"qlazFvjps@ECC?C?C@ABCBIHMJSP?AA??AA??AA?A?A?A?A?A@A??@A??@?@A??@?@?@?@A@@??@?@?@?@@??@\"                           }, \"start_location\" : {                              \"lat\" : 41.1362496, \"lng\" : -8.6085993                           }, \"travel_mode\" : \"WALKING\"                        }, {                           \"distance\" : {                              \"text\" : \"31 m\", \"value\" : 31                           }, \"duration\" : {                              \"text\" : \"1 min\", \"value\" : 27                           }, \"end_location\" : {                              \"lat\" : 41.1369169, \"lng\" : -8.6091462                           }, \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e toward \\u003cb\\u003eJardim do Morro\\u003c/b\\u003e\", \"maneuver\" : \"turn-right\", \"polyline\" : {                              \"points\" : \"ioazFzlps@IHc@^\"                           }, \"start_location\" : {                              \"lat\" : 41.1366889, \"lng\" : -8.608943699999999                           }, \"travel_mode\" : \"WALKING\"                        }, {                           \"distance\" : {                              \"text\" : \"2 m\", \"value\" : 2                           }, \"duration\" : {                              \"text\" : \"1 min\", \"value\" : 2                           }, \"end_location\" : {                              \"lat\" : 41.13693689999999, \"lng\" : -8.6091373                           }, \"html_instructions\" : \"Continue onto \\u003cb\\u003eJardim do Morro\\u003c/b\\u003e\", \"polyline\" : {                              \"points\" : \"wpazFdnps@CA\"                           }, \"start_location\" : {                              \"lat\" : 41.1369169, \"lng\" : -8.6091462                           }, \"travel_mode\" : \"WALKING\"                        }                     ], \"travel_mode\" : \"WALKING\"                  }               ], \"traffic_speed_entry\" : [], \"via_waypoint\" : []            }         ], \"overview_polyline\" : {            \"points\" : \"s|`zFddps@sCn@aC`@}Bl@qAVUp@AFECG?EDo@j@ACEAE?GJ@Nm@h@CA\"         }, \"summary\" : \"Av. da República\", \"warnings\" : [            \"Walking directions are in beta. Use caution – This route may be missing sidewalks or pedestrian paths.\"         ], \"waypoint_order\" : []      }   ], \"status\" : \"OK\"}\n";
                    Gson gson = new Gson();


                    JsonObject obj = gson.fromJson(response.toString(), JsonObject.class);

                    JsonArray routes = obj.getAsJsonArray("routes");

                    if (routes.size() > 0)
                    {
                        JsonObject routeObj = routes.get(0).getAsJsonObject();

                        //System.out.println("routeObj");
                        //System.out.println(routeObj);

                        JsonArray legs1 = routeObj.getAsJsonArray("legs");
                        JsonObject leg = (JsonObject) legs1.get(0);

                        System.out.println();
                        JsonElement startLocation = leg.get("start_address");

                        System.out.println(startLocation);
                        JsonElement endLocation = leg.get("end_address");
                        System.out.println(endLocation);

                        Route route = gson.fromJson(routeObj, Route.class);

                        //System.out.println(route);

                        Route.Leg[] legs = route.getLegs();

                        //System.out.println(legs[0]);
                        Route.Step[] steps = legs[0].getSteps();

                        for (Route.Step step : steps)
                        {
                            if (step.getTransit_details() != null)
                            {
                                Route.TransitDetails transitDetails = step.getTransit_details();
//                                if (transitDetails.getDeparture_stop().getName().equalsIgnoreCase(stationsStrings.get(i)) && transitDetails.getArrival_stop().getName().equalsIgnoreCase(stationsStrings.get(i+1)))
//                                {
                                System.out.println(stationsStrings.get(i));
                                System.out.println("Departure Time: " + transitDetails.getDeparture_time().getText());
                                System.out.println(stationsStrings.get(i + 1));
                                System.out.println("Arrival Time: " + transitDetails.getArrival_time().getText());
//                                System.out.println();
                                //break;
                                //}
                                //}
                            }
                            else
                            {
                                System.out.println("ERROR");
                            }
//                        for (Route.Step step : steps)
//                        {
//                            if (step.getTransit_details() != null)
//                            {
//                                Route.TransitDetails transitDetails = step.getTransit_details();
//                                System.out.println("Line: " + transitDetails.getLine().getShort_name());
//                                System.out.println("Departure Time: " + transitDetails.getDeparture_time().getText());
//                                System.out.println("Arrival Time: " + transitDetails.getArrival_time().getText());
//                                System.out.println("From Stop: " + transitDetails.getDeparture_stop().getName());
//                                System.out.println("To Stop: " + transitDetails.getArrival_stop().getName());
//                                System.out.println();
//
//                                // Get the arrival times at all stops along the transit route
//                                Route.Stop[] stops = transitDetails.getStops();
//                                if (stops != null)
//                                {
//                                    for (Route.Stop stop : stops)
//                                    {
//                                        System.out.println("Stop Name: " + stop.getName());
//                                        System.out.println("Arrival Time: " + stop.getArrival_time().getText());
//                                        System.out.println();
//                                    }
//                                }
//                            }
//                        }
                        }

                    }
                } catch (ProtocolException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
}
