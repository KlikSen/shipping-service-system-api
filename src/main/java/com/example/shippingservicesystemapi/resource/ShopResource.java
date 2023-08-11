package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.OrderDTO;
import com.example.shippingservicesystemapi.dto.ProductDTO;
import com.example.shippingservicesystemapi.dto.ShopDTO;
import com.example.shippingservicesystemapi.service.OrderService;
import com.example.shippingservicesystemapi.service.ProductService;
import com.example.shippingservicesystemapi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopResource {
    @Autowired
    private ShopService shopService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> read(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(shopService.read(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShopDTO>> readAll(@RequestParam(name = "working_now", required = false, defaultValue = "true") boolean workingNow) {
        return ResponseEntity.ok(shopService.readAllByCriteria(workingNow));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<ShopDTO> update(@RequestBody @Validated final ShopDTO shopDTO) {
        return ResponseEntity.ok(shopService.update(shopDTO));
    }

    @GetMapping("/{id}/order")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<OrderDTO>> readOrdersByShopId(@PathVariable("id") long shopId) {
        return new ResponseEntity<>(orderService.readByShopId(shopId), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDTO>> readProductsByShopId(
            @PathVariable("id") long shopId,
            @RequestParam(value = "available_quantity", defaultValue = "true", required = false) boolean availableQuantity,
            @RequestParam(value = "possible_to_order_now", defaultValue = "true", required = false) boolean possibleToOrderNow
    ) {
        return new ResponseEntity<>(productService.readAllByCriteria(shopId, availableQuantity, possibleToOrderNow), HttpStatus.OK);
    }
}
