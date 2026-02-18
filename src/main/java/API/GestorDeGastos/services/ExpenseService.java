package API.GestorDeGastos.services;

import API.GestorDeGastos.entity.Expense;
import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    Expense create(Expense expense);

    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    Expense updateExpense(Long id, Expense expense);

    void deleteExpense(Long id);

    List<Expense> getExpensesByCategory(CategoryEnum category);

    List<Expense> getExpensesByPaymentMethod(PaymentMethodEnum paymentMethod);

    List<Expense> getExpensesByDateBetween(LocalDate start, LocalDate end);

    List<Map<String, Object>> getReportByCategory();

    Map<String, Object> getReportByPeriod(LocalDate startDate, LocalDate endDate);

    Map<String, Object> getCurrentMonthReport();
}
