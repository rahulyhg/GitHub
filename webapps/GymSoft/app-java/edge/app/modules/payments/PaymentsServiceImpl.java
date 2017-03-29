package edge.app.modules.payments;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.app.modules.clients.ClientsService;
import edge.app.modules.common.AppConstants;
import edge.app.modules.gyms.GymsService;
import edge.appCore.modules.auth.SecurityRoles;
import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class PaymentsServiceImpl implements PaymentsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Autowired
	private ClientsService clientsService;

	@Autowired
	private GymsService gymsService;

	@Override
	@Transactional
	public Payment savePayment(Payment payment, String loggedInId) {
		try{
			int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
			
			payment.setCreatedBy(loggedInId);
			payment.setUpdatedBy(loggedInId);
			payment.setSystemId(systemId);
			commonHibernateDao.save(payment);
			clientsService.updateClientAsPerPayment(payment, loggedInId, systemId);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return payment;
	}

	@Override
	public List<Payment> getAllPayments(String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_OPERATOR);
		return commonHibernateDao.getHibernateTemplate().find("from Payment where systemId = '" + systemId +"'");
	}

	public GymsService getGymsService() {
		return gymsService;
	}

	public void setGymsService(GymsService gymsService) {
		this.gymsService = gymsService;
	}

	@Override
	public Payment approvePayment(int paymentId, String loggedInId) {
		int systemId = gymsService.getSystemId(loggedInId, SecurityRoles.GYM_ADMIN);
		Payment payment = (Payment) commonHibernateDao.getHibernateTemplate().find("from Payment where systemId = '" + systemId +"' and paymentId = " + paymentId).get(0);
		payment.setStatus(AppConstants.EntityStatus.ACTIVE.getStatus());
		commonHibernateDao.update(payment);
		return payment;
	}
}
