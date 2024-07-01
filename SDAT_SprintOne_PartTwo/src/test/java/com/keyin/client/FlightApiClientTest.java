package com.keyin.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.flight_client.FlightApiClient;
import com.keyin.flight_client.model.Passenger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FlightApiClientTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse httpResponse;

    @Mock
    private HttpEntity httpEntity;

    @InjectMocks
    private FlightApiClient flightApiClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAllPassengers() throws IOException {
        // Mocking the response from the server
        String json = "[{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\"}]";
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(EntityUtils.toString(httpEntity)).thenReturn(json);

        // Calling the method to be tested
        flightApiClient.fetchAllPassengers();

        // Verify the expected behavior
        Passenger[] passengers = objectMapper.readValue(json, Passenger[].class);
        assertEquals(1, passengers.length);
        assertEquals(1, passengers[0].getId());
        assertEquals("John", passengers[0].getFirstName());
        assertEquals("Doe", passengers[0].getLastName());
        assertEquals("1234567890", passengers[0].getPhoneNumber());
    }

    @Test
    public void testAddPassenger() throws IOException {
        // Mocking the response from the server
        String json = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\"}";
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(EntityUtils.toString(httpEntity)).thenReturn(json);

        // Creating a passenger to add
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setPhoneNumber("1234567890");

        // Calling the method to be tested
        flightApiClient.addPassenger(passenger);

        // Verify the expected behavior
        Passenger createdPassenger = objectMapper.readValue(json, Passenger.class);
        assertEquals(1, createdPassenger.getId());
        assertEquals("John", createdPassenger.getFirstName());
        assertEquals("Doe", createdPassenger.getLastName());
        assertEquals("1234567890", createdPassenger.getPhoneNumber());
    }

    @Test
    public void testUpdatePassenger() throws IOException {
        // Mocking the response from the server
        String json = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\"}";
        when(httpClient.execute(any(HttpPut.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(EntityUtils.toString(httpEntity)).thenReturn(json);

        // Creating a passenger to update
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setPhoneNumber("1234567890");

        // Calling the method to be tested
        flightApiClient.updatePassenger(1, passenger);

        // Verify the expected behavior
        Passenger updatedPassenger = objectMapper.readValue(json, Passenger.class);
        assertEquals(1, updatedPassenger.getId());
        assertEquals("John", updatedPassenger.getFirstName());
        assertEquals("Doe", updatedPassenger.getLastName());
        assertEquals("1234567890", updatedPassenger.getPhoneNumber());
    }

    @Test
    public void testDeletePassenger() throws IOException {
        // Mocking the response from the server
        when(httpClient.execute(any(HttpDelete.class))).thenReturn(httpResponse);
        when(httpResponse.getStatusLine().getStatusCode()).thenReturn(200);

        // Calling the method to be tested
        flightApiClient.deletePassenger(1);

        // Verify the expected behavior
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

}

