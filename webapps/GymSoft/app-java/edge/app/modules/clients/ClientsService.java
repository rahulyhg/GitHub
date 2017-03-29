package edge.app.modules.clients;

import java.util.List;

import edge.app.modules.memberships.Membership;
import edge.app.modules.payments.Payment;

public interface ClientsService {

	Client saveClient(Client client, String loggedInId);

	List<Client> getAllClients(String loggedInId);

	Client updateClientAsPerMembership(Membership membership, String loggedInId, int systemId);

	Client updateClientAsPerPayment(Payment payment, String loggedInId, int systemId) throws Exception;

	Invoice generateInvoice(int clientId, String loggedInId) throws Exception;

	List<Client> getAllReminders(String loggedInId);

	NewClient saveAll(NewClient newClient, String loggedInId) throws Exception;

	Client saveReminder(Client client, String loggedInId) throws Exception;

}
