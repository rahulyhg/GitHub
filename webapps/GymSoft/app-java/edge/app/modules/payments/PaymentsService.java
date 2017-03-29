package edge.app.modules.payments;

import java.util.List;

public interface PaymentsService {

	Payment savePayment(Payment gym, String loggedInId);

	List<Payment> getAllPayments(String loggedInId);

	Payment approvePayment(int paymentId, String loggedInId);

}
