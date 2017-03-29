/*var signIn = function signIn($scope, $http){
	$scope.es.closePopup('SIGN_IN_POP_UP');
	startAjax('SIGN_IN_POP_UP', $scope);
	//var params={'j_username' : $scope.es.signInForm.profileId, 'j_password' : $scope.es.signInForm.pwd };
	$http.post('server/unsecured/signIn.json', $scope.es.signInForm ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SIGN_IN_POP_UP', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SIGN_IN_POP_UP', $scope, data, status, headers, config);
    });
};*/