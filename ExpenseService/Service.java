import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Service {

    public Service(){
        load();
    }


    HashMap<UUID , Expense> expenses = new HashMap<>();


    public void add(String description, int amount, String cate){

        UUID uuid; //= UUID.randomUUID();
        LocalDate currentDate = LocalDate.now();

        do {
            uuid = UUID.randomUUID();
        } while (expenses.containsKey(uuid));

        if(!(expenses.containsKey(uuid))){
            expenses.put(uuid, new Expense(uuid, currentDate, description, amount, cate));
            System.out.println("Expense added successfully (ID: " + uuid + ") ");
        } else {System.out.println("ID Generating went wrong");}
        safe();
    }
    


    public void listThem() {
        System.out.printf("%-36s %-12s %-12s %-10s%n", "ID", "Date", "Description", "Amount");
        System.out.println("--------------------------------------------------------");

        for (Map.Entry<UUID, Expense> entry : expenses.entrySet()) {
            UUID id = entry.getKey();
            Expense expense = entry.getValue();
            System.out.printf("%-36s %-12s %-12s $%-10.2f%n", id, expense.date(), expense.description(), expense.amount());
        }
    }

    public void summary(){
        float total = 0;
        for(Map.Entry<UUID, Expense> entry : expenses.entrySet()){
            float num =  entry.getValue().amount();
            total += num;
        }
        System.out.printf("Total expenses: $"+total);
    }

    public void delete(UUID uuid){
        if(expenses.containsKey(uuid)){
            expenses.remove(uuid);
        }
        System.out.printf("Expense deleted successfully");
        safe();
        
    }

    public void summary(int month){}



    public void safe() {
        //System.out.printf("safe1");
        try (PrintWriter writer = new PrintWriter(new FileWriter("Expenses.txt"))) {
            for (Map.Entry<UUID, Expense> entry : expenses.entrySet()) {
                Expense expense = entry.getValue();
                writer.printf("%s,%s,%s,%.2f,%s%n", entry.getKey(), expense.date(), expense.description(), expense.amount(), expense.category());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.printf("safe2");
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Expenses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Reading line: " + line); // Debug-Ausgabe
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    UUID uuid = UUID.fromString(parts[0]);
                    LocalDate date = LocalDate.parse(parts[1]);
                    String description = parts[2];
                    float amount = Float.parseFloat(parts[3]); // Verwende float statt int
                    String category = parts[4];
    
                    expenses.put(uuid, new Expense(uuid, date, description, amount, category));
                    System.out.println("Expense loaded: " + description); // Debug-Ausgabe
                } else {
                    System.err.println("Invalid line format: " + line); // Fehlerausgabe
                }
            }
            System.out.println("Expenses loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
        System.err.println(expenses);
    }
    
}


