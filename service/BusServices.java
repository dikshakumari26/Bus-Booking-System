package service;

import model.Admin;
import model.Booking;
import model.Bus;
import model.User;
import repository.BusRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static controller.BusBookingSystem.*;
import static repository.BusRepository.readBusFromFile;

public class BusServices {
    public BusServices(Admin admin) {
    }

    public static void saveUserToFile(String userId, String username, String encryptedPassword) {
        BusRepository.saveUserToFile(userId, username, encryptedPassword);
    }

    public static void saveAdminToFile(String adminId, String username, String encryptedPassword) {
        BusRepository.saveAdminToFile(adminId, username, encryptedPassword);
    }

    public static User readUsersFromFile(String username, String encryptedPassword) {
        User user = BusRepository.readUsersFromFile(username, encryptedPassword);
        return user;
    }

    public static Admin readAdminFromFile(String username, String encryptedPassword) {
        Admin admin = BusRepository.readAdminFromFile(username, encryptedPassword);
        return admin;
    }

    public static void addBus(Scanner scanner, Admin admin) {
        System.out.println("Enter bus number:");
        String busNumber = scanner.nextLine();
        System.out.println("Enter source:");
        String source = scanner.nextLine();
        System.out.println("Enter destination:");
        String destination = scanner.nextLine();
        System.out.println("Enter departure time:");
        String departureTime = scanner.nextLine();
        System.out.println("Enter arrival time:");
        String arrivalTime = scanner.nextLine();
        System.out.println("Enter total seats:");
        int totalSeats = scanner.nextInt();
        scanner.nextLine();  // consume newline

        Bus bus = new Bus(busNumber, source, destination, departureTime, arrivalTime, totalSeats , totalSeats);
        admin.addBus(bus);
        BusRepository.writeBusDataToFile(bus);
        System.out.println("Bus added successfully.");
    }

    public static void removeBus(Scanner scanner, Admin admin) {
        System.out.println("Enter bus number to remove:");
        String busNumber = scanner.nextLine();
        admin.removeBus(busNumber);
        if(!BusRepository.removeBusDataFromFile(busNumber)){
            System.out.println("Bus does not exist");
        }else{
            System.out.println("Bus removed successfully.");
        }
    }

    public static void updateBus(Scanner scanner, Admin admin) {
        System.out.println("Enter bus number to update:");
        String busNumber = scanner.nextLine();
        if(!BusRepository.SearchBus(busNumber)){
            System.out.println("Bus does not exist");
        }else {
            System.out.println("Enter new source:");
            String source = scanner.nextLine();
            System.out.println("Enter new destination:");
            String destination = scanner.nextLine();
            System.out.println("Enter new departure time:");
            String departureTime = scanner.nextLine();
            System.out.println("Enter new arrival time:");
            String arrivalTime = scanner.nextLine();
            System.out.println("Enter new total seats:");
            int totalSeats = scanner.nextInt();
            scanner.nextLine();  // consume newline
            Bus bus = new Bus(busNumber, source, destination, departureTime, arrivalTime, totalSeats , totalSeats);
            if(BusRepository.removeBusDataFromFile(busNumber)){
                BusRepository.writeBusDataToFile(bus);
            }
            System.out.println("Bus updated successfully.");
        }
    }

    public static void getallBus(Admin admin){
        List<Bus> buses = BusRepository.getallBus();
        if (buses.isEmpty()) {
            System.out.println("No buses found.");
        } else {
            System.out.println(BLUE+"B-No||Source||Destination||D-Time||A-Time||Total Seats||Available Seats"+RESET);
            System.out.println("==============================================================================");
            for (Bus bus : buses) {
                System.out.println(bus);
            }
        }
    }
    ////////////////////////////////////////////////////


    private static List<Bus> filterBuses(List<Bus> buses, String source, String destination) {
        List<Bus> matchingBuses = new ArrayList<>();
        for (Bus bus : buses) {
            if (bus.getSource().equalsIgnoreCase(source) && bus.getDestination().equalsIgnoreCase(destination)) {
                matchingBuses.add(bus);
            }
        }
        return matchingBuses;
    }
    
    public static void searchBuses(Scanner scanner) {
        System.out.println("Enter source:");
        String source = scanner.nextLine();
        System.out.println("Enter destination:");
        String destination = scanner.nextLine();


        List<Bus> buses = BusRepository.readBusDetails();
        List<Bus> matchingBuses = filterBuses(buses, source, destination);
        if (matchingBuses.isEmpty()) {
            System.out.println("No buses available for the given source and destination.");
        } else {
            System.out.println(BLUE+"B-No Source  Destination D-Time         A-Time    Total Seats Available Seats"+RESET);
            System.out.println("==============================================================================");
            for (Bus bus : matchingBuses) {
                System.out.println(bus);
            }
        }
    }



    public static void bookSeat(Scanner scanner, User user) {
        System.out.println("Enter bus number:(for bus number check from option1 - Press Enter to go back)");
        String busNumber = scanner.nextLine();
        Bus bus = BusRepository.readBusFromFile(busNumber);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }
        System.out.println("Enter number of seats to book:");
        int numberOfSeats = scanner.nextInt();
        scanner.nextLine();  // consume newline

        if (bus.getAvailableSeats() < numberOfSeats) {
            System.out.println("Not enough seats available.");
            return;
        }

        bus.updateAvailableSeats(numberOfSeats);
        updateBusFile(bus);

        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking(bookingId, user.getUserId(), busNumber, numberOfSeats);
        user.addBooking(booking);
        BusRepository.writeBookingstoFile(booking);
//        if(BusRepository.removeBusDataFromFile(busNumber)){
//            BusRepository.writeBusDataToFile(bus);
//        }
        System.out.println("Booking successful.");
    }
    private static void updateBusFile(Bus bus) {
        try (FileWriter writer = new FileWriter(Bus.FILE_NAME, false)) {
            for (Bus b : buses) {
                writer.write(String.valueOf(bus));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void cancelBooking(Scanner scanner, User user) {
        System.out.println("Enter booking ID to cancel:");
        String bookingId = scanner.nextLine();
        boolean booking = BusRepository.cancelBooking(bookingId);

//        Booking booking = user.getBookingHistory().stream()
//                .filter(b -> b.getBookingId().equals(bookingId))
//                .findFirst().orElse(null);

        if (!booking) {
            System.out.println("Booking not found.");
            return;
        }

//        Bus bus = buses.stream()
//                .filter(b -> b.getBusNumber().equals(booking.getBusNumber()))
//                .findFirst().orElse(null);

//        if (bus != null) {
//            bus.updateAvailableSeats(-booking.getNumberOfSeats());
//        }

        user.cancelBooking(bookingId);
        //BusRepository.cancelBooking(bookingId);
        System.out.println("Booking cancelled.");
    }


    public static String encryptPassword(String password) {
        // Simple encryption: reverse the string (example only)
        return new StringBuilder(password).reverse().toString();
    }
}
