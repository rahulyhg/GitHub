package edge.app.modules.attendance;

import java.util.List;

public interface AttendancesService {

	Attendance saveAttendance(Attendance gym, String loggedInId);

	List<Attendance> getAllAttendances(String loggedInId);

}
