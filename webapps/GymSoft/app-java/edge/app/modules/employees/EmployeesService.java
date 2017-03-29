package edge.app.modules.employees;

import java.util.List;

public interface EmployeesService {

	Employee saveEmployee(Employee gym, String loggedInId);

	List<Employee> getAllEmployees(String loggedInId);

	List<Employee> getActiveEmployees(String loggedInId);

}
