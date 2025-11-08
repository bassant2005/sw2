import java.util.List;

public interface DiscountStrategy {
    double apply(List<OrderItem> items);
}