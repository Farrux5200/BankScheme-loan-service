package com.company.loanservice.controller;

import com.company.loanservice.dto.LoanDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.service.LoanService;
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
@RequestMapping("loan")
@RequiredArgsConstructor
public class LoanController{
    private final LoanService loanService;

    @Operation(
            tags = "BankSchemeMVS Create! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanDto.class
                            )
                    )
            )
    )
    @PostMapping("/create")
    public ResponseDto<LoanDto> create(@RequestBody LoanDto dto) {
        return this.loanService.create(dto);
    }

    @Operation(
            tags = "BanSchemeNVS Get! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanDto.class
                            )
                    )
            )
    )
    @GetMapping("/get/{id}")
    public ResponseDto<LoanDto> get(@PathVariable("id") Integer id) {
        return this.loanService.get(id);
    }

    @Operation(
            tags = "BankScheme Update! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanDto.class
                            )
                    )
            )
    )
    @PutMapping("/update/{id}")
    public ResponseDto<LoanDto> update(@RequestBody LoanDto dto,
                                       @PathVariable("id") Integer id) {
        return this.loanService.update(dto, id);
    }

    @Operation(
            tags = "BankScheme Delete! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanDto.class
                            )
                    )
            )
    )
    @DeleteMapping("/delete/{id}")
    public ResponseDto<LoanDto> delete(@PathVariable("id") Integer id) {
        return this.loanService.delete(id);
    }

    @Operation(
            tags = "BankScheme GetAll! ",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanController.class
                            )
                    )
            )
    )
    @GetMapping("/get-all")
    public ResponseDto<List<LoanDto>> getAll() {
        return this.loanService.getAll();
    }

    @Operation(
            tags = "BankScheme GetSearch",
            summary = "Your summary create method",
            description = "Your description this method",
            responses = @ApiResponse(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanDto.class
                            )
                    )
            )
    )
    @GetMapping("/get-search")
    public ResponseDto<Page<LoanDto>> getSearch(@RequestParam Map<String, String> params){
        return this.loanService.getSearch(params);
    }

}
