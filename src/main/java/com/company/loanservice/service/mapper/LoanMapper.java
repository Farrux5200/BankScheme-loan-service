package com.company.loanservice.service.mapper;

import com.company.loanservice.dto.LoanDto;
import com.company.loanservice.model.Loan;
import com.company.loanservice.service.LoadPaymentService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring")
public abstract class LoanMapper {

    @Lazy
    @Autowired
    protected LoadPaymentService loadPaymentService;

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "loadPayment", ignore = true)
    public abstract Loan toEntity(LoanDto dto);

    @Mapping(target = "loadPayment", ignore = true)
    public abstract LoanDto toDto(Loan loan);


    @Mapping(target = "loadPayment", expression = "java(loadPaymentService.get(loan.getLoanId()).getData())")

    public abstract LoanDto toDtoWithAll(Loan loan);

   /* void aVoid(){
        LoanDto loanDto=new LoanDto();
        Loan loan=new Loan();
        loanDto.setLoadPayment(loadPaymentService.get(loan.getLoanId()).getData());
        loanDto.setBorrower(borrowerService.get(loan.getLoanId()).getData());
    }*/

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "loadPayment", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Loan loan, LoanDto dto);
}
