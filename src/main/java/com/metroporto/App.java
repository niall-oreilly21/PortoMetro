package com.metroporto;


import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class App
{
    //"AIzaSyApc9RB30dgry1pBlQJHsNAwEmUILoSW0k"
    public static void main(String[] args)
    {
        String apiKey = "AIzaSyCENxP1GM6FrQA2okaazh7Hh-hVSyMGZjY"; // Replace with your own Google Maps API key

        String stationName = "Aliados";
        String outputFilePath = "timetable.txt";

        try {
            String origin = "Aliados, Porto";
            String destination = "Hospital Sao Joao, Porto";
            String mode = "transit";

            // Set the initial departure time to the next Monday at 6am
            String departureTime = "2023-04-10T06:00:00";

//            https://maps.googleapis.com/maps/api/directions/json?origin=Aliados, Porto&destination=Hospital Sao Joao, Porto&mode=transit&departure_time=2023-04-10T06:00:00&key=AIzaSyCENxP1GM6FrQA2okaazh7Hh-hVSyMGZjY
            while (departureTime.compareTo("2023-04-11T01:00:00") <= 0) {
                // Build the URL string with the current departure time
                String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin="
                        + origin + "&destination=" + destination + "&mode=" + mode + "&departure_time="
                        + departureTime + "&key=" + apiKey;

                // Create a URL object from the URL string
                URL url = new URL(urlString);

                // Create a HttpURLConnection object to send the GET request
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read the response from the API and print it to the console
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println(response.toString());

                // Increment the departure time by one hour
                String[] parts = departureTime.split("T|:");
                int hour = Integer.parseInt(parts[3]);
                hour++;
                if (hour == 24) {
                    // If the hour is 24, set it to 00 and increment the date by one day
                    parts[3] = "00";
                    int day = Integer.parseInt(parts[2]);
                    day++;
                    parts[2] = String.format("%02d", day);
                } else {
                    // Otherwise, just increment the hour
                    parts[3] = String.format("%02d", hour);
                }
                departureTime = String.join("T", parts);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}










