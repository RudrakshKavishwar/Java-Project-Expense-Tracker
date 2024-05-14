import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseTrackerGUI extends JFrame {


    private JTextField expenseField;
    private JTextField descriptionField;
    private JTextArea expenseList;
    private JTextField totalExpenseField;
    private JTextField totalMonthlyExpenseField;
    private JTextField dailyExpenseField;
    private JTextArea dailyExpenseList;
    private List<ExpenseEntry> expenses;
    private List<Double> dailyExpenses;

    public ExpenseTrackerGUI() {
        setTitle("Expense Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        expenses = new ArrayList<>();
        dailyExpenses = new ArrayList<>();

        JLabel titleLabel = new JLabel("Expense Tracker");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.add(titleLabel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
        JPanel expenseInputPanel = new JPanel(new FlowLayout());
        expenseInputPanel.setBackground(Color.WHITE);
        expenseInputPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel expenseLabel = new JLabel("Enter Expense:");
        expenseField = new JTextField(10);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(10);
        JButton addButton = new JButton("Add Expense");
        JButton viewButton = new JButton("View Expenses");
        JButton deleteButton = new JButton("Delete Expense");
        JButton saveButton = new JButton("Save to File");
        expenseInputPanel.add(expenseLabel);
        expenseInputPanel.add(expenseField);
        expenseInputPanel.add(descriptionLabel);
        expenseInputPanel.add(descriptionField);
        expenseInputPanel.add(addButton);
        expenseInputPanel.add(viewButton);
        expenseInputPanel.add(deleteButton);
        expenseInputPanel.add(saveButton);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(expenseInputPanel, BorderLayout.CENTER);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(Color.WHITE);
        JPanel expenseListPanel = new JPanel(new BorderLayout());
        expenseListPanel.setBackground(Color.WHITE);
        expenseListPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel expenseListLabel = new JLabel("Expense List");
        expenseList = new JTextArea(10, 40);
        expenseList.setFont(new Font("Cambria", Font.PLAIN, 40));
        JScrollPane scrollPane = new JScrollPane(expenseList);
        expenseListPanel.add(expenseListLabel, BorderLayout.NORTH);
        expenseListPanel.add(scrollPane, BorderLayout.CENTER);
        middlePanel.add(expenseListPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        JPanel totalExpensePanel = new JPanel(new FlowLayout());
        totalExpensePanel.setBackground(Color.WHITE);
        totalExpensePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel totalExpenseLabel = new JLabel("Total Expense:");
        totalExpenseLabel.setForeground(Color.BLACK);
        totalExpenseField = new JTextField(10);
        totalExpenseField.setEditable(false);
        JButton calculateButton = new JButton("Calculate Total");
        totalExpensePanel.add(totalExpenseLabel);
        totalExpensePanel.add(totalExpenseField);
        totalExpensePanel.add(calculateButton);
        bottomPanel.add(totalExpensePanel, BorderLayout.NORTH);

        // Panel for total monthly expense
        JPanel monthlyExpensePanel = new JPanel(new FlowLayout());
        monthlyExpensePanel.setBackground(Color.WHITE);
        monthlyExpensePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel totalMonthlyExpenseLabel = new JLabel("Total Monthly Expense:");
        totalMonthlyExpenseLabel.setForeground(Color.BLACK);
        totalMonthlyExpenseField = new JTextField(10);
        totalMonthlyExpenseField.setEditable(false);
        JButton calculateMonthlyButton = new JButton("Calculate Monthly Total");
        monthlyExpensePanel.add(totalMonthlyExpenseLabel);
        monthlyExpensePanel.add(totalMonthlyExpenseField);
        monthlyExpensePanel.add(calculateMonthlyButton);
        bottomPanel.add(monthlyExpensePanel, BorderLayout.CENTER);

        // Panel for managing daily expenses
        JPanel dailyExpensePanel = new JPanel(new BorderLayout());
        dailyExpensePanel.setBackground(Color.WHITE);
        dailyExpensePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel dailyExpenseLabel = new JLabel("Enter Daily Expense:");
        dailyExpenseField = new JTextField(10);
        dailyExpenseList = new JTextArea(10, 20);
        dailyExpenseList.setEditable(false);
        JScrollPane dailyScrollPane = new JScrollPane(dailyExpenseList);
        JButton addDailyButton = new JButton("Add Daily Expense");

        JButton viewDailyButton = new JButton("View Daily Expenses");
        JPanel dailyInputPanel = new JPanel(new FlowLayout());
        dailyInputPanel.add(dailyExpenseLabel);
        dailyInputPanel.add(dailyExpenseField);
        dailyInputPanel.add(addDailyButton);
        dailyInputPanel.add(viewDailyButton);
        dailyExpensePanel.add(dailyInputPanel, BorderLayout.NORTH);
        dailyExpensePanel.add(dailyScrollPane, BorderLayout.CENTER);
        bottomPanel.add(dailyExpensePanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                addExpense();
            } catch (InvalidExpenseAmountException | InvalidDescriptionException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton loadButton = new JButton("Load from File");
        expenseInputPanel.add(loadButton);

        viewButton.addActionListener(e -> viewExpenses());
        deleteButton.addActionListener(e -> deleteExpense());
        saveButton.addActionListener(e -> saveToFile());
        calculateButton.addActionListener(e -> calculateTotalExpense());
        calculateMonthlyButton.addActionListener(e -> calculateTotalMonthlyExpense());
        addDailyButton.addActionListener(e -> addDailyExpense());
        viewDailyButton.addActionListener(e -> viewDailyExpenses());
        loadButton.addActionListener(e -> loadFromFile());
    }

    private void addExpense() throws InvalidExpenseAmountException, InvalidDescriptionException {
        String expenseStr = expenseField.getText().trim();
        String description = descriptionField.getText().trim();
        if (!expenseStr.isEmpty()) {
            try {
                double expenseAmount = Double.parseDouble(expenseStr);
                if (expenseAmount <= 0) {
                    throw new InvalidExpenseAmountException("Expense amount must be a positive number.");
                }

                if (!validateDescription(description)) {
                    throw new InvalidDescriptionException("Description should not contain numerical values.");
                }

                Date datetime = new Date();
                ExpenseEntry entry = new ExpenseEntry(expenseAmount, description, datetime);
                expenses.add(entry);
                updateExpenseList();
                expenseField.setText("");
                descriptionField.setText("");
            } catch (NumberFormatException ex) {
                throw new InvalidExpenseAmountException("Invalid expense amount format.");
            }
        } else {
            throw new InvalidExpenseAmountException("Please enter an expense amount.");
        }
    }

    private boolean validateDescription(String description) {
        return !description.matches(".*\\d.*");
    }

    private void viewExpenses() {
        updateExpenseList();
    }

    private void updateExpenseList() {
        expenseList.setText("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Font font = new Font("Cambria", Font.PLAIN, 20); // Define the font
        expenseList.setFont(font); // Set the font for the expense list
        for (ExpenseEntry entry : expenses) {
            String dateTimeStr = dateFormat.format(entry.getDateTime());
            expenseList.append("Date-Time: " + dateTimeStr + ", Rs:" + entry.getAmount() + ", Description: " + entry.getDescription() + "\n");
        }
    }


    private void deleteExpense() {
        String indexStr = JOptionPane.showInputDialog(this, "Enter index of expense to delete:");
        if (indexStr != null && !indexStr.isEmpty()) {
            try {
                int index = Integer.parseInt(indexStr);
                if (index >= 0 && index < expenses.size()) {
                    expenses.remove(index);
                    updateExpenseList();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid index.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid index.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Expenses");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Downloads"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            try {
                FileWriter writer = new FileWriter(filePath);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (ExpenseEntry entry : expenses) {
                    String dateTimeStr = dateFormat.format(entry.getDateTime());
                    writer.write(entry.getAmount() + "," + entry.getDescription() + "," + dateTimeStr + "\n");
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Expenses saved to file successfully: " + fileToSave.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving expenses to file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }





    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "Java Project Expense"));
        fileChooser.setDialogTitle("Load Expenses");

        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String filePath = fileToLoad.getAbsolutePath();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        double amount = Double.parseDouble(parts[0]);
                        String description = parts[1];
                        Date datetime = dateFormat.parse(parts[2]);
                        ExpenseEntry entry = new ExpenseEntry(amount, description, datetime);
                        expenses.add(entry);
                    }
                }
                reader.close();
                updateExpenseList(); // Update the display after loading
                JOptionPane.showMessageDialog(this, "Expenses loaded from file successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error loading expenses from file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void calculateTotalExpense() {
        double total = 0;
        for (ExpenseEntry entry : expenses) {
            total += entry.getAmount();
        }
        totalExpenseField.setText(String.valueOf(total));
    }

    private void calculateTotalMonthlyExpense() {
        double totalMonthlyExpense = 0;
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int currentMonth = cal.get(java.util.Calendar.MONTH);
        int currentYear = cal.get(java.util.Calendar.YEAR);

        for (ExpenseEntry entry : expenses) {
            cal.setTime(entry.getDateTime());
            int expenseMonth = cal.get(java.util.Calendar.MONTH);
            int expenseYear = cal.get(java.util.Calendar.YEAR);

            if (expenseMonth == currentMonth && expenseYear == currentYear) {
                totalMonthlyExpense += entry.getAmount();
            }
        }
        totalMonthlyExpenseField.setText(String.valueOf(totalMonthlyExpense));
    }

    private void addDailyExpense() {
        String expenseStr = dailyExpenseField.getText().trim();
        if (!expenseStr.isEmpty()) {
            try {
                double expenseAmount = Double.parseDouble(expenseStr);
                dailyExpenses.add(expenseAmount);
                updateDailyExpenseList();
                dailyExpenseField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid expense amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a daily expense amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewDailyExpenses() {
        updateDailyExpenseList();
    }

    private void updateDailyExpenseList() {
        dailyExpenseList.setText("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ExpenseEntry entry : expenses) {
            String dateTimeStr = dateFormat.format(entry.getDateTime());
            dailyExpenseList.append("Date-Time: " + dateTimeStr + ", Rs:" + entry.getAmount() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpenseTrackerGUI().setVisible(true));
    }

    private static class ExpenseEntry {
        private double amount;
        private String description;
        private Date datetime;

        public ExpenseEntry(double amount, String description, Date datetime) {
            this.amount = amount;
            this.description = description;
            this.datetime = datetime;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public Date getDateTime() {
            return datetime;
        }
    }

    private static class InvalidExpenseAmountException extends Exception {
        public InvalidExpenseAmountException(String message) {
            super(message);
        }
    }

    private static class InvalidDescriptionException extends Exception {
        public InvalidDescriptionException(String message) {
            super(message);
        }
    }
}
