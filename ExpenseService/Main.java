
import java.util.UUID;


public class Main {

    static Service service =  new Service();

    public static void main(String[] args) {
        
        /*
        UUID test = UUID.randomUUID();
        Expense e = new Expense(test, LocalDate.now(), "idk", 0);

        service.add("Joe Mama", 10, "Joe Mama");
        service.add("Jhin", 10, "idk");
        service.add("lol", 10, "idk");
        
        service.listThem();
        service.summary();
        service.delete(test);

        System.err.println(args);
        System.err.println(Arrays.toString(args)); */
        
        switch(args[0]){

            case "add" -> add(args);

            case "delete" -> delete(args);
            
            default -> System.err.println("Nix eingegeben");
            
        }
    }

    private static void add(String[] para){  //java Main.java add --description "Lunch" --amount 20
        service.add(para[2], Integer.parseInt(para[4]), null);
    }

    private static void delete(String[] para){
        service.delete(UUID.fromString(para[2]));
    }

}
