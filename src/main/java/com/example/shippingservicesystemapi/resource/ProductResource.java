package com.example.shippingservicesystemapi.resource;

import com.example.shippingservicesystemapi.dto.ProductDTO;
import com.example.shippingservicesystemapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResource {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<ProductDTO> create(@RequestBody @Validated ProductDTO productDTO) {
        return new ResponseEntity<>(productService.create(productDTO), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> read(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.read(id), HttpStatusCode.valueOf(200));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<ProductDTO> update(@RequestBody @Validated final ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> readAllByCriteria(
            @RequestParam(value = "shop_id", required = false) Long shopId,
            @RequestParam(value = "available_quantity", defaultValue = "true", required = false) boolean availableQuantity,
            @RequestParam(value = "possible_to_order_now", defaultValue = "true", required = false) boolean possibleToOrderNow
    ) {
        return ResponseEntity.ok(productService.readAllByCriteria(shopId, availableQuantity, possibleToOrderNow));
    }
}
