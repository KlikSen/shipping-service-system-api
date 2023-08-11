package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.ProductDTO;
import com.example.shippingservicesystemapi.entity.Product;
import com.example.shippingservicesystemapi.mapper.ProductMapper;
import com.example.shippingservicesystemapi.repository.ProductRepository;
import com.example.shippingservicesystemapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper mapper;

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Product product = mapper.toEntity(productDTO); //contains shopId in shop.id and quantity in storage.quantity
        product.setUpStorage(); //used to set product in storage in order to persist the nested entity such as storage ,because it is mapped

        return mapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO read(long id) {
        return mapper.toDTO(productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found")));
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        productRepository.update(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getQuantity()
        );

        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return mapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> readAllByCriteria(Long shopId, boolean availableQuantity, boolean possibleToOrderNow) {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();

        return productRepository.findAllByCriteria(shopId, availableQuantity, possibleToOrderNow, LocalTime.now(), dayOfWeek).stream()
                .map(mapper::toDTO)
                .toList();
    }

}
