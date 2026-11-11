import org.junit.jupiter.api.Test;
import ru.cofffeen.domain.entity.car.order.states.*;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;


class OrderStateTest {

    @Test
    void orderCreated_NextState_ShouldReturnOrderApproved() {
        OrderState state = new OrderCreated();
        OrderState nextState = state.nextState();
        assertInstanceOf(OrderApproved.class, nextState);
    }

    @Test
    void orderCreated_Cancel_ShouldReturnOrderCancelled() {
        OrderState state = new OrderCreated();
        OrderState cancelled = state.cancel();
        assertInstanceOf(OrderCancelled.class, cancelled);
    }

    @Test
    void orderApproved_NextState_ShouldReturnOrderReady() {
        OrderState state = new OrderApproved();
        OrderState nextState = state.nextState();
        assertInstanceOf(OrderReady.class, nextState);
    }

    @Test
    void orderApproved_Cancel_ShouldReturnOrderCancelled() {
        OrderState state = new OrderApproved();
        OrderState cancelled = state.cancel();
        assertInstanceOf(OrderCancelled.class, cancelled);
    }

    @Test
    void orderReady_NextState_ShouldReturnOrderCompleted() {
        OrderState state = new OrderReady();
        OrderState nextState = state.nextState();
        assertInstanceOf(OrderCompleted.class, nextState);
    }

    @Test
    void orderReady_Cancel_ShouldReturnOrderCancelled() {
        OrderState state = new OrderReady();
        OrderState cancelled = state.cancel();
        assertInstanceOf(OrderCancelled.class, cancelled);
    }

    @Test
    void orderCompleted_NextState_ShouldReturnNull() {
        OrderState state = new OrderCompleted();
        assertNull(state.nextState());
    }

    @Test
    void orderCompleted_Cancel_ShouldReturnNull() {
        OrderState state = new OrderCompleted();
        assertNull(state.cancel());
    }

    @Test
    void orderCancelled_NextState_ShouldReturnNull() {
        OrderState state = new OrderCancelled();
        assertNull(state.nextState());
    }

    @Test
    void orderCancelled_Cancel_ShouldReturnNull() {
        OrderState state = new OrderCancelled();
        assertNull(state.cancel());
    }

    @Test
    void orderBundlingError_NextState_ShouldReturnOrderCancelled() {
        OrderState state = new OrderBundlingError();
        OrderState nextState = state.nextState();
        assertInstanceOf(OrderCancelled.class, nextState);
    }

    @Test
    void orderBundlingError_Cancel_ShouldReturnOrderCancelled() {
        OrderState state = new OrderBundlingError();
        OrderState cancelled = state.cancel();
        assertInstanceOf(OrderCancelled.class, cancelled);
    }
}