package com.company.loanservice.service.validate;

import com.company.loanservice.dto.ErrorDto;
import com.company.loanservice.dto.LoadPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoadPaymentValidate {


    public List<ErrorDto> validate(LoadPaymentDto dto) {
        List<ErrorDto> errors = new ArrayList<>();
        if (dto.getAmount() < 0) {
            errors.add(new ErrorDto("Amount", "Amount<0 error"));
        }
        return errors;
    }
}
