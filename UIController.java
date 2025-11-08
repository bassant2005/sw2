import java.util.*;

public class UIController {
    private final Scanner sc = new Scanner(System.in);

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try { return Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { System.out.println("Invalid number!"); }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }

    // Display menu with a selected title
    public static void displayMenu(Menu menu) {
        System.out.println("\n=== " + "menu" + " ===");
        int idx = 1;
        for (MenuItem item : menu.getItems()) {
            // Format string: "%d) %-30s -> %.2f\n"
            System.out.printf("%d) %-30s -> %.2f\n", idx++, item.getDescription(), item.getPrice());
        }
    }
}
