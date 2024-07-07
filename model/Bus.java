package model;

import java.io.Serializable;

public class Bus implements Serializable {
    public static final String FILE_NAME = "database/Bus.txt";
    private String busNumber;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private int availableSeats;

    public Bus(String busNumber, String source, String destination,
               String departureTime, String arrivalTime, int totalSeats, int i) {
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public Bus(String csvLine) {
        String[] values = csvLine.split(",");
        if (values.length == 7) {
            this.busNumber = values[0];
            this.source = values[1];
            this.destination = values[2];
            this.departureTime = values[3];
            this.arrivalTime = values[4];
            this.totalSeats = Integer.parseInt(values[5]);
            this.availableSeats = Integer.parseInt(values[6]);
        } else {
            throw new IllegalArgumentException("Invalid CSV line: " + csvLine);
        }
    }

    public void updateAvailableSeats(int bookedSeats) {
        if (availableSeats >= bookedSeats) {
            availableSeats -= bookedSeats;
        } else {
            System.out.println("Not enough available seats.");
        }
    }

    // Getters and Setters
    public String getBusNumber() {

        return busNumber;
    }

    public String getSource() {

        return source;
    }

    public String getDestination() {

        return destination;
    }

    public String getDepartureTime() {

        return departureTime;
    }

    public String getArrivalTime() {

        return arrivalTime;
    }

    public int getTotalSeats() {

        return totalSeats;
    }

    public int getAvailableSeats() {

        return availableSeats;
    }


    @Override
    public String toString() {
        return "Bus Numer"+busNumber + ",Source" + source +
                ",Destination" + destination + ",Departure" +
                departureTime + ",Arrival" + arrivalTime +
                ",Total Seats" + totalSeats + ",Available Seats" + availableSeats;
    }
}
