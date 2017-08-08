package edge.app.modules.payments;

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

import edge.app.modules.common.AppConstants;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class PaymentsController {

private static final Logger logger = LoggerFactory.getLogger(PaymentsController.class);
	
	@Autowired
	private PaymentsService paymentsService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public PaymentsService getPaymentsService() {
		return paymentsService;
	}

	@RequestMapping(value={"/secured/approvePayment"})
	public EdgeResponse<Payment> approvePayment(
			@RequestBody Payment payment, Principal principal			
			) throws Exception{	
		try{
			Payment addPayment = paymentsService.approvePayment(payment.getPaymentId(), principal.getName());
			return EdgeResponse.createDataResponse(addPayment, "Payment approved Successfully with ID : " + addPayment.getPaymentId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}

	@RequestMapping(value={"/secured/rejectPayment"})
	public EdgeResponse<Payment> rejectPayment(
			@RequestBody Payment payment, Principal principal			
			) throws Exception{	
		try{
			Payment addPayment = paymentsService.rejectPayment(payment.getPaymentId(), payment.getRejectReason(), principal.getName());
			return EdgeResponse.createDataResponse(addPayment, "Payment #" + addPayment.getPaymentId() + " rejected Successfully" );
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/secured/savePayment"})
	public EdgeResponse<Payment> addPayment(
			@RequestBody Payment payment, Principal principal			
			) throws Exception{	
		try{
			Payment addPayment = paymentsService.savePayment(payment, principal.getName());
			return EdgeResponse.createDataResponse(addPayment, "Payment added Successfully with ID : " + addPayment.getPaymentId());
			
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
		
	}
	
	@RequestMapping(value={"/secured/getAllPayments"})
	public EdgeResponse<List<Payment>> getAllPayments( 
			Principal principal			
			) throws Exception{	
		try{	
			List<Payment> allPayments = paymentsService.getAllPayments(principal.getName());
			return EdgeResponse.createDataResponse(allPayments, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setPaymentsService(PaymentsService paymentsService) {
		this.paymentsService = paymentsService;
	}

}
