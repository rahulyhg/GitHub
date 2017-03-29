
var sendVerificationCode = function signUp($scope, $http){
	$scope.es.verificationCodeStatus="Sending Mail..."
	//startAjax('VERIFICATION_CODE', $scope);
	$http.post('server/unsecured/auth/sendVerificationCode.json', $scope.es.signInForm).
    success(function(data, status, headers, config) {
    	//handleAjaxSuccess('VERIFICATION_CODE', $scope, data, status, headers, config);
    	$scope.es.verificationCodeStatus=data.edgeResponse.responseData;
    }).
    error(function(data, status, headers, config) {
    	//handleAjaxError('VERIFICATION_CODE', $scope, data, status, headers, config);
    	$scope.es.verificationCodeStatus=data.edgeResponse.responseData;
    });
};

var resetPassword = function resetPassword($scope, $http){
	startAjax('FORGOT_PWD_POP_UP', $scope);
	$http.post('server/unsecured/auth/resetPassword.json', $scope.es.signInForm).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('FORGOT_PWD_POP_UP', $scope, data, status, headers, config);
    	$scope.es.closePopup('FORGOT_PWD_POP_UP');
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('FORGOT_PWD_POP_UP', $scope, data, status, headers, config);
    	$scope.es.closePopup('FORGOT_PWD_POP_UP');
    });
	};