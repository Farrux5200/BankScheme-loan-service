package com.company.loanservice.service;

import com.company.loanservice.dto.ErrorDto;
import com.company.loanservice.dto.LoanDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.model.Loan;
import com.company.loanservice.repository.LoanRepository;
import com.company.loanservice.service.mapper.LoanMapper;
import com.company.loanservice.service.validate.LoanValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final LoanValidate loanValidate;

    public ResponseDto<LoanDto> create(LoanDto dto) {
        List<ErrorDto> errors = this.loanValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<LoanDto>builder()
                    .code(-2)
                    .message("Validate error")
                    .errors(errors)
                    .build();
        }

        try {
            Loan loan = this.loanMapper.toEntity(dto);
            this.loanRepository.save(loan);
            return ResponseDto.<LoanDto>builder()
                    .success(true)
                    .message("Loan successful create method!")
                    .data(this.loanMapper.toDto(loan))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .code(-3)
                    .message(String.format("Loan while saving error %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<LoanDto> get(Integer id) {
        return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                .map(loan -> ResponseDto.<LoanDto>builder()
                        .success(true)
                        .message("Loan successful get method!")
                        .data(this.loanMapper.toDtoWithAll(loan))
                        .build())
                .orElse(ResponseDto.<LoanDto>builder()
                        .code(-1)
                        .message(String.format("Loan id is not found! %s", id))
                        .build());
    }

    public ResponseDto<LoanDto> update(LoanDto dto, Integer id) {
        try {
            return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                    .map(loan -> {
                        this.loanMapper.update(loan, dto);
                        this.loanRepository.save(loan);
                        return ResponseDto.<LoanDto>builder()
                                .message("Loan successful update!")
                                .success(true)
                                .data(this.loanMapper.toDto(loan))
                                .build();
                    }).orElse(ResponseDto.<LoanDto>builder()
                            .code(-1)
                            .message(String.format("Loan id is not found! %s", id))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .code(-3)
                    .message(String.format("Loan while updating error %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<LoanDto> delete(Integer id) {
        try {
            return this.loanRepository.findByLoanIdAndDeletedAtIsNull(id)
                    .map(loan -> {
                        loan.setDeletedAt(LocalDateTime.now());
                        loan.setStatus(false);
                        this.loanRepository.save(loan);
                        return ResponseDto.<LoanDto>builder()
                                .message("Loan successful delete!")
                                .success(true)
                                .data(this.loanMapper.toDto(loan))
                                .build();
                    }).orElse(ResponseDto.<LoanDto>builder()
                            .code(-1)
                            .message(String.format("Loan id is not found! %s", id))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoanDto>builder()
                    .code(-3)
                    .message(String.format("Loan while deleting error %s", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<List<LoanDto>> getAll() {
        return ResponseDto.<List<LoanDto>>builder()
                .success(true)
                .message("Loan getAll method successful!")
                .data(this.loanRepository.findAll()
                        .stream().map(loanMapper::toDto)
                        .toList())
                .build();
    }

    public ResponseDto<Page<LoanDto>> getSearch(Map<String, String> params) {
        try {
            int page=0, size=10;
            if (params.containsKey("page")){
                page=Integer.parseInt(params.get("page"));
            }
            if (params.containsKey("size")){
                size=Integer.parseInt(params.get("size"));
            }
            return ResponseDto.<Page<LoanDto>>builder()
                    .success(true)
                    .message("Loan successful getSearch! ")
                    .data(this.loanRepository.getSearch(
                            params.get("loanId")==null?null:Integer.parseInt(params.get("loanId")),
                            params.get("goal"),
                            params.get("issuedAmount")==null?null:Double.parseDouble(params.get("issuedAmount")),
                            params.get("remainingAmount")==null?null:Double.parseDouble(params.get("remainingAmount")),
                            PageRequest.of(page, size)).map(this.loanMapper::toDto))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Page<LoanDto>>builder()
                    .code(-1)
                    .message(String.format("Loan getSearch error! " + e.getMessage()))
                    .build();
        }
    }
}
