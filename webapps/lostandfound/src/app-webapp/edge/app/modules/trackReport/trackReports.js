
var initFoundReports = function initFoundReports($scope, $http){
	
};

var searchMatchingReqAsPerLRI = function searchMatchingReqAsPerLRI($scope, $http){	
	startAjax('MATCHING_REPORT_LRI', $scope);

	var params = "?lostReportId=" + $scope.es.search.lostReportId + "&emailId=" + $scope.es.search.emailId;
	
	$http.post('server/unsecured/searchMatchingReqAsPerLRI.json' + params ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MATCHING_REPORT_LRI', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MATCHING_REPORT_LRI', $scope, data, status, headers, config);
    });
};

var searchMatchingReqAsPerLFI = function searchMatchingReqAsPerLFI($scope, $http){	
	startAjax('MATCHING_REPORT_LFI', $scope);
	
	var params = "?lostAndFoundId=" + $scope.es.search.lostAndFoundId + "&emailId=" + $scope.es.search.emailId;
	
	$http.post('server/unsecured/searchMatchingReqAsPerLFI.json' + params ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MATCHING_REPORT_LFI', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MATCHING_REPORT_LFI', $scope, data, status, headers, config);
    });
};
