package edge.core.modules.mailSender;

public class EventDetails {

	private String subject;
	private String templatePath;	
	
	public EventDetails(String subject, String templatePath) {
		this.templatePath = templatePath;
		this.subject = subject;
	} 
	
	public String getSubject() {
		return subject;
	}
	public String getTemplatePath() {
		return templatePath;
	}
}
