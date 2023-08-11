package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.WorkDayDTO;
import com.example.shippingservicesystemapi.entity.WorkDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkDayMapper {
    WorkDay toEntity(WorkDayDTO workDayDTO);
    WorkDayDTO toDTO(WorkDay workDay);
}
