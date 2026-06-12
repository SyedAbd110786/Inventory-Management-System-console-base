import ui.MainMenu;

public class Main{
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("INVENTORY MANAGEMENT SYSTEM");
        System.out.println("Manage products, stock, and prices cleanly.");
        System.out.println("==========================================");

        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }
}
