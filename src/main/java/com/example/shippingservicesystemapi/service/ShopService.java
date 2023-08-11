package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.ShopDTO;

import java.util.List;

public interface ShopService {
    ShopDTO create(ShopDTO shopDTO);
    ShopDTO read(long id);
    List<ShopDTO> readAllByCriteria(boolean workingNow);
    ShopDTO update(ShopDTO shopDTO);
}
