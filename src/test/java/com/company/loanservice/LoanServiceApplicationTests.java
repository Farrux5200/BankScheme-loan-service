package com.company.loanservice;

import com.company.loanservice.dto.LoadPaymentDto;
import com.company.loanservice.dto.LoanDto;
import com.company.loanservice.dto.ResponseDto;
import com.company.loanservice.model.Loan;
import com.company.loanservice.repository.LoanRepository;
import com.company.loanservice.service.LoanService;
import com.company.loanservice.service.mapper.LoanMapper;
import com.company.loanservice.service.validate.LoanValidate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceApplicationTests {


	private LoanRepository loanRepository;
	private LoanMapper loanMapper;
	private LoanValidate loanValidate;
	private LoanService loanService;

	@BeforeEach
	void initMethod() {
		this.loanMapper = mock(LoanMapper.class);
		this.loanRepository = mock(LoanRepository.class);
		this.loanValidate = mock(LoanValidate.class);
		this.loanService = new LoanService(loanMapper, loanRepository, loanValidate);
	}

	@Test
	void testCreatePositive(){
		LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.loadPayment(loadPaymentDto)
				.build();

		when(this.loanMapper.toDto(any()))
				.thenReturn(loanDto);
		ResponseDto<LoanDto> response = this.loanService.create(loanDto);
		Assertions.assertEquals(0, response.getCode());
		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getData());
		Assertions.assertEquals("Loan successful create method!", response.getMessage());

		verify(this.loanMapper, times(1)).toDto(any());
		verify(this.loanRepository, times(1)).save(any());
		verify(this.loanMapper, times(1)).toEntity(any());
	}

	@Test
	void testCreateException(){
		LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.loadPayment(loadPaymentDto)
				.build();
		when(this.loanMapper.toDto(any()))
				.thenThrow(RuntimeException.class);

		ResponseDto<LoanDto> response = this.loanService.create(loanDto);

		Assertions.assertFalse(response.isSuccess());
		Assertions.assertEquals(-3, response.getCode());
		Assertions.assertNull(response.getData());

		verify(this.loanMapper, times(1)).toDto(any());
	}

	@Test
	void testGetPositive(){
        Integer loanId=1;
        LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
                .loadPaymentId(1)
                .amount(1000D)
                .description("description")
                .build();

        LoanDto loanDto= LoanDto.builder()
                .loanId(1)
                .remainingAmount(1000D)
                .issuedAmount(1000D)
                .loadPayment(loadPaymentDto)
                .build();
        when(this.loanMapper.toDto(any()))
                .thenReturn(loanDto);
        when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
                .thenReturn(Optional.of(Loan.builder()
                        .loanId(1)
                        .remainingAmount(1000D)
                        .issuedAmount(1000D)
                        .build()));
        ResponseDto<LoanDto> response = this.loanService.get(loanId);
        Assertions.assertEquals(0, response.getCode());
        Assertions.assertEquals("Loan successful get method!", response.getMessage());
        Assertions.assertTrue(response.isSuccess());
      //  Assertions.assertNotNull(response.getData());

        verify(this.loanMapper, times(1)).toDtoWithAll(any());
        verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());
	}

	@Test
	void testGetNegative(){
		Integer loanId = 1;
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenReturn(Optional.empty());

		ResponseDto<LoanDto> response = this.loanService.get(loanId);
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertNull(response.getData());
		Assertions.assertEquals(-1, response.getCode());

		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());

	}

	@Test
	void testUpdatePositive(){
		Integer loanId = 1;
				LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
			.loadPayment(loadPaymentDto)
				.build();
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenReturn(Optional.of(Loan.builder()
						.loanId(1)
						.remainingAmount(1000D)
						.issuedAmount(1000D)
						.build()));
		when(this.loanMapper.toDto(any()))
				.thenReturn(loanDto);

		ResponseDto<LoanDto> response = this.loanService.update(loanDto, loanId);

		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getData());
		Assertions.assertEquals(0, response.getCode());
		Assertions.assertEquals("Loan successful update!", response.getMessage());

		verify(this.loanMapper, times(1)).update(any(), any());
		verify(this.loanMapper, times(1)).toDto(any());
		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());
		verify(this.loanRepository, times(1)).save(any());

	}

	@Test
	void testUpdateException(){
		Integer loanId = 1;
			LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.loadPayment(loadPaymentDto)
				.build();
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenThrow(RuntimeException.class);

		ResponseDto<LoanDto> response = this.loanService.update(loanDto, loanId);

		Assertions.assertEquals(-3, response.getCode());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertNull(response.getData());

		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());
	}

	@Test
	void testUpdateNegative(){
		Integer loanId = 1;
		LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.loadPayment(loadPaymentDto)
				.build();

		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenReturn(Optional.empty());

		ResponseDto<LoanDto> response = this.loanService.update(loanDto, loanId);
		Assertions.assertEquals(-1, response.getCode());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertNull(response.getData());

		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());

	}

	@Test
	void testDeletePositive(){
		Integer loanId = 1;
		LoadPaymentDto loadPaymentDto=LoadPaymentDto.builder()
				.loadPaymentId(1)
				.amount(1000D)
				.description("description")
				.build();

		LoanDto loanDto= LoanDto.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.loadPayment(loadPaymentDto)
				.build();
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenReturn(Optional.of(Loan.builder()
						.loanId(1)
						.remainingAmount(1000D)
						.issuedAmount(1000D)
						.build()));
		when(this.loanMapper.toDto(any()))
				.thenReturn(loanDto);

		ResponseDto<LoanDto> response = this.loanService.delete(loanId);

		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getData());
		Assertions.assertEquals(0, response.getCode());
		Assertions.assertEquals("Loan successful delete!", response.getMessage());

		verify(this.loanMapper, times(1)).toDto(any());
		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());

	}

	@Test
	void testDeleteException(){
		Integer loanId = 1;
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenThrow(RuntimeException.class);

		ResponseDto<LoanDto> response = this.loanService.delete(loanId);
		Assertions.assertEquals(-3, response.getCode());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertNull(response.getData());
		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());

	}

	@Test
	void testDeleteNegative(){
		Integer loanId = 1;
		when(this.loanRepository.findByLoanIdAndDeletedAtIsNull(any()))
				.thenReturn(Optional.empty());
		ResponseDto<LoanDto> response = this.loanService.delete(loanId);
		Assertions.assertEquals(-1, response.getCode());
		Assertions.assertFalse(response.isSuccess());
		Assertions.assertNull(response.getData());
		verify(this.loanRepository, times(1)).findByLoanIdAndDeletedAtIsNull(any());

	}

	@Test
	void testGetAllPositive(){
		List<Loan> loans = new ArrayList<>();
		loans.add(Loan.builder()
				.loanId(1)
				.remainingAmount(1000D)
				.issuedAmount(1000D)
				.build());
		when(this.loanRepository.findAll())
				.thenReturn(loans);
		ResponseDto<List<LoanDto>> response = this.loanService.getAll();

		Assertions.assertTrue(response.isSuccess());
		Assertions.assertNotNull(response.getData());
		Assertions.assertEquals(0, response.getCode());
		Assertions.assertEquals("Loan getAll method successful!", response.getMessage());

		verify(this.loanRepository, times(1)).findAll();
		verify(this.loanMapper, times(1)).toDto(any());
	}

	@Test
	void testGetSearchPositive() {

	}

	@Test
	void testGetSearchNegative() {

	}

}
