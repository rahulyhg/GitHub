package edge.app.modules.cashTransactions;

import java.util.List;

import edge.app.modules.payments.Payment;

public interface CashTransactionsService {

	CashTransaction saveCashTransaction(CashTransaction parent, String loggedInId);

	List<CashTransaction> getCashTransactions(String loggedInId);

	CashTransaction addCashTransactionAsPerPayment(Payment payment, String loggedInId);

	CashTransaction approveCashTransaction(int cashTransactionId, String name);

}
