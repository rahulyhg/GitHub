package edge.core.exception;

import java.util.List;

public class AppException extends RuntimeException{

	private Exception exception;
	private String customMessage;
	private List<String> errorMessages;
	private String customFooter;
	
	public AppException(Exception ex, String message) {
		super(message);
		this.exception = ex;
		customMessage = message;
	}
	
	public AppException(Exception ex, String message, String customFooter, List<String> errorMessages) {
		super(message);
		this.exception = ex;
		this.customFooter = customFooter;
		this.errorMessages = errorMessages;
		customMessage = message;
	}

	public String getCustomMessage() {
		String message = exception != null? customMessage + " : " + exception.getMessage() : customMessage;
		System.out.println(message);
		return customMessage ;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public String getCustomFooter() {
		return customFooter;
	}

	public void setCustomFooter(String customFooter) {
		this.customFooter = customFooter;
	}

	private static final long serialVersionUID = 1L;
	
}
