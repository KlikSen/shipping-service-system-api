package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    ProductDTO read(long id);
    ProductDTO update(ProductDTO shopDTO);
    List<ProductDTO> readAllByCriteria(Long shopId, boolean availableQuantity, boolean possibleToOrderNow);
}
