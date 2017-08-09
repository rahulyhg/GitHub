package edge.app.modules.lost;


public interface LostRequestsService {

	LostRequest saveLostRequest(LostRequest lostRequest);

	LostRequest getLostRequest(int lostRequestId);

}
