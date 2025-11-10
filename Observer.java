// ============================================================================
// Order & Observer Pattern - Improved Version
// ============================================================================

/**
 * Observer interface for order notifications
 * Follows Observer Pattern - allows objects to be notified of order changes
 */
public interface Observer {
    void update(OrderTemplate order);
}

