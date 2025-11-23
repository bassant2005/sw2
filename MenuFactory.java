/// ABSTRACT FACTORY PATTERN
/// -------------------------
/// MenuFactory defines an interface for creating families of related
/// menu items (Veg, Non-Veg, Kids, etc.).
/// Each concrete factory returns a Menu containing items specific to that category.

public abstract class MenuFactory {

    // Each concrete factory must implement this
    // to return a complete menu for its category.
    public abstract Menu createMenu();
}
