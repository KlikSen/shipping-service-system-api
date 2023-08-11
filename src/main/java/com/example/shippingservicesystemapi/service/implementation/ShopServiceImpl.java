package com.example.shippingservicesystemapi.service.implementation;

import com.example.shippingservicesystemapi.dto.ShopDTO;
import com.example.shippingservicesystemapi.entity.Shop;
import com.example.shippingservicesystemapi.mapper.ShopMapper;
import com.example.shippingservicesystemapi.repository.ShopRepository;
import com.example.shippingservicesystemapi.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public ShopDTO create(ShopDTO shopDTO) {
        Shop shop = shopMapper.toEntity(shopDTO); //contains shopOwnerId in shopOwner.id and workDayDTOList in workDayList
        shop.setUpWorkDayList(); //used to set workDayList elements in order to persist the nested list of entities

        return shopMapper.toDTO(shopRepository.save(shop));
    }

    @Override
    public ShopDTO read(long id) {
        return shopMapper.toDTO(shopRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shop not found by id")));
    }

    @Override
    public ShopDTO update(ShopDTO shopDTO) {
        shopRepository.update(
                shopDTO.getId(),
                shopDTO.getName(),
                shopDTO.getLocality(),
                shopDTO.getStreet(),
                shopDTO.getNumber()
        );
        Shop shop = shopRepository.findById(shopDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException(("Shop not found")));

        return shopMapper.toDTO(shop);
    }

    @Override
    public List<ShopDTO> readAllByCriteria(boolean workingNow) {
        return shopRepository.findAllByCriteria(workingNow, LocalTime.now(), LocalDate.now().getDayOfWeek()).stream()
                .map(shopMapper::toDTO)
                .toList();
    }
}