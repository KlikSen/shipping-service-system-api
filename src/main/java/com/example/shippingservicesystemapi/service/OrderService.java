package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO create(OrderDTO orderDTO);
    OrderDTO read(Long id);
    List<OrderDTO> readByCustomerId(Long userId);
    List<OrderDTO> readByShopId(long userId);
    OrderDTO changeStatusToReadyToDeliver(long id);
    OrderDTO assignOrder(long orderId, long courierId);
    OrderDTO changeStatusToInProcessing(long id);
    OrderDTO changeStatusToCompleted(long id);
    List<OrderDTO> readAllReadyToDeliver();
}
