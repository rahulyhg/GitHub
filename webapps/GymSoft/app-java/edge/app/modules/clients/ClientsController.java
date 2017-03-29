package edge.app.modules.clients;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edge.app.modules.memberships.MembershipsService;
import edge.app.modules.payments.PaymentsService;
import edge.core.config.CoreConstants;
import edge.core.exception.AppException;
import edge.core.modules.common.EdgeResponse;

@Controller
public class ClientsController {

private static final Logger logger = LoggerFactory.getLogger(ClientsController.class);

	@Autowired
	private ClientsService clientsService;
	
	@Autowired
	private MembershipsService membershipsService;
	
	@Autowired
	private PaymentsService paymentService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));        
    }
	
	public ClientsService getClientsService() {
		return clientsService;
	}
	
	@RequestMapping(value={"/secured/uploadProfilePic"})
	public EdgeResponse<String> uploadProfilePic(@RequestParam("fileUploadFile") MultipartFile file, 
			@RequestParam("fileUploadId") String fileUploadId,
			@RequestParam("fileUploadType") String fileUploadType) {
	
		try {
		
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			System.out.println(fileUploadId);
			System.out.println(fileUploadType);
			Path path = Paths.get("images/" + file.getOriginalFilename());
			Files.write(path, bytes);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EdgeResponse.createDataResponse("Success", " File uploaded Succcesfully " );
	}

	@RequestMapping(value={"/secured/saveReminder"})
	public EdgeResponse<Client> saveReminder(
			@RequestBody Client client, Principal principal		
			) throws Exception{	
		
		EdgeResponse<Client> retValue = null;
		try{
			
			clientsService.saveReminder(client, principal.getName());
			
			retValue = EdgeResponse.createDataResponse(client, 
					" Reminder Updated Successfully. Client ID : "  + client.getClientId()
					);
		}catch(AppException ex){
			retValue = EdgeResponse.createErrorResponse(client, ex.getMessage(), null, null);
		}
		return retValue;
	}
	
	@RequestMapping(value={"/secured/saveEnquiry"})
	public EdgeResponse<Client> saveEnquiry(
			@RequestBody Client client, Principal principal		
			) throws Exception{	
		
		EdgeResponse<Client> retValue = null;
		try{
			
			clientsService.saveClient(client, principal.getName());
			
			retValue = EdgeResponse.createDataResponse(client, 
					" Client / Enquiry Saved Successfully. Client ID : "  + client.getClientId()
					);
		}catch(AppException ex){
			retValue = EdgeResponse.createExceptionResponse(ex);
		}
		return retValue;
	}
	
	@RequestMapping(value={"/secured/saveClient"})
	public EdgeResponse<NewClient> addClient(
			@RequestBody NewClient newClient, Principal principal		
			) throws Exception{	
		
		EdgeResponse<NewClient> retValue = null;
		try{
			
			NewClient saveAll = clientsService.saveAll(newClient, principal.getName());
			retValue = EdgeResponse.createDataResponse(newClient, 
					" Save Successfull. Client ID : "  + saveAll.getClient().getClientId() + 
					" Membership ID : " + saveAll.getMembership().getMembershipId()  +
					" Payment ID : " + saveAll.getPayment().getPaymentId()
						
					);
		}catch(AppException ex){
			retValue = EdgeResponse.createExceptionResponse(ex);
		}
		return retValue;
	}


	@RequestMapping(value={"/secured/getAllReminders"})
	public EdgeResponse<List<Client>> getAllReminders( 
			Principal principal			
			) throws Exception{	
		try{
			List<Client> remindClients = clientsService.getAllReminders(principal.getName());
			return EdgeResponse.createDataResponse(remindClients, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/getAllClients"})
	public EdgeResponse<List<Client>> getAllClients( 
			Principal principal			
			) throws Exception{	
		try{
			List<Client> allClients = clientsService.getAllClients(principal.getName());
			return EdgeResponse.createDataResponse(allClients, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	@RequestMapping(value={"/secured/generateInvoice"})
	public EdgeResponse<Invoice> generateInvoice( 
			@RequestBody int clientId, Principal principal			
			) throws Exception{	
		try{
			Invoice invoice = clientsService.generateInvoice(clientId, principal.getName());
			return EdgeResponse.createDataResponse(invoice, "");
		}catch(AppException ae){
			return EdgeResponse.createExceptionResponse(ae);
		}
	}
	
	public void setClientsService(ClientsService clientsService) {
		this.clientsService = clientsService;
	}

}
