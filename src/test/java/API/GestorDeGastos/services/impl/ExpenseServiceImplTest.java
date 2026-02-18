package API.GestorDeGastos.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import API.GestorDeGastos.entity.Expense;
import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;
import API.GestorDeGastos.exceptions.ExpenseNotFoundException;
import API.GestorDeGastos.repositories.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseServiceImpl service;

    private Expense expense;

    @BeforeEach
    void setUp() {
        expense = Expense.builder()
                .id(1L)
                .description("Supermercado")
                .amount(new BigDecimal("100.00"))
                .category(CategoryEnum.FOOD)
                .date(LocalDate.now())
                .paymentMethod(PaymentMethodEnum.DEBIT_CARD)
                .build();
    }

    @Test
    void shouldCreateExpense() {
        when(repository.save(expense)).thenReturn(expense);

        Expense saved = service.create(expense);

        assertNotNull(saved);
        assertEquals("Supermercado", saved.getDescription());
        verify(repository, times(1)).save(expense);
    }

    @Test
    void shouldReturnExpenseById() {
        when(repository.findById(1L)).thenReturn(Optional.of(expense));

        Expense found = service.getExpenseById(1L);

        assertEquals(1L, found.getId());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenExpenseNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class,
                () -> service.getExpenseById(1L));
    }


}