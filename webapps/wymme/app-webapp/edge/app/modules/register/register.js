
var register = function register($scope, $http, registerForm){	

	if(registerForm.$invalid){
		alert("Some input values are missing or invalid. Marked with red text or '**' sign. Please correct them and submit the form again.");
		return;
	}
	
	startAjax('REGISTER', $scope);
	$http.post('server/unsecured/register.json', $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('REGISTER', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('REGISTER', $scope, data, status, headers, config);
    });
};