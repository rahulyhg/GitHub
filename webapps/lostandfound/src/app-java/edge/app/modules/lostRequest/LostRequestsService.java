package edge.app.modules.lostRequest;


public interface LostRequestsService {

	LostRequest saveLostRequest(LostRequest lostRequest);

	LostRequest getLostRequest(int lostRequestId);

}
