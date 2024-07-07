package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admin implements Serializable {
    
    public static final String ADMIN_FILE_NAME = "database/admin.txt";

    // Member Variables
    private String adminId;
    private String username;
    private String password;
    private List<Bus> busList;

    // Admin login (simple username/password check)
    public boolean login( String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }


    public Admin(String adminId,String username, String password) {
        this.adminId=adminId;
        this.username = username;
        this.password = password;
        this.busList = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        busList.add(bus);
    }

    public void removeBus(String busNumber) {
        busList.removeIf(bus -> bus.getBusNumber().equals(busNumber));
    }

    public void updateBus(Bus updatedBus) {
        for (Bus bus : busList) {
            if (bus.getBusNumber().equals(updatedBus.getBusNumber())) {
                busList.remove(bus);
                busList.add(updatedBus);
                break;
            }
        }
    }

    // Getters
    public List<Bus> getBusList() {
        /*if (busList == null) {
            busList = new ArrayList<>();
            readBusDataFromFile();
        } */
        return busList;
    }




    public String getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
