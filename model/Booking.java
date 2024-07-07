package model;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    public static final String BOOKING_FILE_NAME = "database/Booking.txt";
    private String bookingId;
    private String userId;
    private String busNumber;
    private int numberOfSeats;
    private Date bookingDate;

    public Booking(String bookingId, String userId, String busNumber, int numberOfSeats) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.busNumber = busNumber;
        this.numberOfSeats = numberOfSeats;
        this.bookingDate = new Date();
    }

    // Getters and Setters
    public String getBookingId() { 
        return this.bookingId; }
    public String getUserId() { 
        return this.userId; }
    public String getBusNumber() {
         return this.busNumber; }
    public int getNumberOfSeats() { 
        return this.numberOfSeats; }
    public Date getBookingDate() { 
        return this.bookingDate; }

    @Override
    public String toString() {
        return "Booking Id: "+bookingId + ",Bus Number: " + busNumber + ",Number Of Seats: " + numberOfSeats + ",Booking Date: " + bookingDate;
    }
}
