package edge.app.modules.expenses;

import java.util.List;

public interface ExpensesService {

	Expense saveExpense(Expense parent, String loggedInId);

	List<Expense> getAllExpenses(String loggedInId);

}
