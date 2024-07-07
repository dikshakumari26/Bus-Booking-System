import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class BookBus {

            private static final int TOTAL_SEATS = 20;
            private static final String FILE_NAME = "controller/seats.txt";

            public static void main(String[] args) {
                displaySeats();
                saveSeatData();
            }

            private static void displaySeats() {
                System.out.println("           BUS NO           ");
                System.out.println("LEFT SEATS        RIGHT SEATS");
                for (int i = 1; i <= TOTAL_SEATS; i++) {
                    System.out.printf("|%d|", i);
                    if (i % 5 == 3 && i <= 10) {
                        System.out.print("         ");
                    }else if(i % 5 == 3 && i > 10){
                        System.out.print("      ");
                    }
                    if (i % 5 == 0) {
                        System.out.println();
                    }
                }
            }

            private static void saveSeatData() {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                    for (int i = 1; i <= TOTAL_SEATS; i++) {
                        writer.write(String.valueOf(i));
                        if (i < TOTAL_SEATS) {
                            writer.write(",");
                        }
                    }
                    System.out.println("Seat data has been saved to " + FILE_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
