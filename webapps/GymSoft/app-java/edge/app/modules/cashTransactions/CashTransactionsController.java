package edge.app.modules.cashTransactions;

import java.math.BigDecimal;
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
import edge.core.modules.parents.ParentsService;

@Controller
public class CashTransactionsController {

private static final Logger logger = LoggerFactory.getLogger(CashTransactionsController.class);
	
	@Autowired
	private CashTransactionsService cashTransactionsService;
	
	@Autowired
	private ParentsService parentsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public CashTransactionsService getCashTransactionsService() {
		return cashTransactionsService;
	}

	@RequestMapping(value={"/secured/saveCashTransaction"})
	public EdgeResponse<CashTransaction> addCashTransaction(
			@RequestBody CashTransaction cashTransactioni, Principal principal			
			) throws Exception{	
		try{
			CashTransaction addCashTransaction = cashTransactionsService.saveCashTransaction(cashTransactioni, principal.getName());
			return EdgeResponse.createDataResponse(addCashTransaction, "CashTransaction added Successfully with ID : " + addCashTransaction.getCashTransactionId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}

	@RequestMapping(value={"/secured/getCurrentDeskCashBalance"})
	public EdgeResponse<BigDecimal> getCurrentDeskCashBalance(
			Principal principal			
			) throws Exception{	
		try{
			return EdgeResponse.createDataResponse(parentsService.getParentData(principal.getName()).getDeskCashBalance(),"");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
		

	@RequestMapping(value={"/secured/approveCashTransaction"})
	public EdgeResponse<CashTransaction> approveCashTransaction(
			@RequestBody CashTransaction cashTransaction, Principal principal			
			) throws Exception{	
		try{
			CashTransaction approvedCashTransaction = cashTransactionsService.approveCashTransaction(cashTransaction.getCashTransactionId(), principal.getName());
			return EdgeResponse.createDataResponse(approvedCashTransaction, "Cash Transaction approved Successfully with ID : " + approvedCashTransaction.getCashTransactionId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/secured/getCashTransactions"})
	public EdgeResponse<List<CashTransaction>> getAllCashTransactions( 
			Principal principal			
			) throws Exception{	
		
		try{	
			List<CashTransaction> allCashTransactions = cashTransactionsService.getCashTransactions(principal.getName());
			return EdgeResponse.createDataResponse(allCashTransactions, "");
				
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setCashTransactionsService(CashTransactionsService cashTransactionsService) {
		this.cashTransactionsService = cashTransactionsService;
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
