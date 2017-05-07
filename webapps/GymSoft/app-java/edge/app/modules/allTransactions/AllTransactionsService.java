package edge.app.modules.allTransactions;

import java.util.List;

import edge.app.modules.payments.Payment;

public interface AllTransactionsService {

	AllTransaction saveAllTransaction(AllTransaction parent, String loggedInId);

	List<AllTransaction> getAllTransactions(String loggedInId);

	AllTransaction addAllTransactionAsPerPayment(Payment payment, String loggedInId) throws Exception;

	AllTransaction approveAllTransaction(int allTransactionId, String name);

}
