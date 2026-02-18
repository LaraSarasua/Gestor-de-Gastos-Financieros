package API.GestorDeGastos.controller;

import API.GestorDeGastos.entity.Expense;
import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;
import API.GestorDeGastos.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor

public class ExpenseController {
    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense createdExpense = service.create(expense);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = service.getAllExpenses();

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Expense expense = service.getExpenseById(id);

        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        Expense updatedExpense = service.updateExpense(id, expense);

        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable Long id) {
        service.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable CategoryEnum category) {
        List<Expense> expenses = service.getExpensesByCategory(category);

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/payment-method/{paymentMethod}")
    public ResponseEntity<List<Expense>> getByPaymentMethod(@PathVariable PaymentMethodEnum paymentMethod) {
        List<Expense> expenses = service.getExpensesByPaymentMethod(paymentMethod);

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Expense>> getByDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Expense> expenses = service.getExpensesByDateBetween(startDate, endDate);

        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/reports/by-category")
    public ResponseEntity<List<Map<String, Object>>> reportByCategory() {
        List<Map<String, Object>> report = service.getReportByCategory();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/period")
    public ResponseEntity<Map<String, Object>> reportByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> report = service.getReportByPeriod(startDate, endDate);

        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/current-month")
    public ResponseEntity<Map<String, Object>> getcurrentMonthReport() {
        Map<String, Object> report = service.getCurrentMonthReport();

        return ResponseEntity.ok(report);
    }

}
