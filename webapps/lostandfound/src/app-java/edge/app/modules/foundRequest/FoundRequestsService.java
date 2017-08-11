package edge.app.modules.foundRequest;

import java.util.List;

import edge.app.modules.lostRequest.LostRequest;

public interface FoundRequestsService {

	FoundRequest saveFoundRequest(FoundRequest foundRequest);

	FoundRequest getFoundRequest(int foundRequestId);

	List<FoundRequest> searchMatchingRequests(int lostRequestId) throws Exception;
	
	List<FoundRequest> searchMatchingRequests(LostRequest lostRequest) throws Exception;

	List<LostRequest> searchMatchingRequests(FoundRequest foundRequest) throws Exception;

}
