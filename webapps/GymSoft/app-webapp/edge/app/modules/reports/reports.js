
var initReports = function initReports($scope, $http){
	getMembershipsReport($scope, $http);
	getPaymentsReport($scope, $http);
	getAttendancesReport($scope, $http);
	getExpensesReport($scope, $http);
};

var getMembershipsReport = function getMembershipsReport($scope, $http){	
	startAjax('COLLECTIONS_REPORT', $scope);
	$http.post('server/secured/getMembershipsReport.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('COLLECTIONS_REPORT', $scope, data, status, headers, config);
    	$scope.es.membershipsReport = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('COLLECTIONS_REPORT', $scope, data, status, headers, config);
    });
};


var getPaymentsReport = function getPaymentsReport($scope, $http){	
	startAjax('COLLECTIONS_MODE_REPORT', $scope);
	$http.post('server/secured/getPaymentsReport.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('COLLECTIONS_MODE_REPORT', $scope, data, status, headers, config);
    	$scope.es.paymentsReport = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('COLLECTIONS_MODE_REPORT', $scope, data, status, headers, config);
    });
};


var getAttendancesReport = function getAttendancesReport($scope, $http){	
	startAjax('ATTEENDANCE_REPORT', $scope);
	$http.post('server/secured/getAttendancesReport.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('ATTEENDANCE_REPORT', $scope, data, status, headers, config);
    	$scope.es.attendancesReport = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('ATTEENDANCE_REPORT', $scope, data, status, headers, config);
    });
};

var getExpensesReport = function getExpensesReport($scope, $http){	
	startAjax('EXPENSE_REPORT', $scope);
	$http.post('server/secured/getExpensesReport.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('EXPENSE_REPORT', $scope, data, status, headers, config);
    	$scope.es.expensesReport = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('EXPENSE_REPORT', $scope, data, status, headers, config);
    });
};