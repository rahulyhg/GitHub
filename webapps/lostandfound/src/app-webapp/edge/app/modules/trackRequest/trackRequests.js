
var initFoundRequests = function initFoundRequests($scope, $http){
	
};

var searchMatchingReqAsPerLRI = function searchMatchingReqAsPerLRI($scope, $http){	
	startAjax('MATCHING_REQUEST_LRI', $scope);
	$http.post('server/unsecured/searchMatchingReqAsPerLRI.json', $scope.es.search.lostRequestId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MATCHING_REQUEST_LRI', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MATCHING_REQUEST_LRI', $scope, data, status, headers, config);
    });
};

var searchMatchingReqAsPerLFI = function searchMatchingReqAsPerLFI($scope, $http){	
	startAjax('MATCHING_REQUEST_LFI', $scope);
	$http.post('server/unsecured/searchMatchingReqAsPerLFI.json', $scope.es.search.lostAndFoundId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MATCHING_REQUEST_LFI', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MATCHING_REQUEST_LFI', $scope, data, status, headers, config);
    });
};
