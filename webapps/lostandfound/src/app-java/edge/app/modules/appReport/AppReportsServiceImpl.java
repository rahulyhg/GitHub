package edge.app.modules.appReport;

import java.util.Date;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edge.core.exception.AppException;
import edge.core.modules.common.CommonHibernateDao;

@WebService
@Component
public class AppReportsServiceImpl implements AppReportsService {

	@Autowired
	private CommonHibernateDao commonHibernateDao;

	@Override
	@Transactional
	public AppReport saveAppReport(AppReport appReport) {
		try{
			
			Date current = new Date();
			appReport.setUpdatedOn(current);
			
			commonHibernateDao.save(appReport);
			
		}catch(DataIntegrityViolationException ex){
			ex.printStackTrace();
			throw new AppException(ex, "Similar report already exists");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new AppException(ex, ex.getMessage());
		}
		return appReport;
	}

	@Override
	public AppReport getAppReport() {
		return (AppReport) commonHibernateDao.getHibernateTemplate().find(" from AppReport ").get(0);
	}

	
}
