import java.util.*;

/**
 * Represents a menu that contains multiple menu items.
 * This class is used by different MenuFactory subclasses
 * to build specific types of menus (Kids, Veg, Non-Veg, etc.).
 */
public class Menu {

    // Stores all menu items inside the menu
    private final List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
