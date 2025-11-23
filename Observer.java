/**
 * Observer - Interface in Observer Pattern
 * 
 * This interface defines the contract for objects that need to be notified
 * of changes to orders. It's part of the Observer Pattern design.
 * 
 * Design Pattern: Observer Pattern
 * - Observer Interface: Defines the contract for observers
 * - Concrete Observers: Kitchen, Waiter
 * - Subject: OrderNotifier maintains a list of observers and notifies them
 */
public interface Observer {
    /**
     * Update method called by the subject (OrderNotifier) when an order event occurs
     */
    void update(OrderTemplate order);
}

