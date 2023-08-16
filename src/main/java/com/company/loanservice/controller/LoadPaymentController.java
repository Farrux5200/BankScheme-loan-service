package com.company.loanservice.controller;
import com.company.loanservice.dto.LoadPaymentDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.service.LoadPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("load")
@RequiredArgsConstructor
public class LoadPaymentController {
private final LoadPaymentService loadPaymentService;

    @Operation(
            tags = "BankSchemeMVS Create! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema =@Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @PostMapping("/create")
    public ResponseDto<LoadPaymentDto> create(@RequestBody LoadPaymentDto dto) {
        return this.loadPaymentService.create(dto);
    }

    @Operation(
            tags = "BanSchemeNVS Get! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema =@Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @GetMapping("/get/{id}")
    public ResponseDto<LoadPaymentDto> get(@PathVariable("id") Integer id) {
        return this.loadPaymentService.get(id);
    }

    @Operation(
            tags = "BankScheme Update! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema =@Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @PutMapping("/update/{id}")
    public ResponseDto<LoadPaymentDto> update(@RequestBody LoadPaymentDto dto,
                                              @PathVariable("id") Integer id) {
        return this.loadPaymentService.update(dto, id);
    }

    @Operation(
            tags = "BankScheme Delete! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema =@Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @DeleteMapping("/delete/{id}")
    public ResponseDto<LoadPaymentDto> delete(@PathVariable("id") Integer id) {
        return this.loadPaymentService.delete(id);
    }

    @Operation(
            tags = "BankScheme GetAll! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @GetMapping("get-all")
    public ResponseDto<List<LoadPaymentDto>> getAll() {
        return this.loadPaymentService.getAll();
    }

    @Operation(
            tags = "BankScheme GetSearch",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoadPaymentDto.class
                            )
                    )
            )
    )
    @GetMapping("/get-search")
    public ResponseDto<Page<LoadPaymentDto>> getSearch(@RequestParam Map<String , String > params){
        return this.loadPaymentService.getSearch(params);
    }
}
