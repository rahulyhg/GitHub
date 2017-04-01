package edge.app.modules.expenses;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class ExpensesController {

private static final Logger logger = LoggerFactory.getLogger(ExpensesController.class);
	
	@Autowired
	private ExpensesService expensesService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public ExpensesService getExpensesService() {
		return expensesService;
	}

	@RequestMapping(value={"/secured/saveExpense"})
	public EdgeResponse<Expense> addExpense(
			@RequestBody Expense expense, Principal principal			
			) throws Exception{	
		try{
			Expense addExpense = expensesService.saveExpense(expense, principal.getName());
			return EdgeResponse.createDataResponse(addExpense, "Expense added Successfully with ID : " + addExpense.getExpenseId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/secured/getAllExpenses"})
	public EdgeResponse<List<Expense>> getAllExpenses( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Expense> allExpenses = expensesService.getAllExpenses(principal.getName());
			return EdgeResponse.createDataResponse(allExpenses, "");
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setExpensesService(ExpensesService expensesService) {
		this.expensesService = expensesService;
	}
}
