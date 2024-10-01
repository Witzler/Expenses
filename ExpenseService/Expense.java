import java.time.LocalDate;
import java.util.UUID;

public record Expense(UUID uuid, LocalDate date, String description, float amount, String category) {
    
    
    public Expense(UUID uuid, LocalDate date, String description, float amount) {
        this(uuid, date, description, amount, null);
    }
}