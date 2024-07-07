package repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Booking;
import model.Bus;
import model.User;

public class BusRepository {
    public static void saveUserToFile(String userId, String username, String encryptedPassword) {
        try (FileWriter writer = new FileWriter(User.USER_FILE_NAME, true)) {
            writer.write(userId + "," + username + "," + encryptedPassword + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAdminToFile(String adminId, String username, String encryptedPassword) {
        try (FileWriter writer = new FileWriter(Admin.ADMIN_FILE_NAME, true)) {
            writer.write(adminId + "," + username + "," + encryptedPassword + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User readUsersFromFile(String username, String encryptedPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(User.USER_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[1];
                    String storedEncryptedPassword = parts[2];
                    if (storedUsername.equals(username) && storedEncryptedPassword.equals(encryptedPassword)) {
                        return new User(username, storedUsername, storedEncryptedPassword);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Admin readAdminFromFile(String username, String encryptedPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Admin.ADMIN_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String storedUsername = parts[1];
                    String storedEncryptedPassword = parts[2];
                    if (storedUsername.equals(username) && storedEncryptedPassword.equals(encryptedPassword)) {
                        return new Admin(parts[0], storedUsername, storedEncryptedPassword);  // Create and return the Admin object
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // Return null if credentials are invalid
    }

    public static void writeBusDataToFile(Bus bus) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Bus.FILE_NAME, true))) {
            writer.write(bus.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeBusDataFromFile(String busNumber) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(Bus.FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter("database/temp.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String trimmedLine = parts[0].trim();
                if (trimmedLine.equals(busNumber)) {
                    found = true;
                    continue;
                }
                writer.write(line + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            File originalFile = new File(Bus.FILE_NAME);
            File tempFile = new File("database/temp.txt");

            // Delete the original file
            if (!originalFile.delete()) {
                System.err.println("Could not delete original file");
                return false;
            }

            // Rename temp file to original file
            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Could not rename temp file to original file");
                return false;
            }
        }
        return found;
    }

    public static boolean SearchBus(String busNumber){
        try (BufferedReader reader = new BufferedReader(new FileReader(Bus.FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String busno = parts[0];
                    if (busno.equals(busNumber) ) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Bus> getallBus() {
        List<Bus> buses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Bus.FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure the line has at least 7 parts
                    String busNumber = parts[0].trim();
                    String source = parts[1].trim();
                    String destination = parts[2].trim();
                    String departureTime = parts[3].trim();
                    String arrivalTime = parts[4].trim();
                    int totalSeats = Integer.parseInt(parts[5].trim());
                    int availableSeats = Integer.parseInt(parts[6].trim());

                    Bus bus = new Bus(busNumber, source, destination, departureTime, arrivalTime, totalSeats, availableSeats);
                    buses.add(bus);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return buses;
    }

    public static List<Bus> readBusDetails() {
        List<Bus> buses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Bus.FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Bus bus = new Bus(line);
                buses.add(bus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buses;
    }

    public static Bus readBusFromFile(String busNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Bus.FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String number = parts[0];
                    if (parts[0].equals(busNumber)) {
                        return new Bus(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeBookingstoFile(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Booking.BOOKING_FILE_NAME, true))) {
            writer.write(booking.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing booking to file: " + e.getMessage());
        }
    }

    public static boolean cancelBooking(String bookingId){
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(Booking.BOOKING_FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter("database/temp.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                String trimmedLine = parts[0].trim();
                if (trimmedLine.equals(bookingId)) {
                    found = true;
                    continue;
                }
                writer.write(line + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            File originalFile = new File(Booking.BOOKING_FILE_NAME);
            File tempFile = new File("database/temp.txt");

            // Delete the original file
            if (!originalFile.delete()) {
                System.err.println("Could not delete original file");
                return false;
            }

            // Rename temp file to original file
            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Could not rename temp file to original file");
                return false;
            }
        }
        return found;
    }
    }



