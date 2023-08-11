package com.example.shippingservicesystemapi;

import com.example.shippingservicesystemapi.dto.OrderDTO;
import com.example.shippingservicesystemapi.entity.OrderedProduct;
import com.example.shippingservicesystemapi.entity.Product;
import com.example.shippingservicesystemapi.mapper.OrderMapper;
import com.example.shippingservicesystemapi.mapper.OrderedProductMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShippingServiceSystemApiApplication {
    public static void main(String[] args) {
        var variable = SpringApplication.run(ShippingServiceSystemApiApplication.class, args);
//        var mapper = variable.getBean(OrderedProductMapper.class);
//        OrderedProduct orderedProduct = new OrderedProduct();
//        orderedProduct.setId(1L);
//
//        Product product = new Product();
//        product.setId(14L);
//        product.setName("Bread");
//        product.setDescription("This is bread");
//
//        orderedProduct.setCurrentPrice(28.0);
//        orderedProduct.setQuantity(10);
//        orderedProduct.setProduct(product);
//        var dto = mapper.toDTO(orderedProduct);
//        System.out.println(dto);
//        System.out.println(mapper.toEntity(dto));

//        var orderMapper = variable.getBean(OrderMapper.class);
//
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(2L);
//        orderDTO.setNumber(67);
//        orderDTO.getOrderedProductDTOList().add(mapper.toDTO(orderedProduct));
//        orderDTO.setLocality("Lviv");
//        orderDTO.setStreet("Zamarstynivska");
//        orderDTO.setCourierId(6L);
//        orderDTO.setShopId(8L);
//        orderDTO.setCustomerId(90L);
//        orderDTO.setTotalAmount(780);
//
//        var order = orderMapper.toEntity(orderDTO);
//        System.out.println(order+"\n\n\n");
//
//        System.out.println(orderMapper.toDTO(order));
    }
}
