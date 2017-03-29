package edge.app.modules.expenses;

import java.util.List;

public interface ExpensesService {

	Expense saveExpense(Expense gym, String loggedInId);

	List<Expense> getAllExpenses(String loggedInId);

}
