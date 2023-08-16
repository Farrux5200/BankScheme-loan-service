package com.company.loanservice.service;
import com.company.loanservice.dto.ErrorDto;
import com.company.loanservice.dto.LoadPaymentDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.model.LoadPayment;
import com.company.loanservice.repository.LoadPaymentRepository;
import com.company.loanservice.service.mapper.LoadPaymentMapper;
import com.company.loanservice.service.validate.LoadPaymentValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoadPaymentService {
    private final LoadPaymentRepository loadPaymentRepository;
    private final LoadPaymentMapper loadPaymentMapper;
    private final LoadPaymentValidate loadPaymentValidate;

    public ResponseDto<LoadPaymentDto> create(LoadPaymentDto dto) {
        List<ErrorDto> errors = this.loadPaymentValidate.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<LoadPaymentDto>builder()
                    .code(-2)
                    .message("Validate error")
                    .errors(errors)
                    .build();
        }
        try {
            LoadPayment loadPayment = this.loadPaymentMapper.toEntity(dto);
            this.loadPaymentRepository.save(loadPayment);
            return ResponseDto.<LoadPaymentDto>builder()
                    .success(true)
                    .message("LoadPayment successful create!")
                    .data(this.loadPaymentMapper.toDto(loadPayment))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<LoadPaymentDto>builder()
                    .code(-3)
                    .message(String.format("LoadPayment while saving error! %s ", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<LoadPaymentDto> get(Integer id) {
        return this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(id)
                .map(loadPayment -> ResponseDto.<LoadPaymentDto>builder()
                        .message("LoadPayment successful get method!")
                        .success(true)
                        .data(this.loadPaymentMapper.toDto(loadPayment))
                        .build())
                .orElse(ResponseDto.<LoadPaymentDto>builder()
                        .code(-1)
                        .message(String.format("LoadPayment id is not found! " + id))
                        .build());
    }

    public ResponseDto<LoadPaymentDto> update(LoadPaymentDto dto, Integer id) {
        try {
            return this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(id)
                    .map(loadPayment -> {
                        loadPayment.setStatus(true);
                        this.loadPaymentMapper.update(loadPayment, dto);
                        this.loadPaymentRepository.save(loadPayment);
                        return ResponseDto.<LoadPaymentDto>builder()
                                .message("LoadPayment successful update method")
                                .success(true)
                                .data(this.loadPaymentMapper.toDto(loadPayment))
                                .build();
                    }).orElse(ResponseDto.<LoadPaymentDto>builder()
                            .code(-1)
                            .message(String.format("LoadPayment id is not found! " + id))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoadPaymentDto>builder()
                    .code(-3)
                    .message(String.format("LoadPayment while updating error! %s ", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<LoadPaymentDto> delete(Integer id) {
        try {
            return this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(id)
                    .map(loadPayment -> {
                        loadPayment.setDeletedAt(LocalDateTime.now());
                        loadPayment.setStatus(false);
                        this.loadPaymentRepository.save(loadPayment);
                        return ResponseDto.<LoadPaymentDto>builder()
                                .message("LoadPayment successful delete method")
                                .success(true)
                                .data(this.loadPaymentMapper.toDto(loadPayment))
                                .build();
                    }).orElse(ResponseDto.<LoadPaymentDto>builder()
                            .code(-1)
                            .message(String.format("LoadPayment id is not found! " + id))
                            .build());
        } catch (Exception e) {
            return ResponseDto.<LoadPaymentDto>builder()
                    .code(-3)
                    .message(String.format("LoadPayment while deleting error! %s ", e.getMessage()))
                    .build();
        }
    }

    public ResponseDto<List<LoadPaymentDto>> getAll() {
        return ResponseDto.<List<LoadPaymentDto>>builder()
                .success(true)
                .message("LoadPayment successful getAll method!")
                .data(this.loadPaymentRepository.findAll()
                        .stream().map(loadPaymentMapper::toDto)
                        .toList())
                .build();
    }

    public ResponseDto<Page<LoadPaymentDto>> getSearch(Map<String, String> params) {
      try {
          int page=0, size=10;
          if (params.containsKey("page")){
              page=Integer.parseInt(params.get("page"));
          }
          if (params.containsKey("size")){
              size=Integer.parseInt(params.get("size"));
          }
          return ResponseDto.<Page<LoadPaymentDto>>builder()
                  .message("LoadPayment successful getSearch!")
                  .success(true)
                  .data(this.loadPaymentRepository.getSearch(
                          params.get("loadPaymentId")==null?null:Integer.parseInt(params.get("loadPaymentId")),
                          params.get("amount")==null?null:Double.parseDouble(params.get("amount")),
                          params.get("description"),
                          params.get("loanId")==null?null:Integer.parseInt(params.get("loanId")),
                          PageRequest.of(page,size)).map(this.loadPaymentMapper::toDto))
                  .build();
      }catch (Exception e){
          return ResponseDto.<Page<LoadPaymentDto>>builder()
                  .code(-1)
                  .message(String.format("Load Payment search error!"+ e.getMessage()))
                  .build();
      }
    }
}
