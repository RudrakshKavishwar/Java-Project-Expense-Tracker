import java.util.ArrayList;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseTracker implements ExpenseManager {
    private ArrayList<Expense> expenses;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid expense index.");
        }
    }

    public double getTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public void displayExpenses() {
        System.out.println("Expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }
    //  public void saveExpensesToFile(String fileName) {
    //      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
    //          oos.writeObject(expenses);
    //        System.out.println("Expenses saved to file successfully.");
    //      } catch (IOException e) {
    //         System.out.println("Error saving expenses to file: " + e.getMessage());
    //     }
    // }


    public void saveExpensesToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense expense : expenses) {
                writer.write(expense.getDescription() + "," + expense.getAmount());
                writer.newLine();
            }
            System.out.println("Expenses saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses to file: " + e.getMessage());
        }
    }

    @Override
    public void loadExpensesFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses from file: " + e.getMessage());
        }
    }
}
