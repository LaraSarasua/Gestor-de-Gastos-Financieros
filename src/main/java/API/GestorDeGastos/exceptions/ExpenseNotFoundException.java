package API.GestorDeGastos.exceptions;

public class ExpenseNotFoundException extends RuntimeException{
    public ExpenseNotFoundException(Long id) {
        super(String.format("Expense with id " + id + " not found"));
    }

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
