
public interface ExpenseManager {
    void addExpense(Expense expense);

    void deleteExpense(int index);

    double getTotalExpenses();

    void displayExpenses();

    void saveExpensesToFile(String fileName);

    void loadExpensesFromFile(String fileName);
}

