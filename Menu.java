import java.util.*;

public class Menu {
    private final List<MenuItem> items = new ArrayList<>();

    // Add item to menu
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Retrieve all items
    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}