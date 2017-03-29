var signUp = function signUp($scope, $http){
	$scope.es.closePopup('SIGN_UP_POP_UP');
	startAjax('SIGN_UP_POP_UP', $scope);
	$http.post('server/unsecured/auth/signUp.json', $scope.es.signUpForm ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SIGN_UP_POP_UP', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SIGN_UP_POP_UP', $scope, data, status, headers, config);
    });
};