package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    public static final String USER_FILE_NAME = "database/users.txt";
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String password;

    private List<Booking> bookingHistory;

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.bookingHistory = new ArrayList<>();
    }


    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void viewBookings() {
        if (bookingHistory.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookingHistory) {
                System.out.println(booking);
            }
        }
    }

    public void cancelBooking(String bookingId) {
        bookingHistory.removeIf(booking -> booking.getBookingId().equals(bookingId));
    }
    public String toString() {
        return "model.User ID: " + userId + ", Username: " + username;
    }

    public void addBooking(Booking booking) {
        bookingHistory.add(booking);
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Booking> getBookingHistory() { return bookingHistory; }
}
