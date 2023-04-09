import com.google.gson.*;
import com.metroporto.RouteTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;



    public class Test
    {
        public static void main(String[] args)
        {
//        LocalDateTime departureDateTime = LocalDateTime.of(2023, 4, 10, 6, 0);
//        ZoneId departureTimeZone = ZoneId.of("Europe/Lisbon");
//        ZonedDateTime zonedDepartureTime = ZonedDateTime.of(departureDateTime, departureTimeZone);
//        long departureTimestamp = zonedDepartureTime.toEpochSecond();
//
//        System.out.println(departureTimestamp);


            String api = "AIzaSyCENxP1GM6FrQA2okaazh7Hh-hVSyMGZjY";// make API call and retrieve response as a string

            // Define the start and end times for the departure time range
            LocalDateTime startTime = LocalDateTime.of(2023, 4, 10, 6, 0);
            LocalDateTime endTime = LocalDateTime.of(2023, 4, 10, 8, 0);

            // Define the time zone for the departure times
            ZoneId timeZone = ZoneId.of("Europe/Lisbon");

            // Iterate over the departure times in the range and make an API request for each one
            for (LocalDateTime departureTime = startTime; departureTime.isBefore(endTime); departureTime = departureTime.plusMinutes(30))
            {
                // Convert the departure time to a UNIX timestamp
                ZonedDateTime zonedDepartureTime = ZonedDateTime.of(departureTime, timeZone);
                long departureTimestamp = zonedDepartureTime.toEpochSecond();

                try
                {
                    // Set up the URL and HttpURLConnection objects
                    String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin=Aliados,%20Porto&destination=Hospital%20Sao%20Joao,%20Porto&mode=transit&subway_mode=D&transit_mode=subway&departure_time=" +departureTimestamp+ "&key=" + api;
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    // Get the response from the API
                    Scanner scanner = new Scanner(conn.getInputStream());
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNext())
                    {
                        builder.append(scanner.nextLine());
                    }
                    String response = builder.toString();

                    Gson gson = new Gson();
                    JsonObject obj = gson.fromJson(response.toString(), JsonObject.class);
                    JsonArray routes = obj.getAsJsonArray("routes");
                    JsonObject routeObj = routes.get(0).getAsJsonObject();
                    RouteTest route = gson.fromJson(routeObj, RouteTest.class);

                    RouteTest.Leg[] legs = route.getLegs();
                    RouteTest.Step[] steps = legs[0].getSteps();
                    RouteTest.ArrivalTime arrivalTime = null;

                    for (RouteTest.Step step : steps) {
                        if (step.getTransit_details() != null) {
                            arrivalTime = step.getTransit_details().getArrival_time();
                            break;
                        }
                    }

                    if (arrivalTime != null) {
                        System.out.println("Arrival Time: " + arrivalTime.getText());
                    } else {
                        System.out.println("No transit details found in the route.");
                    }

                } catch (ProtocolException e)
                {
                    e.printStackTrace();
                } catch (MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


