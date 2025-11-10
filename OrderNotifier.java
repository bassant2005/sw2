import java.util.*;

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

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(OrderTemplate order) {
        // Create copy to avoid concurrent modification issues
        List<Observer> observersCopy = new ArrayList<>(observers);
        for (Observer observer : observersCopy) {
            observer.update(order);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }

    public void clearObservers() {
        observers.clear();
    }
}

