import java.util.*;

/**
 * OrderNotifier - Subject in Observer Pattern
 * This class acts as the Subject (Observable) in the Observer Pattern.
 */
public class OrderNotifier {
    private final List<Observer> observers;
    public OrderNotifier() {
        this.observers = new ArrayList<>();
    }

    public void registerObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Notify all registered observers about an order event
     */
    public void notifyObservers(OrderTemplate order) {
        // Create copy to avoid concurrent modification issues
        List<Observer> observersCopy = new ArrayList<>(observers);
        for (Observer observer : observersCopy) {
            observer.update(order);
        }
    }
}

