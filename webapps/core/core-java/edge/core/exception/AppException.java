package edge.core.exception;

public class AppException extends RuntimeException{

	private Exception exception;
	private String customMessage;
	
	public AppException(Exception ex, String message) {
		super(message);
		this.exception = ex;
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

	private static final long serialVersionUID = 1L;
	
}
