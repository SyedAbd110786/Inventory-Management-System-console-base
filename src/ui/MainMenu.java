package ui;

import InputHelper.Input;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main Menu");
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Product Management");
        System.out.println("2. Exit");
        printLine();
    }

    @Override
    protected void handleChoice() {
        System.out.print("Choose an option (1-2): ");
        int choice = Input.getIntInRange(1, 3);

        switch (choice) {
            case 1:
                ProductMenu pm = new ProductMenu();
                pm.show();
                break;
            case 2:
                printInfo("Thank you for using Inventory Management System.");
                running = false;
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
}
