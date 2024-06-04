package com.alibou.security.service;

import com.alibou.security.config.TestConfig;
import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Order;
import com.alibou.security.repositories.OrderRepo;
import com.alibou.security.services.*;
import com.alibou.security.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepo orderRepo;

    @InjectMocks
    OrderService orderService;

    @Test
    public void testGetOrder_Success() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrder(orderId);

        // Assert
        assertEquals(order, result);
    }

    @Test
    public void testGetOrder_NotFound() {
        // Arrange
        Long orderId = 1L;

        when(orderRepo.findById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.getOrder(orderId));
    }


    @Test
    public void testSaveOrder() {
        // Arrange
        Order order = new Order();

        // Act
        orderService.saveOrder(order);

        // Assert
        verify(orderRepo, times(1)).save(order);
    }

    @Test
    public void testChangeStatus_Success() {
        // Arrange
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.DELIVERED;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.PROCESSING);

        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.changeStatus(orderId, newStatus);

        // Assert
        verify(orderRepo, times(1)).findById(orderId);
        verify(orderRepo, times(1)).save(order);
        assertEquals(newStatus, order.getStatus());
    }

}
