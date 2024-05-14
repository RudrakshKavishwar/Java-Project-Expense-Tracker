import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();


        DatabaseManager.createTables();

        while (true) {
            System.out.println("1. Add Expense\n2. View Expenses\n3. Total Expenses\n4. Delete Expense\n5. Save Expenses to File\n6. Load Expense from File File\n7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter expense description: ");
                    scanner.nextLine();
                    String description = scanner.nextLine();
                    System.out.print("Enter expense amount: ");
                    double amount = scanner.nextDouble();
                    tracker.addExpense(new Expense(description, amount));
                    break;

                case 2:
                    tracker.displayExpenses();
                    break;
                case 3:
                    System.out.println("Total Expenses: Rs" + tracker.getTotalExpenses());
                    break;
                case 4:
                    System.out.print("Enter index of expense to delete: ");
                    int index = scanner.nextInt();
                    tracker.deleteExpense(index);
                    break;
                case 5:
                    System.out.println("Enter file name to save expenses: ");
                    String saveFileName = scanner.next();
                    tracker.saveExpensesToFile(saveFileName);
                    break;
                case 6:
                    System.out.println("Enter file name to load expenses: ");
                    String loadFileName = scanner.next();
                    tracker.loadExpensesFromFile(loadFileName);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
