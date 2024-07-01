package com.keyin.flight_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.flight_client.model.Passenger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class FlightApiClient {

    private final String BASE_URL = "http://localhost:8080/api"; // Replace with your server's base URL
    private final HttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void fetchAllPassengers() throws IOException {
        String endpoint = "/passengers";
        HttpGet request = new HttpGet(BASE_URL + endpoint);

        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        Passenger[] passengers = objectMapper.readValue(json, Passenger[].class);

        for (Passenger passenger : passengers) {
            System.out.println(passenger.toString());
        }
    }

    public void addPassenger(Passenger passenger) throws IOException {
        String endpoint = "/passengers";
        HttpPost request = new HttpPost(BASE_URL + endpoint);
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(passenger));
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        Passenger createdPassenger = objectMapper.readValue(json, Passenger.class);
        System.out.println(createdPassenger.toString());
    }

    public void updatePassenger(int id, Passenger passenger) throws IOException {
        String endpoint = "/passengers/" + id;
        HttpPut request = new HttpPut(BASE_URL + endpoint);
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(passenger));
        request.setEntity(entity);
        request.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());

        Passenger updatedPassenger = objectMapper.readValue(json, Passenger.class);
        System.out.println(updatedPassenger.toString());
    }

    public void deletePassenger(int id) throws IOException {
        String endpoint = "/passengers/" + id;
        HttpDelete request = new HttpDelete(BASE_URL + endpoint);

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println("Passenger deleted successfully.");
        } else {
            System.out.println("Failed to delete passenger.");
        }
    }
}
