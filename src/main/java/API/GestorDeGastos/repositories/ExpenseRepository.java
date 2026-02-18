package API.GestorDeGastos.repositories;

import API.GestorDeGastos.entity.Expense;
import API.GestorDeGastos.entity.enums.CategoryEnum;
import API.GestorDeGastos.entity.enums.PaymentMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByOrderByDateDesc();

    List<Expense> findByCategoryOrderByDateDesc(CategoryEnum category);

    List<Expense> findByPaymentMethodOrderByDateDesc(PaymentMethodEnum paymentMethod);

    List<Expense> findByDateBetweenOrderByDateDesc(LocalDate start, LocalDate end);

}
