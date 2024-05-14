import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Expense implements Serializable {
    private String description;
    private double amount;
    private LocalDateTime dateTime;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.dateTime = LocalDateTime.now(); // Set current system time
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "description='" + description + '\'' +
                ", amount= Rs" + amount +
                ", dateTime=" + dateTime.format(formatter) + // Format the LocalDateTime
                '}';
    }
}
