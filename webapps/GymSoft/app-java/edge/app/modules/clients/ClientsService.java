package edge.app.modules.clients;

import java.util.List;

import edge.app.modules.memberships.Membership;
import edge.app.modules.payments.Payment;

public interface ClientsService {

	Client saveClient(Client client, String loggedInId);

	List<Client> getAllClients(String loggedInId);

	Client updateClientAsPerMembership(Membership membership, String loggedInId, int parentId);

	Client updateClientAsPerPayment(Payment payment, String loggedInId, int parentId) throws Exception;

	Invoice generateInvoice(int clientId, String loggedInId) throws Exception;

	List<Client> getAllReminders(String loggedInId);

	NewClient saveAll(NewClient newClient, String loggedInId) throws Exception;

	Client saveReminder(Client client, String loggedInId) throws Exception;

	String uploadClientsFile(String loggedInId);

	String uploadMembershipsFile(String loggedInId);

	Client getClientByOldClientId(String oldClientId, int parentId);

	Client updateClientAsPerRejectedPayment(Payment payment, String loggedInId, int parentId) throws Exception;

}
