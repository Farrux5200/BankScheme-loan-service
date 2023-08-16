package com.company.loanservice.service.validate;

import com.company.loanservice.dto.ErrorDto;
import com.company.loanservice.dto.LoanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoanValidate {

    public List<ErrorDto> validate(LoanDto dto) {
        List<ErrorDto> errors = new ArrayList<>();

        return errors;
    }

}
