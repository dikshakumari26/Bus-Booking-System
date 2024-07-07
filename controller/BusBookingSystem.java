package controller;
import java.util.*;
import model.Admin;
import model.Bus;
import model.User;

import static service.BusServices.*;

public class BusBookingSystem {

    public static List<User> users = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();
    public static List<Bus> buses = new ArrayList<>();
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public BusBookingSystem() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=================================");
        System.out.println(BLUE+"Welcome to the Bus Booking System"+RESET);
        
        while (true) {
            System.out.println("=================================");
            System.out.println("1. User Login\n2. Admin Login\n3. Register User\n4. Register Admin\n5. Exit");
            System.out.print(BLUE+"Enter your choice: "+RESET);
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1: userMenu(scanner);
                break;
                case 2: adminMenu(scanner);
                break;
                case 3: registerUser(scanner);
                break;
                case 4: registerAdmin(scanner);
                break;
                case 5: {
                    System.out.println("Exiting...");
                    return;
                }
                default: System.out.println(RED+"Invalid choice."+RESET);
                
            }
        }
    }

    public static void registerUser(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = encryptPassword(scanner.nextLine());

        String userId = UUID.randomUUID().toString();
        users.add(new User(userId, username, password));
        saveUserToFile(userId, username, password);
        System.out.println(GREEN+"User registered successfully."+RESET);
    }

    public static void registerAdmin(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = encryptPassword(scanner.nextLine());

        String adminId = UUID.randomUUID().toString();
        admins.add(new Admin(adminId, username, password));
        saveAdminToFile(adminId, username, password);
        System.out.println("=================================");
        System.out.println(GREEN+"Admin registered successfully."+RESET);
    }
    
    public static void userMenu(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = encryptPassword(scanner.nextLine());

        User user = readUsersFromFile(username, password);

        if (user == null) {
            System.out.println("=================================");
            System.out.println(RED + "Invalid credentials." + RESET);
            return;
        }

        while (true) {
            System.out.println("=================================");
            System.out.println(BLUE+"Welcome User"+RESET);
            System.out.println("1. Search Buses\n2. Book Seat\n3. View Bookings\n4. Cancel Booking\n5. Logout\nYour Choice : ");
            System.out.println("=================================");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1: searchBuses(scanner);
                    break;
                case 2: bookSeat(scanner, user);
                    break;
                case 3: user.viewBookings();
                    break;
                case 4: cancelBooking(scanner, user);
                    break;
                case 5: { return; }
                default : System.out.println(RED+"Invalid choice."+RESET);
                    break;
            }
        }
    }
    public static void adminMenu(Scanner scanner) {
        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = encryptPassword(scanner.nextLine());

        Admin admin = readAdminFromFile(username, password);

        if ( admin == null) {
            System.out.println("=================================");
            System.out.println(RED + "Invalid credentials." + RESET);
            return;
        }

        while (true) {
            System.out.println(BLUE+"Welcome Admin"+RESET);
            System.out.println("1. Add Bus\n2. Remove Bus\n3. Update Bus\n4. View All Buses\n5. Logout\nYour Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1: addBus(scanner, admin);
                    break;
                case 2: removeBus(scanner, admin);
                    break;
                case 3: updateBus(scanner, admin);
                    break;
                case 4: getallBus(admin);
                    break;
                case 5: { return; }
                default : System.out.println(RED+"Invalid choice."+RESET);
                    break;
            }
        }
    }
}
