package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.OrderDTO;
import com.example.shippingservicesystemapi.entity.*;
import com.example.shippingservicesystemapi.enumeration.OrderStatus;
import com.example.shippingservicesystemapi.mapper.OrderMapper;
import com.example.shippingservicesystemapi.repository.*;
import com.example.shippingservicesystemapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkDayRepository workDayRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        //checking if it is working right now in order to make an order
        List<WorkDay> workDayList = workDayRepository.findAllByShopId(order.getShop().getId());
        LocalTime currentTime = LocalTime.now();
        boolean ifWorksToday = workDayList.stream()
                .anyMatch(o -> o.getDayOfWeek() == LocalDate.now().getDayOfWeek() &&
                        currentTime.isAfter(o.getStartTime()) &&
                        currentTime.isBefore(o.getEndTime()));
        if (!ifWorksToday) {
            throw  new IllegalStateException("You cannot order, because the shop is not working right now");
        }

        order.setOrderStatus(OrderStatus.OPENED);
        order.setCourier(null); //cannot be assigned during making an order
        order.setUpOrderedProductList();

        //checking if it is the same shop
        order.getOrderedProductList().forEach(o -> { //setting orderedProductList
            Product product = productRepository.findById(o.getProduct().getId()) //getting id which was assigned by user
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            if (Objects.equals(product.getShop().getId(), order.getShop().getId())) {
                o.setProduct(product);
                o.setCurrentPrice(product.getPrice());
            } else {
                throw new IllegalStateException("The shop doesn't have the product");
            }
        });

        Map<Long, Integer> map = order.getOrderedProductList().stream() //map with key(id of product) and value(wished quantity)
                .collect(Collectors.toMap((orderedProduct -> orderedProduct.getProduct().getId()), OrderedProduct::getQuantity));

        double totalAmount = map.entrySet().stream()
                .mapToDouble(o -> calculateTotalAmount().apply(o.getKey(), o.getValue()))
                .sum();

        order.setTotalAmount(totalAmount);

        return orderMapper.toDTO(orderRepository.save(order));
    }

    private BiFunction<Long, Integer, Double> calculateTotalAmount() {
        return (id, quantity) -> {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            int currentQuantity = product.getStorage().getQuantity();
            int left = currentQuantity - quantity;

            if (left >= 0) {
                product.getStorage().setQuantity(left);
            } else {
                throw new IllegalStateException("Quantity in storage has to be greater than wished quantity");
            }
            return product.getPrice() * quantity;
        };
    }

    @Override
    public OrderDTO read(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> readByCustomerId(Long id) {
        return orderRepository.findByCustomerId(id).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<OrderDTO> readByShopId(long shopId) {
        return orderRepository.findByShopId(shopId).stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO changeStatusToInProcessing(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getOrderStatus() == OrderStatus.OPENED) {
            order.setOrderStatus(OrderStatus.IN_PROCESSING);
            return orderMapper.toDTO(order);
        } else {
            throw new IllegalStateException("Status of order cannot be changed to IN_PROCESSING");
        }
    }

    @Override
    public OrderDTO changeStatusToReadyToDeliver(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getOrderStatus() == OrderStatus.IN_PROCESSING) {
            order.setOrderStatus(OrderStatus.READY_TO_DELIVER);
            return orderMapper.toDTO(order);
        } else {
            throw new IllegalStateException("Status of order cannot be changed to READY_TO_DELIVER");
        }
    }

    @Override
    public List<OrderDTO> readAllReadyToDeliver() {
        List<Order> list = orderRepository.findAllByOrderStatus(OrderStatus.READY_TO_DELIVER);
        return list.stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO assignOrder(long orderId, long courierId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.READY_TO_DELIVER) {
            order.setOrderStatus(OrderStatus.DELIVERING);
            User courier = userRepository.getReferenceById(courierId); //creating a proxy
            order.setCourier(courier);
            return orderMapper.toDTO(order);
        } else {
            throw new IllegalStateException("Assigning order to courier is unsuccessful");
        }
    }

    @Override
    public OrderDTO changeStatusToCompleted(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getOrderStatus() == OrderStatus.DELIVERING) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            return orderMapper.toDTO(order);
        } else {
            throw new IllegalStateException("Status of order cannot be changed to COMPLETED");
        }
    }
}
