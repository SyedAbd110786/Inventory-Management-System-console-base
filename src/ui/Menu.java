package ui;

public abstract class Menu {
    protected String menuType;
    protected boolean running;
    private static final int MENU_WIDTH = 42;

    Menu(String menuType) {
        this.menuType = menuType;
        running = true;
    }

    protected abstract void printOptions();
    protected abstract void handleChoice();

    public final void show() {
        running = true;
        while (running) {
            printHeader();
            printOptions();
            handleChoice();
        }
    }

    protected void printHeader() {
        System.out.println();
        printLine();
        System.out.println(centerText(menuType.toUpperCase()));
        printLine();
    }

    protected void printLine() {
        System.out.println("=".repeat(MENU_WIDTH));
    }

    protected void printSuccess(String message) {
        printMessage("[SUCCESS] " + message);
    }

    protected void printError(String message) {
        printMessage("[ERROR] " + message);
    }

    protected void printInfo(String message) {
        printMessage("[INFO] " + message);
    }

    private void printMessage(String message) {
        System.out.println();
        System.out.println(message);
    }

    private String centerText(String text) {
        if (text.length() >= MENU_WIDTH) {
            return text;
        }

        int padding = (MENU_WIDTH - text.length()) / 2;
        return " ".repeat(padding) + text;
    }
}
