package API.GestorDeGastos.services.impl;

import API.GestorDeGastos.entity.Expense;
import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;
import API.GestorDeGastos.exceptions.ExpenseNotFoundException;
import API.GestorDeGastos.repositories.ExpenseRepository;
import API.GestorDeGastos.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    @Override
    public Expense create(Expense expense) {
        if(expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(("La fehca del gasto no puede ser futura."));
        }
        return repository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {

        return repository.findAllByOrderByDateDesc();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return repository.findById(id).
                orElseThrow(() -> new ExpenseNotFoundException(id));

    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existing = getExpenseById(id);

        if(expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del gasto no puede ser futura.");
        }

        existing.setDescription(expense.getDescription());
        existing.setAmount(expense.getAmount());
        existing.setCategory(expense.getCategory());
        existing.setDate(expense.getDate());
        existing.setPaymentMethod(expense.getPaymentMethod());

        return repository.save(existing);
    }

    @Override
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        repository.delete(expense);
    }

    @Override
    public List<Expense> getExpensesByCategory(CategoryEnum category) {
        return repository.findByCategoryOrderByDateDesc(category);
    }

    @Override
    public List<Expense> getExpensesByPaymentMethod(PaymentMethodEnum paymentMethod) {
        return repository.findByPaymentMethodOrderByDateDesc(paymentMethod);
    }

    @Override
    public List<Expense> getExpensesByDateBetween(LocalDate start, LocalDate end) {
        return repository.findByDateBetweenOrderByDateDesc(start, end);
    }

    //reportes

    @Override
    public List<Map<String, Object>> getReportByCategory() {
        List<Expense> allExpenses = repository.findAll();

        Map<CategoryEnum, List<Expense>> expensesByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));

        List<Map<String, Object>> reportByCategory = new ArrayList<>();

        for(Map.Entry<CategoryEnum, List<Expense>> entry : expensesByCategory.entrySet()) {
            CategoryEnum category = entry.getKey();
            List<Expense> expenses = entry.getValue();

            BigDecimal amount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> report = new HashMap<>();
            report.put("category", category.name());
            report.put("amount", amount);
            report.put("expenses", expenses.size());

            reportByCategory.add(report);
        }
        reportByCategory.sort((a, b) -> {
            BigDecimal totalA = (BigDecimal) a.get("amount");
            BigDecimal totalB = (BigDecimal) b.get("amount");
            return totalB.compareTo(totalA);
        });

        return reportByCategory;
    }

    @Override
    public Map<String, Object> getReportByPeriod(LocalDate start, LocalDate end) {
        List<Expense> expenses = repository.findByDateBetweenOrderByDateDesc(start, end);

        BigDecimal amount = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int expenseCount = expenses.size();

        BigDecimal averageExpense = BigDecimal.ZERO;
        if(expenseCount > 0) {
            averageExpense = amount.divide(
                    BigDecimal.valueOf(expenseCount),
                    2,
                    RoundingMode.HALF_UP
            );
        }

        Map<String, Object> report = new HashMap<>();
        report.put("start", start.toString());
        report.put("end", end.toString());
        report.put("totalAmount", amount);
        report.put("expenseCount", expenseCount);
        report.put("averageExpense", averageExpense);

        return report;
    }

    @Override
    public Map<String, Object> getCurrentMonthReport() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> monthExpenses = repository.findByDateBetweenOrderByDateDesc(startOfMonth, endOfMonth);

        BigDecimal totalAmount = monthExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<CategoryEnum, BigDecimal> totalsByCategory = monthExpenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Expense::getAmount,
                                BigDecimal::add
                        )
                ));

        CategoryEnum mostExpensive = null;
        CategoryEnum leastExpensive = null;

        if(!totalsByCategory.isEmpty()) {
            mostExpensive = totalsByCategory.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            leastExpensive = totalsByCategory.entrySet().stream()
                    .min(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }

        Map<String, Object> report = new HashMap<>();
        report.put("month", now.getMonth().name());
        report.put("year", now.getYear());
        report.put("totalAmount", totalAmount);
        report.put("expenseCount", monthExpenses.size());
        report.put("mostExpensiveCategory", mostExpensive != null ? mostExpensive.name() : null);
        report.put("leastExpensiveCategory", leastExpensive != null ? leastExpensive.name() : null);

        return report;
    }



}
