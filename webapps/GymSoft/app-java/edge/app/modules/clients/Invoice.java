package edge.app.modules.clients;

import java.util.Date;
import java.util.List;

import edge.app.modules.memberships.Membership;
import edge.app.modules.payments.Payment;
import edge.core.modules.parents.Parent;

public class Invoice {

	private Client client;
	private List<Membership> memberships;
	private List<Payment> payments;
	private Parent parent;
	private Date invoiceDate;
	
	public Invoice(Client client, List<Membership> memberships, List<Payment> payments, Parent parent) {
		this.client = client;
		this.memberships = memberships;
		this.payments = payments;
		this.parent = parent;
		this.setInvoiceDate(new Date());
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Membership> getMemberships() {
		return memberships;
	}
	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
	
}
