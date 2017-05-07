package edge.app.modules.allTransactions;

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
import edge.core.modules.parents.ParentData;
import edge.core.modules.parents.ParentsService;

@Controller
public class AllTransactionsController {

private static final Logger logger = LoggerFactory.getLogger(AllTransactionsController.class);
	
	@Autowired
	private AllTransactionsService allTransactionsService;
	
	@Autowired
	private ParentsService parentsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public AllTransactionsService getAllTransactionsService() {
		return allTransactionsService;
	}

	@RequestMapping(value={"/secured/saveAllTransaction"})
	public EdgeResponse<AllTransaction> addAllTransaction(
			@RequestBody AllTransaction allTransactioni, Principal principal			
			) throws Exception{	
		try{
			AllTransaction addAllTransaction = allTransactionsService.saveAllTransaction(allTransactioni, principal.getName());
			return EdgeResponse.createDataResponse(addAllTransaction, "AllTransaction added Successfully with ID : " + addAllTransaction.getAllTransactionId());
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}

	@RequestMapping(value={"/secured/getParentData"})
	public EdgeResponse<ParentData> getParentData(
			Principal principal			
			) throws Exception{	
		try{
			return EdgeResponse.createDataResponse(parentsService.getParentData(principal.getName()),"");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
		

	@RequestMapping(value={"/secured/approveAllTransaction"})
	public EdgeResponse<AllTransaction> approveAllTransaction(
			@RequestBody AllTransaction allTransaction, Principal principal			
			) throws Exception{	
		try{
			AllTransaction approvedAllTransaction = allTransactionsService.approveAllTransaction(allTransaction.getAllTransactionId(), principal.getName());
			return EdgeResponse.createDataResponse(approvedAllTransaction, "Cash Transaction approved Successfully with ID : " + approvedAllTransaction.getAllTransactionId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/secured/getAllTransactions"})
	public EdgeResponse<List<AllTransaction>> getAllAllTransactions( 
			Principal principal			
			) throws Exception{	
		
		try{	
			List<AllTransaction> allAllTransactions = allTransactionsService.getAllTransactions(principal.getName());
			return EdgeResponse.createDataResponse(allAllTransactions, "");
				
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setAllTransactionsService(AllTransactionsService allTransactionsService) {
		this.allTransactionsService = allTransactionsService;
	}

	public ParentsService getParentsService() {
		return parentsService;
	}

	public void setParentsService(ParentsService parentsService) {
		this.parentsService = parentsService;
	}
}
