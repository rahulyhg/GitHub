
var initTagCreations = function initTagCreations($scope, $http){
	$scope.es.tagCreation = {};
	$scope.es.loadTagCreations();
};

var saveTagCreation = function saveTagCreation($scope, $http){	
	startAjax('SAVE_TAG_CREATION', $scope);
	$http.post('server/unsecured/saveTagCreation.json', $scope.es.tagCreation ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SAVE_TAG_CREATION', $scope, data, status, headers, config);
    	//loadTagCreations($scope, $http);
    	$scope.es.tagCreation = {};
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SAVE_TAG_CREATION', $scope, data, status, headers, config);
    });
};


var sendOTPForTagUpdate = function sendOTPForTagUpdate($scope, $http){	
	startAjax('SEND_OTP_FOR_TAG_UPDATE', $scope);

	var params = "?emailId=" + $scope.es.tagUpdate.emailId;
	
	$http.post('server/unsecured/sendOTPForTagUpdate.json' + params ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SEND_OTP_FOR_TAG_UPDATE', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SEND_OTP_FOR_TAG_UPDATE', $scope, data, status, headers, config);
    });
};

var verifyOTPForTagUpdate = function sendOTPForTagUpdate($scope, $http){	
	startAjax('VERIFY_OTP_FOR_TAG_UPDATE', $scope);

	var params = "?emailId=" + $scope.es.tagUpdate.emailId + "&otp=" + $scope.es.tagUpdate.otp;
	
	$http.post('server/unsecured/verifyOTPForTagUpdate.json' + params ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('VERIFY_OTP_FOR_TAG_UPDATE', $scope, data, status, headers, config);
    	$scope.es.tagCreation = data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('VERIFY_OTP_FOR_TAG_UPDATE', $scope, data, status, headers, config);
    });
};
