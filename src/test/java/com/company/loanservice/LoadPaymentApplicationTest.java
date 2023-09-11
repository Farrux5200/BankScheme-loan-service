package com.company.loanservice;

import com.company.loanservice.dto.LoadPaymentDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.model.LoadPayment;
import com.company.loanservice.repository.LoadPaymentRepository;
import com.company.loanservice.service.LoadPaymentService;
import com.company.loanservice.service.mapper.LoadPaymentMapper;
import com.company.loanservice.service.validate.LoadPaymentValidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoadPaymentApplicationTest {
    private LoadPaymentMapper loadPaymentMapper;
    private LoadPaymentRepository loadPaymentRepository;
    private LoadPaymentValidate loadPaymentValidate;
    private LoadPaymentService loadPaymentService;


    @BeforeEach
    void initMethod(){
        this.loadPaymentMapper= mock(LoadPaymentMapper.class);
        this.loadPaymentRepository=mock(LoadPaymentRepository.class);
        this.loadPaymentValidate=mock(LoadPaymentValidate.class);
        this.loadPaymentService=new LoadPaymentService(loadPaymentRepository,loadPaymentMapper,loadPaymentValidate);
    }

    @Test
    void testCreatePositive(){
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();

        when(this.loadPaymentMapper.toDto(any()))
                .thenReturn(loadPaymentDto);
        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.create(loadPaymentDto);
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals("LoadPayment successful create!", response.getMessage());

        verify(this.loadPaymentMapper, times(1)).toDto(any());
        verify(this.loadPaymentRepository, times(1)).save(any());
        verify(this.loadPaymentMapper, times(1)).toEntity(any());
    }

    @Test
    void testCreateException(){
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();
        when(this.loadPaymentMapper.toDto(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.create(loadPaymentDto);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals(-3, response.getCode());
        Assertions.assertNull(response.getData());

        verify(this.loadPaymentMapper, times(1)).toDto(any());
    }

    @Test
    void testGetPositive(){
        Integer loadPaymentId=1;
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();
        when(this.loadPaymentMapper.toDto(any()))
                .thenReturn(loadPaymentDto);
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(LoadPayment.builder()
                        .loadPaymentId(1)
                        .amount(1000D)
                        .description("description")
                        .loanId(1)
                        .build()));
        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.get(loadPaymentId);
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertEquals("LoadPayment successful get method!", response.getMessage());
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());

        verify(this.loadPaymentMapper, times(1)).toDto(any());
        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testGetNegative(){
        Integer loadPaymentId = 1;
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.get(loadPaymentId);
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        Assertions.assertEquals(-1, response.getCode());

        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testUpdatePositive(){
        Integer loadPaymentId = 1;
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(LoadPayment.builder()
                        .loadPaymentId(1)
                        .amount(1000D)
                        .description("description")
                        .loanId(1)
                        .build()));
        when(this.loadPaymentMapper.toDto(any()))
                .thenReturn(loadPaymentDto);

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.update(loadPaymentDto, loadPaymentId);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertEquals("LoadPayment successful update method", response.getMessage());

        verify(this.loadPaymentMapper, times(1)).update(any(), any());
        verify(this.loadPaymentMapper, times(1)).toDto(any());
        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());
        verify(this.loadPaymentRepository, times(1)).save(any());

    }

    @Test
    void testUpdateException(){
        Integer loadPaymentId = 1;
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.update(loadPaymentDto, loadPaymentId);

        Assertions.assertEquals(-3, response.getCode());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());

        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testUpdateNegative(){
        Integer loadPaymentId = 1;
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();

        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.update(loadPaymentDto, loadPaymentId);
        Assertions.assertEquals(-1, response.getCode());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());

        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testDeletePositive(){
        Integer loadPaymentId = 1;
        LoadPaymentDto loadPaymentDto= LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build();
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(LoadPayment.builder()
                        .loadPaymentId(1)
                        .amount(1000D)
                        .description("description")
                        .loanId(1)
                        .build()));
        when(this.loadPaymentMapper.toDto(any()))
                .thenReturn(loadPaymentDto);

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.delete(loadPaymentId);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertEquals("LoadPayment successful delete method", response.getMessage());

        verify(this.loadPaymentMapper, times(1)).toDto(any());
        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testDeleteException(){
        Integer loadPaymentId = 1;
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenThrow(RuntimeException.class);

        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.delete(loadPaymentId);
        Assertions.assertEquals(-3, response.getCode());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testDeleteNegative(){
        Integer loadPaymentId = 1;
        when(this.loadPaymentRepository.findByLoadPaymentIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.empty());
        ResponseDto<LoadPaymentDto> response = this.loadPaymentService.delete(loadPaymentId);
        Assertions.assertEquals(-1, response.getCode());
        Assertions.assertFalse(response.isSuccess());
        Assertions.assertNull(response.getData());
        verify(this.loadPaymentRepository, times(1)).findByLoadPaymentIdAndDeletedAtIsNull(any());

    }

    @Test
    void testGetAllPositive(){
        List<LoadPayment> loadPayments = new ArrayList<>();
        loadPayments.add(LoadPayment.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .loanId(1)
                .build());
        when(this.loadPaymentRepository.findAll())
                .thenReturn(loadPayments);
        ResponseDto<List<LoadPaymentDto>> response = this.loadPaymentService.getAll();

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNotNull(response.getData());
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertEquals("LoadPayment successful getAll method!", response.getMessage());

        verify(this.loadPaymentRepository, times(1)).findAll();
        verify(this.loadPaymentMapper, times(1)).toDto(any());
    }

    @Test
    void testGetSearchPositive(){

    }

    @Test
    void testGetSearchNegative(){

    }


}
