package edge.app.modules.clients;

import edge.app.modules.memberships.Membership;
import edge.app.modules.payments.Payment;

public class NewClient {

	private Client client;
	private Membership membership;
	private Payment payment;
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Membership getMembership() {
		return membership;
	}
	public void setMembership(Membership membership) {
		this.membership = membership;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
}
