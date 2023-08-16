package com.company.loanservice.service.mapper;
import com.company.loanservice.dto.LoadPaymentDto;
import com.company.loanservice.model.LoadPayment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class LoadPaymentMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "loanId", ignore = true)
    public abstract LoadPayment toEntity(LoadPaymentDto dto);

    public  abstract LoadPaymentDto toDto(LoadPayment loadPayment);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "loanId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget LoadPayment loadPayment, LoadPaymentDto dto);
}
