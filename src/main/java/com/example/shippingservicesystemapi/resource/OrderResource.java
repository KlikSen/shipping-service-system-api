package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.OrderDTO;
import com.example.shippingservicesystemapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderResource {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody @Validated OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.create(orderDTO), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> read(@PathVariable("id") long id) {
        return new ResponseEntity<>(orderService.read(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<List<OrderDTO>> readOrdersByCustomerId(@RequestParam(name = "customer_id") long id) {
        return new ResponseEntity<>(orderService.readByCustomerId(id), HttpStatus.OK);
    }

    @PostMapping("/make_in_processing")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<OrderDTO> changeStatusToInProcessing(@RequestParam(name = "order_id") long id) {
        return new ResponseEntity<>(orderService.changeStatusToInProcessing(id), HttpStatus.OK);
    }

    @PostMapping("/make_ready_to_deliver")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<OrderDTO> changeStatusToReadyToDeliver(@RequestParam(name = "order_id") long id) {
        return new ResponseEntity<>(orderService.changeStatusToReadyToDeliver(id), HttpStatus.OK);
    }

    @GetMapping("/all/ready_to_deliver")
    @PreAuthorize("hasAnyRole('COURIER')")
    public ResponseEntity<List<OrderDTO>> findAllReadyToDeliver() {
        return new ResponseEntity<>(orderService.readAllReadyToDeliver(), HttpStatus.OK);
    }

    @PostMapping("/assign_order")
    @PreAuthorize("hasAnyRole('COURIER')")
    public ResponseEntity<OrderDTO> assignOrder(@RequestParam("order_id") long orderId, @RequestParam("courier_id") long courierId) {
        return new ResponseEntity<>(orderService.assignOrder(orderId, courierId), HttpStatus.OK);
    }

    @PostMapping("/make_completed")
    @PreAuthorize("hasAnyRole('COURIER')")
    public ResponseEntity<OrderDTO> changeStatusToCompleted(@RequestParam("order_id") long id) {
        return new ResponseEntity<>(orderService.changeStatusToCompleted(id), HttpStatus.OK);
    }
}
